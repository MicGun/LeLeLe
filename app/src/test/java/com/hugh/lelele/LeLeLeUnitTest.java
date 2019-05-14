package com.hugh.lelele;

import com.hugh.lelele.data.source.LeLeLeRepository;
import com.hugh.lelele.electricity_landlord.ElectricityLandlordFragment;
import com.hugh.lelele.electricity_landlord.ElectricityLandlordPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

public class LeLeLeUnitTest {

    @Mock
    ElectricityLandlordFragment mElectricityLandlordFragment;

    @Mock
    LeLeLeRepository mLeLeLeRepository;

    private ElectricityLandlordPresenter mElectricityLandlordPresenter;

    @Before
    public void setup() {
        mElectricityLandlordPresenter = new ElectricityLandlordPresenter(mLeLeLeRepository, mElectricityLandlordFragment);
    }

    @Test
    public void testMonthBeUpdated() {

        assertEquals("12", mElectricityLandlordPresenter.getMonthBeUpdated(0));
    }

}
