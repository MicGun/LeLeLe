package com.hugh.lelele.profile_landlord;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Landlord;

public interface ProfileLandlordContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void updateLandlordProfile(Landlord landlord);
    }
}
