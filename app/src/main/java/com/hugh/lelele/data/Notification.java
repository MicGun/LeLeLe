package com.hugh.lelele.data;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "notificationData", primaryKeys = {"mId"})
public class Notification {

    private String mAuthorEmail;
    private String mTitle;
    private String mMessage;
    @NonNull
    private String mId;
    private String mNotificationType;
    private long mTimeMillisecond;
    private boolean mIsRead;

    public Notification() {

        mAuthorEmail = "";
        mTitle = "";
        mMessage = "";
        mId = "";
        mNotificationType = "";
        mTimeMillisecond = -1;
        mIsRead = false;
    }

    public String getAuthorEmail() {
        return mAuthorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        mAuthorEmail = authorEmail;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    public void setId(@NonNull String id) {
        mId = id;
    }

    public String getNotificationType() {
        return mNotificationType;
    }

    public void setNotificationType(String notificationType) {
        mNotificationType = notificationType;
    }

    public long getTimeMillisecond() {
        return mTimeMillisecond;
    }

    public void setTimeMillisecond(long timeMillisecond) {
        mTimeMillisecond = timeMillisecond;
    }

    public boolean isRead() {
        return mIsRead;
    }

    public void setIsRead(boolean read) {
        mIsRead = read;
    }
}
