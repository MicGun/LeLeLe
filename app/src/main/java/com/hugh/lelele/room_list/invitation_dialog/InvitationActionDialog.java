package com.hugh.lelele.room_list.invitation_dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.hugh.lelele.R;
import com.hugh.lelele.data.Room;

import static com.google.common.base.Preconditions.checkNotNull;

public class InvitationActionDialog extends DialogFragment implements InvitationActionContract.View {

    private InvitationActionContract.Presenter mPresenter;
    private View mView;
    private String mMessage;
    private Room mRoom;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mPresenter.getViewType();

        if (mView.getId() == R.id.item_image_view_room_list_inviting_tenant) {
            mMessage = getString(R.string.cancel_action_content, mRoom.getTenant().getName());

            builder.setMessage(mMessage)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.cancelInvitingAction(mRoom);
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

        } else if (mView.getId() == R.id.item_image_view_room_list_delete_tenant) {
            mMessage = getString(R.string.remove_action_content, mRoom.getTenant().getName());

            builder.setMessage(mMessage)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.removeTenantAction(mRoom);
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
        }



        return builder.create();
    }

    @Override
    public void setPresenter(InvitationActionContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setViewType(View viewType, Room room) {
        mView = checkNotNull(viewType);
        mRoom = checkNotNull(room);
    }
}
