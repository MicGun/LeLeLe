package com.hugh.lelele.login;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void setupLoginSuccessEnvironment();

    }

    interface Presenter extends BasePresenter {

        void hideToolbarAndBottomNavigation();

        void showToolbarAndBottomNavigation();

        void setUserType(int selectedUserType);

        void openHome();

        void loadGroupListDrawerMenu();

        void resetDrawer();

        void loadNotificationsForBadge();
    }
}
