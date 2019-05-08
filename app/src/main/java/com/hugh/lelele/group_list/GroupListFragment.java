package com.hugh.lelele.group_list;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.component.GridSpacingItemDecoration;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.home.HomeContract;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class GroupListFragment extends Fragment implements GroupListContract.View, SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = GroupListFragment.class.getSimpleName();

    private GroupListContract.Presenter mPresenter;
    private GroupListAdapter mAdapter;
    private FloatingActionButton mFloatingAddGroupButton;
    private TextView mNotifyTextView;
    private ArrayList<Group> mGroups;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group_list, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_group_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1,
                getContext().getResources().getDimensionPixelOffset(R.dimen.space_detail_circle), true));

        mSwipeRefreshLayout = root.findViewById(R.id.swipe_container_group_list);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mNotifyTextView = root.findViewById(R.id.text_view_notify_group_list);

        checkNotifyTextViewStatus();

        mFloatingAddGroupButton = root.findViewById(R.id.button_add_group);
        mFloatingAddGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //給一個全新的Group
                Group group = new Group();
                mPresenter.openGroupDetails(group);
            }
        });

        return root;
    }

    private void checkNotifyTextViewStatus() {

        if (mNotifyTextView != null) {
            if (mGroups.size() == 0) {
                mNotifyTextView.setVisibility(View.VISIBLE);
            } else {
                mNotifyTextView.setVisibility(View.GONE);
            }
        }

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
        mGroups = groups;
        checkNotifyTextViewStatus();
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (mAdapter == null) {
            mAdapter = new GroupListAdapter(mPresenter);
            mAdapter.updateData(groups);
        } else {
            mAdapter.updateData(groups);
        }
    }

    @Override
    public void reLoadGroupList() {
        mPresenter.loadGroupList(UserManager.getInstance().getLandlord().getEmail());
        Log.d(TAG, "reLoadGroupList: ");
    }


    @Override
    public void onRefresh() {
        mPresenter.loadGroupList(UserManager.getInstance().getLandlord().getEmail());
        mPresenter.loadGroupListDrawerMenu();
        mSwipeRefreshLayout.setRefreshing(true);
    }
}
