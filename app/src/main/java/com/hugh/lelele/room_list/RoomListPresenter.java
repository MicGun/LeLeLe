package com.hugh.lelele.room_list;

import android.view.View;

import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.UserManager;

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

    @Override
    public void loadGroupData() {
        mLeLeLeRepository.getGroupData(UserManager.getInstance().getLandlord().getEmail(),
                UserManager.getInstance().getUserData().getGroupNow(), new LeLeLeDataSource.GetGroupDataCallback() {
                    @Override
                    public void onCompleted(Group group) {
                        mRoomListView.showGroupData(group);
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
    }

    @Override
    public void openInvitationSending(Room room) {

    }

    @Override
    public void openInvitationActionDialog(View view) {

    }
}
