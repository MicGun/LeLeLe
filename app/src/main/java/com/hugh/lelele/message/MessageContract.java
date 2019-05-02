package com.hugh.lelele.message;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;

public interface MessageContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void showBottomNavigation();

        void hideBottomNavigation();
    }
}
