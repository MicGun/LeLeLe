package com.hugh.lelele.data.source;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Landlord;

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
}
