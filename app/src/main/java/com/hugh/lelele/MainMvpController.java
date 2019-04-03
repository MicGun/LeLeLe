package com.hugh.lelele;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.hugh.lelele.application_landlord.AppLandlordFragment;
import com.hugh.lelele.application_landlord.AppLandlordPresenter;
import com.hugh.lelele.application_tenant.AppTenantFragment;
import com.hugh.lelele.application_tenant.AppTenantPresenter;
import com.hugh.lelele.data.LeLeLeRemoteDataSource;
import com.hugh.lelele.data.LeLeLeRepository;
import com.hugh.lelele.home.HomeFragment;
import com.hugh.lelele.home.HomePresenter;
import com.hugh.lelele.notify.NotifyFragment;
import com.hugh.lelele.notify.NotifyPresenter;
import com.hugh.lelele.profile_landlord.ProfileLandlordFragment;
import com.hugh.lelele.profile_landlord.ProfileLandlordPresenter;
import com.hugh.lelele.profile_tenant.ProfileTenantFragment;
import com.hugh.lelele.profile_tenant.ProfileTenantPresenter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainMvpController {

    private final FragmentActivity mActivity;
    private MainPresenter mMainPresenter;

    private HomePresenter mHomePresenter;
    private AppTenantPresenter mAppTenantPresenter;
    private AppLandlordPresenter mAppLandlordPresenter;
    private NotifyPresenter mNotifyPresenter;
    private ProfileTenantPresenter mProfileTenantPresenter;
    private ProfileLandlordPresenter mProfileLandlordPresenter;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            HOME, APPLICATION_TENANT, APPLICATION_LANDLORD, NOTIFY, PROFILE
    })
    public @interface FragmentType {}
    static final String HOME    = "HOME";
    static final String APPLICATION_TENANT = "APPLICATION_TENANT";
    static final String APPLICATION_LANDLORD = "APPLICATION_LANDLORD";
    static final String NOTIFY    = "NOTIFY";
    static final String PROFILE = "PROFILE";

    private MainMvpController(@NonNull FragmentActivity activity) {
        mActivity = activity;
    }

    /**
     * Creates a controller.
     * @param activity the context activity
     * @return a MainMvpController
     */
    static MainMvpController create(@NonNull FragmentActivity activity) {
        checkNotNull(activity);
        MainMvpController mainMvpController = new MainMvpController(activity);
        mainMvpController.createMainPresenter();
        return mainMvpController;
    }

    /*
    * Home View
    * */
    void findOrCreateHomeView() {

        HomeFragment homeFragment = findOrCreateHomeFragment();

        if (mHomePresenter == null) {
            mHomePresenter = new HomePresenter(LeLeLeRepository.getInstance(
                    LeLeLeRemoteDataSource.getInstance()), homeFragment);
            mMainPresenter.setHomePresenter(mHomePresenter);
            homeFragment.setPresenter(mMainPresenter);
        }
    }

    /*
     * Application View
     * */
    void findOrCreateApplicationTenantView() {
        AppTenantFragment appTenantFragment = findOrCreateAppTenantFragment();

        if (mAppTenantPresenter == null) {
            mAppTenantPresenter = new AppTenantPresenter(LeLeLeRepository.getInstance(
                    LeLeLeRemoteDataSource.getInstance()), appTenantFragment);
            mMainPresenter.setAppTenantPresenter(mAppTenantPresenter);
            appTenantFragment.setPresenter(mMainPresenter);
        }
    }

    void findOrCreateApplicationLandlordView() {
        AppLandlordFragment appLandlordFragment = findOrCreateAppLandlordFragment();

        if (mAppTenantPresenter == null) {
            mAppLandlordPresenter = new AppLandlordPresenter(LeLeLeRepository.getInstance(
                    LeLeLeRemoteDataSource.getInstance()), appLandlordFragment);
            mMainPresenter.setAppLandlordPresenter(mAppLandlordPresenter);
            appLandlordFragment.setPresenter(mMainPresenter);
        }
    }

    /**
     * Home Fragment
     * @return HomeFragment
     */
    @NonNull
    private HomeFragment findOrCreateHomeFragment() {

        HomeFragment homeFragment =
                (HomeFragment) getFragmentManager().findFragmentByTag(HOME);
        if (homeFragment == null) {
            // Create the fragment
            homeFragment = HomeFragment.newInstance();
        }

        ActivityUtils.showOrAddFragmentByTag(
                getFragmentManager(), homeFragment, HOME);

        return homeFragment;
    }

    /**
     * Application Tenant Fragment
     * @return HomeFragment
     */
    private AppTenantFragment findOrCreateAppTenantFragment() {

        AppTenantFragment appTenantFragment =
                (AppTenantFragment) getFragmentManager().findFragmentByTag(APPLICATION_TENANT);
        if (appTenantFragment == null) {
            // Create the fragment
            appTenantFragment = AppTenantFragment.newInstance();
        }

        ActivityUtils.showOrAddFragmentByTag(
                getFragmentManager(), appTenantFragment, APPLICATION_TENANT);

        return appTenantFragment;
    }

    /**
     * Application Tenant Fragment
     * @return HomeFragment
     */
    private AppLandlordFragment findOrCreateAppLandlordFragment() {

        AppLandlordFragment appLandlordFragment =
                (AppLandlordFragment) getFragmentManager().findFragmentByTag(APPLICATION_LANDLORD);
        if (appLandlordFragment == null) {
            // Create the fragment
            appLandlordFragment = AppLandlordFragment.newInstance();
        }

        ActivityUtils.showOrAddFragmentByTag(
                getFragmentManager(), appLandlordFragment, APPLICATION_LANDLORD);

        return appLandlordFragment;
    }

    /**
     * Create Main Presenter
     * @return MainPresenter
     */
    private MainPresenter createMainPresenter() {
        mMainPresenter = new MainPresenter(LeLeLeRepository.getInstance(
                LeLeLeRemoteDataSource.getInstance()), (MainActivity) mActivity);

        return mMainPresenter;
    }

    private FragmentManager getFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }
}
