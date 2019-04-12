package com.hugh.lelele.group_detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

public class GroupDetailsAdapter extends RecyclerView.Adapter {

    private ArrayList<Room> mRooms;

    public class GroupDetailsRoomItemViewHolder extends RecyclerView.ViewHolder {

        public GroupDetailsRoomItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_room_info, viewGroup, false);

        GroupDetailsRoomItemViewHolder viewHolder = new GroupDetailsRoomItemViewHolder(rootView);

        return viewHolder;
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
        mRooms = rooms;
        notifyDataSetChanged();
    }
}
