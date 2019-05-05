package com.hugh.lelele.group_detail;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.data.source.RoomsElectricityRecursive;
import com.hugh.lelele.data.source.RoomsElectricityRecursiveCallback;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;
import java.util.Calendar;

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

    @Override
    public void loadRoomListFromGroupDetails(final String email, final String groupName) {
        mLeLeLeRepository.getRoomList(email, groupName, new LeLeLeDataSource.GetRoomListCallback() {
            @Override
            public void onCompleted(ArrayList<Room> rooms) {

                new RoomsElectricityRecursive(rooms, email, groupName,
                        String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
                        mLeLeLeRepository, new RoomsElectricityRecursiveCallback() {
                    @Override
                    public void onCompleted(ArrayList<Room> roomArrayList) {
                        mGroupDetailsView.updateRoomDataUi(roomArrayList);
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }

    @Override
    public void uploadGroup(Group group) {
        mLeLeLeRepository.updateGroupList(group, UserManager.getInstance().getLandlord().getEmail(),
                new LeLeLeDataSource.UpdateGroupListCallback() {
                    @Override
                    public void onCompleted() {
                        showLastFragment();
                        loadGroupListDrawerMenu();
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
    }

    @Override
    public void showLastFragment() {

    }

    @Override
    public void deleteRoom(Room room) {
        mGroupDetailsView.deleteRoomData(room);
    }

    @Override
    public void deleteRemoteRoom(Room room, String groupName, String email) {
        mLeLeLeRepository.deleteRoom(room, email, groupName);
    }

    @Override
    public void notifyGroupListChanged() {

    }

    @Override
    public void loadGroupListDrawerMenu() {

    }

    @Override
    public void hideKeyBoard() {

    }
}
