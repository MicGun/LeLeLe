package com.hugh.lelele.group_list;

import android.util.Log;

import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class GroupListPresenter implements GroupListContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final GroupListContract.View mGroupListView;

    public GroupListPresenter(LeLeLeRepository leLeLeRepository, GroupListContract.View groupListView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mGroupListView = checkNotNull(groupListView, "groupListView cannot be null!");
    }

    @Override
    public void start() {

    }

    @Override
    public void hideBottomNavigation() {

    }

    @Override
    public void showBottomNavigation() {

    }

    @Override
    public void updateToolbar(String title) {

    }

    @Override
    public void setGroupsData(ArrayList<Group> groups) {
        mGroupListView.showGroupListUi(groups);
    }

    @Override
    public void openGroupDetails(Group group) {

    }

    @Override
    public void loadGroupList(String email) {
        mLeLeLeRepository.getGroupList(email, new LeLeLeDataSource.GetGroupListCallback() {
            @Override
            public void onCompleted(ArrayList<Group> groups) {
                mGroupListView.showGroupListUi(groups);
                Log.v("MainPresenter", "Group Number: " + groups.size());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void notifyGroupListChanged() {
        mGroupListView.reLoadGroupList();
    }
}
