package com.hugh.lelele.application_landlord;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Landlord;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class AppLandlordFragment extends Fragment implements AppLandlordContract.View {

    private AppLandlordContract.Presenter mPresenter;

    private Button mElectricityButton;
    private Button mGroupListButton;
    private Button mRoomListButton;
    private Button mMessageButton;
    private ProgressBar mProgressBar;

    private Landlord mLandlord;

    private boolean mIsLoading;

    private static final String TAG = "AppLandlordFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_app_landlord, container, false);

        mLandlord = UserManager.getInstance().getLandlord();

        mProgressBar = root.findViewById(R.id.progress_bar_app_landlord);

        mElectricityButton = root.findViewById(R.id.button_electricity_landlord);
        mElectricityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLoading()) {
                    if (!UserManager.getInstance().getUserData().getGroupNow().equals("")) {
                        mPresenter.loadRoomList(mLandlord.getEmail(), UserManager.getInstance().getUserData().getGroupNow());
                        setLoading(true);
                    } else {
                        Toast.makeText(getContext(), getString(R.string.select_group_before_edit), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mGroupListButton = root.findViewById(R.id.button_group_list_landlord);
        mGroupListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLoading()) {
                    mPresenter.loadGroupListFromApp(mLandlord.getEmail());
                    showProgressBar(true);
                    setLoading(true);
                }
            }
        });

        mRoomListButton = root.findViewById(R.id.button_room_list_landlord);
        mRoomListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserManager.getInstance().getUserData().getGroupNow().equals("")) {
                    mPresenter.openRoomList();
                } else {
                    Toast.makeText(getContext(), getString(R.string.select_group_before_edit), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mMessageButton = root.findViewById(R.id.button_message_landlord);
        mMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + UserManager.getInstance().getUserData().getGroupNow());
                if (!UserManager.getInstance().getUserData().getGroupNow().equals("")) {
                    mPresenter.openMessagingList();
                } else {
                    Toast.makeText(getContext(), getString(R.string.select_group_before_chatting), Toast.LENGTH_SHORT).show();
                }
            }
        });

        showProgressBar(false);
        setLoading(false);

        return root;
    }

    @Override
    public void showProgressBar(boolean showProgress) {
        if (showProgress) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public static AppLandlordFragment newInstance() {
        return new AppLandlordFragment();
    }

    @Override
    public void setPresenter(AppLandlordContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showElectricityEditorUi(ArrayList<Room> rooms) {
        mPresenter.openElectricityEditor(rooms);
    }

    @Override
    public void showGroupListUi(ArrayList<Group> groups) {
        mPresenter.openGroupList(groups);
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    @Override
    public void setLoading(boolean loading) {
        mIsLoading = loading;
    }
}
