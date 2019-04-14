package com.hugh.lelele.group_detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;


import java.util.ArrayList;

public class GroupDetailsAdapter extends RecyclerView.Adapter {

    GroupDetailsContract.Presenter mPresenter;
    private ArrayList<Room> mRooms;
    public static final String TAG = GroupDetailsAdapter.class.getSimpleName();

    public GroupDetailsAdapter(GroupDetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public class GroupDetailsRoomItemViewHolder extends RecyclerView.ViewHolder {

        TextView roomName;
        TextView roomTenant;
        ImageView deleteButton;

        public GroupDetailsRoomItemViewHolder(@NonNull View itemView) {
            super(itemView);

            roomName = itemView.findViewById(R.id.item_text_view_room_name);
            roomTenant = itemView.findViewById(R.id.item_text_view_tenant_name);
            deleteButton = itemView.findViewById(R.id.item_image_view_delete_button_room_info);
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final Room room = mRooms.get(i);

        ((GroupDetailsRoomItemViewHolder) viewHolder).roomName.setText(room.getRoomName());

        if (room.getTenant() != null) {
            ((GroupDetailsRoomItemViewHolder) viewHolder).roomTenant.setText(room.getTenant().getName());
        }

        if (room.getElectricities().size() != 0) {
            ((GroupDetailsRoomItemViewHolder) viewHolder).deleteButton.setVisibility(View.INVISIBLE);
        } else {
            ((GroupDetailsRoomItemViewHolder) viewHolder).deleteButton.setVisibility(View.VISIBLE);
        }
        ((GroupDetailsRoomItemViewHolder) viewHolder).deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteRoom(room);
                mRooms.remove(i);
//                notifyDataSetChanged();
                mPresenter.updateRoomData(mRooms);
                Log.v(TAG, "remove room: " + mRooms.size());

            }
        });

    }

    @Override
    public int getItemCount() {
        if (mRooms != null) {
            Log.v(TAG, "getItemCount: " + mRooms.size());
            return mRooms.size();
        } else {
            return 0;
        }
    }

    public void updateData(ArrayList<Room> rooms) {
        mRooms = rooms;
        Log.v(TAG, "updateData: " + mRooms.size());
        notifyDataSetChanged();
    }
}
