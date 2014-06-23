package org.redplatoon.reddified.app.services;

import android.test.ActivityInstrumentationTestCase2;

import org.redplatoon.reddified.app.MainActivity;

/**
 * Created by nmiano on 6/23/14 6:48 PM for Reddified
 */
public class MixPanelServiceTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private MainActivity mActivity;
    private MixPanelService.ReddifiedMixpanelAPI mReddifiedMixpanelAPI;

    public MixPanelServiceTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
        mReddifiedMixpanelAPI =
                MixPanelService.createReddifiedMixpanelAPIInstance(mActivity);
    }

    public void testSetTracking() throws Exception {
        assertEquals(false, mReddifiedMixpanelAPI.isTracking());
        mReddifiedMixpanelAPI.setTracking(true);
        assertEquals(true, mReddifiedMixpanelAPI.isTracking());
        mReddifiedMixpanelAPI.setTracking(false);
    }
}
