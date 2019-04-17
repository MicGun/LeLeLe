package com.hugh.lelele.room_list.invitation_dialog;

import android.view.View;

import com.hugh.lelele.data.source.LeLeLeRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class InvitationActionPresenter implements InvitationActionContract.Presenter {

    private InvitationActionContract.View mInvitationActionDialog;
    private View mView;

    public InvitationActionPresenter(InvitationActionContract.View invitationActionDialog, View view) {

        mInvitationActionDialog = checkNotNull(invitationActionDialog, "invitationActionDialog cannot be null!");
        mInvitationActionDialog.setPresenter(this);
        mView = view;
    }

    @Override
    public void start() {

    }
}
