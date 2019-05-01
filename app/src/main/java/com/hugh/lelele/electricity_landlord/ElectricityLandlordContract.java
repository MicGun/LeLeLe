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

        void showHasEmptyDataToast();

        void toBackStack();
    }

    interface Presenter extends BasePresenter {

        void hideBottomNavigation();

        void showBottomNavigation();

        void updateToolbar(String title);

        void setRoomData(ArrayList<Room> rooms);

        void uploadElectricity(String landlordEmail, String groupName,
                               String roomName, String year, String month, Electricity electricity);

        void initialElectricityMonth(String landlordEmail, String groupName,
                                     String roomName, String year, String month);

        void checkRoomData(ArrayList<Room> rooms);

        void showLastFragment();

        void hideKeyBoard();
    }
}
