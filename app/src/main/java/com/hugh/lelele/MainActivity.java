package com.hugh.lelele;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.common.images.ImageManager;
import com.hugh.lelele.component.ProfileAvatarOutlineProvider;
import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.loco_data.UserData;
import com.hugh.lelele.data.loco_data.UserDataDAO;
import com.hugh.lelele.data.loco_data.UserDatabase;
import com.hugh.lelele.util.UserManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends BaseActivivty implements MainContract.View,
        NavigationView.OnNavigationItemSelectedListener {

    private int mUserType;

    private BottomNavigationView mBottomNavigation;
    private DrawerLayout mDrawerLayout;
    private SubMenu mGroupMenu;
    private Menu mDrawerMenu;
    private ImageView mDrawerUserImage;
    private TextView mDrawerUserName;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private View mBadge;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private MainMvpController mMainMvpController;

    private MainContract.Presenter mPresenter;

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

//        printHashKey(this);
    }
/*
    public void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("Huge", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("Huge", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("Huge", "printHashKey()", e);
        }
    }
    */

    private void init() {
        setContentView(R.layout.activity_main);

        mMainMvpController = MainMvpController.create(this);

        if (UserManager.getInstance().isLoggedIn()) {
            UserManager.getInstance().setupUserEnvironment();
            mUserType = UserManager.getInstance().getUserData().getUserType();
            mPresenter.openHome();
        } else {
            mPresenter.openLogin();
        }

        setToolbar();
        setBottomNavigation();
        setDrawerLayout();

        if (mUserType == R.string.landlord) {
            Log.v(TAG, getResources().getString(R.string.landlord));

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        UserManager.getInstance().getFbCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Let toolbar to extend to status bar.
     *
     * @notice this method have to be used after setContentView.
     */
    private void setToolbar() {
        // Retrieve the AppCompact Toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        // Set the padding to match the Status Bar height
        mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        mToolbarTitle = mToolbar.findViewById(R.id.text_toolbar_title);
        mToolbarTitle.setText(MainActivity.this.getResources().getString(R.string.home));
    }

    /**
     * plugin: BottomNavigationViewEx.
     */
    private void setBottomNavigation() {

        mBottomNavigation = findViewById(R.id.bottom_navigationView);
        mBottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationMenuView menuView =
                (BottomNavigationMenuView) mBottomNavigation.getChildAt(0);

        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            layoutParams.height = (int) getResources().getDimension(R.dimen.size_bottom_nav_icon);
            layoutParams.width = (int) getResources().getDimension(R.dimen.size_bottom_nav_icon);
            iconView.setLayoutParams(layoutParams);
        }

        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(2);
        mBadge = LayoutInflater.from(this)
                .inflate(R.layout.badge_main_bottom, itemView, true);

        mPresenter.updateNotifyBadge();
    }

    /**
     * @return height of status bar
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * It's the item selected listener of bottom navigation.
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:

                    mPresenter.updateToolbar(MainActivity.this.getResources().getString(R.string.home));
                    mPresenter.openHome();
                    return true;

                case R.id.navigation_application:

                    mPresenter.updateToolbar(MainActivity.this.getResources().getString(R.string.application));
                    mPresenter.openApplication();
                    return true;

                case R.id.navigation_notify:

                    mPresenter.updateToolbar(MainActivity.this.getResources().getString(R.string.notify));
                    mPresenter.openNotify();
                    UserDatabase userDatabase = android.arch.persistence.room.Room
                            .databaseBuilder(LeLeLe.getAppContext(), UserDatabase.class, "userDataBase")
                            .allowMainThreadQueries()
                            .build();
                    UserDataDAO userDataDAO = userDatabase.getUserDAO();
                    List<UserData> userDataList = userDataDAO.getItems();
                    UserData userData = userDataList.get(0);
                    Toast.makeText(getApplicationContext(), userData.getEmail(), Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.navigation_profile:

                    mPresenter.updateToolbar(MainActivity.this.getResources().getString(R.string.profile));
                    mPresenter.openProfile();
                    return true;

                default:
                    return false;
            }
        }
    };

    /**
     * Set Drawer
     */
    private void setDrawerLayout() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.layout_main);
        mDrawerLayout.setFitsSystemWindows(true);
        mDrawerLayout.setClipToPadding(false);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                mPresenter.onDrawerOpened();
            }
        };
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerMenu = navigationView.getMenu();

        //if user type is landlord, to download group list and show on navigation menu
        if (mUserType == R.string.landlord) {
//            mGroupMenu = drawerMenu.addSubMenu("Groups");
            mPresenter.loadGroupListDrawerMenu();
        }

        // nav view header
        mDrawerUserImage = navigationView.getHeaderView(0).findViewById(R.id.image_drawer_avatar);
        mDrawerUserImage.setOutlineProvider(new ProfileAvatarOutlineProvider(getResources().
                getDimensionPixelSize(R.dimen.radius_profile_avatar)));

        mDrawerUserName = navigationView.getHeaderView(0).findViewById(R.id.image_drawer_name);
    }

    @Override
    public void showGroupListDrawerUi() {

        ArrayList<Group> groups = UserManager.getInstance().getLandlord().getGroups();
        if (mGroupMenu == null) {
            mGroupMenu = mDrawerMenu.addSubMenu(getResources().getString(R.string.application_groups_list));
        }

        if (groups.size() != 0) {
            mGroupMenu.clear();
            for (Group group:groups) {
                mGroupMenu.add(group.getGroupName());
            }
        } else {
            mDrawerMenu.add(getResources().getString(R.string.ask_to_create_a_group));
        }

    }

    @Override
    public void openRoomListUi() {
        mPresenter.updateToolbar(getResources().getString(R.string.application_room_list));
        mMainMvpController.findOrCreateRoomListView();
    }

    @Override
    public void openInvitationSendingUi(Room room) {
        mPresenter.updateToolbar(getResources().getString(R.string.invitation_sending));
        mMainMvpController.findOrCreateInvitationSendingView(room);
    }

    @Override
    public void showInvitationActionDialog(View view, Room room) {
        mMainMvpController.findOrCreateInvitationActionDialog(view, room);
    }

    @Override
    public void resetDrawerUi() {
        if (mGroupMenu != null) {
            mGroupMenu.clear();
        }
    }

    @Override
    public void openPostingUi() {
        mPresenter.updateToolbar(getResources().getString(R.string.new_post));
        mMainMvpController.findOrCreatePostView();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void updateNotifyBadgeUi(int amount) {
        if (amount > 0) {
            mBadge.findViewById(R.id.text_badge_main).setVisibility(View.VISIBLE);
            ((TextView) mBadge.findViewById(R.id.text_badge_main)).setText(String.valueOf(amount));
        } else {
            mBadge.findViewById(R.id.text_badge_main).setVisibility(View.GONE);
        }
    }

    @Override
    public void setToolbarTitleUi(String title) {

        if (title.equals(getString(R.string.application_electricity)) ||
                title.equals(getString(R.string.application_groups_list)) ||
                title.equals(getString(R.string.group_edit)) ||
                title.equals(getString(R.string.application_room_list)) ||
                title.equals(getString(R.string.invitation_sending)) ||
                title.equals(getString(R.string.new_post))) {
            mActionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.toolbar_back);
            mActionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.onBackPressed();
                }
            });
        } else {
            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mToolbar.setNavigationIcon(R.drawable.toolbar_menu);
            mActionBarDrawerToggle.setToolbarNavigationClickListener(null);
        }
        mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setText(title);
    }

    @Override
    public void openHomeUi() {
        mMainMvpController.findOrCreateHomeView();
    }

    @Override
    public void openApplicationUi() {
        if (mUserType == R.string.landlord) {
            mMainMvpController.findOrCreateApplicationLandlordView();
        } else if (mUserType == R.string.tenant) {
            mMainMvpController.findOrCreateApplicationTenantView();
        }
    }

    @Override
    public void openNotifyUi() {

    }

    @Override
    public void openProfileUi() {

    }

    @Override
    public void openElectricityUi(ArrayList<Electricity> electricityYearly) {
        mPresenter.updateToolbar(getResources().getString(R.string.application_electricity));
        mMainMvpController.findOrCreateElectricityTenantView(electricityYearly);
    }

    @Override
    public void openElectricityEditorUi(ArrayList<Room> rooms) {
        mPresenter.updateToolbar(getResources().getString(R.string.application_electricity));
        mMainMvpController.findOrCreateElectricityLandlordView(rooms);
    }

    @Override
    public void showDrawerUserUi() {
//        ImageManager.getInstance().setImageByUrl(mDrawerUserImage,
//                UserManager.getInstance().getUser().getPicture());
//
//        mDrawerUserName.setText(UserManager.getInstance().getUser().getName());
//        mDrawerUserInfo.setText(UserManager.getInstance().getUserInfo());
    }

    @Override
    public void closeDrawerUi() {
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void hideBottomNavigationUi() {
        mBottomNavigation.setVisibility(View.GONE);
    }

    @Override
    public void showBottomNavigationUi() {
        mBottomNavigation.setVisibility(View.VISIBLE);
    }

    @Override
    public void openLoginUi() {
        mMainMvpController.findOrCreateLoginView();
    }

    @Override
    public void hideToolbarUi() {
        mToolbar.setVisibility(View.GONE);
    }

    @Override
    public void showToolbarUi() {
        mToolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUserTypeForView(int userTypeForView) {
        mUserType = userTypeForView;
        Log.v("Hugh", "UserType: " + mUserType);
    }

    @Override
    public void openGroupListUi(ArrayList<Group> groups) {
        mPresenter.updateToolbar(getResources().getString(R.string.application_groups_list));
        mMainMvpController.findOrCreateGroupListView(groups);
    }

    @Override
    public void openGroupDetailsUi(Group group) {
        mPresenter.updateToolbar(getResources().getString(R.string.group_edit));
        mMainMvpController.findOrCreateGroupDetailsView(group);
    }

    @Override
    public void showLastFragmentUi() {
        MainActivity.this.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        String string = "";

        switch (menuItem.getItemId()) {

            case R.id.nav_loging_out:
//                string = getString(R.string.logout);
                UserManager.getInstance().logout(new UserManager.LogoutCallback() {
                    @Override
                    public void onSuccess() {
                        mPresenter.openLogin();
                        mPresenter.hideToolbarAndBottomNavigation();
                        mDrawerLayout.closeDrawers();
                    }
                });
//                Toast.makeText(this, getString(R.string._coming_soon, string), Toast.LENGTH_SHORT).show();
                break;
            default:
        }

        int clickGroup = menuItem.getItemId();

        if (!menuItem.getTitle().toString().equals(getString(R.string.ask_to_create_a_group)) &&
                UserManager.getInstance().getUserData().getUserType() == R.string.landlord) {
            if (clickGroup == mGroupMenu.getItem().getItemId()) {
                String groupNow = (String) menuItem.getTitle();
                mGroupMenu.getItem().setChecked(true);
                UserManager.getInstance().getUserData().setGroupNow(groupNow);
                mPresenter.loadArticles();
                mDrawerLayout.closeDrawers();
                Toast.makeText(this, getString(R.string.switch_group_to_, groupNow), Toast.LENGTH_SHORT).show();
            }
            Log.v(TAG, "clickGroup: " + clickGroup);
        } else if (menuItem.getTitle().toString().equals(getString(R.string.ask_to_create_a_group))) {
            Toast.makeText(this, getString(R.string.ask_to_go_to_group_list), Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
