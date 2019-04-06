package com.hugh.lelele.electricity_landlord;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

public interface ElectricityLandlordContract {

    interface View extends BaseView<Presenter> {

        void showElectricityEditorUi(ArrayList<Room> rooms);
    }

    interface Presenter extends BasePresenter {

        void hideBottomNavigation();

        void showBottomNavigation();

        void updateToolbar(String title);

        void setRoomData(ArrayList<Room> rooms);
    }
}
