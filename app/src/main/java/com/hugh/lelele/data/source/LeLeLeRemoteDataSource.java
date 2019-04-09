package com.hugh.lelele.data.source;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

public class LeLeLeRemoteDataSource implements LeLeLeDataSource {

    private final static String LANDLORDS = "landlords";
    private final static String GROUPS = "groups";
    private final static String ROOMS = "rooms";
    private final static String ELECTRICITY_FEE = "electricity_fee";

    /*Electricity Data*/
    private final static String PRICE = "price";
    private final static String SCALE_LAST = "scale_last";
    private final static String SCALE_THIS = "scale_this";
    private final static String TIME = "time";
    private final static String TOTAL_CONSUMPTION = "total_consumption";

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
     * 去拿房東資訊
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
                            DocumentSnapshot documentSnapshot = task.getResult();
                            assert documentSnapshot != null;
                            if (documentSnapshot.exists()) {
                                Landlord landlord = LeLeLeParser.getLandlordData(documentSnapshot);
                                callback.onCompleted(landlord);
                            } else {
                                //create and initialize a landlord
                                Map<String, Object> user = new HashMap<>();
                                user.put("email", email);
                                user.put("ID_card_number", "");
                                user.put("address", "");
                                user.put("assess_token", "");
                                user.put("phone_number", "");
                                user.put("id", "");
                                user.put("name", "");
                                user.put("picture", "");
                                mFirebaseFirestore.collection(LANDLORDS)
                                        .document(email)
                                        .set(user);
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
    public void updateGroupList(@NonNull Group group, @NonNull String email, @NonNull final UpdateGroupListCallback callback) {

        Map<String, Object> groupInfo = new HashMap<>();
        groupInfo.put("address", group.getGroupAddress());
        groupInfo.put("max_room_number", group.getGroupRoomNumber());
        groupInfo.put("tenant_number", group.getGroupTenantNumber());

        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .collection(GROUPS)
                .document(group.getGroupName())
                .set(groupInfo);
    }

    @Override
    public void getElectricityList(@NonNull String email, @NonNull String groupName,
                                   @NonNull String year, @NonNull String roomName,
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
                            assert electricityFeeYearly != null;
                            if (electricityFeeYearly.exists()) {
                                ArrayList<Electricity> electricities = new ArrayList<>();
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter =
                                        new SimpleDateFormat("yyyy-MM-dd");
                                Map<String, Object> electricityFeeYearlyData = electricityFeeYearly.getData();
                                for (int i = 0; i < 12; i++) {
                                    Map<String, String> electricityFee;
                                    if (i < 9) {
                                        electricityFee = (Map<String, String>) electricityFeeYearlyData.get("0" + String.valueOf(i + 1));
                                    } else {
                                        electricityFee = (Map<String, String>) electricityFeeYearlyData.get(String.valueOf(i + 1));
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
                            }
                        }
                    }
                });
    }

    @Override
    public void uploadElectricityData(String landlordEmail, String groupName, String roomName, String month, Electricity electricity) {

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
                .document(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))
                .update(electricityYearly);
    }
}
