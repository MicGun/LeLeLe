package com.hugh.lelele.login;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.source.LeLeLeRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoginPresenter implements LoginContract.Presenter {

    private LeLeLeRepository mLeLeLeRepository;
    private LoginContract.View mLoginView;

    public LoginPresenter(@NonNull LeLeLeRepository leLeLeRepository,
                          @NonNull LoginContract.View loginView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mLoginView = checkNotNull(loginView, "leleleRepository cannot be null!");
        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void hideToolbarAndBottomNavigation() {

    }

    @Override
    public void showToolbarAndBottomNavigation() {

    }
}
