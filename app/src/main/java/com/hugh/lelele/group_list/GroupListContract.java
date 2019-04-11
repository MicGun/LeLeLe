package com.hugh.lelele.group_list;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.home.HomeContract;

public interface GroupListContract {

    interface View extends BaseView<HomeContract.Presenter> {

    }

    interface Presenter extends BasePresenter {
    }
}
