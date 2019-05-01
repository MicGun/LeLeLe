package com.hugh.lelele.data.loco_data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.hugh.lelele.data.Notification;

@Database(entities = {Notification.class}, version = 1)
public abstract class NotificationDatabase extends RoomDatabase {
    public abstract NotificationDAO getNotificationDAO();
}
