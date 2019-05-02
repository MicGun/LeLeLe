package com.hugh.lelele.message;

import android.support.annotation.NonNull;

import com.hugh.lelele.data.source.LeLeLeRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class MessagePresenter implements MessageContract.Presenter {

    private LeLeLeRepository mLeLeLeRepository;
    private MessageContract.View mMessageView;

    public MessagePresenter(@NonNull LeLeLeRepository leLeLeRepository, @NonNull MessageContract.View messageView) {
        mLeLeLeRepository = checkNotNull(leLeLeRepository, "leleleRepository cannot be null!");
        mMessageView = checkNotNull(messageView, "messageView cannot be null!");
        mMessageView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void showBottomNavigation() {

    }

    @Override
    public void hideBottomNavigation() {

    }
}
