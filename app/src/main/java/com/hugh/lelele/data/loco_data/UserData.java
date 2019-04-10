package com.hugh.lelele.data.loco_data;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

@Entity(tableName = "userData", primaryKeys = {"mEmail"})
public class UserData {

    private String mAssessToken;
    @NonNull
    private String mEmail;
    private String mId;
    private String mName;

    public UserData() {

        mEmail = "";
        mAssessToken = "";
        mId = "";
        mName = "";
    }

    public String getAssessToken() {
        return mAssessToken;
    }

    public void setAssessToken(String assessToken) {
        mAssessToken = assessToken;
    }

    @NonNull
    public String getEmail() {
        return mEmail;
    }

    public void setEmail(@NonNull String email) {
        mEmail = email;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
