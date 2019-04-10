package com.hugh.lelele.util;

import android.util.Log;

import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRemoteDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;

public class UserManager {

    private final String TAG = UserManager.class.getSimpleName();

    private LeLeLeRepository mLeLeLeRepository;
    private Landlord mLandlord;
    private Tenant mTenant;

    private static class UserManagerHolder {
        private static final UserManager INSTANCE = new UserManager();
    }

    private UserManager() {
        mLeLeLeRepository = LeLeLeRepository.getInstance(LeLeLeRemoteDataSource.getInstance());
    }

    public static UserManager getInstance() {
        return UserManagerHolder.INSTANCE;
    }

    void getLandlordProfile(String email, final LoadUserProfileCallback callback) {
        mLeLeLeRepository.getLandlordProfile(email, new LeLeLeDataSource.GetLandlordProfileCallback() {
            @Override
            public void onCompleted(Landlord landlord) {

                setLandlord(landlord);

                callback.onSuccess();
            }

            @Override
            public void onError(String errorMessage) {

                Log.d(TAG, errorMessage);
                callback.onFail(errorMessage);
            }
        });
    }

    void getTenantProfile(String email, final LoadUserProfileCallback callback) {
        mLeLeLeRepository.getTenantProfile(email, new LeLeLeDataSource.GetTenantProfileCallback() {
            @Override
            public void onCompleted(Tenant tenant) {

                setTenant(tenant);

                callback.onSuccess();
            }

            @Override
            public void onError(String errorMessage) {

                Log.d(TAG, errorMessage);
                callback.onFail(errorMessage);
            }
        });
    }

    public Landlord getLandlord() {
        return mLandlord;
    }

    public void setLandlord(Landlord landlord) {
        mLandlord = landlord;
    }

    public Tenant getTenant() {
        return mTenant;
    }

    public void setTenant(Tenant tenant) {
        mTenant = tenant;
    }

    interface LoadUserProfileCallback {

        void onSuccess();

        void onFail(String errorMessage);

        void onInvalidToken(String errorMessage);
    }
}
