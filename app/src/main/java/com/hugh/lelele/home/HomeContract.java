package com.hugh.lelele.home;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;

public interface HomeContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void loadLandlord(String email);
    }
}
