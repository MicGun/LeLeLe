package com.hugh.lelele.group_list;

import com.hugh.lelele.data.source.LeLeLeRepository;

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
}
