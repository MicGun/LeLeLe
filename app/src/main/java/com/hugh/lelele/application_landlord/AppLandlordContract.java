package com.hugh.lelele.application_landlord;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;

public interface AppLandlordContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void loadRoomList();

    }
}
