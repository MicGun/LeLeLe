package com.hugh.lelele;

import com.hugh.lelele.data.Electricity;

import java.util.HashMap;

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void updateNotifyBadgeUi(int amount);

        void setToolbarTitleUi(String title);

        void openHomeUi();

        void openApplicationUi();

        void openNotifyUi();

        void openProfileUi();

        void openElectricityUi(HashMap<String, Electricity> electricityYearly);

        void showDrawerUserUi();

    }

    interface Presenter extends BasePresenter {

        void updateNotifyBadge();

        void updateToolbar(String title);

        void openHome();

        void openApplication();

        void openNotify();

        void openProfile();

        void onDrawerOpened();
    }
}
