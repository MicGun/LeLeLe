package com.hugh.lelele.group_list;

import com.hugh.lelele.BasePresenter;
import com.hugh.lelele.BaseView;
import com.hugh.lelele.data.Group;
import com.hugh.lelele.home.HomeContract;

import java.util.ArrayList;

public interface GroupListContract {

    interface View extends BaseView<GroupListContract.Presenter> {

        void showGroupListUi(ArrayList<Group> groups);

        void reLoadGroupList();
    }

    interface Presenter extends BasePresenter {

        void hideBottomNavigation();

        void showBottomNavigation();

        void updateToolbar(String title);

        void setGroupsData(ArrayList<Group> groups);

        void openGroupDetails(Group group);

        void loadGroupList(String email);

        void notifyGroupListChanged();

        void loadGroupListDrawerMenu();

        void showLastFragment();
    }
}
