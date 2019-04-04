package com.hugh.lelele.application_landlord;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.source.LeLeLeRepository;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class AppLandlordPresenter implements AppLandlordContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final AppLandlordContract.View mAppLandlordView;

    public AppLandlordPresenter(@NonNull LeLeLeRepository leLeLeRepository,
                                @NonNull AppLandlordContract.View appLandlordView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mAppLandlordView = checkNotNull(appLandlordView, "appLandlordView cannot be null!");
        mAppLandlordView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadRoomList() {
        //給假資料

        ArrayList<Room> rooms = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            Room room = new Room();
            int n = i + 1;
            room.setRoomName("30" + n);

            rooms.add(room);
            if (rooms.size() == 5) {

            }
        }
    }

    @Override
    public void openElectricityEditor(ArrayList<Room> rooms) {

    }
}
