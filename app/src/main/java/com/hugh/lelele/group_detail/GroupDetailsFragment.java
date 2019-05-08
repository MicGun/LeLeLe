package com.hugh.lelele.group_detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.MainActivity;
import com.hugh.lelele.R;
import com.hugh.lelele.component.GridSpacingItemDecoration;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class GroupDetailsFragment extends Fragment implements GroupDetailsContract.View {

    private static final String TAG = GroupDetailsFragment.class.getSimpleName();

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
    private FloatingActionButton mFloatingGroupDetailEditDoneButton;
    private boolean mIsNewGroup;

    private ProgressBar mProgressBar;

    public GroupDetailsFragment() {
        mRoomNames = new ArrayList<>();
        mRoomName = "";
        mIsNewGroup = true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group_details, container, false);

        root.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mPresenter.hideKeyBoard();
                }
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.recycler_group_details_room_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1,
                getContext().getResources().getDimensionPixelOffset(R.dimen.space_detail_circle), true));

        mProgressBar = root.findViewById(R.id.progress_bar_group_list);

        mEditGroupName = root.findViewById(R.id.edit_text_group_name_group_detail);
        mEditGroupAddress = root.findViewById(R.id.edit_text_group_address_group_detail);
        mEditGroupAddRoomName = root.findViewById(R.id.edit_text_add_room_name_group_detail);
        mNumberOfRooms = root.findViewById(R.id.text_view_number_of_rooms_group_details);
        mNumberOfTenants = root.findViewById(R.id.text_view_number_of_tenants_group_details);
        mAddRoomButton = root.findViewById(R.id.image_view_add_room_button);
        mFloatingGroupDetailEditDoneButton = root.findViewById(R.id.button_group_edit_done_group_details);

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
                if (!mRoomName.equals("")) {
                    if (!mRoomNames.contains(mRoomName)) {
                        Room room = new Room();
                        mRoomNames.add(mRoomName);
                        room.setRoomName(mRoomName);
                        room.getTenant().setRoomNumber(mRoomName);
                        mRooms.add(room);
                        mEditGroupAddRoomName.setText("");
                        notifyRoomDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.should_not_repeat_room_name), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.room_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mFloatingGroupDetailEditDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCompleted()) {
                    Toast.makeText(getContext(), getString(R.string.group_details_edit_done), Toast.LENGTH_SHORT).show();
                    mGroup = getGroupFinalStatus();
                    mPresenter.uploadGroup(mGroup);
                    mPresenter.showLastFragment();
                    mPresenter.loadGroupListDrawerMenu();
                } else {
                    Toast.makeText(getContext(), getString(R.string.info_cannot_be_empty), Toast.LENGTH_SHORT).show();
                }

            }
        });

        init();

        return root;
    }

    private boolean isCompleted() {
        return (!mEditGroupName.getText().toString().equals("") &&
                !mEditGroupAddress.getText().toString().equals(""));
    }

    private void init() {

        if (!mGroup.getGroupName().equals("")) {

            mEditGroupName.setText(mGroup.getGroupName());
            mEditGroupName.setKeyListener(null); //不可更改已經存在群組的名稱

            mEditGroupAddress.setText(mGroup.getGroupAddress());
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

            loadRoomList();
        } else {
            showProgressBar(false);
        }
    }

    private void loadRoomList() {
        //如果是要編輯既有群組，需要去下載群組原有房間，而且房間名稱需不能被更改
        mIsNewGroup = false;
        mPresenter.loadRoomListFromGroupDetails(UserManager.getInstance().getLandlord().getEmail(),
                mGroup.getGroupName());
        showProgressBar(true);
    }

    private Group getGroupFinalStatus() {

        mGroup.setRooms(mRooms);
        mGroup.setGroupRoomNumber(String.valueOf(mRooms.size()));
        mGroup.setGroupAddress(String.valueOf(mEditGroupAddress.getText()));
        mGroup.setGroupName(String.valueOf(mEditGroupName.getText()));

        return mGroup;
    }

    private ArrayList<String> getRoomNames() {
        mRoomNames.clear();

        for (Room room : mRooms) {
            mRoomNames.add(room.getRoomName());
        }
        return mRoomNames;
    }

    private void notifyRoomDataSetChanged() {

        mNumberOfRooms.setText(String.valueOf(mRooms.size()));
        mAdapter.updateData(mRooms);
        showProgressBar(false);

    }

    private void showProgressBar(boolean showProgress) {
        if (showProgress) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public static GroupDetailsFragment newInstance() {
        return new GroupDetailsFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.updateToolbar(getResources().getString(R.string.application_groups_list));
        mPresenter.notifyGroupListChanged();
        mPresenter.hideKeyBoard();
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

    @Override
    public void deleteRoomData(Room room) {
        //如果不是原有群組，代表firestore上沒有資料，因此不須刪除firestore上的房間，反之則需要刪除
        if (!mIsNewGroup) {
            mPresenter.deleteRemoteRoom(room, mGroup.getGroupName(),
                    UserManager.getInstance().getLandlord().getEmail());
        }
    }
}
