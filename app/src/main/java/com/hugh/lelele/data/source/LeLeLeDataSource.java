package com.hugh.lelele.data.source;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

public interface LeLeLeDataSource {

    interface LandlordUserCallback {

        void onCompleted(Landlord landlord);

        void onError(String errorMessage);

    }

    interface GetRoomListCallback {

        void onCompleted(ArrayList<Room> rooms);

        void onError(String errorMessage);

    }

    interface GetGroupListCallback {

        void onCompleted(ArrayList<Group> groups);

        void onError(String errorMessage);
    }

    void updateLandlordUser(@NonNull String email, @NonNull LandlordUserCallback callback);

    void getRoomList(@NonNull String email, @NonNull String groupName, @NonNull GetRoomListCallback callback);

    void getGroupList(@NonNull String email, @NonNull GetGroupListCallback callback);
}
