package com.hugh.lelele.room_list;

import com.hugh.lelele.data.source.LeLeLeRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class RoomListPresenter implements RoomListContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private RoomListContract.View mRoomListView;

    public RoomListPresenter(LeLeLeRepository leLeLeRepository, RoomListContract.View roomListView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mRoomListView = checkNotNull(roomListView, "roomListView cannot be null!");
        mRoomListView.setPresenter(this);
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
