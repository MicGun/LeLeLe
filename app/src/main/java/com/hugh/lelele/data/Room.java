package com.hugh.lelele.data;

import java.util.ArrayList;

public class Room {

    private String mRoomName;
    private Tenant mTenant;
    private ArrayList<Electricity> mElectricities;

    public Room() {

        mElectricities = new ArrayList<>();
        mTenant = new Tenant();
        mRoomName = "";
    }

    public String getRoomName() {
        return mRoomName;
    }

    public void setRoomName(String roomName) {
        mRoomName = roomName;
    }

    public Tenant getTenant() {
        return mTenant;
    }

    public void setTenant(Tenant tenant) {
        mTenant = tenant;
    }

    public ArrayList<Electricity> getElectricities() {
        return mElectricities;
    }

    public void setElectricities(ArrayList<Electricity> electricities) {
        mElectricities = electricities;
    }
}
