package com.hugh.lelele.data;

import java.util.ArrayList;

public class Room {

    private String mRoomName;
    private Tenant mTenant;
    private ArrayList<Electricity> mElectricities;
    private ArrayList<Message> mMessages;

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

    public ArrayList<Message> getMessages() {
        return mMessages;
    }

    public void setMessages(ArrayList<Message> messages) {
        mMessages = messages;
    }
}
