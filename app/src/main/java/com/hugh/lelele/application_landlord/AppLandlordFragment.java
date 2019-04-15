package com.hugh.lelele.application_landlord;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button mMessageButton;

    private Landlord mLandlord;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_app_landlord, container, false);

        mLandlord = UserManager.getInstance().getLandlord();

        mElectricityButton = root.findViewById(R.id.button_electricity_landlord);

        mElectricityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserManager.getInstance().getUserData().getGroupNow().equals("")) {
                    mPresenter.loadRoomList(mLandlord.getEmail(), UserManager.getInstance().getUserData().getGroupNow());
                } else {
                    Toast.makeText(getContext(), "選擇群組後，才可進行編輯。", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mGroupListButton = root.findViewById(R.id.button_group_list_landlord);
        mGroupListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.loadGroupListFromApp(mLandlord.getEmail());
            }
        });
        return root;
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
}
