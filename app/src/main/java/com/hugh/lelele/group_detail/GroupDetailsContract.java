package com.hugh.lelele.group_detail;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.group_list.GroupListContract;

import java.util.ArrayList;

public interface GroupDetailsContract {

    interface View extends BaseView<GroupDetailsContract.Presenter> {

        void showGroupDetailsUi(Group group);

        void updateRoomDataUi(ArrayList<Room> rooms);

        void deleteRoomData(Room room);

        void resetDrawerUi();
    }

    interface Presenter extends BasePresenter {

        void updateToolbar(String title);

        void setGroupData(Group group);

        void updateRoomData(ArrayList<Room> rooms);

        void loadRoomListFromGroupDetails(String email, String groupName);

        void uploadGroup(Group group);

        void showLastFragment();

        void deleteRoom(Room room);

        void deleteRemoteRoom(Room room, String groupName, String email);

        void notifyGroupListChanged();

        void loadGroupListDrawerMenu();

        void resetDrawer();

        void hideKeyBoard();
    }
}
