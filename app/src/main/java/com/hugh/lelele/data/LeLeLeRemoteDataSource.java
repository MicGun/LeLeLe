package com.hugh.lelele.data;

public class LeLeLeRemoteDataSource implements LeLeLeDataSource {

    private static LeLeLeRemoteDataSource INSTANCE;

    public static LeLeLeRemoteDataSource getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new LeLeLeRemoteDataSource();
        }

        return INSTANCE;
    }

    public LeLeLeRemoteDataSource() {
    }
}
