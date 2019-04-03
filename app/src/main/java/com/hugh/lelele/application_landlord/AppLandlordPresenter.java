package com.hugh.lelele.application_landlord;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.LeLeLeRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class AppLandlordPresenter implements AppLandlordContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final AppLandlordContract.View mAppLandlordView;

    public AppLandlordPresenter(@NonNull LeLeLeRepository leLeLeRepository,
                                @NonNull AppLandlordContract.View appLandlordView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mAppLandlordView = checkNotNull(appLandlordView, "appLandlordView cannot be null!");
        mAppLandlordView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
