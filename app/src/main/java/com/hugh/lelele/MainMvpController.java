package com.hugh.lelele;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.hugh.lelele.application_landlord.AppLandlordFragment;
import com.hugh.lelele.application_landlord.AppLandlordPresenter;
import com.hugh.lelele.application_tenant.AppTenantFragment;
import com.hugh.lelele.application_tenant.AppTenantPresenter;
import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Message;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.source.LeLeLeRemoteDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.electricity_landlord.ElectricityLandlordFragment;
import com.hugh.lelele.electricity_landlord.ElectricityLandlordPresenter;
import com.hugh.lelele.electricity_tenant.ElectricityTenantFragment;
import com.hugh.lelele.electricity_tenant.ElectricityTenantPresenter;
import com.hugh.lelele.group_detail.GroupDetailsFragment;
import com.hugh.lelele.group_detail.GroupDetailsPresenter;
import com.hugh.lelele.group_list.GroupListFragment;
import com.hugh.lelele.group_list.GroupListPresenter;
import com.hugh.lelele.home.HomeFragment;
import com.hugh.lelele.home.HomePresenter;
import com.hugh.lelele.invitation_sending.InvitationSendingFragment;
import com.hugh.lelele.invitation_sending.InvitationSendingPresenter;
import com.hugh.lelele.login.LoginFragment;
import com.hugh.lelele.login.LoginPresenter;
import com.hugh.lelele.message.MessageFragment;
import com.hugh.lelele.message.MessagePresenter;
import com.hugh.lelele.messaging_list.MessagingListFragment;
import com.hugh.lelele.messaging_list.MessagingListPresenter;
import com.hugh.lelele.notify.NotifyFragment;
import com.hugh.lelele.notify.NotifyPresenter;
import com.hugh.lelele.post.PostFragment;
import com.hugh.lelele.post.PostPresenter;
import com.hugh.lelele.profile_landlord.ProfileLandlordFragment;
import com.hugh.lelele.profile_landlord.ProfileLandlordPresenter;
import com.hugh.lelele.profile_tenant.ProfileTenantFragment;
import com.hugh.lelele.profile_tenant.ProfileTenantPresenter;
import com.hugh.lelele.room_list.RoomListFragment;
import com.hugh.lelele.room_list.RoomListPresenter;
import com.hugh.lelele.room_list.invitation_dialog.InvitationActionDialog;
import com.hugh.lelele.room_list.invitation_dialog.InvitationActionPresenter;

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
    private ElectricityLandlordPresenter mElectricityLandlordPresenter;
    private LoginPresenter mLoginPresenter;
    private GroupListPresenter mGroupListPresenter;
    private RoomListPresenter mRoomListPresenter;
    private MessagingListPresenter mMessagingListPresenter;
    private GroupDetailsPresenter mGroupDetailsPresenter;
    private InvitationSendingPresenter mInvitationSendingPresenter;
    private InvitationActionPresenter mInvitationActionPresenter;
    private PostPresenter mPostPresenter;
    private NotifyPresenter mNotifyPresenter;
    private ProfileTenantPresenter mProfileTenantPresenter;
    private ProfileLandlordPresenter mProfileLandlordPresenter;
    private MessagePresenter mMessagePresenter;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            HOME, APPLICATION_TENANT, APPLICATION_LANDLORD, NOTIFY, PROFILE, ELECTRICITY_TENANT,
            ELECTRICITY_LANDLORD, LOGIN, GROUP_LIST, GROUP_DETAILS, ROOM_LIST, INVITATION_SENDING,
            POST, PROFILE_TENANT, PROFILE_LANDLORD, MESSAGE, MESSAGING_LIST
    })
    public @interface FragmentType {}
    static final String HOME    = "HOME";
    static final String LOGIN    = "LOGIN";
    static final String GROUP_LIST    = "GROUP_LIST";
    static final String ROOM_LIST    = "ROOM_LIST";
    static final String MESSAGING_LIST    = "MESSAGING_LIST";
    static final String INVITATION_SENDING    = "INVITATION_SENDING";
    static final String GROUP_DETAILS    = "GROUP_DETAILS";
    static final String APPLICATION_TENANT = "APPLICATION_TENANT";
    static final String APPLICATION_LANDLORD = "APPLICATION_LANDLORD";
    static final String NOTIFY    = "NOTIFY";
    static final String PROFILE = "PROFILE";
    static final String PROFILE_TENANT = "PROFILE_TENANT";
    static final String PROFILE_LANDLORD = "PROFILE_LANDLORD";
    static final String ELECTRICITY_TENANT = "ELECTRICITY_TENANT";
    static final String ELECTRICITY_LANDLORD = "ELECTRICITY_LANDLORD";
    static final String INVITATION_ACTION = "INVITATION_ACTION";
    static final String POST = "POST";
    static final String MESSAGE = "MESSAGE";

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
//        mHomePresenter.setPostingButton();
        mHomePresenter.loadArticles();
    }

    /*
     * Notify View
     * */
    void findOrCreateNotifyView() {

        NotifyFragment notifyFragment = findOrCreateNotifyFragment();

        if (mNotifyPresenter == null) {
            mNotifyPresenter = new NotifyPresenter(LeLeLeRepository.getInstance(
                    LeLeLeRemoteDataSource.getInstance()), notifyFragment);
            mMainPresenter.setNotifyPresenter(mNotifyPresenter);
            notifyFragment.setPresenter(mMainPresenter);
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
     * Profile View
     * */
    void findOrCreateProfileTenantView() {
        ProfileTenantFragment profileTenantFragment = findOrCreateProfileTenantFragment();

        if (mProfileTenantPresenter == null) {
            mProfileTenantPresenter = new ProfileTenantPresenter(LeLeLeRepository.getInstance(
                    LeLeLeRemoteDataSource.getInstance()), profileTenantFragment);
            mMainPresenter.setProfileTenantPresenter(mProfileTenantPresenter);
            profileTenantFragment.setPresenter(mMainPresenter);
        }
    }

    void findOrCreateProfileLandlordView() {
        ProfileLandlordFragment profileLandlordFragment = findOrCreateProfileLandlordFragment();

        if (mProfileLandlordPresenter == null) {
            mProfileLandlordPresenter = new ProfileLandlordPresenter(LeLeLeRepository.getInstance(
                    LeLeLeRemoteDataSource.getInstance()), profileLandlordFragment);
            mMainPresenter.setProfileLandlordPresenter(mProfileLandlordPresenter);
            profileLandlordFragment.setPresenter(mMainPresenter);
        }
    }

    /*
     * Electricity View
     * */

    void findOrCreateElectricityLandlordView(ArrayList<Room> rooms) {
        ElectricityLandlordFragment electricityLandlordFragment = findOrCreateElectricityLandlordFragment();
        mElectricityLandlordPresenter = new ElectricityLandlordPresenter(LeLeLeRepository.getInstance(
                LeLeLeRemoteDataSource.getInstance()), electricityLandlordFragment);
        mMainPresenter.setElectricityLandlordPresenter(mElectricityLandlordPresenter);
        electricityLandlordFragment.setPresenter(mMainPresenter);
        mElectricityLandlordPresenter.setRoomData(rooms);
    }

    void findOrCreateElectricityTenantView(ArrayList<Electricity> electricityYearly) {
        ElectricityTenantFragment electricityTenantFragment = findOrCreateElectricityTenantFragment();

        Log.v("is null", "" + (mElectricityTenantPresenter == null));

        mElectricityTenantPresenter = new ElectricityTenantPresenter(LeLeLeRepository.getInstance(
                LeLeLeRemoteDataSource.getInstance()), electricityTenantFragment);
        mMainPresenter.setElectricityTenantPresenter(mElectricityTenantPresenter);
        electricityTenantFragment.setPresenter(mMainPresenter);
        mElectricityTenantPresenter.setElectricityData(electricityYearly);
    }

    /*
     * Login View
     * */
    void findOrCreateLoginView() {
        LoginFragment loginFragment = findOrCreateLoginFragment();
        mLoginPresenter = new LoginPresenter(LeLeLeRepository.getInstance(
                LeLeLeRemoteDataSource.getInstance()), loginFragment);
        mMainPresenter.setLoginPresenter(mLoginPresenter);
        loginFragment.setPresenter(mMainPresenter);
        mLoginPresenter.hideToolbarAndBottomNavigation();
    }

    /*
     * Group List View
     * */
    void findOrCreateGroupListView(ArrayList<Group> groups) {

        GroupListFragment groupListFragment = findOrCreateGroupListFragment();

        mGroupListPresenter = new GroupListPresenter(LeLeLeRepository.getInstance(
                LeLeLeRemoteDataSource.getInstance()), groupListFragment);

        mMainPresenter.setGroupListPresenter(mGroupListPresenter);
        groupListFragment.setPresenter(mMainPresenter);

        mGroupListPresenter.setGroupsData(groups);
    }

    /*
     * Room List View
     * */
    void findOrCreateRoomListView() {

        RoomListFragment roomListFragment = findOrCreateRoomListFragment();

        mRoomListPresenter = new RoomListPresenter(LeLeLeRepository.getInstance(
                LeLeLeRemoteDataSource.getInstance()), roomListFragment);

        mMainPresenter.setRoomListPresenter(mRoomListPresenter);
        roomListFragment.setPresenter(mMainPresenter);
    }

    /*
     * Messaging List View
     * */
    void findOrCreateMessagingListView() {

        MessagingListFragment messagingListFragment = findOrCreateMessagingListFragment();

        mMessagingListPresenter = new MessagingListPresenter(LeLeLeRepository.getInstance(
                LeLeLeRemoteDataSource.getInstance()), messagingListFragment);

        mMainPresenter.setMessagingListPresenter(mMessagingListPresenter);
        messagingListFragment.setPresenter(mMainPresenter);
    }

    /*
     * Invitation Sending View
     * */
    void findOrCreateInvitationSendingView( Room room) {

        InvitationSendingFragment invitationSendingFragment = findOrCreateInvitationSendingFragment();

        mInvitationSendingPresenter = new InvitationSendingPresenter(LeLeLeRepository.getInstance(
                LeLeLeRemoteDataSource.getInstance()), invitationSendingFragment);

        mMainPresenter.setInvitationSendingPresenter(mInvitationSendingPresenter);
        invitationSendingFragment.setPresenter(mMainPresenter);
        mInvitationSendingPresenter.setRoomData(room);
    }

    /*
     * Group Details View
     * */
    void findOrCreateGroupDetailsView(Group group) {
        GroupDetailsFragment groupDetailsFragment = findOrCreateGroupDetailsFragment();
        mGroupDetailsPresenter = new GroupDetailsPresenter(LeLeLeRepository.getInstance(
                LeLeLeRemoteDataSource.getInstance()), groupDetailsFragment);
        mMainPresenter.setGroupDetailsPresenter(mGroupDetailsPresenter);
        groupDetailsFragment.setPresenter(mMainPresenter);
        mGroupDetailsPresenter.setGroupData(group);
    }

    /*
     * Invitation Action View
     * */
    void findOrCreateInvitationActionDialog(View view, Room room) {

        InvitationActionDialog dialog =
                (InvitationActionDialog) getFragmentManager().findFragmentByTag(INVITATION_ACTION);

        if (dialog == null) {

            dialog = new InvitationActionDialog();
            mInvitationActionPresenter = new InvitationActionPresenter(dialog, view, room);
            mMainPresenter.mInvitationActionPresenter(mInvitationActionPresenter);
            dialog.setPresenter(mMainPresenter);
            dialog.show(getFragmentManager(), INVITATION_ACTION);

        }
    }

    /*
     * Post View
     * */
    void findOrCreatePostView() {

        PostFragment postFragment = findOrCreatePostFragment();

        mPostPresenter = new PostPresenter(LeLeLeRepository.getInstance(
                LeLeLeRemoteDataSource.getInstance()), postFragment);
        mMainPresenter.setPostPresenter(mPostPresenter);
        postFragment.setPresenter(mMainPresenter);
    }

    /*
     * Message View
     * */
    void findOrCreateMessageView(ArrayList<Message> messages, Tenant tenant) {

        MessageFragment messageFragment = findOrCreateMessageFragment();

        mMessagePresenter = new MessagePresenter(LeLeLeRepository.getInstance(
                LeLeLeRemoteDataSource.getInstance()), messageFragment);
        mMainPresenter.setMessagePresenter(mMessagePresenter);
        messageFragment.setPresenter(mMainPresenter);

        mMessagePresenter.setTenant(tenant);
        mMessagePresenter.setMessages(messages);
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
     * Notify Fragment
     * @return NotifyFragment
     */
    @NonNull
    private NotifyFragment findOrCreateNotifyFragment() {

        NotifyFragment notifyFragment =
                (NotifyFragment) getFragmentManager().findFragmentByTag(NOTIFY);
        if (notifyFragment == null) {
            // Create the fragment
            notifyFragment = NotifyFragment.newInstance();
        }

        ActivityUtils.showOrAddFragmentByTag(
                getFragmentManager(), notifyFragment, NOTIFY);

        return notifyFragment;
    }

    /**
     * Login Fragment
     * @return HomeFragment
     */
    @NonNull
    private LoginFragment findOrCreateLoginFragment() {

        LoginFragment loginFragment =
                (LoginFragment) getFragmentManager().findFragmentByTag(LOGIN);
        if (loginFragment == null) {
            // Create the fragment
            loginFragment = LoginFragment.newInstance();
        }

        ActivityUtils.showOrAddFragmentByTag(
                getFragmentManager(), loginFragment, LOGIN);

        return loginFragment;
    }

    /**
     * Group List Fragment
     * @return HomeFragment
     */
    @NonNull
    private GroupListFragment findOrCreateGroupListFragment() {

        GroupListFragment groupListFragment =
                (GroupListFragment) getFragmentManager().findFragmentByTag(GROUP_LIST);
        if (groupListFragment == null) {
            // Create the fragment
            groupListFragment = GroupListFragment.newInstance();
        }

        ActivityUtils.addFragmentByTag(
                getFragmentManager(), groupListFragment, GROUP_LIST);

        return groupListFragment;
    }

    /**
     * Room List Fragment
     * @return HomeFragment
     */
    @NonNull
    private RoomListFragment findOrCreateRoomListFragment() {

        RoomListFragment roomListFragment =
                (RoomListFragment) getFragmentManager().findFragmentByTag(ROOM_LIST);
        if (roomListFragment == null) {
            // Create the fragment
            roomListFragment = RoomListFragment.newInstance();
        }

        ActivityUtils.addFragmentByTag(
                getFragmentManager(), roomListFragment, ROOM_LIST);

        return roomListFragment;
    }

    /**
     * Room List Fragment
     * @return HomeFragment
     */
    @NonNull
    private MessagingListFragment findOrCreateMessagingListFragment() {

        MessagingListFragment messagingListFragment =
                (MessagingListFragment) getFragmentManager().findFragmentByTag(MESSAGING_LIST);
        if (messagingListFragment == null) {
            // Create the fragment
            messagingListFragment = MessagingListFragment.newInstance();
        }

        ActivityUtils.addFragmentByTag(
                getFragmentManager(), messagingListFragment, MESSAGING_LIST);

        return messagingListFragment;
    }

    /**
     * Invitation Sending Fragment
     * @return HomeFragment
     */
    @NonNull
    private InvitationSendingFragment findOrCreateInvitationSendingFragment() {

        InvitationSendingFragment invitationSendingFragment =
                (InvitationSendingFragment) getFragmentManager().findFragmentByTag(INVITATION_SENDING);
        if (invitationSendingFragment == null) {
            // Create the fragment
            invitationSendingFragment = InvitationSendingFragment.newInstance();
        }

        ActivityUtils.addFragmentByTag(
                getFragmentManager(), invitationSendingFragment, INVITATION_SENDING);

        return invitationSendingFragment;
    }

    /**
     * Group Details Fragment
     * @return HomeFragment
     */
    @NonNull
    private GroupDetailsFragment findOrCreateGroupDetailsFragment() {

        GroupDetailsFragment groupDetailsFragment =
                (GroupDetailsFragment) getFragmentManager().findFragmentByTag(GROUP_DETAILS);
        if (groupDetailsFragment == null) {
            // Create the fragment
            groupDetailsFragment = GroupDetailsFragment.newInstance();
        }

        ActivityUtils.addFragmentByTag(
                getFragmentManager(), groupDetailsFragment, GROUP_DETAILS);

        return groupDetailsFragment;
    }

    /**
     * Post Fragment
     * @return PostFragment
     */
    @NonNull
    private PostFragment findOrCreatePostFragment() {

        PostFragment postFragment =
                (PostFragment) getFragmentManager().findFragmentByTag(POST);
        if (postFragment == null) {
            // Create the fragment
            postFragment = PostFragment.newInstance();
        }

        ActivityUtils.addFragmentByTag(
                getFragmentManager(), postFragment, POST);

        return postFragment;
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
     * Electricity Landlord Fragment
     * @return ElectricityLandlordFragment
     */
    private ElectricityLandlordFragment findOrCreateElectricityLandlordFragment() {

        ElectricityLandlordFragment electricityLandlordFragment =
                (ElectricityLandlordFragment) getFragmentManager().findFragmentByTag(ELECTRICITY_LANDLORD);

        if (electricityLandlordFragment == null) {
            // Create the fragment
            electricityLandlordFragment = ElectricityLandlordFragment.newInstance();
        }

        ActivityUtils.addFragmentByTag(
                getFragmentManager(), electricityLandlordFragment, ELECTRICITY_LANDLORD);

        return electricityLandlordFragment;
    }

    /**
     * Message Fragment
     * @return MessageFragment
     */
    private MessageFragment findOrCreateMessageFragment() {

        MessageFragment messageFragment =
                (MessageFragment) getFragmentManager().findFragmentByTag(MESSAGE);

        if (messageFragment == null) {
            // Create the fragment
            messageFragment = MessageFragment.newInstance();
        }

        ActivityUtils.addFragmentByTag(
                getFragmentManager(), messageFragment, MESSAGE);

        return messageFragment;
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
     * Profile Tenant Fragment
     * @return ProfileTenantFragment
     */
    private ProfileTenantFragment findOrCreateProfileTenantFragment() {

        ProfileTenantFragment profileTenantFragment =
                (ProfileTenantFragment) getFragmentManager().findFragmentByTag(PROFILE_TENANT);
        if (profileTenantFragment == null) {
            // Create the fragment
            profileTenantFragment = ProfileTenantFragment.newInstance();
        }

        ActivityUtils.addFragmentByTag(
                getFragmentManager(), profileTenantFragment, PROFILE_TENANT);

        return profileTenantFragment;
    }

    /**
     * Profile Landlord Fragment
     * @return ProfileLandlordFragment
     */
    private ProfileLandlordFragment findOrCreateProfileLandlordFragment() {

        ProfileLandlordFragment profileLandlordFragment =
                (ProfileLandlordFragment) getFragmentManager().findFragmentByTag(PROFILE_LANDLORD);
        if (profileLandlordFragment == null) {
            // Create the fragment
            profileLandlordFragment = ProfileLandlordFragment.newInstance();
        }

        ActivityUtils.addFragmentByTag(
                getFragmentManager(), profileLandlordFragment, PROFILE_LANDLORD);

        return profileLandlordFragment;
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
