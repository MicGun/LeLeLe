package com.hugh.lelele.notify;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.hugh.lelele.data.Notification;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotifyAdapter extends RecyclerView.Adapter {

    private NotifyContract.Presenter mPresenter;
    private ArrayList<Notification> mNotifications;

    private static final String TAG = "NotifyAdapter";

    public NotifyAdapter(NotifyContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

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
