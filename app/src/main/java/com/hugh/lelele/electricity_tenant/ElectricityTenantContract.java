package com.hugh.lelele.electricity_tenant;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Electricity;

import java.util.ArrayList;
import java.util.HashMap;

public interface ElectricityTenantContract {

    interface View extends BaseView<Presenter> {

        void showElectricityUi(ArrayList<Electricity> electricityYearly);
    }

    interface Presenter extends BasePresenter {

        void updateToolbar(String title);

        void showBottomNavigation();

        void hideBottomNavigation();

        void setElectricityData(ArrayList<Electricity> electricityYearly);

    }
}
