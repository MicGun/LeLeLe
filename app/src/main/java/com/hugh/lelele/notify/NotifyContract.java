package com.hugh.lelele.notify;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;

public interface NotifyContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
    }
}
