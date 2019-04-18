package com.hugh.lelele.room_list;

import android.util.Log;
import android.view.View;

import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.Constants;
import com.hugh.lelele.util.UserManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class RoomListPresenter implements RoomListContract.Presenter {

    private static final String TAG = "RoomListPresenter";

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
    public void openInvitationActionDialog(View view, Room room) {

    }

    @Override
    public void cancelInvitingAction(Room room) {
        resetRoom(room);
        resetTenant(room.getTenant());
        loadGroupData();
        Log.v(TAG, "cancelInvitingAction: ");
    }

    private void resetRoom(Room room) {
        Room emptyRoom = new Room();
        emptyRoom.setRoomName(room.getRoomName());
        emptyRoom.getTenant().setRoomNumber(room.getRoomName());
        mLeLeLeRepository.updateRoom(emptyRoom, UserManager.getInstance().getLandlord().getEmail(),
                UserManager.getInstance().getUserData().getGroupNow());
    }

    private void resetTenant(Tenant tenant) {
        tenant.setRoomNumber(Constants.EMPTY);
        tenant.setLandlordEmail(Constants.EMPTY);
        tenant.setGroup(Constants.EMPTY);
        tenant.setBinding(false);
        tenant.setInviting(false);
        mLeLeLeRepository.uploadTenant(tenant);
    }
}
