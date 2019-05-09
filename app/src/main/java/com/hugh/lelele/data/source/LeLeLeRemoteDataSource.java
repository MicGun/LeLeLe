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
import com.google.firebase.firestore.Query;
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

    /*
     * 去拿房東資訊，若不存在則新創一個
     * */
    @Override
    public void updateLandlordUser(@NonNull final String email, @NonNull final LandlordUserCallback callback) {
        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot landlordDoc = task.getResult();
                            assert landlordDoc != null;
                            if (landlordDoc.exists()) {
                                Landlord landlord = LeLeLeParser.parseLandlordInfo(landlordDoc);
                                landlord.setAssessToken(UserManager.getInstance().getUserData().getAssessToken());
                                uploadLandlord(landlord);
                                callback.onCompleted(landlord);
                            } else {
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
                                mFirebaseFirestore.collection(LANDLORDS)
                                        .document(email)
                                        .set(user);

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

    /*
     * 去拿房客資訊，若不存在則新創一個
     * */
    @Override
    public void updateTenantUser(@NonNull final String email, @NonNull final TenantUserCallback callback) {
        mFirebaseFirestore.collection(TENANTS)
                .document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot tenantDoc = task.getResult();
                            assert tenantDoc != null;
                            if (tenantDoc.exists()) {
//                                Tenant tenant = LeLeLeParser.parseTenantInfo(tenantDoc);
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
//                                callback.onCompleted(tenant);
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
                                        mFirebaseFirestore.collection(TENANTS)
                                                .document(email)
                                                .set(tenant);
                                        callback.onCompleted(tenant);
                                    }
                                });
//                                mFirebaseFirestore.collection(TENANTS)
//                                        .document(email)
//                                        .set(tenant);
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
        CollectionReference roomCollection = mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(groupName)
                .collection(ROOMS);

        roomCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot documentSnapshots = task.getResult();
                    if (documentSnapshots != null) {
                        ArrayList<DocumentSnapshot> roomDocuments =
                                (ArrayList<DocumentSnapshot>) documentSnapshots.getDocuments();
                        ArrayList<Room> rooms = LeLeLeParser.parseRoomList(roomDocuments);
                        String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

                        //ToDo: check is this necessary?
                        for (final Room room : rooms) {
                            getElectricityList(email, groupName, year, room.getRoomName(), new GetElectricityCallback() {
                                @Override
                                public void onCompleted(ArrayList<Electricity> electricities) {
                                    room.setElectricities(electricities);
                                    Log.v(TAG, "getRoomList electricities size" + electricities.size());
                                    Log.v(TAG, "getRoomList electricities size" + room.getElectricities().size());
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
        CollectionReference groupCollection = mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS);

        groupCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(group.getGroupName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot.exists()) {
                                mFirebaseFirestore.collection(LANDLORDS)
                                        .document(email)
                                        .collection(GROUPS)
                                        .document(group.getGroupName())
                                        .update(groupInfo);
                                callback.onCompleted();
                            } else {
                                mFirebaseFirestore.collection(LANDLORDS)
                                        .document(email)
                                        .collection(GROUPS)
                                        .document(group.getGroupName())
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
        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(groupName)
                .collection(ROOMS)
                .document(roomName)
                .collection(ELECTRICITY_FEE)
                .document(year)
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
        Map<String, Object> electricityData = new HashMap<>();
        electricityData.put(PRICE, electricity.getPrice());
        electricityData.put(SCALE_LAST, electricity.getScaleLast());
        electricityData.put(SCALE_THIS, electricity.getScale());
        electricityData.put(TIME, electricity.getTime());
        electricityData.put(TOTAL_CONSUMPTION, electricity.getTotalConsumption());

        electricityYearly.put(month, electricityData);

        mFirebaseFirestore.collection(LANDLORDS)
                .document(landlordEmail)
                .collection(GROUPS)
                .document(groupName)
                .collection(ROOMS)
                .document(roomName)
                .collection(ELECTRICITY_FEE)
                .document(String.valueOf(year))
                .update(electricityYearly);
    }

    @Override
    public void initialElectricityMonthData(final String landlordEmail, final String groupName, final String roomName, final String year, final String month) {
        mFirebaseFirestore.collection(LANDLORDS)
                .document(landlordEmail)
                .collection(GROUPS)
                .document(groupName)
                .collection(ROOMS)
                .document(roomName)
                .collection(ELECTRICITY_FEE)
                .document(String.valueOf(year))
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.v(TAG, "initialElectricityMonthData: " + task.getResult().exists());
                            if (!task.getResult().exists()) {
                                Map<String, Object> electricityYearly = new HashMap<>();
                                Map<String, Object> electricityData = new HashMap<>();
                                Electricity electricity = new Electricity();
                                electricityData.put(PRICE, electricity.getPrice());
                                electricityData.put(SCALE_LAST, electricity.getScaleLast());
                                electricityData.put(SCALE_THIS, electricity.getScale());
                                electricityData.put(TIME, electricity.getTime());
                                electricityData.put(TOTAL_CONSUMPTION, electricity.getTotalConsumption());
                                electricityYearly.put(month, electricityData);
                                mFirebaseFirestore.collection(LANDLORDS)
                                        .document(landlordEmail)
                                        .collection(GROUPS)
                                        .document(groupName)
                                        .collection(ROOMS)
                                        .document(roomName)
                                        .collection(ELECTRICITY_FEE)
                                        .document(String.valueOf(year))
                                        .set(electricityYearly);
                            }
                        }
                    }
                });
    }

    @Override
    public void getLandlordProfile(@NonNull String email, @NonNull final GetLandlordProfileCallback callback) {
        mFirebaseFirestore.collection(LANDLORDS).document(email).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot landlordDoc = task.getResult();
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
        mFirebaseFirestore.collection(TENANTS).document(email).get()
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
        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(groupName)
                .collection(ROOMS)
                .document(room.getRoomName())
                .set(room.getTenant());

    }

    @Override
    public void deleteRoom(@NonNull final Room room, @NonNull final String email, @NonNull final String groupName) {
        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(groupName)
                .collection(ROOMS)
                .document(room.getRoomName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Log.v(TAG, "task" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            DocumentSnapshot roomDoc = task.getResult();
                            Log.v(TAG, "roomDoc" + roomDoc.exists());
                            Log.v(TAG, "roomDoc" + roomDoc.getId());
                            mFirebaseFirestore.collection(LANDLORDS)
                                    .document(email)
                                    .collection(GROUPS)
                                    .document(groupName)
                                    .collection(ROOMS)
                                    .document(room.getRoomName())
                                    .delete();
                        }
                    }
                });
    }

    @Override
    public void getGroupData(@NonNull final String email, @NonNull final String groupName, @NonNull final GetGroupDataCallback callback) {
        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(groupName)
                .get()
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
        mFirebaseFirestore.collection(TENANTS)
                .document(tenant.getEmail())
                .set(tenant);
    }

    @Override
    public void uploadLandlord(@NonNull Landlord landlord) {
        Map<String, Object> user = new HashMap<>();
        user.put("email", landlord.getEmail());
        user.put("ID_card_number", landlord.getIdCardNumber());
        user.put("address", landlord.getAddress());
        user.put("assess_token", landlord.getAssessToken());
        user.put("phone_number", landlord.getPhoneNumber());
        user.put("id", landlord.getId());
        user.put("name", landlord.getName());
        user.put("picture", landlord.getPicture());
        mFirebaseFirestore.collection(LANDLORDS)
                .document(landlord.getEmail())
                .set(user);
    }

    @Override
    public void groupArticlesListener(@NonNull String email, @NonNull String groupName, final ArticlesCallback callback) {

        Log.d(TAG, "groupArticlesListener: ");
        ListenerRegistration registration = mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(groupName)
                .collection(ARTICLES)
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
    }

    @Override
    public void userArticlesListener(@NonNull String email, @NonNull int userType, final ArticlesCallback callback) {
        String user = "";
        if (userType == R.string.landlord) {
            user = LANDLORDS;
        } else {
            user = TENANTS;
        }

        mFirebaseFirestore.collection(user)
                .document(email)
                .collection(ARTICLES)
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

//    @Override
//    public void pushNotificationToTenant(@NonNull Map<String, Object> notificationMessage, @NonNull String email, @NonNull final PushNotificationCallback callback) {
//        mFirebaseFirestore.collection(TENANTS)
//                .document(email)
//                .collection(NOTIFICATION)
//                .add(notificationMessage)
//                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentReference> task) {
//                        if (task.isSuccessful()) {
//                            callback.onCompleted();
//                        } else {
//                            callback.onError(String.valueOf(task.getException()));
//                        }
//                    }
//                });
//    }

    @Override
    public void pushNotificationToTenant(@NonNull Notification notification, @NonNull String email,
                                         @NonNull final PushNotificationCallback callback) {

        long time= System.currentTimeMillis();
        mFirebaseFirestore.collection(TENANTS)
                .document(email)
                .collection(NOTIFICATION)
                .document(String.valueOf(time))
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
        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(NOTIFICATION)
                .document(String.valueOf(time))
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
        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(groupName)
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

        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(group.getGroupName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            assert documentSnapshot != null;
                            if (documentSnapshot.exists()) {
                                mFirebaseFirestore.collection(LANDLORDS)
                                        .document(email)
                                        .collection(GROUPS)
                                        .document(group.getGroupName())
                                        .update(groupInfo);
                            } else {
                                mFirebaseFirestore.collection(LANDLORDS)
                                        .document(email)
                                        .collection(GROUPS)
                                        .document(group.getGroupName())
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
        mFirebaseFirestore.collection(userType)
                .document(email)
                .collection(NOTIFICATION)
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

        mFirebaseFirestore.collection(userType)
                .document(email)
                .collection(NOTIFICATION)
                .document(notification.getId())
                .set(notification);
    }

    @Override
    public void sendMessageToRoom(@NonNull Message message, @NonNull String email,
                                  @NonNull String groupName, @NonNull String roomName) {

        long time = System.currentTimeMillis();

        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(groupName)
                .collection(ROOMS)
                .document(roomName)
                .collection(MESSAGES)
                .document(String.valueOf(time))
                .set(message);
    }

    @Override
    public void updateMessageToRoom(@NonNull Message message, @NonNull String email, @NonNull String groupName, @NonNull String roomName) {
        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(groupName)
                .collection(ROOMS)
                .document(roomName)
                .collection(MESSAGES)
                .document(message.getId())
                .set(message);
    }

    @Override
    public void getMessagesFromRoom(@NonNull String email, @NonNull String groupName, @NonNull String roomName, @NonNull final GetMessagesCallback callback) {
        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(groupName)
                .collection(ROOMS)
                .document(roomName)
                .collection(MESSAGES)
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
            mMessageListener = mFirebaseFirestore.collection(LANDLORDS)
                    .document(email)
                    .collection(GROUPS)
                    .document(groupName)
                    .collection(ROOMS)
                    .document(roomName)
                    .collection(MESSAGES)
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
        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(ARTICLES)
                .document()
                .set(article);
    }

    @Override
    public void sendTenantArticle(@NonNull Article article, @NonNull String email) {
        mFirebaseFirestore.collection(TENANTS)
                .document(email)
                .collection(ARTICLES)
                .document()
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
