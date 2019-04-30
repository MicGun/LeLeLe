package com.hugh.lelele.electricity_landlord;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class ElectricityLandlordFragment extends Fragment implements ElectricityLandlordContract.View {

    private ElectricityLandlordContract.Presenter mPresenter;
    private ElectricityLandlordAdapter mAdapter;
    private FloatingActionButton mFloatingElectricityEditDoneButton;
    private RecyclerView mRecyclerView;
    private ArrayList<Room> mRooms;

    private EditText mUnitElectricityFeeEditText;
    private ImageView mButtonUnitElectricityFeeCheck;
    private double mUnitPrice;

    private ProgressBar mProgressBar;

    final String TAG = LeLeLe.class.getSimpleName();

    public ElectricityLandlordFragment() {
        mUnitPrice = 0;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ElectricityLandlordAdapter(mPresenter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_electricity_landlord, container, false);

        mRecyclerView = root.findViewById(R.id.recycler_electricity_editor_landlord);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mProgressBar = root.findViewById(R.id.progress_bar_electricity_fee);

        mUnitElectricityFeeEditText = root.findViewById(R.id.edit_text_unit_price_electricity);
        mUnitElectricityFeeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUnitPrice = Double.valueOf(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mButtonUnitElectricityFeeCheck = root.findViewById(R.id.image_view_button_unit_price_check_electricity);
        mButtonUnitElectricityFeeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUnitPrice != 0) {
                    mAdapter.setUnitPrice(mUnitPrice);
                }
            }
        });

        mFloatingElectricityEditDoneButton = root.findViewById(R.id.button_electricity_edit_done_electricity_editor);
        mFloatingElectricityEditDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar(true);
                mPresenter.checkRoomData(mRooms);
            }
        });

        mPresenter.hideBottomNavigation();

        return root;
    }

    private void showProgressBar(boolean showProgress) {
        if (showProgress) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(ElectricityLandlordContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    public static ElectricityLandlordFragment newInstance() {

        return new ElectricityLandlordFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.showBottomNavigation();
        mPresenter.updateToolbar(getResources().getString(R.string.application));
    }

    @Override
    public void showElectricityEditorUi(ArrayList<Room> rooms) {
        mRooms = rooms;
        showProgressBar(false);
        if (mAdapter == null) {
            mAdapter = new ElectricityLandlordAdapter(mPresenter);
            mAdapter.updateData(rooms);
        } else {
            mAdapter.updateData(rooms);
        }
    }

    @Override
    public void showHasEmptyDataToast() {
        showProgressBar(false);
        Toast.makeText(getContext(), getString(R.string.has_empty_data), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toBackStack() {
        showProgressBar(false);
        mPresenter.showLastFragment();
    }
}
