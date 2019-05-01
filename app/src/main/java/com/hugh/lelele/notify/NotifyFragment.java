package com.hugh.lelele.notify;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.LeLeLe;
import com.hugh.lelele.R;
import com.hugh.lelele.data.Notification;
import com.hugh.lelele.data.loco_data.NotificationDAO;
import com.hugh.lelele.data.loco_data.NotificationDatabase;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotifyFragment extends Fragment implements NotifyContract.View {

    private NotifyContract.Presenter mPresenter;
    private NotifyAdapter mNotifyAdapter;

    private ArrayList<Notification> mNotifications;

    private static final String TAG = "NotifyFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotifyAdapter = new NotifyAdapter(mPresenter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPresenter.loadNotifications();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notify, container, false);

        return root;
    }

    public static NotifyFragment newInstance() {
        return new NotifyFragment();
    }

    @Override
    public void setPresenter(NotifyContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showNotifications(ArrayList<Notification> notifications) {
        mNotifications = notifications;

        if (mNotifyAdapter == null) {
            mNotifyAdapter = new NotifyAdapter(mPresenter);
            mNotifyAdapter.updateData(notifications);
        } else {
            mNotifyAdapter.updateData(notifications);
        }
    }
}
