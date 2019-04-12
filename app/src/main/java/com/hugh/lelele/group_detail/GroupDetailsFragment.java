package com.hugh.lelele.group_detail;

import android.support.v4.app.Fragment;

import com.hugh.lelele.data.Group;

import java.util.ArrayList;

public class GroupDetailsFragment extends Fragment implements GroupDetailsContract.View {
    @Override
    public void showGroupListUi(ArrayList<Group> groups) {

    }

    public static GroupDetailsFragment newInstance() {
        return new GroupDetailsFragment();
    }

    @Override
    public void setPresenter(GroupDetailsContract.Presenter presenter) {

    }
}
