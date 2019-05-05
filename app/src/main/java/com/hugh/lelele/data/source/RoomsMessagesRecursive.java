package com.hugh.lelele.data.source;

import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Message;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

public class RoomsMessagesRecursive {

    private ArrayList<Room> mRooms;
    private String mLandlordEmail;
    private String mGroupName;
    private String mYear;
    private RoomsMessagesRecursiveCallback mCallback;

    private final LeLeLeRepository mLeLeLeRepository;

    public RoomsMessagesRecursive(ArrayList<Room> rooms, String landlordEmail,
                                  String groupName, LeLeLeRepository leLeLeRepository,
                                  RoomsMessagesRecursiveCallback callback) {
        mRooms = rooms;
        mLandlordEmail = landlordEmail;
        mGroupName = groupName;
        mCallback = callback;
        mLeLeLeRepository = leLeLeRepository;
        int count = 0;
        downloadRoomMessagesList(count);
    }

    private void downloadRoomMessagesList(int i) {
        final Room room = mRooms.get(i);
        i++;
        final int finalI = i;
        mLeLeLeRepository.getMessagesFromRoom(mLandlordEmail,
                mGroupName,
                room.getRoomName(), new LeLeLeDataSource.GetMessagesCallback() {

                    @Override
                    public void onCompleted(ArrayList<Message> messages) {
                        room.setMessages(messages);
                        if (finalI < mRooms.size()) {
                            downloadRoomMessagesList(finalI);
                        } else {
                            mCallback.onCompleted(mRooms);
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {

                    }
                });
    }
}
