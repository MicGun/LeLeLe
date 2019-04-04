package com.hugh.lelele;


/*
* 主要用來implement所有fragment的presenter，
* 讓其作為所有fragment的presenter。
* */

import com.hugh.lelele.application_landlord.AppLandlordContract;
import com.hugh.lelele.application_landlord.AppLandlordPresenter;
import com.hugh.lelele.application_tenant.AppTenantContract;
import com.hugh.lelele.application_tenant.AppTenantPresenter;
import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.electricity_tenant.ElectricityTenantContract;
import com.hugh.lelele.electricity_tenant.ElectricityTenantPresenter;
import com.hugh.lelele.home.HomeContract;
import com.hugh.lelele.home.HomePresenter;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenter implements MainContract.Presenter, HomeContract.Presenter,
        AppTenantContract.Presenter, AppLandlordContract.Presenter, ElectricityTenantContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private MainContract.View mMainView;

    private HomePresenter mHomePresenter;
    private AppTenantPresenter mAppTenantPresenter;
    private AppLandlordPresenter mAppLandlordPresenter;
    private ElectricityTenantPresenter mElectricityTenantPresenter;

    public MainPresenter(LeLeLeRepository leLeLeRepository, MainContract.View mainView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leLeLeRepository cannot be null!");;
        mMainView = checkNotNull(mainView, "mainView cannot be null!");;
        mMainView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void updateNotifyBadge() {
        //ToDo Change five to not hard code.
        mMainView.updateNotifyBadgeUi(5);
    }

    @Override
    public void updateToolbar(String title) {
        mMainView.setToolbarTitleUi(title);
    }

    void setHomePresenter(HomePresenter homePresenter) {
        mHomePresenter = checkNotNull(homePresenter);
    }

    void setAppTenantPresenter(AppTenantPresenter appTenantPresenter) {
        mAppTenantPresenter = checkNotNull(appTenantPresenter);
    }

    void setAppLandlordPresenter(AppLandlordPresenter appLandlordPresenter) {
        mAppLandlordPresenter = checkNotNull(appLandlordPresenter);
    }

    void setElectricityTenantPresenter(ElectricityTenantPresenter electricityTenantPresenter) {
        mElectricityTenantPresenter = checkNotNull(electricityTenantPresenter);
    }

    @Override
    public void openHome() {
        mMainView.openHomeUi();
    }

    @Override
    public void openApplication() {
        mMainView.openApplicationUi();
    }

    @Override
    public void openNotify() {

    }

    @Override
    public void openProfile() {

    }

    @Override
    public void onDrawerOpened() {
//        if (!UserManager.getInstance().isLoggedIn()) {
//
//            mMainView.closeDrawerUi();
//            showLoginDialog(LoginDialog.FROM_DRAWER);
//
//        } else if (!UserManager.getInstance().hasUserInfo()) {
//
//            UserManager.getInstance().getUserProfile(new UserManager.LoadCallback() {
//                @Override
//                public void onSuccess() {
//
//                    mMainView.showDrawerUserUi();
//                }
//
//                @Override
//                public void onFail(String errorMessage) {}
//
//                @Override
//                public void onInvalidToken(String errorMessage) {
//                    showLoginDialog(LoginDialog.FROM_DRAWER);
//                }
//            });
//        } else {
//
//            mMainView.showDrawerUserUi();
//        }

        mMainView.closeDrawerUi();
//        mMainView.showDrawerUserUi();
    }

    @Override
    public void hideBottomNavigation() {
        mMainView.hideBottomNavigationUi();
    }

    @Override
    public void setElectricityData(ArrayList<Electricity> electricityYearly) {
        mElectricityTenantPresenter.setElectricityData(electricityYearly);
    }

    @Override
    public void showBottomNavigation() {
        mMainView.showBottomNavigationUi();
    }


    @Override
    public void loadElectricityData() {
        if (mAppTenantPresenter != null) {
            mAppTenantPresenter.loadElectricityData();
        }
    }

    @Override
    public void openElectricity(ArrayList<Electricity> electricityYearly) {
        mMainView.openElectricityUi(electricityYearly);
    }


    //AppLandlord
    @Override
    public void loadRoomList() {
        if (mAppLandlordPresenter != null) {
            mAppLandlordPresenter.loadRoomList();
        }
    }
}
