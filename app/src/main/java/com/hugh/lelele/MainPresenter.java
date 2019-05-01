package com.hugh.lelele;


/*
* 主要用來implement所有fragment的presenter，
* 讓其作為所有fragment的presenter。
* */

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.hugh.lelele.application_landlord.AppLandlordContract;
import com.hugh.lelele.application_landlord.AppLandlordPresenter;
import com.hugh.lelele.application_tenant.AppTenantContract;
import com.hugh.lelele.application_tenant.AppTenantPresenter;
import com.hugh.lelele.data.Article;
import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Notification;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.loco_data.NotificationDAO;
import com.hugh.lelele.data.loco_data.NotificationDatabase;
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
import com.hugh.lelele.invitation_sending.InvitationSendingContract;
import com.hugh.lelele.invitation_sending.InvitationSendingPresenter;
import com.hugh.lelele.login.LoginContract;
import com.hugh.lelele.login.LoginPresenter;
import com.hugh.lelele.notify.NotifyContract;
import com.hugh.lelele.notify.NotifyPresenter;
import com.hugh.lelele.post.PostContract;
import com.hugh.lelele.post.PostPresenter;
import com.hugh.lelele.profile_landlord.ProfileLandlordContract;
import com.hugh.lelele.profile_landlord.ProfileLandlordPresenter;
import com.hugh.lelele.profile_tenant.ProfileTenantContract;
import com.hugh.lelele.profile_tenant.ProfileTenantPresenter;
import com.hugh.lelele.room_list.RoomListContract;
import com.hugh.lelele.room_list.RoomListPresenter;
import com.hugh.lelele.room_list.invitation_dialog.InvitationActionContract;
import com.hugh.lelele.room_list.invitation_dialog.InvitationActionPresenter;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenter implements MainContract.Presenter, HomeContract.Presenter,
        AppTenantContract.Presenter, AppLandlordContract.Presenter, ElectricityTenantContract.Presenter,
        ElectricityLandlordContract.Presenter, LoginContract.Presenter, GroupListContract.Presenter,
        GroupDetailsContract.Presenter, RoomListContract.Presenter, InvitationSendingContract.Presenter,
        InvitationActionContract.Presenter, PostContract.Presenter, ProfileTenantContract.Presenter,
        ProfileLandlordContract.Presenter, NotifyContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private MainContract.View mMainView;

    private HomePresenter mHomePresenter;
    private NotifyPresenter mNotifyPresenter;
    private AppTenantPresenter mAppTenantPresenter;
    private AppLandlordPresenter mAppLandlordPresenter;
    private ElectricityTenantPresenter mElectricityTenantPresenter;
    private ElectricityLandlordPresenter mElectricityLandlordPresenter;
    private GroupListPresenter mGroupListPresenter;
    private GroupDetailsPresenter mGroupDetailsPresenter;
    private RoomListPresenter mRoomListPresenter;
    private InvitationSendingPresenter mInvitationSendingPresenter;
    private InvitationActionPresenter mInvitationActionPresenter;
    private PostPresenter mPostPresenter;
    private ProfileTenantPresenter mProfileTenantPresenter;
    private ProfileLandlordPresenter mProfileLandlordPresenter;
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
    public void updateNotifyBadge(int amount) {
        //ToDo Change five to not hard code.
        mMainView.updateNotifyBadgeUi(amount);
    }

    @Override
    public void updateToolbar(String title) {
        mMainView.setToolbarTitleUi(title);
    }

    @Override
    public void loadGroupData() {
        if (mRoomListPresenter != null) {
            mRoomListPresenter.loadGroupData();
        }
    }

    @Override
    public void openInvitationSending(Room room) {
        mMainView.openInvitationSendingUi(room);
    }

    @Override
    public void openInvitationActionDialog(View view, Room room) {
        mMainView.showInvitationActionDialog(view, room);
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
    }

    @Override
    public void openGroupDetails(Group group) {
        mMainView.openGroupDetailsUi(group);
    }

    void setHomePresenter(HomePresenter homePresenter) {
        mHomePresenter = checkNotNull(homePresenter);
    }

    void setNotifyPresenter(NotifyPresenter notifyPresenter) {
        mNotifyPresenter = checkNotNull(notifyPresenter);
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

    void setPostPresenter(PostPresenter postPresenter) {
        mPostPresenter = postPresenter;
    }

    void setRoomListPresenter(RoomListPresenter roomListPresenter) {
        mRoomListPresenter = checkNotNull(roomListPresenter);
    }

    void setInvitationSendingPresenter(InvitationSendingPresenter invitationSendingPresenter) {
        mInvitationSendingPresenter = checkNotNull(invitationSendingPresenter);
    }

    void mInvitationActionPresenter(InvitationActionPresenter invitationActionPresenter) {
        mInvitationActionPresenter = checkNotNull(invitationActionPresenter);
    }

    void setProfileTenantPresenter(ProfileTenantPresenter profileTenantPresenter) {
        mProfileTenantPresenter = checkNotNull(profileTenantPresenter);
    }

    void setProfileLandlordPresenter(ProfileLandlordPresenter profileLandlordPresenter) {
        mProfileLandlordPresenter = checkNotNull(profileLandlordPresenter);
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
        mMainView.openNotifyUi();
    }

    @Override
    public void openProfile() {
        mMainView.openProfileUi();
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
    public void resetDrawer() {
        mMainView.resetDrawerUi();
    }



    @Override
    public void loadNotificationsForBadge() {
        mLeLeLeRepository.getUserNotifications(UserManager.getInstance().getUserData().getEmail(), new LeLeLeDataSource.GetUserNotificationsCallback() {
            @Override
            public void onCompleted(ArrayList<Notification> notifications) {

                updateNotifyBadge(countUnreadNotification(notifications));
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    private int countUnreadNotification(ArrayList<Notification> notifications) {

        int unreadCount = 0;

        for (Notification notification:notifications) {
            if (!notification.isRead()) {
                unreadCount++;
            }
        }
        return unreadCount;
    }

    @Override
    public void setDrawerUserInfo() {
        mMainView.setDrawerUserInfoUi();
    }

    @Override
    public void hideKeyBoard() {
        mMainView.hideKeyBoardUi();
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
    public void releaseGroupArticle(Article article) {
        if (mPostPresenter != null) {
            mPostPresenter.releaseGroupArticle(article);
        }
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
    public void checkRoomData(ArrayList<Room> rooms) {
        if (mElectricityLandlordPresenter != null) {
            mElectricityLandlordPresenter.checkRoomData(rooms);
        }
    }

    @Override
    public void loadRoomList(String email, String groupName) {
        if (mAppLandlordPresenter != null) {
            mAppLandlordPresenter.loadRoomList(email, groupName);
        }
    }



    @Override
    public void loadTenant(String email) {
        if (mInvitationSendingPresenter != null) {
            mInvitationSendingPresenter.loadTenant(email);
        }
    }

    @Override
    public void updateTenant(Tenant tenant) {
        if (mInvitationSendingPresenter != null) {
            mInvitationSendingPresenter.updateTenant(tenant);
        }
    }

    @Override
    public void updateRoom(Room room) {
        if (mInvitationSendingPresenter != null) {
            mInvitationSendingPresenter.updateRoom(room);
        }
    }

    @Override
    public void updateRoomListStatus() {
        if (mRoomListPresenter != null) {
            mRoomListPresenter.loadGroupData();
        }
    }

    @Override
    public void sendInvitationToTenant(Room room) {
        if (mInvitationSendingPresenter != null) {
            mInvitationSendingPresenter.sendInvitationToTenant(room);
        }
    }

    @Override
    public void setRoomData(Room room) {
        if (mInvitationSendingPresenter != null) {
            mInvitationSendingPresenter.setRoomData(room);
        }
    }

    @Override
    public void getViewType() {
        if (mInvitationActionPresenter != null) {
            mInvitationActionPresenter.getViewType();
        }
    }

    @Override
    public void cancelInvitingAction(Room room) {
        if (mRoomListPresenter != null) {
            mRoomListPresenter.cancelInvitingAction(room);
        }
    }

    @Override
    public void removeTenantAction(Room room) {
        if (mRoomListPresenter != null) {
            mRoomListPresenter.removeTenantAction(room);
        }
    }

    @Override
    public void loadArticles() {
        if (mHomePresenter != null) {
            mHomePresenter.loadArticles();
        }
    }

    @Override
    public void cancelInvitation(Article article) {
        if (mHomePresenter != null) {
            mHomePresenter.cancelInvitation(article);
        }
    }

    @Override
    public void agreeInvitation(Article article) {
        if (mHomePresenter != null) {
            mHomePresenter.agreeInvitation(article);
        }
    }

    @Override
    public void setPostingButton() {
        if (mHomePresenter != null) {
            mHomePresenter.setPostingButton();
        }
    }

    @Override
    public void openPosting() {
        mMainView.openPostingUi();
    }

    @Override
    public void loadUserArticles() {
        if (mHomePresenter != null) {
            mHomePresenter.loadUserArticles();
        }
    }

    @Override
    public void loadGroupArticles() {
        if (mHomePresenter != null) {
            mHomePresenter.loadGroupArticles();
        }
    }

    @Override
    public void deleteElectricityNotification(Article article) {
        if (mHomePresenter != null) {
            mHomePresenter.deleteElectricityNotification(article);
        }
    }

    @Override
    public void setupArticleListener() {
        if (mHomePresenter != null) {
            mHomePresenter.setupArticleListener();
        }
    }

    @Override
    public void deleteGroupArticle(Article article) {
        if (mHomePresenter != null) {
            mHomePresenter.deleteGroupArticle(article);
        }
    }

    @Override
    public void updateTenantProfile(Tenant tenant) {
        if (mProfileTenantPresenter != null) {
            mProfileTenantPresenter.updateTenantProfile(tenant);
        }
    }

    @Override
    public void updateLandlordProfile(Landlord landlord) {
        if (mProfileLandlordPresenter != null) {
            mProfileLandlordPresenter.updateLandlordProfile(landlord);
        }
    }

    @Override
    public void loadNotifications() {
        if (mNotifyPresenter != null) {
            mNotifyPresenter.loadNotifications();
        }
    }
}
