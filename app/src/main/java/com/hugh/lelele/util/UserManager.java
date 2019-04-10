package com.hugh.lelele.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.loco_data.UserDataDAO;
import com.hugh.lelele.data.loco_data.UserDatabase;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRemoteDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;

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
    UserDatabase mUserDatabase;
    UserDataDAO mUserDataDAO;

    private static class UserManagerHolder {
        private static final UserManager INSTANCE = new UserManager();
    }

    private UserManager() {
        mLeLeLeRepository = LeLeLeRepository.getInstance(LeLeLeRemoteDataSource.getInstance());

        mUserDatabase = android.arch.persistence.room.Room
                .databaseBuilder(LeLeLe.getAppContext(), UserDatabase.class, "userDataBase")
                .allowMainThreadQueries()
                .build();
        mUserDataDAO = mUserDatabase.getUserDAO();
    }

    public void loginByFacebook(Context context, final LoadUserProfileCallback loadCallback) {

        mFbCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mFbCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                loadCallback.onSuccess();
                Log.d(Constants.TAG, "FB Login Success");
                Log.i(Constants.TAG, "loginResult.getAccessToken().getToken() = " + loginResult.getAccessToken().getToken());
                Log.i(Constants.TAG, "loginResult.getAccessToken().getUserId() = " + loginResult.getAccessToken().getUserId());
                Log.i(Constants.TAG, "loginResult.getAccessToken().getApplicationId() = " + loginResult.getAccessToken().getApplicationId());

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
//
//                request.executeAsync();

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

    void getLandlordProfile(String email, final LoadUserProfileCallback callback) {
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

    void getTenantProfile(String email, final LoadUserProfileCallback callback) {
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

    public boolean isLoggedIn() {
        return mUserDataDAO.getItems().size() != 0;
    }

    public CallbackManager getFbCallbackManager() {
        return mFbCallbackManager;
    }

    public interface LoadUserProfileCallback {

        void onSuccess();

        void onFail(String errorMessage);

        void onInvalidToken(String errorMessage);
    }
}
