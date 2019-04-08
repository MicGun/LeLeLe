package com.hugh.lelele.electricity_landlord;

import android.support.annotation.NonNull;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Electricity;
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

        void showElectrcityData();

        void loadRoomElectricityData(@NonNull String email, @NonNull String groupName,
                                     @NonNull String year, @NonNull String roomName);
    }
}
