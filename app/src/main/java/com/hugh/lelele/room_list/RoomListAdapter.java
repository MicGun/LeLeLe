package com.hugh.lelele.room_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hugh.lelele.LeLeLe;
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
        ImageView cancleInvitingButton;

        public RoomListViewHolder(@NonNull View itemView) {
            super(itemView);

            roomName = itemView.findViewById(R.id.item_text_view_room_list_room_name);
            tenantName = itemView.findViewById(R.id.item_text_view_room_list_tenant_name);
            addTenantButton = itemView.findViewById(R.id.item_image_view_room_list_add_tenant);
            deleteTenantButton = itemView.findViewById(R.id.item_image_view_room_list_delete_tenant);
            cancleInvitingButton = itemView.findViewById(R.id.item_image_view_room_list_inviting_tenant);
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

        final Room room = mGroup.getRooms().get(i);

        ((RoomListViewHolder) viewHolder).roomName.setText(room.getRoomName());
        if (!room.getTenant().getName().equals("") && !room.getTenant().isInviting()) {
            ((RoomListViewHolder) viewHolder).tenantName.setText(room.getTenant().getName());
        } else if (room.getTenant().isInviting()){
            ((RoomListViewHolder) viewHolder).tenantName
                    .setText(LeLeLe.getAppContext().getResources().getString(R.string.tenant_inviting));
        } else {
            ((RoomListViewHolder) viewHolder).tenantName
                    .setText(LeLeLe.getAppContext().getResources().getString(R.string.tenant_empty));
        }

        ((RoomListViewHolder) viewHolder).addTenantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.openInvitationSending(room);
            }
        });

        ((RoomListViewHolder) viewHolder).cancleInvitingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.openInvitationActionDialog(v);
            }
        });

        if (room.getTenant().isBinding()) {
            //有房客，只剩下刪除鍵
            ((RoomListViewHolder) viewHolder).addTenantButton.setVisibility(View.INVISIBLE);
            ((RoomListViewHolder) viewHolder).deleteTenantButton.setVisibility(View.VISIBLE);
            ((RoomListViewHolder) viewHolder).cancleInvitingButton.setVisibility(View.INVISIBLE);
        } else if (room.getTenant().isInviting()) {
            //邀請中，剩下解除邀請的按鈕
            ((RoomListViewHolder) viewHolder).addTenantButton.setVisibility(View.INVISIBLE);
            ((RoomListViewHolder) viewHolder).deleteTenantButton.setVisibility(View.INVISIBLE);
            ((RoomListViewHolder) viewHolder).cancleInvitingButton.setVisibility(View.VISIBLE);
        } else {
            ((RoomListViewHolder) viewHolder).addTenantButton.setVisibility(View.VISIBLE);
            ((RoomListViewHolder) viewHolder).deleteTenantButton.setVisibility(View.INVISIBLE);
            ((RoomListViewHolder) viewHolder).cancleInvitingButton.setVisibility(View.INVISIBLE);
        }

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
