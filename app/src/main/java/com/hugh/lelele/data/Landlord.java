package com.hugh.lelele.data;

import java.util.ArrayList;

public class Landlord {

    private String mName;
    private String mEmail;
    private String mId;
    private String mAddress;
    private String mAssessToken;
    private String mPhoneNumber;
    private String mIdCardNumber;
    private String mPicture;
    private ArrayList<Group> mGroups;

    public Landlord() {
        mName = "";
        mEmail = "";
        mId = "";
        mAddress = "";
        mAssessToken = "";
        mPhoneNumber = "";
        mIdCardNumber = "";
        mPicture = "";
        mGroups = new ArrayList<>();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getAssessToken() {
        return mAssessToken;
    }

    public void setAssessToken(String assessToken) {
        mAssessToken = assessToken;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getIdCardNumber() {
        return mIdCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        mIdCardNumber = idCardNumber;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public ArrayList<Group> getGroups() {
        return mGroups;
    }

    public void setGroups(ArrayList<Group> groups) {
        mGroups = groups;
    }
}
