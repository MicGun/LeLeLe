package com.hugh.lelele.room_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;

public class RoomListAdapter extends RecyclerView.Adapter {

    private RoomListContract.Presenter mPresenter;
    private Group mGroup;

    public RoomListAdapter(RoomListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public class RoomListViewHolder extends RecyclerView.ViewHolder {

        TextView roomName;
        TextView tenantName;
        ImageView addTenantButton;
        ImageView deleteTenantButton;

        public RoomListViewHolder(@NonNull View itemView) {
            super(itemView);

            roomName = itemView.findViewById(R.id.item_text_view_room_list_room_name);
            tenantName = itemView.findViewById(R.id.item_text_view_room_list_tenant_name);
            addTenantButton = itemView.findViewById(R.id.item_image_view_room_list_add_tenant);
            deleteTenantButton = itemView.findViewById(R.id.item_image_view_room_list_delete_tenant);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_room_list, viewGroup, false);

        RoomListViewHolder viewHolder = new RoomListViewHolder(rootView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Room room = mGroup.getRooms().get(i);

        ((RoomListViewHolder) viewHolder).roomName.setText(room.getRoomName());
//        ((RoomListViewHolder) viewHolder).tenantName.setText(room.get());

    }

    @Override
    public int getItemCount() {
        if (mGroup != null) {
            return mGroup.getRooms().size();
        } else {
            return 0;
        }
    }

    public void updateData(Group group) {
        mGroup = group;
        notifyDataSetChanged();
    }
}
