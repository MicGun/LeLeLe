package com.hugh.lelele.data.source;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

public class LeLeLeParser {

    public static Landlord getLandlordData(DocumentSnapshot documentSnapshot) {

        Landlord landlord = new Landlord();

        landlord.setEmail(documentSnapshot.getId());
        landlord.setName((String) documentSnapshot.get("name"));
        landlord.setAddress((String) documentSnapshot.get("address"));
        landlord.setId((String) documentSnapshot.get("id"));
        landlord.setIdCardNumber((String) documentSnapshot.get("ID_card_number"));
        landlord.setPhoneNumber((String) documentSnapshot.get("phone_number"));
//        landlord.setGroups(getGroups(documentSnapshot));

        Log.v("Landlord Info", "Landlord:" + landlord);

        return landlord;
    }

    public static ArrayList<Room> parseRoomList(ArrayList<DocumentSnapshot> roomDocuments) {

        ArrayList<Room> rooms = new ArrayList<>();

        for (DocumentSnapshot roomDocument:roomDocuments) {
            Room room = new Room();
            room.setRoomName(roomDocument.getId());
            rooms.add(room);
            //ToDo to think about that the data structure of tenants
        }

        return rooms;
    }

    public static ArrayList<Group> parseGroupList(ArrayList<DocumentSnapshot> groupDocuments) {

        ArrayList<Group> groups = new ArrayList<>();

        for (DocumentSnapshot groupDocument:groupDocuments) {
            Group group = new Group();
            group.setGroupName(groupDocument.getId());
            group.setGroupAddress(String.valueOf(groupDocument.get("address")));
            group.setGroupRoomNumber(String.valueOf(groupDocument.get("max_room_numbers")));
            group.setGroupTenantNumber(String.valueOf(groupDocument.get("tenant_numbers")));
            groups.add(group);
        }

        return groups;
    }
}
