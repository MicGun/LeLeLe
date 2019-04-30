package com.hugh.lelele.notify;

import com.hugh.lelele.data.source.LeLeLeRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotifyPresenter implements NotifyContract.Presenter {

    private LeLeLeRepository mLeLeLeRepository;
    private NotifyContract.View mNotifyView;

    public NotifyPresenter(LeLeLeRepository leLeLeRepository, NotifyContract.View notifyView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mNotifyView = checkNotNull(notifyView, "notifyView cannot be null!");
        mNotifyView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
