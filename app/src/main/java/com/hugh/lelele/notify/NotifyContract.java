package com.hugh.lelele.notify;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Notification;

import java.util.ArrayList;

public interface NotifyContract {

    interface View extends BaseView<Presenter> {

        void showNotifications(ArrayList<Notification> notificationsView);

    }

    interface Presenter extends BasePresenter {

        void loadNotifications();

        void changeReadStatus(ArrayList<Notification> notifications);

        void updateNotifyBadge(int amount);
    }
}
