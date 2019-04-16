package com.hugh.lelele.electricity_landlord;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.data.source.RoomsElectricityRecursive;
import com.hugh.lelele.data.source.RoomsElectricityRecursiveCallback;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;
import java.util.Calendar;

import static com.google.common.base.Preconditions.checkNotNull;

public class ElectricityLandlordPresenter implements ElectricityLandlordContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final ElectricityLandlordContract.View mElectricityLandlordtView;
    ArrayList<Room> mRooms;

    private final String TAG = ElectricityLandlordPresenter.class.getSimpleName();

    public ElectricityLandlordPresenter(@NonNull LeLeLeRepository leLeLeRepository,
                                      @NonNull ElectricityLandlordContract.View electricityLandlordView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mElectricityLandlordtView = checkNotNull(electricityLandlordView, "electricityTenantView cannot be null!");
        mElectricityLandlordtView.setPresenter(this);
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
    public void setRoomData(ArrayList<Room> rooms) {

        //ToDo: replace the landlord info by user manager.
        //call RoomsElectricityRecursive to get electric fee for each room.
        new RoomsElectricityRecursive(rooms, UserManager.getInstance().getLandlord().getEmail(),
                UserManager.getInstance().getUserData().getGroupNow(),
                String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
                mLeLeLeRepository, new RoomsElectricityRecursiveCallback() {
            @Override
            public void onCompleted(ArrayList<Room> roomArrayList) {
                mRooms = roomArrayList;
                mElectricityLandlordtView.showElectricityEditorUi(mRooms);
                Log.v(TAG, "RoomsElectricityRecursive Working");
            }
        });
    }

    @Override
    public void uploadElectricity(String landlordEmail, String groupName, String roomName,
                                  String year, String month, Electricity electricity) {
        mLeLeLeRepository.uploadElectricityData(landlordEmail, groupName, roomName, year, month, electricity);
    }

    @Override
    public void initialElectricityMonth(String landlordEmail, String groupName, String roomName, String year, String month) {
        mLeLeLeRepository.initialElectricityMonthData(landlordEmail, groupName, roomName, year, month);
    }
}
