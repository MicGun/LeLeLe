package com.hugh.lelele.data;

public class Tenant {

    private String mEmail;
    private String mName;
    private String mAddress;
    private String mPhoneNumber;
    private String mGroup;
    private String mId;
    private String mLandlordEmail;
    private String mPicture;
    private String mRoomNumber;
    private String mIdCardNumber;
    private String mAssessToken;
    private boolean mIsBinding;
    private boolean mIsInviting;
    private String mPhoneToken;

    public Tenant() {
        mEmail = "";
        mName = "";
        mAddress = "";
        mPhoneNumber = "";
        mGroup = "";
        mId = "";
        mLandlordEmail = "";
        mPicture = "";
        mRoomNumber = "";
        mIdCardNumber = "";
        mAssessToken = "";
        mIsBinding = false;
        mIsInviting = false;
        mPhoneToken = "";
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String group) {
        mGroup = group;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getLandlordEmail() {
        return mLandlordEmail;
    }

    public void setLandlordEmail(String landlordEmail) {
        mLandlordEmail = landlordEmail;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public String getRoomNumber() {
        return mRoomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        mRoomNumber = roomNumber;
    }

    public String getIdCardNumber() {
        return mIdCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        mIdCardNumber = idCardNumber;
    }

    public String getAssessToken() {
        return mAssessToken;
    }

    public void setAssessToken(String assessToken) {
        mAssessToken = assessToken;
    }

    public boolean isBinding() {
        return mIsBinding;
    }

    public void setBinding(boolean binding) {
        mIsBinding = binding;
    }

    public boolean isInviting() {
        return mIsInviting;
    }

    public void setInviting(boolean inviting) {
        mIsInviting = inviting;
    }

    public String getPhoneToken() {
        return mPhoneToken;
    }

    public void setPhoneToken(String phoneToken) {
        mPhoneToken = phoneToken;
    }
}
