package com.hugh.lelele.data;

import android.support.annotation.NonNull;

public class LeLeLeRepository implements LeLeLeDataSource {

    private static LeLeLeRepository INSTANCE = null;

    private LeLeLeDataSource mLeLeLeRemoteDataSource;

    public LeLeLeRepository(@NonNull LeLeLeDataSource leLeLeRemoteDataSource) {
        mLeLeLeRemoteDataSource = leLeLeRemoteDataSource;
    }

    public static LeLeLeRepository getInstance(LeLeLeDataSource remoteDataSource) {

        if (INSTANCE == null) {
            INSTANCE = new LeLeLeRepository(remoteDataSource);
        }
        return INSTANCE;
    }
}
