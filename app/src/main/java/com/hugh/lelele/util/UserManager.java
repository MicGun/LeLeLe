package com.hugh.lelele.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.hugh.lelele.Constants;
import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.loco_data.UserData;
import com.hugh.lelele.data.loco_data.UserDataDAO;
import com.hugh.lelele.data.loco_data.UserDatabase;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeParser;
import com.hugh.lelele.data.source.LeLeLeRemoteDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class UserManager {

    private final String TAG = UserManager.class.getSimpleName();

    private CallbackManager mFbCallbackManager;
    private LeLeLeRepository mLeLeLeRepository;
    private Landlord mLandlord;
    private Tenant mTenant;
    private UserDataDAO mUserDataDAO;
    private int mUserType;
    private UserData mUserData;

    private static class UserManagerHolder {
        private static final UserManager INSTANCE = new UserManager();
    }

    private UserManager() {
        mLeLeLeRepository = LeLeLeRepository.getInstance(LeLeLeRemoteDataSource.getInstance());

        UserDatabase userDatabase = android.arch.persistence.room.Room
                .databaseBuilder(LeLeLe.getAppContext(), UserDatabase.class, "userDataBase")
                .allowMainThreadQueries()
                .build();
        mUserDataDAO = userDatabase.getUserDAO();
        mUserData = new UserData();
    }

    public void loginByFacebook(Context context, final LoadUserProfileCallback loadCallback) {

        mFbCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mFbCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                loadCallback.onSuccess();
                mUserData.setAssessToken(loginResult.getAccessToken().getToken());
                mUserData.setId(loginResult.getAccessToken().getUserId());
                String pictureUrl = "https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?type=large";
                mUserData.setPictureUrl(pictureUrl);
                mUserData.setUserType(mUserType);
                Log.d(Constants.TAG, "FB Login Success");
                Log.i(Constants.TAG, "loginResult.getAccessToken().getToken() = " + loginResult.getAccessToken().getToken());
                Log.i(Constants.TAG, "loginResult.getAccessToken().getUserId() = " + loginResult.getAccessToken().getUserId());
                Log.i(Constants.TAG, "loginResult.getAccessToken().getApplicationId() = " + loginResult.getAccessToken().getApplicationId());

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                loginLeLeLe(object);

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

//                AccessToken accessToken =  loginResult.getAccessToken();
//                GraphRequest request = GraphRequest.newGraphPathRequest(
//                        accessToken,
//                        "/" + loginResult.getAccessToken().getUserId() + "/picture",
//                        new GraphRequest.Callback() {
//                            @Override
//                            public void onCompleted(GraphResponse response) {
//                                Log.d(Constants.TAG, "FB Picture" + response.getJSONArray());
//                                // Insert your code here
//                            }
//                        });

                request.executeAsync();

//                try {
//                    Bitmap bitmap = getFacebookProfilePicture(loginResult.getAccessToken().getUserId());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

//                loginStylish(loginResult.getAccessToken().getToken(), loadCallback);
            }

            @Override
            public void onCancel() {

                Log.d("Cancel", "FB Login Cancel");
                loadCallback.onFail("FB Login Cancel");
            }

            @Override
            public void onError(FacebookException exception) {

                Log.d("Error", "FB Login Error");
                loadCallback.onFail("FB Login Error: " + exception.getMessage());
            }
        });
        loginFacebook(context);
    }

    public static Bitmap getFacebookProfilePicture(String userId) throws IOException {
        URL imageURL = null;
        try {
            imageURL = new URL("https://graph.facebook.com/" + userId + "/picture?type=large");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

        return bitmap;
    }

    private void loginLeLeLe(JSONObject object) {


        mUserData = LeLeLeParser.parseUserData(object, mUserData);
//        mUserData = userData;
        if (mUserDataDAO.getUserById(mUserData.getId()) == null) {
            mUserDataDAO.insert(mUserData);
        }

        if (mUserType == R.string.landlord) {
            loginAsALandlord(mUserData.getEmail());
        } else {
            loginAsATenant(mUserData.getEmail());
        }
    }

    private void loginAsALandlord(String email) {
        mLeLeLeRepository.updateLandlordUser(email, new LeLeLeDataSource.LandlordUserCallback() {
            @Override
            public void onCompleted(Landlord landlord) {
                mLandlord = landlord;
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private void loginAsATenant(String email) {

    }

//    private void loginAsALandlord();

    /**
     * Login Stylish by Facebook: Step 2. Login Facebook
     */
    private void loginFacebook(Context context) {

        LoginManager.getInstance().logInWithReadPermissions(
                (Activity) context, Arrays.asList("email"));
    }

    public static UserManager getInstance() {
        return UserManagerHolder.INSTANCE;
    }

    public void setupUserEnvironment() {

        mUserData = mUserDataDAO.getItems().get(0);

        if (mUserData.getUserType() == R.string.landlord) {
            getLandlordProfile(mUserData.getEmail(), new LoadUserProfileCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFail(String errorMessage) {

                }

                @Override
                public void onInvalidToken(String errorMessage) {

                }
            });
        } else {
            getTenantProfile(mUserData.getEmail(), new LoadUserProfileCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFail(String errorMessage) {

                }

                @Override
                public void onInvalidToken(String errorMessage) {

                }
            });
        }


    }

    private void getLandlordProfile(String email, final LoadUserProfileCallback callback) {
        mLeLeLeRepository.getLandlordProfile(email, new LeLeLeDataSource.GetLandlordProfileCallback() {
            @Override
            public void onCompleted(Landlord landlord) {

                setLandlord(landlord);

                callback.onSuccess();
            }

            @Override
            public void onError(String errorMessage) {

                Log.d(TAG, errorMessage);
                callback.onFail(errorMessage);
            }
        });
    }

    private void getTenantProfile(String email, final LoadUserProfileCallback callback) {
        mLeLeLeRepository.getTenantProfile(email, new LeLeLeDataSource.GetTenantProfileCallback() {
            @Override
            public void onCompleted(Tenant tenant) {

                setTenant(tenant);

                callback.onSuccess();
            }

            @Override
            public void onError(String errorMessage) {

                Log.d(TAG, errorMessage);
                callback.onFail(errorMessage);
            }
        });
    }

    public Landlord getLandlord() {
        return mLandlord;
    }

    public void setLandlord(Landlord landlord) {
        mLandlord = landlord;
    }

    public Tenant getTenant() {
        return mTenant;
    }

    public void setTenant(Tenant tenant) {
        mTenant = tenant;
    }

    public int getUserType() {
        return mUserType;
    }

    public void setUserType(int userType) {
        mUserType = userType;
    }

    public boolean isLoggedIn() {
        return mUserDataDAO.getItems().size() != 0;
    }

    public CallbackManager getFbCallbackManager() {
        return mFbCallbackManager;
    }

    public UserData getUserData() {
        return mUserData;
    }

    public void setUserData(UserData userData) {
        mUserData = userData;
    }

    public interface LoadUserProfileCallback {

        void onSuccess();

        void onFail(String errorMessage);

        void onInvalidToken(String errorMessage);
    }
}
