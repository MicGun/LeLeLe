package com.hugh.lelele.group_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.home.HomeContract;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class GroupListFragment extends Fragment implements GroupListContract.View {

    private GroupListContract.Presenter mPresenter;
    private GroupListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mAdapter = new GroupListAdapter(mPresenter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group_list, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_group_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.hideBottomNavigation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.showBottomNavigation();
        mPresenter.updateToolbar(getResources().getString(R.string.application));
    }

    public static GroupListFragment newInstance() {
        return new GroupListFragment();
    }

    @Override
    public void setPresenter(GroupListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showGroupListUi(ArrayList<Group> groups) {
        if (mAdapter == null) {
            mAdapter = new GroupListAdapter(mPresenter);
            mAdapter.updateData(groups);
        } else {
            mAdapter.updateData(groups);
        }
    }
}
