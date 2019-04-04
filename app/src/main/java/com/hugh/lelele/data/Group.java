package com.hugh.lelele.data;

import java.util.ArrayList;

public class Group {

    private String mGroupName;
    private String mGroupAddress;
    private String mGroupRoomNumber;
    private String mGroupTenantNumber;
    private ArrayList<Room> mRooms;

    public Group() {
        mGroupName = "";
        mGroupAddress = "";
        mGroupRoomNumber = "";
        mGroupTenantNumber = "";
        mRooms = new ArrayList<>();
    }

    public String getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String groupName) {
        mGroupName = groupName;
    }

    public String getGroupAddress() {
        return mGroupAddress;
    }

    public void setGroupAddress(String groupAddress) {
        mGroupAddress = groupAddress;
    }

    public String getGroupRoomNumber() {
        return mGroupRoomNumber;
    }

    public void setGroupRoomNumber(String groupRoomNumber) {
        mGroupRoomNumber = groupRoomNumber;
    }

    public String getGroupTenantNumber() {
        return mGroupTenantNumber;
    }

    public void setGroupTenantNumber(String groupTenantNumber) {
        mGroupTenantNumber = groupTenantNumber;
    }

    public ArrayList<Room> getRooms() {
        return mRooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        mRooms = rooms;
    }
}
