package com.hugh.lelele.data;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;
public class Message {

    private String mSenderEmail;
    private String mSenderPicture;
    private String mSenderType;
    private String mContent;
    private String mId;
    private long mTimeMillisecond;
    private boolean mIsRead;

    public Message() {

        mSenderEmail = "";
        mSenderPicture = "";
        mSenderType = "";
        mContent = "";
        mId = "";
        mTimeMillisecond = -1;
        mIsRead = false;
    }

    public String getSenderEmail() {
        return mSenderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        mSenderEmail = senderEmail;
    }

    public String getSenderPicture() {
        return mSenderPicture;
    }

    public void setSenderPicture(String senderPicture) {
        mSenderPicture = senderPicture;
    }

    public String getSenderType() {
        return mSenderType;
    }

    public void setSenderType(String senderType) {
        mSenderType = senderType;
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

    public long getTimeMillisecond() {
        return mTimeMillisecond;
    }

    public void setTimeMillisecond(long timeMillisecond) {
        mTimeMillisecond = timeMillisecond;
    }

    public boolean isRead() {
        return mIsRead;
    }

    public void setRead(boolean read) {
        mIsRead = read;
    }
}
