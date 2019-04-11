package com.hugh.lelele.group_list;

import android.support.v4.app.Fragment;

import com.hugh.lelele.home.HomeContract;

public class GroupListFragment extends Fragment implements GroupListContract.View {

    public static GroupListFragment newInstance() {
        return new GroupListFragment();
    }
    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

    }
}
