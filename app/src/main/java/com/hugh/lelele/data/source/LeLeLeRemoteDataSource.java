package com.hugh.lelele.data.source;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hugh.lelele.data.Landlord;

public class LeLeLeRemoteDataSource implements LeLeLeDataSource {

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

    @Override
    public void updateLandlordUser(@NonNull String email, @NonNull final LandlordUserCallback callback) {
        mFirebaseFirestore.collection("landlords")
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
}
