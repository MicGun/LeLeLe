package com.hugh.lelele.group_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class GroupDetailsFragment extends Fragment implements GroupDetailsContract.View {

    private GroupDetailsContract.Presenter mPresenter;
    private GroupDetailsAdapter mAdapter;
    private Group mGroup;

    private ArrayList<Room> mRooms;

    private EditText mEditGroupName;
    private EditText mEditGroupAddress;
    private EditText mEditGroupAddRoomName;
    private TextView mNumberOfRooms;
    private TextView mNumberOfTenants;
    private ImageView mAddRoomButton;
    private String mRoomName;
    private ArrayList<String> mRoomNames;

    public GroupDetailsFragment() {
        mRoomNames = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group_details, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_group_details_room_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        mEditGroupName = root.findViewById(R.id.edit_text_group_name_group_detail);
        mEditGroupAddress = root.findViewById(R.id.edit_text_group_address_group_detail);
        mEditGroupAddRoomName = root.findViewById(R.id.edit_text_add_room_name_group_detail);
        mNumberOfRooms = root.findViewById(R.id.text_view_number_of_rooms_group_details);
        mNumberOfTenants = root.findViewById(R.id.text_view_number_of_tenants_group_details);
        mAddRoomButton = root.findViewById(R.id.image_view_add_room_button);

        mEditGroupName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mGroup.setGroupRoomNumber(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditGroupAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mGroup.setGroupAddress(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditGroupAddRoomName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRoomName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAddRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRoomNames = getRoomNames(); //to sync RoomName List and mRooms
                if (!mRoomNames.contains(mRoomName)) {
                    Room room = new Room();
                    mRoomNames.add(mRoomName);
                    room.setRoomName(mRoomName);
                    mRooms.add(room);
                    mEditGroupAddRoomName.setText("");
                    notifyRoomDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "房間名稱請勿重複!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        init();

        return root;
    }

    private void init() {

        if (!mGroup.getGroupName().equals("")) {

            mEditGroupName.setText(mGroup.getGroupName());
            mEditGroupName.setKeyListener(null);

            mEditGroupAddress.setText(mGroup.getGroupAddress());
            mNumberOfRooms.setText(mGroup.getGroupRoomNumber());
            mNumberOfTenants.setText(mGroup.getGroupTenantNumber());
        }
    }

    private ArrayList<String> getRoomNames() {
        mRoomNames.clear();

        for (Room room:mRooms) {
            mRoomNames.add(room.getRoomName());
        }
        return mRoomNames;
    }

    private void notifyRoomDataSetChanged() {

        mNumberOfRooms.setText(String.valueOf(mRooms.size()));
        mAdapter.updateData(mRooms);

    }

    public static GroupDetailsFragment newInstance() {
        return new GroupDetailsFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.updateToolbar(getResources().getString(R.string.application_groups_list));
    }

    @Override
    public void setPresenter(GroupDetailsContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showGroupDetailsUi(Group group) {
        mGroup = group;
        mRooms = mGroup.getRooms();
        if (mAdapter == null) {
            mAdapter = new GroupDetailsAdapter(mPresenter);
            mAdapter.updateData(group.getRooms());
        } else {
            mAdapter.updateData(group.getRooms());
        }
    }

    @Override
    public void updateRoomDataUi(ArrayList<Room> rooms) {
        mRooms = rooms;
        notifyRoomDataSetChanged();
    }
}
