package com.hugh.lelele.data.source;

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
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;
import java.util.List;

public class LeLeLeRemoteDataSource implements LeLeLeDataSource {

    private final static String LANDLORDS = "landlords";
    private final static String GROUPS = "groups";
    private final static String ROOMS = "rooms";

    private final static String TAG = LeLeLe.class.getSimpleName();

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
    public void updateLandlordUser(@NonNull String email, @NonNull final LandlordUserCallback callback) {
        mFirebaseFirestore.collection(LANDLORDS)
                .document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if ((documentSnapshot != null)) {
                                Landlord landlord = LeLeLeParser.getLandlordData(documentSnapshot);
                                callback.onCompleted(landlord);
                            } else {
                                //ToDo Create a firestore landlord format here.
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
    public void getRoomList(@NonNull String email, @NonNull String groupName, @NonNull final GetRoomListCallback callback) {
        CollectionReference roomCollection  = mFirebaseFirestore.collection(LANDLORDS)
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
                        callback.onCompleted(rooms);
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
        CollectionReference groupCollection  = mFirebaseFirestore.collection(LANDLORDS)
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
}
