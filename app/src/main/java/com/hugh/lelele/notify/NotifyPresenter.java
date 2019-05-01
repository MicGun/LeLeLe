package com.hugh.lelele.notify;

import com.hugh.lelele.data.Notification;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;

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

    @Override
    public void loadNotifications() {

        mLeLeLeRepository.getUserNotifications(UserManager.getInstance().getUserData().getEmail(), new LeLeLeDataSource.GetUserNotificationsCallback() {
            @Override
            public void onCompleted(ArrayList<Notification> notifications) {
                mNotifyView.showNotifications(notifications);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

    }
}
