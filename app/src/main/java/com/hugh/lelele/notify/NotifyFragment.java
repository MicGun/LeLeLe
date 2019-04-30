package com.hugh.lelele.notify;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class NotifyFragment extends Fragment implements NotifyContract.View {

    private NotifyContract.Presenter mPresenter;

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
}
