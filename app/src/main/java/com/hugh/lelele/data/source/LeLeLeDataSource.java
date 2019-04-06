package com.hugh.lelele.data.source;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.Landlord;

public interface LeLeLeDataSource {

    interface LandlordUserCallback {

        void onCompleted(Landlord landlord);

        void onError(String errorMessage);

    }

    void updateLandlordUser(@NonNull String email, @NonNull LandlordUserCallback callback);
}
