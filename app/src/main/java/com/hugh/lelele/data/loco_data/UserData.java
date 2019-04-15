package com.hugh.lelele.data.loco_data;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "userData", primaryKeys = {"mId"})
public class UserData {

    private String mAssessToken;
    private String mEmail;
    @NonNull
    private String mId;
    private String mName;
    private String mPictureUrl;
    private int mUserType;
    private int mGroupNow;

    public UserData() {

        mEmail = "";
        mAssessToken = "";
        mId = "";
        mName = "";
        mPictureUrl = "";
    }

    public String getAssessToken() {
        return mAssessToken;
    }

    public void setAssessToken(String assessToken) {
        mAssessToken = assessToken;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    public void setId(@NonNull String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }

    public int getUserType() {
        return mUserType;
    }

    public void setUserType(int userType) {
        mUserType = userType;
    }

    public int getGroupNow() {
        return mGroupNow;
    }

    public void setGroupNow(int groupNow) {
        mGroupNow = groupNow;
    }
}
