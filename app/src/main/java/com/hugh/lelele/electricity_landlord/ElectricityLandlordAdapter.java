package com.hugh.lelele.electricity_landlord;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Room;
import com.hugh.lelele.util.UserManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ElectricityLandlordAdapter extends RecyclerView.Adapter {

    private ElectricityLandlordContract.Presenter mPresenter;
    private ArrayList<Room> mRooms;
    private int mMonth;
    private int mYear;
    private int mMonthIndex;
    private String mMonthBeUpdated;
    private String mYearBeUpdated;
    private String mMonthBeUpdatedNext;
    private String mYearBeUpdatedNext;

    private double mUnitPrice;

    private final String BASE_LINE_MONTH = "00";

    public ElectricityLandlordAdapter(ElectricityLandlordContract.Presenter presenter) {
        mPresenter = presenter;
        mUnitPrice = 0;
        mMonth = Calendar.getInstance().get(Calendar.MONTH);
        mYear = Calendar.getInstance().get(Calendar.YEAR);

        //to handle if wants to update Dec's electricity fee of last year
        mMonthBeUpdated = mPresenter.getMonthBeUpdated(mMonth);
        mYearBeUpdated = mPresenter.getYearBeUpdated(mMonth, mYear);
        mMonthBeUpdatedNext = mPresenter.getMonthBeUpdatedNext(mMonth);

    }

    public class ElectricityEditorLandlordItemViewHolder extends RecyclerView.ViewHolder {

        TextView roomNumber;
        EditText scaleLast;
        EditText scaleThis;
        ImageButton cleanButton;

        public ElectricityEditorLandlordItemViewHolder(@NonNull View itemView) {
            super(itemView);

            roomNumber = itemView.findViewById(R.id.text_landlord_item_view_room_number);
            scaleLast = itemView.findViewById(R.id.text_landlord_item_view_scale_last);
            scaleThis = itemView.findViewById(R.id.edir_text_landlord_item_view_scale_this);
            cleanButton = itemView.findViewById(R.id.button_landlord_item_view_clean);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_electricity_fee_editor_landlord, viewGroup, false);

        ElectricityEditorLandlordItemViewHolder viewHolder =
                new ElectricityEditorLandlordItemViewHolder(rootView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

        final Room room = mRooms.get(i);
        Log.v("adapter", "electricity size: " + room.getElectricities().size());

        //指標從0開始，有多一個base line month，因此長度會變為13
        final Electricity electricityThis = room.getElectricities().get(mPresenter.getMonthIndex(mMonth));

        initElectricityDocument4NewYear(room);

        ((ElectricityEditorLandlordItemViewHolder) viewHolder).roomNumber.setText(room.getRoomName());
        if (!electricityThis.getScaleLast().equals("")) {
            ((ElectricityEditorLandlordItemViewHolder) viewHolder).scaleLast.setText(electricityThis.getScaleLast());
            ((ElectricityEditorLandlordItemViewHolder) viewHolder).scaleLast.setKeyListener(null);
        } else {
            ((ElectricityEditorLandlordItemViewHolder) viewHolder).scaleLast.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                    if (s.length() != 0) {
                        electricityThis.setScaleLast(s.toString());
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }


        ((ElectricityEditorLandlordItemViewHolder) viewHolder).cleanButton
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ElectricityEditorLandlordItemViewHolder) viewHolder).scaleThis.setText("");
                    }
                });

        if (!electricityThis.getScale().equals("")) {
            ((ElectricityEditorLandlordItemViewHolder) viewHolder).scaleThis.setText(electricityThis.getScale());
        }

        ((ElectricityEditorLandlordItemViewHolder) viewHolder).scaleThis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String electricityLastData = String.valueOf(((ElectricityEditorLandlordItemViewHolder) viewHolder).scaleLast.getText());

                if (mUnitPrice != 0) {

                    //如果edittext為空就不執行
                    if (s.length() != 0 && !electricityLastData.equals("")) { //避免下面判別式掛掉
                        //如果本月度數低於上月度數，需紅字highlight
                        if (Integer.valueOf(s.toString()) < Integer.valueOf(electricityLastData)) {

                            ((ElectricityEditorLandlordItemViewHolder) viewHolder).scaleThis
                                    .setTextColor(LeLeLe.getAppContext().getColor(R.color.red_ff0000));

                            //本月度數高於上月度數即可上傳firestore
                        } else if (Integer.valueOf(s.toString()) >= Integer.valueOf(electricityLastData)) {
                            ((ElectricityEditorLandlordItemViewHolder) viewHolder).scaleThis
                                    .setTextColor(LeLeLe.getAppContext().getColor(R.color.black_3f3a3a));

                            setElectricityThis(s, electricityThis);

                            if (mMonth == 0) {
                                mPresenter.uploadElectricity(UserManager.getInstance().getLandlord().getEmail(),
                                        UserManager.getInstance().getUserData().getGroupNow(),
                                        room.getRoomName(), String.valueOf(mYear), BASE_LINE_MONTH, electricityThis);
                            }
                        }
                    } else {
                        Toast.makeText(LeLeLe.getAppContext(),
                                LeLeLe.getAppContext().getResources().getString(R.string.last_scale_can_not_be_empty),
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LeLeLe.getAppContext(),
                            LeLeLe.getAppContext().getResources().getString(R.string.empty_unit_electricity_fee),
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private void setElectricityThis(CharSequence s, Electricity electricityThis) {
        electricityThis.setScale(s.toString());
        int total_consumption = (int) (Double.valueOf(s.toString()) - Double.valueOf(electricityThis.getScaleLast()));
        int price = (int) Math.round(total_consumption * mUnitPrice);
        electricityThis.setPrice(String.valueOf(price));
        electricityThis.setTotalConsumption(String.valueOf(total_consumption));
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        electricityThis.setTime(String.valueOf(formatter.format(Calendar.getInstance().getTime())));
    }

    private void initElectricityDocument4NewYear(Room room) {
        //要初始化每年的一月，建立新的Electricity Collection
        if (mMonth == 0) {
            mPresenter.initialElectricityMonth(UserManager.getInstance().getLandlord().getEmail(),
                    UserManager.getInstance().getUserData().getGroupNow(),
                    room.getRoomName(), String.valueOf(mYear), mMonthBeUpdatedNext);
        }
    }

    @Override
    public int getItemCount() {
        if (mRooms != null) {
            return mRooms.size();
        } else {
            return 0;
        }
    }

    public void updateData(ArrayList<Room> rooms) {
        if (rooms != null) {
            mRooms = rooms;
            notifyDataSetChanged();
        }
    }

    public void setUnitPrice(double unitPrice) {
        mUnitPrice = unitPrice;
    }
}
