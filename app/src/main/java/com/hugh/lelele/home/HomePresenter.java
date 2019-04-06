package com.hugh.lelele.home;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class HomePresenter implements HomeContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private final HomeContract.View mHomeView;

    public HomePresenter(@NonNull LeLeLeRepository leLeLeRepository,
                         @NonNull HomeContract.View homeView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mHomeView = checkNotNull(homeView, "homeView cannot be null!");
        mHomeView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadLandlord(String email) {
        mLeLeLeRepository.updateLandlordUser(email, new LeLeLeDataSource.LandlordUserCallback() {
            @Override
            public void onCompleted(Landlord landlord) {
                Log.v("HomePresenter", "Landlord: " + landlord.getIdCardNumber());
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}
