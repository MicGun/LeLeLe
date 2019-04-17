package com.hugh.lelele.room_list.invitation_dialog;

import android.view.View;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.data.Room;

public interface InvitationActionContract {

    interface View extends BaseView<InvitationActionContract.Presenter> {

        void setViewType(android.view.View viewType);
    }

    interface Presenter extends BasePresenter {

        void getViewType();
    }
}
