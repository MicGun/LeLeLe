package com.hugh.lelele.electricity_landlord;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Electricity;
import com.hugh.lelele.data.Room;

import java.util.ArrayList;

public class ElectricityLandlordAdapter extends RecyclerView.Adapter {

    private ElectricityLandlordContract.Presenter mPresenter;
    private ArrayList<Room> mRooms;

    public ElectricityLandlordAdapter(ElectricityLandlordContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public class ElectricityEditorLandlordItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView roomNumber;
        TextView scaleLast;
        EditText scaleThis;
        ImageButton cleanButton;

        public ElectricityEditorLandlordItemViewHolder(@NonNull View itemView) {
            super(itemView);

            roomNumber = itemView.findViewById(R.id.text_landlord_item_view_room_number);
            scaleLast = itemView.findViewById(R.id.text_landlord_item_view_scale_last);
            scaleThis = itemView.findViewById(R.id.edir_text_landlord_item_view_scale_this);
            cleanButton = itemView.findViewById(R.id.button_landlord_item_view_clean);
            cleanButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Room room = mRooms.get(i);
//        Electricity electricity = room.getElectricities().get(2);

        ((ElectricityEditorLandlordItemViewHolder) viewHolder).roomNumber.setText(room.getRoomName());
//        ((ElectricityEditorLandlordItemViewHolder) viewHolder).scaleLast.setText(electricity.getPrice());

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
}
