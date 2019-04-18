package com.hugh.lelele.room_list.invitation_dialog;

import android.view.View;

import com.hugh.lelele.data.Room;
import com.hugh.lelele.data.source.LeLeLeRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class InvitationActionPresenter implements InvitationActionContract.Presenter {

    private InvitationActionContract.View mInvitationActionDialog;
    private View mView;
    private Room mRoom;

    public InvitationActionPresenter(InvitationActionContract.View invitationActionDialog, View view, Room room) {

        mInvitationActionDialog = checkNotNull(invitationActionDialog, "invitationActionDialog cannot be null!");
        mInvitationActionDialog.setPresenter(this);
        mView = view;
        mRoom = room;
    }

    @Override
    public void start() {

    }

    @Override
    public void getViewType() {
        mInvitationActionDialog.setViewType(mView, mRoom);
    }

    @Override
    public void cancelInvitingAction(Room room) {

    }

    @Override
    public void removeTenantAction(Room room) {

    }
}
