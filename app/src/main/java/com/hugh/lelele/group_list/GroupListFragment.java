package com.hugh.lelele.group_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.R;
import com.hugh.lelele.home.HomeContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class GroupListFragment extends Fragment implements GroupListContract.View {

    private GroupListContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group_list, container, false);

        return root;
    }

    public static GroupListFragment newInstance() {
        return new GroupListFragment();
    }

    @Override
    public void setPresenter(GroupListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
