package com.hugh.lelele.data;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "notificationData", primaryKeys = {"mId"})
public class Notification {

    private String mSenderEmail;
    private String mTitle;
    private String mContent;
    @NonNull
    private String mId;
    private String mNotificationType;
    private long mTimeMillisecond;
    private boolean mIsRead;

    public Notification() {

        mSenderEmail = "";
        mTitle = "";
        mContent = "";
        mId = "";
        mNotificationType = "";
        mTimeMillisecond = -1;
        mIsRead = false;
    }

    public String getSenderEmail() {
        return mSenderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        mSenderEmail = senderEmail;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
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
