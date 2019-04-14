package com.hugh.lelele.data.source;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;

import java.util.ArrayList;

public interface LeLeLeDataSource {

    interface LandlordUserCallback {

        void onCompleted(Landlord landlord);

        void onError(String errorMessage);

    }

    interface TenantUserCallback {

        void onCompleted(Tenant tenant);

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

    interface UpdateGroupListCallback {

        void onCompleted(Group group);

        void onError(String errorMessage);
    }

    interface GetElectricityCallback {

        void onCompleted(ArrayList<Electricity> electricities);

        void onError(String errorMessage);
    }

    interface GetLandlordProfileCallback {

        void onCompleted(Landlord landlord);

        void onError(String errorMessage);
    }

    interface GetTenantProfileCallback {

        void onCompleted(Tenant tenant);

        void onError(String errorMessage);
    }

    void updateLandlordUser(@NonNull String email, @NonNull LandlordUserCallback callback);

    void updateTenantUser(@NonNull String email, @NonNull TenantUserCallback callback);

    void getRoomList(@NonNull String email, @NonNull String groupName, @NonNull GetRoomListCallback callback);

    void getGroupList(@NonNull String email, @NonNull GetGroupListCallback callback);

    void updateGroupList(@NonNull Group group, @NonNull String email, @NonNull UpdateGroupListCallback callback);

    void getElectricityList(@NonNull String email, @NonNull String groupName, @NonNull String year,
                            @NonNull String roomName, @NonNull GetElectricityCallback callback);

    void uploadElectricityData(String landlordEmail, String groupName, String roomName,
                               String year, String month, Electricity electricity);

    void initialElectricityMonthData(String landlordEmail, String groupName, String roomName, String year, String month);

    void getLandlordProfile(@NonNull String email, @NonNull GetLandlordProfileCallback callback);

    void getTenantProfile(@NonNull String email, @NonNull GetTenantProfileCallback callback);

    void updateRoom(@NonNull Room room, @NonNull String email, @NonNull String groupName);
}
