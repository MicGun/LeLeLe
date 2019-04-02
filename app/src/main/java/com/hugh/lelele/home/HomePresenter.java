package com.hugh.lelele.home;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.LeLeLeRepository;

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
}
