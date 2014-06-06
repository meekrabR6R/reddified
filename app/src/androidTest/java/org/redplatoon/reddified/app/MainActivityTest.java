package org.redplatoon.reddified.app;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.contrib.DrawerActions.openDrawer;
import static com.google.android.apps.common.testing.ui.espresso.contrib.DrawerMatchers.isOpen;
import static com.google.android.apps.common.testing.ui.espresso.contrib.DrawerMatchers.isClosed;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

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
        onView(withId(R.id.drawer)).check(matches(isClosed()));
        openDrawer(R.id.drawer);
        onView(withId(R.id.drawer)).check(matches(isOpen()));
    }
}
