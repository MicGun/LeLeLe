package com.hugh.lelele.messaging_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hugh.lelele.data.Room;

import java.util.ArrayList;

import static com.google.android.gms.internal.firebase_messaging.zzg.checkNotNull;

public class MessagingListAdapter extends RecyclerView.Adapter {

    private ArrayList<Room> mRooms;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {

        if (mRooms != null) {
            return mRooms.size();
        } else {
            return 0;
        }
    }

    public void updateData(ArrayList<Room> rooms) {
        mRooms = checkNotNull(rooms);
        notifyDataSetChanged();
    }
}
