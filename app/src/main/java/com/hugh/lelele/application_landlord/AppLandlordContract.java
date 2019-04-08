package com.hugh.lelele.application_landlord;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

public interface AppLandlordContract {

    interface View extends BaseView<Presenter> {

        void showElectricityEditorUi(ArrayList<Room> rooms);
    }

    interface Presenter extends BasePresenter {

        void loadRoomList(String email, String groupName);

        void openElectricityEditor(ArrayList<Room> rooms);

    }
}
