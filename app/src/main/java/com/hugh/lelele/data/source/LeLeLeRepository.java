package com.hugh.lelele.data.source;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.Landlord;

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
}
