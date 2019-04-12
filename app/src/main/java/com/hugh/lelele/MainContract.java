package com.hugh.lelele;

import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;
import java.util.HashMap;

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void updateNotifyBadgeUi(int amount);

        void setToolbarTitleUi(String title);

        void openHomeUi();

        void openApplicationUi();

        void openNotifyUi();

        void openProfileUi();

        void openElectricityUi(ArrayList<Electricity> electricityYearly);

        void openElectricityEditorUi(ArrayList<Room> rooms);

        void showDrawerUserUi();

        void closeDrawerUi();

        void hideBottomNavigationUi();

        void showBottomNavigationUi();

        void openLoginUi();

        void hideToolbarUi();

        void showToolbarUi();

        void setUserTypeForView(int userTypeForView);

        void openGroupListUi(ArrayList<Group> groups);

        void openGroupDetailsUi(Group group);

    }

    interface Presenter extends BasePresenter {

        void updateNotifyBadge();

        void updateToolbar(String title);

        void openHome();

        void openApplication();

        void openNotify();

        void openProfile();

        void onDrawerOpened();

        void hideBottomNavigation();

        void showBottomNavigation();

        void openLogin();

        void hideToolbarAndBottomNavigation();

        void showToolbarAndBottomNavigation();
    }
}
