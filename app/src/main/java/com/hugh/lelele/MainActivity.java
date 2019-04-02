package com.hugh.lelele;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private BottomNavigationView mBottomNavigation;
    private View mBadge;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private MainMvpController mMainMvpController;

    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        setContentView(R.layout.activity_main);

        mMainMvpController = MainMvpController.create(this);
        mPresenter.openHome();

        setToolbar();
        setBottomNavigation();
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

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

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

    }

    @Override
    public void openHomeUi() {
        mMainMvpController.findOrCreateHomeView();
    }

    @Override
    public void openApplicationUi() {

    }

    @Override
    public void openNotifyUi() {

    }

    @Override
    public void openProfileUi() {

    }
}
