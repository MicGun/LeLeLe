package com.hugh.lelele;

import com.hugh.lelele.data.Notification;
import com.hugh.lelele.data.source.LeLeLeRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MainPresenterUnitTest {

    @Mock
    MainContract.View mView;

    @Mock
    LeLeLeRepository mLeLeLeRepository;

    private MainPresenter mMainPresenter;

    ArrayList<Notification> mNotifications;

    @Before
    public void setup() {

        //to init all mocks
        MockitoAnnotations.initMocks(this);
        mMainPresenter = new MainPresenter(mLeLeLeRepository, mView);

        mNotifications = new ArrayList<>();

        Notification notification_1 = new Notification();
        notification_1.setIsRead(true);

        Notification notification_2 = new Notification();
        notification_2.setIsRead(false);

        Notification notification_3 = new Notification();
        notification_3.setIsRead(false);

        mNotifications.add(notification_1);
        mNotifications.add(notification_2);
        mNotifications.add(notification_3);

    }

    @Test
    public void testUnreadNotificationsAmount() {

        assertEquals(2, mMainPresenter.countUnreadNotification(mNotifications));
    }

}
