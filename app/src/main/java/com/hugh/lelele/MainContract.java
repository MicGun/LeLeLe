package com.hugh.lelele;

public interface MainContract {

    interface View extends BaseView<Presenter> {

        void updateNotifyBadgeUi(int amount);

        void setToolbarTitleUi(String title);

        void openHomeUi();

        void openApplicationUi();

        void openNotifyUi();

        void openProfileUi();

    }

    interface Presenter extends BasePresenter {

        void updateNotifyBadge();

        void updateToolbar(String title);

        void openHome();

        void openApplication();

        void openNotify();

        void openProfile();
    }
}
