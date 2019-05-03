package com.hugh.lelele.data.source;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.hugh.lelele.data.Article;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Message;
import com.hugh.lelele.data.Notification;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.data.loco_data.UserData;

import org.json.JSONException;
import org.json.JSONObject;

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
            Tenant tenant = roomDocument.toObject(Tenant.class);
            room.setRoomName(roomDocument.getId());
            room.setTenant(tenant);
            rooms.add(room);
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

    public static Group parseGroup(DocumentSnapshot groupDoc) {

        Group group = new Group();
        group.setGroupName(groupDoc.getId());
        group.setGroupAddress(String.valueOf(groupDoc.get("address")));
        group.setGroupRoomNumber(String.valueOf(groupDoc.get("max_room_numbers")));
        group.setGroupTenantNumber(String.valueOf(groupDoc.get("tenant_numbers")));

        return group;

    }

    public static Landlord parseLandlordInfo(DocumentSnapshot landlordDoc) {

        Landlord landlord = new Landlord();

        landlord.setName((String) landlordDoc.get("name"));
        landlord.setAddress((String) landlordDoc.get("address"));
        landlord.setIdCardNumber((String) landlordDoc.get("ID_card_number"));
        landlord.setAssessToken((String) landlordDoc.get("assess_token"));
        landlord.setEmail((String) landlordDoc.get("email"));
        landlord.setId((String) landlordDoc.get("id"));
        landlord.setPhoneNumber((String) landlordDoc.get("phone_number"));
        landlord.setPicture((String) landlordDoc.get("picture"));

        return landlord;
    }

    public static Tenant parseTenantInfo(DocumentSnapshot tenantDoc) {

        Tenant tenant = new Tenant();

        tenant.setName((String) tenantDoc.get("name"));
        tenant.setPhoneNumber((String) tenantDoc.get("phone_number"));
        tenant.setEmail((String) tenantDoc.get("email"));
        tenant.setGroup((String) tenantDoc.get("group"));
        tenant.setId((String) tenantDoc.get("id"));
        tenant.setLandlordEmail((String) tenantDoc.get("landlord_email"));
        tenant.setAddress((String) tenantDoc.get("address"));
        tenant.setPicture((String) tenantDoc.get("picture"));
        tenant.setRoomNumber((String) tenantDoc.get("room_number"));
        tenant.setIdCardNumber((String) tenantDoc.get("ID_card_number"));
        tenant.setAssessToken((String) tenantDoc.get("assess_token"));

        return tenant;
    }

    public static UserData parseUserData(JSONObject object, UserData userData) {

        try {
            String name = object.getString("name");
            String email = object.getString("email");

            userData.setName(name);
            userData.setEmail(email);
            Log.v("Hugh", "Email: " + object.getString("email"));
            Log.v("Hugh", "Name: " + object.getString("name"));
            Log.v("Hugh", "Id: " + object.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userData;
    }

    public static ArrayList<Article> parseArticleList(ArrayList<DocumentSnapshot> articleDocuments) {

        ArrayList<Article> articles = new ArrayList<>();

        for (DocumentSnapshot articleDocument:articleDocuments) {
            Article article = articleDocument.toObject(Article.class);
            assert article != null;
            article.setArticleId(articleDocument.getId());
            articles.add(article);
        }

        return articles;
    }

    public static ArrayList<Message> parseMessageList(ArrayList<DocumentSnapshot> messageDocuments) {

        ArrayList<Message> messages = new ArrayList<>();

        for (DocumentSnapshot messageDocument:messageDocuments) {
            Message message = messageDocument.toObject(Message.class);
            messages.add(message);
        }
        return messages;
    }

    public static ArrayList<Notification> parseNotificationList(ArrayList<DocumentSnapshot> notificationDocuments) {

        ArrayList<Notification> notifications = new ArrayList<>();

        for (DocumentSnapshot notificationDocument:notificationDocuments) {
            Notification notification = notificationDocument.toObject(Notification.class);
            assert notification != null;
            notification.setId(notificationDocument.getId());
            notification.setIsRead((Boolean) notificationDocument.get("read"));
            notifications.add(notification);
        }

        return notifications;
    }
}
