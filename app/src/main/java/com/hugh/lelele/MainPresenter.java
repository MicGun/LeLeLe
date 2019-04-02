package com.hugh.lelele;


/*
* 主要用來implement所有fragment的presenter，
* 讓其作為所有fragment的presenter。
* */

import com.hugh.lelele.data.LeLeLeRepository;
import com.hugh.lelele.home.HomeContract;
import com.hugh.lelele.home.HomePresenter;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainPresenter implements MainContract.Presenter, HomeContract.Presenter {

    private final LeLeLeRepository mLeLeLeRepository;
    private MainContract.View mMainView;

    private HomePresenter mHomePresenter;

    public MainPresenter(LeLeLeRepository leLeLeRepository, MainContract.View mainView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leLeLeRepository cannot be null!");;
        mMainView = checkNotNull(mainView, "mainView cannot be null!");;
        mMainView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void updateNotifyBadge() {
        //ToDo Change five to not hard code.
        mMainView.updateNotifyBadgeUi(5);
    }

    @Override
    public void updateToolbar(String title) {
        mMainView.setToolbarTitleUi(title);
    }

    void setHomePresenter(HomePresenter homePresenter) {
        mHomePresenter = checkNotNull(homePresenter);
    }

    @Override
    public void openHome() {
        mMainView.openHomeUi();
    }

    @Override
    public void openApplication() {

    }

    @Override
    public void openNotify() {

    }

    @Override
    public void openProfile() {

    }
}
