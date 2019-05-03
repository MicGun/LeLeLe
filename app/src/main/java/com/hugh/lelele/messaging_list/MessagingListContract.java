package com.hugh.lelele.messaging_list;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Message;
import com.hugh.lelele.data.Tenant;

import java.util.ArrayList;

public interface MessagingListContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
    }
}
