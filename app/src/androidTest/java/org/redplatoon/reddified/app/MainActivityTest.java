package org.redplatoon.reddified.app;

import android.app.Activity;
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

    /*
    public void testDrawerToggle() throws Exception {
        onView(withId(R.id.drawer)).check(matches(isClosed()));
        openDrawer(R.id.drawer);
        onView(withId(R.id.drawer)).check(matches(isOpen()));
        closeDrawer(R.id.drawer);
        onView(withId(R.id.drawer)).check(matches(isClosed()));
    }

    public void testDrawerOpenAndClick() {
        openDrawer(R.id.drawer);
        onView(withId(R.id.drawer)).check(matches(isOpen()));

        // Click an item in the drawer. We use onData because the drawer is backed by a ListView, and
        // the item may not necessarily be visible in the view hierarchy.
        int rowIndex = 1;
        String rowContents = MainActivity.DRAWER_CONTENTS[rowIndex];
        onData(allOf(is(instanceOf(String.class)), is(rowContents))).perform(click());

        // clicking the item should close the drawer.
        onView(withId(R.id.drawer)).check(matches(isClosed()));

        // The text view will now display "You picked: Pickle"
        //onView(withId(R.id.drawer_text_view)).check(matches(withText("You picked: " + rowContents)));
    }
    */
}
