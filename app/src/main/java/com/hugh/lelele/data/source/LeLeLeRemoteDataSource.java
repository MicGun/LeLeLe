package com.hugh.lelele.data.source;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hugh.lelele.R;
import com.hugh.lelele.data.Article;
import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Message;
import com.hugh.lelele.data.Notification;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.Tenant;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class LeLeLeRemoteDataSource implements LeLeLeDataSource {

    private final static String LANDLORDS = "landlords";
    private final static String TENANTS = "tenants";
    private final static String GROUPS = "groups";
    private final static String ROOMS = "rooms";
    private final static String ELECTRICITY_FEE = "electricity_fee";
    private final static String ARTICLES = "articles";
    private final static String NOTIFICATION = "notification";
    private final static String MESSAGES = "messages";

    /*Electricity Data*/
    private final static String PRICE = "price";
    private final static String SCALE_LAST = "scale_last";
    private final static String SCALE_THIS = "scale_this";
    private final static String TIME = "time";
    private final static String TOTAL_CONSUMPTION = "total_consumption";

    private ListenerRegistration  mMessageListener;

    private final static String TAG = LeLeLeRemoteDataSource.class.getSimpleName();

    private static LeLeLeRemoteDataSource INSTANCE;
    private static FirebaseFirestore mFirebaseFirestore;

    public static LeLeLeRemoteDataSource getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new LeLeLeRemoteDataSource();
        }

        mFirebaseFirestore = FirebaseFirestore.getInstance();

        return INSTANCE;
    }

    public LeLeLeRemoteDataSource() {
    }

    private static DocumentReference landlordDocument(String landlordEmail) {
        return mFirebaseFirestore.collection(LANDLORDS)
                .document(landlordEmail);
    }

    private static DocumentReference tenantDocument(String tenantEmail) {
        return mFirebaseFirestore.collection(TENANTS)
                .document(tenantEmail);
    }

    private CollectionReference userArticleCollection(@NonNull String email, @NonNull String userType) {
        return mFirebaseFirestore.collection(userType)
                .document(email)
                .collection(ARTICLES);
    }

    private DocumentReference userArticleDocument(@NonNull String email, @NonNull String userType) {
        return userArticleCollection(email, userType)
                .document();
    }

    private CollectionReference userNotificationCollection(@NonNull String email, @NonNull String userType) {
        return mFirebaseFirestore.collection(userType)
                .document(email)
                .collection(NOTIFICATION);
    }

    private DocumentReference userNotificationDocument(@NonNull String email, @NonNull String userType, @NonNull String notificationId) {
        return userNotificationCollection(email, userType)
                .document(notificationId);
    }

    private CollectionReference groupCollection(@NonNull String email) {
        return landlordDocument(email)
                .collection(GROUPS);
    }

    private DocumentReference groupDocument(@NonNull String email, String groupName) {
        return groupCollection(email)
                .document(groupName);
    }

    private CollectionReference roomCollection(@NonNull String email, @NonNull String groupName) {
        return groupDocument(email, groupName)
                .collection(ROOMS);
    }

    private DocumentReference roomDocument(@NonNull String email, @NonNull String groupName, @NonNull String roomName) {
        return roomCollection(email, groupName)
                .document(roomName);
    }

    private CollectionReference electricityCollection(@NonNull String email, @NonNull String groupName, @NonNull String roomName) {
        return roomDocument(email, groupName, roomName)
                .collection(ELECTRICITY_FEE);
    }

    private DocumentReference electricityDocument(@NonNull String email, @NonNull String groupName,
                                                      @NonNull String roomName, @NonNull String year) {
        return electricityCollection(email, groupName, roomName)
                .document(year);
    }

    private CollectionReference messageCollection(@NonNull String email, @NonNull String groupName, @NonNull String roomName) {
        return roomDocument(email, groupName, roomName)
                .collection(MESSAGES);
    }

    private DocumentReference messageDocument(@NonNull String email, @NonNull String groupName, @NonNull String roomName, @NonNull String id) {
        return messageCollection(email, groupName, roomName)
                .document(id);
    }

    private CollectionReference groupArticleCollection(@NonNull String email, @NonNull String groupName) {
        return groupDocument(email, groupName)
                .collection(ARTICLES);
    }

    private DocumentReference groupArticleDocument(@NonNull String email, @NonNull String groupName, @NonNull String articleId) {
        return groupArticleCollection(email, groupName)
                .document(articleId);
    }

    /*
     * 去拿房東資訊，若不存在則新創一個
     * */
    @Override
    public void updateLandlordUser(@NonNull final String email, @NonNull final LandlordUserCallback callback) {
        landlordDocument(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot landlordDoc = task.getResult();
                            assert landlordDoc != null;
                            if (landlordDoc.exists()) {
                                Landlord landlord = LeLeLeParser.parseLandlordInfo(landlordDoc);
                                //每次重新登入就更新一次token
                                landlord.setAssessToken(UserManager.getInstance().getUserData().getAssessToken());
                                uploadLandlord(landlord);
                                callback.onCompleted(landlord);
                            } else {
                                Map<String, Object> user = getInitializeLandlordMap(email);
                                landlordDocument(email)
                                        .set(user);

                                //setup landlord in app
                                Landlord landlord = new Landlord();
                                landlord.setAssessToken(UserManager.getInstance().getUserData().getAssessToken());
                                landlord.setId(UserManager.getInstance().getUserData().getId());
                                landlord.setName(UserManager.getInstance().getUserData().getName());
                                landlord.setPicture(UserManager.getInstance().getUserData().getPictureUrl());
                                callback.onCompleted(landlord);
                            }
                        } else {
                            callback.onError(String.valueOf(task.getException()));
                        }
                    }
                });
    }

    private Map<String, Object> getInitializeLandlordMap(@NonNull String email) {
        //create and initialize a landlord
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("ID_card_number", "");
        user.put("address", "");
        user.put("assess_token", UserManager.getInstance().getUserData().getAssessToken());
        user.put("phone_number", "");
        user.put("id", UserManager.getInstance().getUserData().getId());
        user.put("name", UserManager.getInstance().getUserData().getName());
        user.put("picture", UserManager.getInstance().getUserData().getPictureUrl());
        return user;
    }

    /*
     * 去拿房客資訊，若不存在則新創一個
     * */
    @Override
    public void updateTenantUser(@NonNull final String email, @NonNull final TenantUserCallback callback) {
        tenantDocument(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot tenantDoc = task.getResult();
                            assert tenantDoc != null;
                            if (tenantDoc.exists()) {
                                final Tenant tenant = tenantDoc.toObject(Tenant.class);
                                tenant.setAssessToken(UserManager.getInstance().getUserData().getAssessToken());
                                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (task.isSuccessful()) {
                                            String token = task.getResult().getToken();
                                            tenant.setPhoneToken(token);
                                            uploadTenant(tenant);
                                        }
                                        callback.onCompleted(tenant);
                                    }
                                });
                                Log.v(TAG, "Tenant is already exist!");
                            } else {

                                final Tenant tenant = new Tenant();
                                tenant.setAssessToken(UserManager.getInstance().getUserData().getAssessToken());
                                tenant.setId(UserManager.getInstance().getUserData().getId());
                                tenant.setName(UserManager.getInstance().getUserData().getName());
                                tenant.setPicture(UserManager.getInstance().getUserData().getPictureUrl());
                                tenant.setEmail(UserManager.getInstance().getUserData().getEmail());
                                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (task.isSuccessful()) {
                                            String token = task.getResult().getToken();
                                            tenant.setPhoneToken(token);
                                        }
                                        tenantDocument(email)
                                                .set(tenant);
                                        callback.onCompleted(tenant);
                                    }
                                });
                                Log.v(TAG, "Create a new tenant: " + tenant.getName());
                            }
                        } else {
                            callback.onError(String.valueOf(task.getException()));
                        }
                    }
                });
    }

    /*
     * 去拿房間清單
     * 需要知道房東email
     * 與group名稱
     * */
    @Override
    public void getRoomList(@NonNull final String email, @NonNull final String groupName, @NonNull final GetRoomListCallback callback) {
        roomCollection(email, groupName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot documentSnapshots = task.getResult();
                    if (documentSnapshots != null) {
                        ArrayList<DocumentSnapshot> roomDocuments =
                                (ArrayList<DocumentSnapshot>) documentSnapshots.getDocuments();
                        ArrayList<Room> rooms = LeLeLeParser.parseRoomList(roomDocuments);
                        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

                        //to get electricity data of rooms
                        for (final Room room : rooms) {
                            getElectricityList(email, groupName, year, room.getRoomName(), new GetElectricityCallback() {
                                @Override
                                public void onCompleted(ArrayList<Electricity> electricities) {
                                    room.setElectricities(electricities);
                                }

                                @Override
                                public void onError(String errorMessage) {

                                }
                            });
                        }
                        callback.onCompleted(rooms);
                        Log.v(TAG, "room callback: " + rooms.size());
                    } else {
                        Log.v(TAG, "There's no room now.");
                    }

                } else {
                    callback.onError(String.valueOf(task.getException()));
                }
            }
        });
    }



    @Override
    public void getGroupList(@NonNull String email, @NonNull final GetGroupListCallback callback) {
        groupCollection(email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot documentSnapshots = task.getResult();
                    if (documentSnapshots != null) {
                        ArrayList<DocumentSnapshot> groupDocuments =
                                (ArrayList<DocumentSnapshot>) documentSnapshots.getDocuments();
                        ArrayList<Group> groups = LeLeLeParser.parseGroupList(groupDocuments);
                        callback.onCompleted(groups);
                    } else {
                        ArrayList<Group> groups = new ArrayList<>(); //給空的，避免子集合還沒建立，導致fragment掛掉
                        callback.onCompleted(groups);
                    }
                }
            }
        });
    }



    /*
     * 用來新增group到group清單
     * 需要給group資訊與房東信箱來建立group
     * 當groups集合不存在時，會自動建立
     * document ID 會是 group name
     * */
    @Override
    public void updateGroupList(@NonNull final Group group, @NonNull final String email, @NonNull final UpdateGroupListCallback callback) {

        final Map<String, Object> groupInfo = new HashMap<>();
        groupInfo.put("address", group.getGroupAddress());
        groupInfo.put("max_room_numbers", group.getGroupRoomNumber());
        groupInfo.put("tenant_numbers", group.getGroupTenantNumber());

        groupDocument(email, group.getGroupName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            assert documentSnapshot != null;
                            if (documentSnapshot.exists()) {
                                groupDocument(email, group.getGroupName())
                                        .update(groupInfo);
                                callback.onCompleted();
                            } else {
                                groupDocument(email, group.getGroupName())
                                        .set(groupInfo);
                                callback.onCompleted();
                            }
                        }
                    }
                });

        if (group.getRooms().size() != 0) {

            for (Room room:group.getRooms()) {
                updateRoom(room, email, group.getGroupName());
            }
        }
    }

    @Override
    public void getElectricityList(@NonNull final String email, @NonNull final String groupName,
                                   @NonNull final String year, @NonNull final String roomName,
                                   @NonNull final GetElectricityCallback callback) {
        electricityDocument(email, groupName, roomName, year)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot electricityFeeYearly = task.getResult();
                            ArrayList<Electricity> electricities = new ArrayList<>();
                            assert electricityFeeYearly != null;
                            if (electricityFeeYearly.exists()) {

                                Map<String, Object> electricityFeeYearlyData = electricityFeeYearly.getData();
                                for (int i = 0; i < 13; i++) {
                                    Map<String, String> electricityFee;
                                    if (i < 10) {
                                        electricityFee = (Map<String, String>) electricityFeeYearlyData.get("0" + String.valueOf(i));
                                    } else {
                                        electricityFee = (Map<String, String>) electricityFeeYearlyData.get(String.valueOf(i));
                                    }
                                    Electricity electricity = new Electricity();
                                    if (electricityFee != null) {
                                        electricity.setScaleLast(electricityFee.get("scale_last"));
                                        electricity.setPrice(electricityFee.get("price"));
                                        electricity.setScale(electricityFee.get("scale_this"));
                                        electricity.setTime(electricityFee.get("time"));
                                        electricity.setTotalConsumption(electricityFee.get("total_consumption"));
                                    }
                                    electricities.add(electricity);
                                    Log.v(TAG, "" + electricity.getScaleLast());
                                }
                                callback.onCompleted(electricities);
                                Log.v(TAG, "electricity size: " + electricities.size());
                            } else {
                                //一旦沒有此集合，需要初始化
                                initialElectricityMonthData(email, groupName, roomName, year, "00");
                                for (int i = 0; i < 13; i++) {
                                    Electricity electricity = new Electricity();
                                    electricities.add(electricity);
                                }
                                callback.onCompleted(electricities); //不存在還是要回傳空的
                            }
                        }
                    }
                });
    }


    @Override
    public void uploadElectricityData(String landlordEmail, String groupName, String roomName,
                                      String year, String month, Electricity electricity) {

        initialElectricityMonthData(landlordEmail, groupName, roomName, year, month);

        Map<String, Object> electricityYearly = new HashMap<>();
        Map<String, Object> electricityData = getElectricityDataMap(electricity);
        electricityYearly.put(month, electricityData);

        electricityDocument(landlordEmail, groupName, roomName, year)
                .update(electricityYearly);
    }

    @Override
    public void initialElectricityMonthData(final String landlordEmail, final String groupName, final String roomName, final String year, final String month) {
        electricityDocument(landlordEmail, groupName, roomName, year)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.v(TAG, "initialElectricityMonthData: " + task.getResult().exists());
                            if (!task.getResult().exists()) {
                                Map<String, Object> electricityYearly = new HashMap<>();
                                Electricity electricity = new Electricity();
                                Map<String, Object> electricityData = getElectricityDataMap(electricity);
                                electricityYearly.put(month, electricityData);
                                electricityDocument(landlordEmail, groupName, roomName, year)
                                        .set(electricityYearly);
                            }
                        }
                    }
                });
    }

    private Map<String, Object> getElectricityDataMap(Electricity electricity) {
        Map<String, Object> electricityData = new HashMap<>();
        electricityData.put(PRICE, electricity.getPrice());
        electricityData.put(SCALE_LAST, electricity.getScaleLast());
        electricityData.put(SCALE_THIS, electricity.getScale());
        electricityData.put(TIME, electricity.getTime());
        electricityData.put(TOTAL_CONSUMPTION, electricity.getTotalConsumption());
        return electricityData;
    }

    @Override
    public void getLandlordProfile(@NonNull String email, @NonNull final GetLandlordProfileCallback callback) {
        landlordDocument(email).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot landlordDoc = task.getResult();
                    assert landlordDoc != null;
                    if (landlordDoc.exists()) {
                        Landlord landlord = LeLeLeParser.parseLandlordInfo(landlordDoc);
                        callback.onCompleted(landlord);
                    }
                } else {
                    //ToDo create a new user?
                    callback.onError(String.valueOf(task.getException()));
                }
            }
        });
    }

    @Override
    public void getTenantProfile(@NonNull String email, @NonNull final GetTenantProfileCallback callback) {
        tenantDocument(email).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot tenantDoc = task.getResult();
                            assert tenantDoc != null;
                            if (tenantDoc.exists()) {
                                Tenant tenant = tenantDoc.toObject(Tenant.class);
                                callback.onCompleted(tenant);
                            } else {
                                callback.onCompleted(new Tenant());
                            }
                        } else {
                            //ToDo create a new user?
                            callback.onError(String.valueOf(task.getException()));
                        }
                    }
                });
    }

    @Override
    public void updateRoom(@NonNull Room room, @NonNull String email, @NonNull String groupName) {
        roomDocument(email, groupName, room.getRoomName())
                .set(room.getTenant());

    }

    @Override
    public void deleteRoom(@NonNull final Room room, @NonNull final String email, @NonNull final String groupName) {
        roomDocument(email, groupName, room.getRoomName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Log.v(TAG, "task" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            DocumentSnapshot roomDoc = task.getResult();
                            Log.v(TAG, "roomDoc" + roomDoc.exists());
                            Log.v(TAG, "roomDoc" + roomDoc.getId());
                            roomDocument(email, groupName, room.getRoomName())
                                    .delete();
                        }
                    }
                });
    }

    @Override
    public void getGroupData(@NonNull final String email, @NonNull final String groupName, @NonNull final GetGroupDataCallback callback) {
        groupDocument(email, groupName).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot groupDoc = task.getResult();
                            assert groupDoc != null;
                            if (groupDoc.exists()) {
                                final Group group = LeLeLeParser.parseGroup(groupDoc);
                                getRoomList(email, groupName, new GetRoomListCallback() {
                                    @Override
                                    public void onCompleted(ArrayList<Room> rooms) {
                                        group.setRooms(rooms);
                                        callback.onCompleted(group);
                                    }

                                    @Override
                                    public void onError(String errorMessage) {

                                    }
                                });
                            }
                        }
                    }
                });
    }

    @Override
    public void uploadTenant(@NonNull Tenant tenant) {
        tenantDocument(tenant.getEmail()).set(tenant);
    }

    @Override
    public void uploadLandlord(@NonNull Landlord landlord) {
        Map<String, Object> landlordMap = new HashMap<>();
        landlordMap.put("email", landlord.getEmail());
        landlordMap.put("ID_card_number", landlord.getIdCardNumber());
        landlordMap.put("address", landlord.getAddress());
        landlordMap.put("assess_token", landlord.getAssessToken());
        landlordMap.put("phone_number", landlord.getPhoneNumber());
        landlordMap.put("id", landlord.getId());
        landlordMap.put("name", landlord.getName());
        landlordMap.put("picture", landlord.getPicture());
        landlordDocument(landlord.getEmail()).set(landlordMap);
    }

    @Override
    public void groupArticlesListener(@NonNull String email, @NonNull String groupName, final ArticlesCallback callback) {

        Log.d(TAG, "groupArticlesListener: ");
        ListenerRegistration registration = groupArticleCollection(email, groupName)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        Log.d(TAG, "onEvent: ");
                        if (e != null) {
                            callback.onError(e.getMessage());
                            Log.d(TAG, "onEvent: Error");
                        } else {
                            callback.onCompleted();
                            Log.d(TAG, "onEvent: Completed");
                        }
                    }
                });
    }

    @Override
    public void userArticlesListener(@NonNull String email, @NonNull int userType, final ArticlesCallback callback) {
        String user = "";
        if (userType == R.string.landlord) {
            user = LANDLORDS;
        } else {
            user = TENANTS;
        }

        userArticleCollection(email, user)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        Log.d(TAG, "userArticlesListener onEvent: ");
                        if (e != null) {
                            callback.onError(e.getMessage());
                        } else {
                            callback.onCompleted();
                        }
                    }
                });
    }

    @Override
    public void pushNotificationToTenant(@NonNull Notification notification, @NonNull String email,
                                         @NonNull final PushNotificationCallback callback) {

        long time= System.currentTimeMillis();
        userNotificationDocument(email, TENANTS, String.valueOf(time))
                .set(notification)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onCompleted();
                        } else {
                            callback.onError(String.valueOf(task.getException()));
                        }
                    }
                });
    }

    @Override
    public void pushNotificationToLandlord(@NonNull Notification notification, @NonNull String email, @NonNull final PushNotificationCallback callback) {
        long time= System.currentTimeMillis();
        userNotificationDocument(email, LANDLORDS, String.valueOf(time))
                .set(notification)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onCompleted();
                        } else {
                            callback.onError(String.valueOf(task.getException()));
                        }
                    }
                });
    }

    @Override
    public void getGroupInfo(@NonNull final String email, @NonNull final String groupName, @NonNull final GetGroupInfoCallback callback) {
        groupDocument(email, groupName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot groupDoc = task.getResult();
                            assert groupDoc != null;
                            if (groupDoc.exists()) {
                                final Group group = LeLeLeParser.parseGroup(groupDoc);
                                callback.onCompleted(group);
                            }
                        }
                    }
                });
    }

    @Override
    public void updateGroupInfo(@NonNull final Group group, @NonNull final String email, @NonNull String groupName) {
        final Map<String, Object> groupInfo = new HashMap<>();
        groupInfo.put("address", group.getGroupAddress());
        groupInfo.put("max_room_numbers", group.getGroupRoomNumber());
        groupInfo.put("tenant_numbers", group.getGroupTenantNumber());

        groupDocument(email, group.getGroupName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            assert documentSnapshot != null;
                            if (documentSnapshot.exists()) {
                                groupDocument(email, group.getGroupName())
                                        .update(groupInfo);
                            } else {
                                groupDocument(email, group.getGroupName())
                                        .set(groupInfo);
                            }
                        }
                    }
                });
    }

    @Override
    public void getUserNotifications(@NonNull String email, @NonNull final GetUserNotificationsCallback callback) {
        String userType = "";
        if (UserManager.getInstance().getUserData().getUserType() == R.string.landlord) {
            userType = LANDLORDS;
        } else {
            userType = TENANTS;
        }
        userNotificationCollection(email, userType)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot notificationCollection = task.getResult();
                            if (notificationCollection != null) {
                                ArrayList<DocumentSnapshot> notificationDocuments =
                                        (ArrayList<DocumentSnapshot>) notificationCollection.getDocuments();

                                ArrayList<Notification> notifications = LeLeLeParser.parseNotificationList(notificationDocuments);

                                callback.onCompleted(notifications);
                            }
                        } else {
                            callback.onError(String.valueOf(task.getException()));
                        }
                    }
                });
    }

    @Override
    public void updateNotificationRead(@NonNull Notification notification, @NonNull String email) {

        String userType = "";
        if (UserManager.getInstance().getUserData().getUserType() == R.string.landlord) {
            userType = LANDLORDS;
        } else {
            userType = TENANTS;
        }

        userNotificationDocument(email, userType, notification.getId())
                .set(notification);
    }

    @Override
    public void sendMessageToRoom(@NonNull Message message, @NonNull String email,
                                  @NonNull String groupName, @NonNull String roomName) {

        long time = System.currentTimeMillis();
        messageDocument(email, groupName, roomName, String.valueOf(time))
                .set(message);
    }

    @Override
    public void updateMessageToRoom(@NonNull Message message, @NonNull String email, @NonNull String groupName, @NonNull String roomName) {
        messageDocument(email, groupName, roomName, message.getId())
                .set(message);
    }

    @Override
    public void getMessagesFromRoom(@NonNull String email, @NonNull String groupName, @NonNull String roomName, @NonNull final GetMessagesCallback callback) {
        messageCollection(email, groupName, roomName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot messageCollection = task.getResult();
                            if (messageCollection != null) {
                                ArrayList<DocumentSnapshot> messageDocuments =
                                        (ArrayList<DocumentSnapshot>) messageCollection.getDocuments();

                                ArrayList<Message> messages = LeLeLeParser.parseMessageList(messageDocuments);

                                callback.onCompleted(messages);
                            }
                        } else {
                            callback.onError(String.valueOf(task.getException()));
                        }
                    }
                });
    }

    @Override
    public void messageListener(@NonNull String email, @NonNull String groupName,
                                @NonNull String roomName, @NonNull boolean switchOn,
                                final MessageCallback callback) {
        if (switchOn) {
            mMessageListener = messageCollection(email, groupName, roomName)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                            Log.d(TAG, "onEvent: ");
                            if (e != null) {
                                callback.onError(e.getMessage());
                            } else {
                                callback.onCompleted();
                                Log.d(TAG, "onEvent: Completed");
                            }
                        }
                    });
        } else {
            mMessageListener.remove();
        }

    }

    @Override
    public void sendLandlordArticle(@NonNull Article article, @NonNull String email) {
        userArticleDocument(email, LANDLORDS)
                .set(article);
    }

    @Override
    public void sendTenantArticle(@NonNull Article article, @NonNull String email) {
        userArticleDocument(email, TENANTS)
                .set(article);
    }

    @Override
    public void sendGroupArticle(@NonNull Article article, @NonNull String landlordEmail,
                                 @NonNull String groupName, @NonNull final SendGroupArticleCallback callback) {
        long time= System.currentTimeMillis();
        mFirebaseFirestore.collection(LANDLORDS)
                .document(landlordEmail)
                .collection(GROUPS)
                .document(groupName)
                .collection(ARTICLES)
                .document(String.valueOf(time))
                .set(article)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onCompleted();
                        } else {
                            callback.onError(String.valueOf(task.getException()));
                        }
                    }
                });
    }

    @Override
    public void getUserArticles(@NonNull String email, @NonNull final GetArticlesCallback callback) {
        String userType = "";
        if (UserManager.getInstance().getUserData().getUserType() == R.string.landlord) {
            userType = LANDLORDS;
        } else {
            userType = TENANTS;
        }
        mFirebaseFirestore.collection(userType)
                .document(email)
                .collection(ARTICLES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot articleCollection = task.getResult();
                            if (articleCollection != null) {
                                ArrayList<DocumentSnapshot> articleDocuments =
                                        (ArrayList<DocumentSnapshot>) articleCollection.getDocuments();

                                ArrayList<Article> articles = LeLeLeParser.parseArticleList(articleDocuments);

                                callback.onCompleted(articles);
                            }
                        } else {
                            callback.onError(String.valueOf(task.getException()));
                        }
                    }
                });
    }

    @Override
    public void getGroupArticles(@NonNull String email, @NonNull String groupName, @NonNull final GetArticlesCallback callback) {
        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(groupName)
                .collection(ARTICLES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot articleCollection = task.getResult();
                            if (articleCollection != null) {
                                ArrayList<DocumentSnapshot> articleDocuments =
                                        (ArrayList<DocumentSnapshot>) articleCollection.getDocuments();

                                ArrayList<Article> articles = LeLeLeParser.parseArticleList(articleDocuments);

                                callback.onCompleted(articles);
                            }
                        } else {
                            callback.onError(String.valueOf(task.getException()));
                        }
                    }
                });
    }

    @Override
    public void deleteUserArticle(@NonNull Article article, @NonNull String email, @NonNull int userType) {

        String user = "";
        if (userType == R.string.landlord) {
            user = LANDLORDS;
        } else {
            user = TENANTS;
        }
        mFirebaseFirestore.collection(user)
                .document(email)
                .collection(ARTICLES)
                .document(article.getArticleId())
                .delete();
    }

    @Override
    public void deleteGroupArticle(@NonNull Article article, @NonNull String email, @NonNull String groupName) {
        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(groupName)
                .collection(ARTICLES)
                .document(article.getArticleId())
                .delete();
    }

    @Override
    public void queryUserArticleByAuthorAndType(@NonNull String email, @NonNull String authorName,
                                                @NonNull String articleType, @NonNull int userType,
                                                @NonNull final QueryArticleByAuthorAndTypeCallback callback) {

        CollectionReference articleCollection;
        if (userType == R.string.landlord) {
            articleCollection = mFirebaseFirestore.collection(LANDLORDS)
                    .document(email)
                    .collection(ARTICLES);
        } else {
            articleCollection = mFirebaseFirestore.collection(TENANTS)
                    .document(email)
                    .collection(ARTICLES);
        }

        articleCollection.whereEqualTo("author", authorName)
                .whereEqualTo("type", articleType).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot articleCollection = task.getResult();
                    if (articleCollection != null) {
                        ArrayList<DocumentSnapshot> articleDocuments =
                                (ArrayList<DocumentSnapshot>) articleCollection.getDocuments();

                        ArrayList<Article> articles = LeLeLeParser.parseArticleList(articleDocuments);

                        callback.onCompleted(articles);
                    }
                } else {
                    callback.onError(String.valueOf(task.getException()));
                }
            }
        });

    }
}
