package com.hugh.lelele.electricity_tenant;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;

public interface ElectricityTenantContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void updateToolbar(String title);

        void showBottomNavigation();

        void hideBottomNavigation();

    }
}
