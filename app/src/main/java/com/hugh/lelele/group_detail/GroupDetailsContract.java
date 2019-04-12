package com.hugh.lelele.group_detail;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.group_list.GroupListContract;

import java.util.ArrayList;

public interface GroupDetailsContract {

    interface View extends BaseView<GroupDetailsContract.Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
