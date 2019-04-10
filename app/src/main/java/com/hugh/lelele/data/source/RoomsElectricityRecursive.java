package com.hugh.lelele.data.source;

import android.util.Log;

import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

public class RoomsElectricityRecursive {

    private ArrayList<Room> mRooms;
    private String mLandlordEmail;
    private String mGroupName;
    private String mYear;
    private RoomsElectricityRecursiveCallback mCallback;

    private final LeLeLeRepository mLeLeLeRepository;

    public RoomsElectricityRecursive(ArrayList<Room> rooms, String landlordEmail,
                                     String groupName, String year,
                                     LeLeLeRepository leLeLeRepository,
                                     RoomsElectricityRecursiveCallback callback) {
        mRooms = rooms;
        mLandlordEmail = landlordEmail;
        mGroupName = groupName;
        mYear = year;
        mCallback = callback;
        mLeLeLeRepository = leLeLeRepository;
        int count = 0;
        downloadRoomElectricityList(count);
    }

    private void downloadRoomElectricityList(int i) {
        final Room room = mRooms.get(i);
        i++;
        final int finalI = i;
        mLeLeLeRepository.getElectricityList(mLandlordEmail,
                mGroupName, mYear,
                room.getRoomName(), new LeLeLeDataSource.GetElectricityCallback() {
                    @Override
                    public void onCompleted(ArrayList<Electricity> electricities) {
                        room.setElectricities(electricities);
                        if (finalI < mRooms.size()) {
                            downloadRoomElectricityList(finalI);
                        } else {
                            mCallback.onCompleted(mRooms);
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
    }
}
