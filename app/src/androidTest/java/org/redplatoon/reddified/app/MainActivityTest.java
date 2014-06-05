package org.redplatoon.reddified.app;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by nmiano on 6/5/14 2:06 PM for Reddified
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Activity mActivity;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    public void testLayout() throws Exception {
        DrawerLayout drawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer);
        assertNotNull("Drawer not allowed to be null", drawerLayout);

        ViewPager viewPager = (ViewPager) mActivity.findViewById(R.id.pager);
        assertNotNull("ViewPager not allowed to be null", viewPager);

    }
}
