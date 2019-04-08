package com.hugh.lelele.electricity_landlord;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class ElectricityLandlordPresenter implements ElectricityLandlordContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final ElectricityLandlordContract.View mElectricityLandlordtView;
    ArrayList<Room> mRooms;

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
        mRooms = rooms;
//        mElectricityLandlordtView.showElectricityEditorUi(rooms);
    }

    @Override
    public void showElectrcityData() {
        mElectricityLandlordtView.showElectricityEditorUi(mRooms);
    }

    @Override
    public void loadRoomElectricityData(@NonNull String email, @NonNull String groupName,
                                        @NonNull String year, @NonNull String roomName) {
        mLeLeLeRepository.getElectricityList(email, groupName, year, roomName, new LeLeLeDataSource.GetElectricityCallback() {
            @Override
            public void onCompleted(ArrayList<Electricity> electricities) {
                Log.v("電費", "" + electricities.size());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}
