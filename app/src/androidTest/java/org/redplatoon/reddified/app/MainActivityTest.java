package org.redplatoon.reddified.app;

import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

/**
 * Created by nmiano on 6/5/14 2:06 PM for Reddified
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Activity mActivity;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ViewPager mViewPager;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
        mDrawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer);
        mDrawerList = (ListView) mActivity.findViewById(R.id.left_drawer);
        mViewPager = (ViewPager) mActivity.findViewById(R.id.pager);
    }

    public void testLayout() throws Exception {
        assertNotNull("Drawer not allowed to be null", mDrawerLayout);
        assertNotNull("Drawer list not allowed to be null", mDrawerList);
        assertNotNull("ViewPager not allowed to be null", mViewPager);
        assertEquals(true, mActivity.getActionBar().isShowing());
    }

    public void testDrawerToggle() throws Exception {
        assertEquals(false, mDrawerLayout.isDrawerOpen(GravityCompat.START));
        mDrawerLayout.performClick();
        //assertEquals(true, mDrawerLayout.isDrawerOpen(GravityCompat.START));
    }
}
