package com.hugh.lelele.electricity_tenant;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.LeLeLeRepository;

import java.util.ArrayList;
import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;

public class ElectricityTenantPresenter implements ElectricityTenantContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final ElectricityTenantContract.View mElectricityTenantView;

    public ElectricityTenantPresenter(@NonNull LeLeLeRepository leLeLeRepository,
                                      @NonNull ElectricityTenantContract.View electricityTenantView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mElectricityTenantView = checkNotNull(electricityTenantView, "electricityTenantView cannot be null!");
        mElectricityTenantView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void updateToolbar(String title) {

    }

    @Override
    public void showBottomNavigation() {

    }

    @Override
    public void hideBottomNavigation() {

    }

    @Override
    public void setElectricityData(ArrayList<Electricity> electricityYearly) {
        mElectricityTenantView.showElectricityUi(electricityYearly);
    }
}
