package com.hugh.lelele.group_detail;

import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.source.LeLeLeRepository;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class GroupDetailsPresenter implements GroupDetailsContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final GroupDetailsContract.View mGroupDetailsView;

    public GroupDetailsPresenter(LeLeLeRepository leLeLeRepository, GroupDetailsContract.View groupDetailsView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mGroupDetailsView = checkNotNull(groupDetailsView, "groupDetailsView cannot be null!");
    }

    @Override
    public void start() {

    }

    @Override
    public void updateToolbar(String title) {

    }

    @Override
    public void setGroupData(Group group) {

        mGroupDetailsView.showGroupDetailsUi(group);
    }

    @Override
    public void updateRoomData(ArrayList<Room> rooms) {
        mGroupDetailsView.updateRoomDataUi(rooms);
    }
}
