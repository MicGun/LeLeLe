package com.hugh.lelele.room_list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class RoomListFragment extends Fragment implements RoomListContract.View {

    private RoomListContract.Presenter mPresenter;
    private RoomListAdapter mAdapter;
    private Group mGroup;
    private ArrayList<Room> mRooms;

    private TextView mGroupName;
    private TextView mNumberOfRooms;
    private TextView mNumberOfTenants;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.hideBottomNavigation();
        mPresenter.loadGroupData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_room_list, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_room_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        mGroupName = root.findViewById(R.id.text_group_name_room_list);
        mNumberOfRooms = root.findViewById(R.id.text_view_number_of_rooms_room_list);
        mNumberOfTenants = root.findViewById(R.id.text_view_number_of_tenants_room_list);

        return root;
    }

    private void updateRoomListUi() {
        mGroupName.setText(mGroup.getGroupName());
        if (!mGroup.getGroupRoomNumber().equals("")) {
            mNumberOfRooms.setText(mGroup.getGroupRoomNumber());
        } else {
            mNumberOfRooms.setText("0");
        }

        if (!mGroup.getGroupTenantNumber().equals("")) {
            mNumberOfTenants.setText(mGroup.getGroupTenantNumber());
        } else {
            mNumberOfTenants.setText("0");
        }

    }

    public static RoomListFragment newInstance() {
        return new RoomListFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.showBottomNavigation();
        mPresenter.updateToolbar(getResources().getString(R.string.application));
    }

    @Override
    public void setPresenter(RoomListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showGroupData(Group group) {
        mGroup = group;
        mAdapter.updateData(group);
        updateRoomListUi();
    }
}
