package com.hugh.lelele.notify;

import android.util.Log;

import com.hugh.lelele.data.Notification;
import com.hugh.lelele.data.source.LeLeLeDataSource;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotifyPresenter implements NotifyContract.Presenter {

    private LeLeLeRepository mLeLeLeRepository;
    private NotifyContract.View mNotifyView;

    private static final String TAG = "NotifyPresenter";

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

        mLeLeLeRepository.getUserNotifications(UserManager.getInstance().getUserData().getEmail(),
                new LeLeLeDataSource.GetUserNotificationsCallback() {
            @Override
            public void onCompleted(ArrayList<Notification> notifications) {
                mNotifyView.showNotifications(notifications);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

    }

    @Override
    public void changeReadStatus(ArrayList<Notification> notifications) {

        ArrayList<Notification> notificationsBeRead = new ArrayList<>();

        int count = 0;
        for (Notification notification:notifications) {
            count++;

            if (!notification.isRead()) {
                //to create a new notification obj to avoid changing the read status of notification which is showing.
                Notification notificationNotRead = getNotificationNotRead(notification);
                notificationNotRead.setIsRead(true);
                notificationsBeRead.add(notificationNotRead);
            }

            if (count == notifications.size()) {
                updateNotificationsBeRead(notificationsBeRead);
            }
        }
    }

    @Override
    public void updateNotifyBadge(int amount) {
    }

    private void updateNotificationsBeRead(ArrayList<Notification> notificationsBeRead) {

        for (Notification notification:notificationsBeRead) {
            mLeLeLeRepository.updateNotificationRead(notification,
                    UserManager.getInstance().getUserData().getEmail());
        }
    }

    private Notification getNotificationNotRead(Notification notification) {
        Notification notificationNotRead = new Notification();

        notificationNotRead.setNotificationType(notification.getNotificationType());
        notificationNotRead.setTimeMillisecond(notification.getTimeMillisecond());
        notificationNotRead.setSenderEmail(notification.getSenderEmail());
        notificationNotRead.setTitle(notification.getTitle());
        notificationNotRead.setContent(notification.getContent());
        notificationNotRead.setIsRead(notification.isRead());
        notificationNotRead.setId(notification.getId());

        return notificationNotRead;
    }
}
