package com.hugh.lelele;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.hugh.lelele.application_landlord.AppLandlordFragment;
import com.hugh.lelele.application_landlord.AppLandlordPresenter;
import com.hugh.lelele.application_tenant.AppTenantFragment;
import com.hugh.lelele.application_tenant.AppTenantPresenter;
import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.source.LeLeLeRemoteDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.electricity_tenant.ElectricityTenantFragment;
import com.hugh.lelele.electricity_tenant.ElectricityTenantPresenter;
import com.hugh.lelele.home.HomeFragment;
import com.hugh.lelele.home.HomePresenter;
import com.hugh.lelele.notify.NotifyPresenter;
import com.hugh.lelele.profile_landlord.ProfileLandlordPresenter;
import com.hugh.lelele.profile_tenant.ProfileTenantPresenter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainMvpController {

    private final FragmentActivity mActivity;
    private MainPresenter mMainPresenter;

    private HomePresenter mHomePresenter;
    private AppTenantPresenter mAppTenantPresenter;
    private AppLandlordPresenter mAppLandlordPresenter;
    private ElectricityTenantPresenter mElectricityTenantPresenter;
    private NotifyPresenter mNotifyPresenter;
    private ProfileTenantPresenter mProfileTenantPresenter;
    private ProfileLandlordPresenter mProfileLandlordPresenter;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            HOME, APPLICATION_TENANT, APPLICATION_LANDLORD, NOTIFY, PROFILE, ELECTRICITY_TENANT,
            ELECTRICITY_LANDLORD
    })
    public @interface FragmentType {}
    static final String HOME    = "HOME";
    static final String APPLICATION_TENANT = "APPLICATION_TENANT";
    static final String APPLICATION_LANDLORD = "APPLICATION_LANDLORD";
    static final String NOTIFY    = "NOTIFY";
    static final String PROFILE = "PROFILE";
    static final String ELECTRICITY_TENANT = "ELECTRICITY_TENANT";
    static final String ELECTRICITY_LANDLORD = "ELECTRICITY_LANDLORD";

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

        if (mAppLandlordPresenter == null) {
            mAppLandlordPresenter = new AppLandlordPresenter(LeLeLeRepository.getInstance(
                    LeLeLeRemoteDataSource.getInstance()), appLandlordFragment);
            mMainPresenter.setAppLandlordPresenter(mAppLandlordPresenter);
            appLandlordFragment.setPresenter(mMainPresenter);
        }
    }

    /*
     * Electricity View
     * */

    void findOrCreateElectricityLandlordView() {
//        AppTenantFragment appTenantFragment = findOrCreateAppTenantFragment();

//        if (mAppTenantPresenter == null) {
//            mAppTenantPresenter = new AppTenantPresenter(LeLeLeRepository.getInstance(
//                    LeLeLeRemoteDataSource.getInstance()), appTenantFragment);
//            mMainPresenter.setAppTenantPresenter(mAppTenantPresenter);
//            appTenantFragment.setPresenter(mMainPresenter);
//        }
    }

    void findOrCreateElectricityTenantView(ArrayList<Electricity> electricityYearly) {
        ElectricityTenantFragment electricityTenantFragment = findOrCreateElectricityTenantFragment();

        Log.v("is null", "" + (mElectricityTenantPresenter == null));
        if (mElectricityTenantPresenter == null) {
            mElectricityTenantPresenter = new ElectricityTenantPresenter(LeLeLeRepository.getInstance(
                    LeLeLeRemoteDataSource.getInstance()), electricityTenantFragment);
            mMainPresenter.setElectricityTenantPresenter(mElectricityTenantPresenter);
            electricityTenantFragment.setPresenter(mMainPresenter);
        } else {
            mMainPresenter.setElectricityTenantPresenter(mElectricityTenantPresenter);
            electricityTenantFragment.setPresenter(mMainPresenter);
        }
        mElectricityTenantPresenter.setElectricityData(electricityYearly);
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
     * @return AppTenantFragment
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
     * Application Landlord Fragment
     * @return AppLandlordFragment
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
     * Electricity Tenant Fragment
     * @return ElectricityTenantFragment
     */
    private ElectricityTenantFragment findOrCreateElectricityTenantFragment() {

        ElectricityTenantFragment electricityTenantFragment =
                (ElectricityTenantFragment) getFragmentManager().findFragmentByTag(ELECTRICITY_TENANT);
        if (electricityTenantFragment == null) {
            // Create the fragment
            electricityTenantFragment = ElectricityTenantFragment.newInstance();
        }

        ActivityUtils.addFragmentByTag(
                getFragmentManager(), electricityTenantFragment, ELECTRICITY_TENANT);

        return electricityTenantFragment;
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
