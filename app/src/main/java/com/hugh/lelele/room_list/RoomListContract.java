package com.hugh.lelele.room_list;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

public interface RoomListContract {

    interface View extends BaseView<RoomListContract.Presenter> {

        void showGroupData(Group group);
    }

    interface Presenter extends BasePresenter {

        void hideBottomNavigation();

        void showBottomNavigation();

        void updateToolbar(String title);

        void loadGroupData();

        void openInvitationSending(Room room);
    }
}
