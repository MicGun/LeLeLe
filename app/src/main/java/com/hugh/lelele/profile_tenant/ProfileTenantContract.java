package com.hugh.lelele.profile_tenant;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Tenant;

public interface ProfileTenantContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void updateTenantProfile(Tenant tenant);
    }
}
