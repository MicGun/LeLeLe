package com.hugh.lelele;


/*
* 主要用來implement所有fragment的presenter，
* 讓其作為所有fragment的presenter。
* */

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.hugh.lelele.application_landlord.AppLandlordContract;
import com.hugh.lelele.application_landlord.AppLandlordPresenter;
import com.hugh.lelele.application_tenant.AppTenantContract;
import com.hugh.lelele.application_tenant.AppTenantPresenter;
import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.electricity_landlord.ElectricityLandlordContract;
import com.hugh.lelele.electricity_landlord.ElectricityLandlordPresenter;
import com.hugh.lelele.electricity_tenant.ElectricityTenantContract;
import com.hugh.lelele.electricity_tenant.ElectricityTenantPresenter;
import com.hugh.lelele.group_detail.GroupDetailsContract;
import com.hugh.lelele.group_detail.GroupDetailsPresenter;
import com.hugh.lelele.group_list.GroupListContract;
import com.hugh.lelele.group_list.GroupListPresenter;
import com.hugh.lelele.home.HomeContract;
import com.hugh.lelele.home.HomePresenter;
import com.hugh.lelele.login.LoginContract;
import com.hugh.lelele.login.LoginPresenter;
import com.hugh.lelele.room_list.RoomListContract;
import com.hugh.lelele.room_list.RoomListPresenter;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenter implements MainContract.Presenter, HomeContract.Presenter,
        AppTenantContract.Presenter, AppLandlordContract.Presenter, ElectricityTenantContract.Presenter,
        ElectricityLandlordContract.Presenter, LoginContract.Presenter, GroupListContract.Presenter,
        GroupDetailsContract.Presenter, RoomListContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private MainContract.View mMainView;

    private HomePresenter mHomePresenter;
    private AppTenantPresenter mAppTenantPresenter;
    private AppLandlordPresenter mAppLandlordPresenter;
    private ElectricityTenantPresenter mElectricityTenantPresenter;
    private ElectricityLandlordPresenter mElectricityLandlordPresenter;
    private GroupListPresenter mGroupListPresenter;
    private GroupDetailsPresenter mGroupDetailsPresenter;
    private RoomListPresenter mRoomListPresenter;
    private LoginPresenter mLoginPresenter;

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

    @Override
    public void setGroupData(Group group) {
        if (mGroupDetailsPresenter != null) {
            mGroupDetailsPresenter.setGroupData(group);
        }
    }

    @Override
    public void updateRoomData(ArrayList<Room> rooms) {
        if (mGroupDetailsPresenter != null) {
            mGroupDetailsPresenter.updateRoomData(rooms);
        }
    }

    @Override
    public void loadRoomListFromGroupDetails(String email, String groupName) {
        if (mGroupDetailsPresenter != null) {
            mGroupDetailsPresenter.loadRoomListFromGroupDetails(email,groupName);
        }
    }

    @Override
    public void uploadGroup(Group group) {
        if (mGroupDetailsPresenter != null) {
            mGroupDetailsPresenter.uploadGroup(group);
        }
    }

    @Override
    public void setGroupsData(ArrayList<Group> groups) {
        if (mGroupListPresenter != null) {
            mGroupListPresenter.setGroupsData(groups);
        }
    }

    @Override
    public void loadGroupList(String email) {

        if (mGroupListPresenter != null) {
            mGroupListPresenter.loadGroupList(email);
        }
//        if (mHomePresenter != null) {
//            mHomePresenter.loadGroupList(email);
//        }
    }

    @Override
    public void openGroupDetails(Group group) {
        mMainView.openGroupDetailsUi(group);
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

    void setElectricityLandlordPresenter(ElectricityLandlordPresenter electricityLandlordPresenter) {
        mElectricityLandlordPresenter = checkNotNull(electricityLandlordPresenter);
    }

    void setLoginPresenter(LoginPresenter loginPresenter) {
        mLoginPresenter = checkNotNull(loginPresenter);
    }

    void setGroupListPresenter(GroupListPresenter groupListPresenter) {
        mGroupListPresenter = checkNotNull(groupListPresenter);
    }

    void setGroupDetailsPresenter(GroupDetailsPresenter groupDetailsPresenter) {
        mGroupDetailsPresenter = checkNotNull(groupDetailsPresenter);
    }

    void setRoomListPresenter(RoomListPresenter roomListPresenter) {
        mRoomListPresenter = checkNotNull(roomListPresenter);
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
        if (!UserManager.getInstance().isLoggedIn()) {

            mMainView.closeDrawerUi();
//            showLoginDialog(LoginDialog.FROM_DRAWER);

        } else {

            mMainView.showDrawerUserUi();
        }

//        mMainView.closeDrawerUi();
//        mMainView.showDrawerUserUi();
    }

    @Override
    public void hideBottomNavigation() {
        mMainView.hideBottomNavigationUi();
    }

    @Override
    public void openLogin() {
        mMainView.openLoginUi();
    }

    @Override
    public void loadGroupListDrawerMenu() {
        Log.v("Hugh", "User Email: " + UserManager.getInstance().getUserData().getEmail());
        mLeLeLeRepository.getGroupList(UserManager.getInstance().getUserData().getEmail(), new LeLeLeDataSource.GetGroupListCallback() {
            @Override
            public void onCompleted(ArrayList<Group> groups) {
                UserManager.getInstance().getLandlord().setGroups(groups);
                mMainView.showGroupListDrawerUi();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void hideToolbarAndBottomNavigation() {
        mMainView.hideToolbarUi();
        mMainView.hideBottomNavigationUi();
    }

    @Override
    public void showToolbarAndBottomNavigation() {
        mMainView.showToolbarUi();
        mMainView.showBottomNavigationUi();
    }

    @Override
    public void showLastFragment() {
        mMainView.showLastFragmentUi();
    }

    @Override
    public void deleteRoom(Room room) {
        if (mGroupDetailsPresenter != null) {
            mGroupDetailsPresenter.deleteRoom(room);
        }
    }

    @Override
    public void deleteRemoteRoom(Room room, String groupName, String email) {
        if (mGroupDetailsPresenter != null) {
            mGroupDetailsPresenter.deleteRemoteRoom(room, groupName, email);
        }
    }

    @Override
    public void notifyGroupListChanged() {
        if (mGroupListPresenter != null) {
            mGroupListPresenter.notifyGroupListChanged();
        }
    }

    @Override
    public void setUserType(int selectedUserType) {
        mMainView.setUserTypeForView(selectedUserType);
    }

    @Override
    public void setElectricityData(ArrayList<Electricity> electricityYearly) {
        if (mElectricityLandlordPresenter != null) {
            mElectricityTenantPresenter.setElectricityData(electricityYearly);
        }
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


//    //AppLandlord
//    @Override
//    public void loadRoomList() {
//        if (mAppLandlordPresenter != null) {
//            mAppLandlordPresenter.loadRoomList();
//        }
//    }

    @Override
    public void openElectricityEditor(ArrayList<Room> rooms) {
        mMainView.openElectricityEditorUi(rooms);
    }

    @Override
    public void loadGroupListFromApp(String email) {
        if (mAppLandlordPresenter != null) {
            mAppLandlordPresenter.loadGroupListFromApp(email);
        }
    }

    @Override
    public void openGroupList(ArrayList<Group> groups) {
        mMainView.openGroupListUi(groups);
    }

    @Override
    public void openRoomList() {
        mMainView.openRoomListUi();
    }

    @Override
    public void setRoomData(ArrayList<Room> rooms) {
        if (mElectricityLandlordPresenter != null) {
            mElectricityLandlordPresenter.setRoomData(rooms);
        }
    }

    @Override
    public void uploadElectricity(String landlordEmail, String groupName, String roomName, String year, String month, Electricity electricity) {
        if (mElectricityLandlordPresenter != null) {
            mElectricityLandlordPresenter.uploadElectricity(landlordEmail, groupName, roomName, year, month, electricity);
        }
    }

    @Override
    public void initialElectricityMonth(String landlordEmail, String groupName, String roomName, String year, String month) {
        if (mElectricityLandlordPresenter != null) {
            mElectricityLandlordPresenter.initialElectricityMonth(landlordEmail, groupName, roomName, year, month);
        }
    }

    @Override
    public void uploadLandlord(String email) {
        if (mHomePresenter != null) {
            mHomePresenter.uploadLandlord(email);
        }
    }

    @Override
    public void loadRoomList(String email, String groupName) {
        if (mAppLandlordPresenter != null) {
            mAppLandlordPresenter.loadRoomList(email, groupName);
        }

//        if (mHomePresenter != null) {
//            mHomePresenter.loadRoomList(email, groupName);
//        }
    }



    @Override
    public void loadTenant(String email) {
        if (mHomePresenter != null) {
            mHomePresenter.loadTenant(email);
        }
    }
}
