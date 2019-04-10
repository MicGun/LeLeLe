package com.hugh.lelele.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
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

                Log.d(Constants.TAG, "FB Login Success");
                Log.i(Constants.TAG, "loginResult.getAccessToken().getToken() = " + loginResult.getAccessToken().getToken());
                Log.i(Constants.TAG, "loginResult.getAccessToken().getUserId() = " + loginResult.getAccessToken().getUserId());
                Log.i(Constants.TAG, "loginResult.getAccessToken().getApplicationId() = " + loginResult.getAccessToken().getApplicationId());

//                loginStylish(loginResult.getAccessToken().getToken(), loadCallback);
            }

            @Override
            public void onCancel() {

                Log.d(Constants.TAG, "FB Login Cancel");
                loadCallback.onFail("FB Login Cancel");
            }

            @Override
            public void onError(FacebookException exception) {

                Log.d(Constants.TAG, "FB Login Error");
                loadCallback.onFail("FB Login Error: " + exception.getMessage());
            }
        });
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

    interface LoadUserProfileCallback {

        void onSuccess();

        void onFail(String errorMessage);

        void onInvalidToken(String errorMessage);
    }
}
