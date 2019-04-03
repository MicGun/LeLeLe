package com.hugh.lelele.application_tenant;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.LeLeLeRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class AppTenantPresenter implements AppTenantContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final AppTenantContract.View mAppTenantView;

    public AppTenantPresenter(@NonNull LeLeLeRepository leLeLeRepository,
                              @NonNull AppTenantContract.View appTenantView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mAppTenantView = checkNotNull(appTenantView, "appTenantView cannot be null!");
        mAppTenantView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
