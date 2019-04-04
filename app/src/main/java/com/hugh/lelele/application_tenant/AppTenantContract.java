package com.hugh.lelele.application_tenant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Electricity;

import java.util.ArrayList;
import java.util.HashMap;

public interface AppTenantContract {

    interface View extends BaseView<Presenter> {

        void showElectricityUi(ArrayList<Electricity> electricityYearly);
    }

    interface Presenter extends BasePresenter {

        void loadElectricityData();

        void openElectricity(ArrayList<Electricity> electricityYearly);
    }
}
