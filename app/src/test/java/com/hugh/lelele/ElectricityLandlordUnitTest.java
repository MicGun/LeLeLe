package com.hugh.lelele;

import com.hugh.lelele.data.Notification;
import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.electricity_landlord.ElectricityLandlordFragment;
import com.hugh.lelele.electricity_landlord.ElectricityLandlordPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ElectricityLandlordUnitTest {

    @Mock
    ElectricityLandlordFragment mElectricityLandlordFragment;

    @Mock
    LeLeLeRepository mLeLeLeRepository;

    private ElectricityLandlordPresenter mElectricityLandlordPresenter;

    @Before
    public void setup() {

        //to init all mocks
        MockitoAnnotations.initMocks(this);
        mElectricityLandlordPresenter = new ElectricityLandlordPresenter(mLeLeLeRepository, mElectricityLandlordFragment);
    }

    @Test
    public void testMonthBeUpdated() {

        assertEquals("12", mElectricityLandlordPresenter.getMonthBeUpdated(0));
    }

    @Test
    public void testYearBeUpdated() {

        assertEquals("2019", mElectricityLandlordPresenter.getYearBeUpdated(0, 2020));
    }

    @Test
    public void testMonthBeUpdatedNext() {

        assertEquals("01", mElectricityLandlordPresenter.getMonthBeUpdatedNext(0));
    }

}
