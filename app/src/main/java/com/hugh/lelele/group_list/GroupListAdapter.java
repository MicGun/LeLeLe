package com.hugh.lelele.group_list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Group;

import java.util.ArrayList;

public class GroupListAdapter extends RecyclerView.Adapter {

    private GroupListContract.Presenter mPresenter;
    private ArrayList<Group> mGroups;

    public GroupListAdapter(GroupListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public class GroupListViewHolder extends RecyclerView.ViewHolder {

        TextView groupName;
        TextView groupAddress;
        TextView groupRoomAmount;
        TextView groupTenantNumber;
        ImageView editButton;

        public GroupListViewHolder(@NonNull View itemView) {
            super(itemView);

            groupName = itemView.findViewById(R.id.item_text_view_group_name);
            groupAddress = itemView.findViewById(R.id.item_text_view_group_address);
            groupRoomAmount = itemView.findViewById(R.id.item_text_view_room_amount);
            groupTenantNumber = itemView.findViewById(R.id.item_text_view_tenant_number);
            editButton = itemView.findViewById(R.id.item_image_view_edit_group_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.openGroupDetails(mGroups.get(getAdapterPosition()));
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_group_info, viewGroup, false);

        GroupListViewHolder viewHolder = new GroupListViewHolder(rootView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        final Group group = mGroups.get(i);

        ((GroupListViewHolder) viewHolder).groupName.setText(group.getGroupName());
        ((GroupListViewHolder) viewHolder).groupAddress.setText(group.getGroupAddress());

        if (!group.getGroupRoomNumber().equals("")) {
            ((GroupListViewHolder) viewHolder).groupRoomAmount.setText(group.getGroupRoomNumber());
        } else {
            ((GroupListViewHolder) viewHolder).groupRoomAmount.setText("0");
        }

        if (!group.getGroupTenantNumber().equals("")) {
            ((GroupListViewHolder) viewHolder).groupTenantNumber.setText(group.getGroupTenantNumber());
        } else {
            ((GroupListViewHolder) viewHolder).groupTenantNumber.setText("0");
        }


        ((GroupListViewHolder) viewHolder).editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToDo: open group editor
                mPresenter.openGroupDetails(group);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (mGroups != null) {
            return mGroups.size();
        } else {
            return 0;
        }
    }

    public void updateData(ArrayList<Group> groups) {
        if (groups != null) {
            mGroups = groups;
            notifyDataSetChanged();
        }
    }
}
