package com.hugh.lelele.message;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.component.ProfileAvatarOutlineProvider;
import com.hugh.lelele.data.Message;
import com.hugh.lelele.util.Constants;
import com.hugh.lelele.util.ImageManager;
import com.hugh.lelele.util.UserManager;

import java.util.ArrayList;

import static com.google.android.gms.internal.firebase_messaging.zzg.checkNotNull;

public class MessageAdapter extends RecyclerView.Adapter {

    private static final int ME = 0x01;
    private static final int HE = 0x02;

    private MessageContract.Presenter mPresenter;

    private ArrayList<Message> mMessages;
    private String mUserType;

    private static final String TAG = "MessageAdapter";

    public MessageAdapter(MessageContract.Presenter presenter) {
        mPresenter = presenter;
        if (UserManager.getInstance().getUserType() == R.string.landlord) {
            mUserType = Constants.LANDLORD;
        } else if (UserManager.getInstance().getUserType() == R.string.tenant){
            mUserType = Constants.TENANT;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (mMessages.get(position).getSenderType().equals(mUserType)) {
            return ME;
        } else {
            return HE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View recyclerView;

        switch (viewType) {
            case ME:
                recyclerView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_message_me, viewGroup, false);
                viewHolder = new MessageMyViewHolder(recyclerView);
                break;
            case HE:
                recyclerView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_message_he, viewGroup, false);
                viewHolder = new MessageHisViewHolder(recyclerView);
                break;
            default:
                return null;
        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (viewHolder instanceof MessageMyViewHolder) {
            bindMessageMyViewHolder((MessageMyViewHolder) viewHolder, mMessages.get(i));
        } else {
            bindMessageHisViewHolder((MessageHisViewHolder) viewHolder, mMessages.get(i));
        }

    }

    private void bindMessageMyViewHolder(MessageMyViewHolder viewHolder, Message message) {

        viewHolder.getContent().setText(message.getContent());

    }

    private void bindMessageHisViewHolder(MessageHisViewHolder viewHolder, Message message) {

        viewHolder.getContent().setText(message.getContent());

        viewHolder.getHisName().setText(message.getSenderName());

        ImageManager.getInstance().setImageByUrl(viewHolder.getHisPicture(), message.getSenderPicture());
    }

    public class MessageMyViewHolder extends RecyclerView.ViewHolder {

        TextView content;

        public MessageMyViewHolder(@NonNull View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.item_text_me_message);
        }

        public TextView getContent() {
            return content;
        }
    }

    public class MessageHisViewHolder extends RecyclerView.ViewHolder {

        TextView hisName;
        ImageView hisPicture;
        TextView content;

        public MessageHisViewHolder(@NonNull View itemView) {
            super(itemView);

            hisName = itemView.findViewById(R.id.item_text_his_name_message);
            hisPicture = itemView.findViewById(R.id.item_image_his_picture_message);
            hisPicture.setOutlineProvider(new ProfileAvatarOutlineProvider(LeLeLe.getAppContext().
                    getResources().getDimensionPixelSize(R.dimen.login_radio_button_spacing)));
            content = itemView.findViewById(R.id.item_text_he_message);
        }

        public TextView getHisName() {
            return hisName;
        }

        public ImageView getHisPicture() {
            return hisPicture;
        }

        public TextView getContent() {
            return content;
        }
    }

    @Override
    public int getItemCount() {

        if (mMessages != null) {
            return mMessages.size();
        } else {
            return 0;
        }
    }

    public void updateData(ArrayList<Message> messages) {
        mMessages = checkNotNull(messages);
        notifyDataSetChanged();
        Log.d(TAG, "updateData: " + mMessages.size());
    }
}
