package com.hugh.lelele.login;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hugh.lelele.Constants;

import java.util.Arrays;
import java.util.concurrent.Executor;

public class LoginManager {

    private final static String SIGN_IN_FAILURE = "signInWithCredential:failure";

    private CallbackManager mFbCallbackManager;

    private static FirebaseAuth mAuth;

    private static class UserManagerHolder {
        private static final LoginManager INSTANCE = new LoginManager();
    }

    public static LoginManager getInstance() {
        mAuth = FirebaseAuth.getInstance();
        return UserManagerHolder.INSTANCE;
    }

    public void loginStylishByFacebook(Context context, final LoadCallback loadCallback) {

        mFbCallbackManager = CallbackManager.Factory.create();
        com.facebook.login.LoginManager.getInstance().registerCallback(mFbCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.d(Constants.TAG, "FB Login Success");
                Log.i(Constants.TAG, "loginResult.getAccessToken().getToken() = " + loginResult.getAccessToken().getToken());
                Log.i(Constants.TAG, "loginResult.getAccessToken().getUserId() = " + loginResult.getAccessToken().getUserId());
                Log.i(Constants.TAG, "loginResult.getAccessToken().getApplicationId() = " + loginResult.getAccessToken().getApplicationId());

                handleFacebookAccessToken(loginResult.getAccessToken(), loadCallback);
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

        loginFacebook(context);
    }

    private void loginFacebook(Context context) {

        com.facebook.login.LoginManager.getInstance().logInWithReadPermissions(
                (Activity) context, Arrays.asList("email", "name", "profile_picture"));
    }

    private void handleFacebookAccessToken(AccessToken token, final LoadCallback loadCallback) {
        Log.d(Constants.TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(Constants.TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            loadCallback.onSuccess();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(Constants.TAG, "signInWithCredential:failure", task.getException());
                            loadCallback.onFail(SIGN_IN_FAILURE);
                        }

                        // ...
                    }
                });
    }

    public interface LoadCallback {

        void onSuccess();

        void onFail(String errorMessage);

        void onInvalidToken(String errorMessage);
    }
}
