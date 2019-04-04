package com.hugh.lelele.electricity_landlord;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.source.LeLeLeRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class ElectricityLandlordPresenter implements ElectricityLandlordContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final ElectricityLandlordContract.View mElectricityLandlordtView;

    public ElectricityLandlordPresenter(@NonNull LeLeLeRepository leLeLeRepository,
                                      @NonNull ElectricityLandlordContract.View electricityLandlordView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mElectricityLandlordtView = checkNotNull(electricityLandlordView, "electricityTenantView cannot be null!");
        mElectricityLandlordtView.setPresenter(this);
    }
    @Override
    public void start() {

    }
}
