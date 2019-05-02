package com.hugh.lelele.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugh.lelele.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class MessageFragment extends Fragment implements MessageContract.View {

    private MessageContract.Presenter mPresenter;
    private MessageAdapter mMessageAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_message, container, false);

        mPresenter.hideBottomNavigation();

        return root;
    }

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.showBottomNavigation();
    }

    @Override
    public void setPresenter(MessageContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }
}
