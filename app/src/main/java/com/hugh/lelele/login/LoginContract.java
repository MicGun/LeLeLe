package com.hugh.lelele.login;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void hideToolbarAndBottomNavigation();

        void showToolbarAndBottomNavigation();
    }
}
