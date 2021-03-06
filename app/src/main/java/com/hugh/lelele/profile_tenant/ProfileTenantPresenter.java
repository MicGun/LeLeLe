package com.hugh.lelele.profile_tenant;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.UserManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProfileTenantPresenter implements ProfileTenantContract.Presenter {

    private LeLeLeRepository mLeLeLeRepository;
    private ProfileTenantContract.View mProfileTenantView;

    public ProfileTenantPresenter(@NonNull LeLeLeRepository leLeLeRepository,
                                  @NonNull ProfileTenantContract.View profileTenantView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mProfileTenantView = checkNotNull(profileTenantView, "profileTenantView cannot be null!");
    }

    @Override
    public void start() {

    }

    @Override
    public void updateTenantProfile(Tenant tenant) {
        mLeLeLeRepository.uploadTenant(tenant);
        UserManager.getInstance().refreshUserEnvironment();
    }
}
