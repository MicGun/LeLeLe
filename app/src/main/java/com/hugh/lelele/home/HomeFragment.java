package com.hugh.lelele.home;

import android.support.v4.app.Fragment;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomeFragment extends Fragment implements HomeContract.View {

    private HomeContract.Presenter mPresenter;
    private HomeAdapter mHomeAdapter;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
