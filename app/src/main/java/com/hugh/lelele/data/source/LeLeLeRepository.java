package com.hugh.lelele.data.source;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class LeLeLeRepository implements LeLeLeDataSource {

    private static LeLeLeRepository INSTANCE = null;

    private LeLeLeDataSource mLeLeLeRemoteDataSource;

    public LeLeLeRepository(@NonNull LeLeLeDataSource leLeLeRemoteDataSource) {
        mLeLeLeRemoteDataSource = checkNotNull(leLeLeRemoteDataSource);
    }

    public static LeLeLeRepository getInstance(LeLeLeDataSource remoteDataSource) {

        if (INSTANCE == null) {
            INSTANCE = new LeLeLeRepository(remoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void updateLandlordUser(@NonNull String email, @NonNull final LandlordUserCallback callback) {
        mLeLeLeRemoteDataSource.updateLandlordUser(email, new LandlordUserCallback() {
            @Override
            public void onCompleted(Landlord landlord) {
                callback.onCompleted(landlord);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void getRoomList(@NonNull String email, @NonNull String groupName, @NonNull final GetRoomListCallback callback) {
        mLeLeLeRemoteDataSource.getRoomList(email, groupName, new GetRoomListCallback() {
            @Override
            public void onCompleted(ArrayList<Room> rooms) {
                callback.onCompleted(rooms);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }

    @Override
    public void getGroupList(@NonNull String email, @NonNull final GetGroupListCallback callback) {
        mLeLeLeRemoteDataSource.getGroupList(email, new GetGroupListCallback() {
            @Override
            public void onCompleted(ArrayList<Group> groups) {
                callback.onCompleted(groups);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }
}
