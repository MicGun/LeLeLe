package com.hugh.lelele.data.loco_data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.hugh.lelele.data.Notification;

import java.util.List;

@Dao
public interface NotificationDAO {

    @Insert
    public void insert(Notification... items);

    @Update
    public void update(Notification... items);

    @Delete
    public void delete(Notification item);

    @Query("SELECT * FROM notificationData")
    public List<Notification> getItems();

    @Query("SELECT * FROM notificationData WHERE mId LIKE :id")
    public Notification getNotificationById(String id);

    @Query("DELETE FROM notificationData")
    public void nukeTable();
}
