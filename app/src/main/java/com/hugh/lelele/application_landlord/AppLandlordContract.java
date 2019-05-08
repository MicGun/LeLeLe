package com.hugh.lelele.application_landlord;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

public interface AppLandlordContract {

    interface View extends BaseView<Presenter> {

        void showElectricityEditorUi(ArrayList<Room> rooms);

        void showGroupListUi(ArrayList<Group> groups);

        void showProgressBar(boolean needs2Show);
    }

    interface Presenter extends BasePresenter {

        void loadRoomList(String email, String groupName);

        void openElectricityEditor(ArrayList<Room> rooms);

        void loadGroupListFromApp(String email);

        void openGroupList(ArrayList<Group> groups);

        void openRoomList();

        void openMessagingList();

    }
}
