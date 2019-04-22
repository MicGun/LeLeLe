package com.hugh.lelele.post;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;

public interface PostContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void hideBottomNavigation();

        void showBottomNavigation();
    }
}
