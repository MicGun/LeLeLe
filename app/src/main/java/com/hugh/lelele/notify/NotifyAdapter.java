package com.hugh.lelele.notify;

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
import com.hugh.lelele.data.Notification;
import com.hugh.lelele.util.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotifyAdapter extends RecyclerView.Adapter {

    private NotifyContract.Presenter mPresenter;
    private ArrayList<Notification> mNotifications;


    private static final String TAG = "NotifyAdapter";

    public NotifyAdapter(NotifyContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        ImageView notificationImage;
        TextView contentText;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            notificationImage = itemView.findViewById(R.id.item_image_view_notify);
            contentText = itemView.findViewById(R.id.item_text_view_content_notification);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_notification, viewGroup, false);

        NotificationViewHolder viewHolder = new NotificationViewHolder(rootView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Notification notification = mNotifications.get(i);

        ((NotificationViewHolder) viewHolder).contentText.setText(notification.getContent());


        if (notification.getNotificationType().equals(Constants.ELECTRICITY)) {
            ((NotificationViewHolder) viewHolder).notificationImage
                    .setBackground(LeLeLe.getAppContext().getDrawable(R.drawable.ic_electricity));
        }

        if (notification.isRead()) {
            ((NotificationViewHolder) viewHolder).itemView
                    .setBackgroundColor(LeLeLe.getAppContext().getColor(R.color.while_ffffff_40_transparent));
        } else {
            ((NotificationViewHolder) viewHolder).itemView
                    .setBackgroundColor(LeLeLe.getAppContext().getColor(R.color.green_6eafa6_40_transparent));
        }

    }

    @Override
    public int getItemCount() {

        if (mNotifications != null) {
            return mNotifications.size();
        } else {
            return 0;
        }
    }

    public void updateData(ArrayList<Notification> notifications) {
        mNotifications = checkNotNull(notifications);
        notifyDataSetChanged();
        Log.d(TAG, "notifications size: " + mNotifications.size());
    }
}
