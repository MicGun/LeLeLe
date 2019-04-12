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
import java.util.zip.Inflater;

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

            groupAddress = itemView.findViewById(R.id.gr)
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_room_info, viewGroup, false);

        GroupListViewHolder viewHolder = new GroupListViewHolder(rootView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Group group = mGroups.get(i);

        ((GroupListViewHolder) viewHolder)

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
