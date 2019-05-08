package com.hugh.lelele.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.hugh.lelele.data.Article;
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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

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

                                loginLeLeLe(object, new LoginLeLeLeCallback() {
                                    @Override
                                    public void onSuccess() {
                                        loadCallback.onSuccess();
                                    }

                                    @Override
                                    public void onError(String errorMessage) {
                                        loadCallback.onFail(errorMessage);
                                    }
                                });


                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
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

    private void loginLeLeLe(JSONObject object, final LoginLeLeLeCallback callback) {


        mUserData = LeLeLeParser.parseUserData(object, mUserData);
//        mUserData = userData;
        if (mUserDataDAO.getUserById(mUserData.getId()) == null) {
            mUserDataDAO.insert(mUserData);
        }

        if (mUserType == R.string.landlord) {
            loginAsALandlord(mUserData.getEmail(), new LoginLeLeLeCallback() {
                @Override
                public void onSuccess() {
                    callback.onSuccess();
                }

                @Override
                public void onError(String errorMessage) {
                    callback.onError(errorMessage);
                }
            });
        } else {
            loginAsATenant(mUserData.getEmail(), new LoginLeLeLeCallback() {
                @Override
                public void onSuccess() {
                    callback.onSuccess();
                }

                @Override
                public void onError(String errorMessage) {
                    callback.onError(errorMessage);
                }
            });
        }
    }

    private void loginAsALandlord(String email, final LoginLeLeLeCallback callback) {
        mLeLeLeRepository.updateLandlordUser(email, new LeLeLeDataSource.LandlordUserCallback() {
            @Override
            public void onCompleted(Landlord landlord) {
                mLandlord = landlord;
                callback.onSuccess();
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    private void loginAsATenant(String email, final LoginLeLeLeCallback callback) {
        mLeLeLeRepository.updateTenantUser(email, new LeLeLeDataSource.TenantUserCallback() {
            @Override
            public void onCompleted(Tenant tenant) {
                mTenant = tenant;
                callback.onSuccess();
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
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

    public void setupUserEnvironment(final EnvironmentSetupCallback callback) {

        mUserData = mUserDataDAO.getItems().get(0);

        int userType = mUserData.getUserType();
        Log.v(TAG, "UserType" + mUserData.getUserType());
        if (mUserData.getUserType() == R.string.landlord) {
            //每次都要確定有設定UserType才不會造成系統作業混亂
            setUserType(R.string.landlord);
            getLandlordProfile(mUserData.getEmail(), new LoadUserProfileCallback() {
                @Override
                public void onSuccess() {
                    Log.v(TAG, "房東");
                    callback.onSuccess();
                }

                @Override
                public void onFail(String errorMessage) {
                    callback.onError(errorMessage);
                }

                @Override
                public void onInvalidToken(String errorMessage) {

                }
            });
        } else {
            //每次都要確定有設定UserType才不會造成系統作業混亂
            setUserType(R.string.tenant);
            getTenantProfile(mUserData.getEmail(), new LoadUserProfileCallback() {
                @Override
                public void onSuccess() {
                    Log.v(TAG, "房客");
                    callback.onSuccess();
                }

                @Override
                public void onFail(String errorMessage) {
                    callback.onError(errorMessage);
                }

                @Override
                public void onInvalidToken(String errorMessage) {

                }
            });
        }


    }

    public void refreshUserEnvironment() {

        mUserData = mUserDataDAO.getItems().get(0);

        int userType = mUserData.getUserType();
        Log.v(TAG, "UserType" + mUserData.getUserType());
        if (mUserData.getUserType() == R.string.landlord) {
            getLandlordProfile(mUserData.getEmail(), new LoadUserProfileCallback() {
                @Override
                public void onSuccess() {
                    Log.v(TAG, "房東");
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
                    Log.v(TAG, "房客");
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

                if (tenant.isBinding()) {
                    mUserData.setGroupNow(tenant.getGroup());
                }

                callback.onSuccess();
            }

            @Override
            public void onError(String errorMessage) {

                Log.d(TAG, errorMessage);
                callback.onFail(errorMessage);
            }
        });
    }

    public void checkProfileCompleted() {

        if (!isProfileCompleted()) {
            Article article = getProfileFillingRequest();
            if (mUserType == R.string.landlord) {
                mLeLeLeRepository.sendLandlordArticle(article, mUserData.getEmail());
            } else {
                mLeLeLeRepository.sendTenantArticle(article, mUserData.getEmail());
            }
        }

    }

    private Article getProfileFillingRequest() {
        Article article = new Article();

        article.setTitle(LeLeLe.getAppContext().getString(R.string.profile_filling_title));
        article.setContent(LeLeLe.getAppContext().getString(R.string.profile_filling_request, mUserData.getName()));
        article.setType(com.hugh.lelele.util.Constants.SYSTEM);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        article.setTime(String.valueOf(formatter.format(Calendar.getInstance().getTime())));

        return article;
    }

    private boolean isProfileCompleted() {

        if (mUserType == R.string.landlord) {
            if (mLandlord.getAddress().equals("") ||
                    mLandlord.getPhoneNumber().equals("") ||
                    mLandlord.getIdCardNumber().equals("")) {
                return false;
            } else {
                return true;
            }
        } else if (mUserType == R.string.tenant) {
            if (mTenant.getAddress().equals("") ||
                    mTenant.getPhoneNumber().equals("") ||
                    mTenant.getIdCardNumber().equals("")) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    private void clearUserData() {
        mUserDataDAO.nukeTable();
        mUserData = new UserData();
    }

    public void logout(@NonNull LogoutCallback callback) {
        if (mUserType == R.string.landlord) {
            mLandlord = null;
        } else {
            mTenant = null;
        }

        mUserDataDAO.nukeTable();
        mUserData = new UserData();

        callback.onSuccess();
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

    public interface LogoutCallback {

        void onSuccess();
    }

    public interface LoginLeLeLeCallback {
        void onSuccess();

        void onError(String errorMessage);
    }

    public interface EnvironmentSetupCallback {
        void onSuccess();

        void onError(String errorMessage);
    }
}
