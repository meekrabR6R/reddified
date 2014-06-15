package org.redplatoon.reddified.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements PostFragment.OnFragmentInteractionListener, ItemFragment.OnFragmentInteractionListener {

    public static final String USER_CREDS = "ReddifiedUser";
    private SharedPreferences mSettings;
    private ViewPager mViewPager;
    private PostFragmentStatePagerAdapter mPostFragmentPagerAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mDrawerItems;
    private Fragment mItemFragment;
    public static String[] DRAWER_CONTENTS;
    private ActionBar.TabListener mTabListener;
    private static final int TABS_COUNT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.main);

        mSettings = getSharedPreferences(USER_CREDS, Context.MODE_PRIVATE);

        //FragmentManager fragmentManager = getFragmentManager();
        //final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        if(mSettings.contains("modHash"))
            mDrawerItems = getResources().getStringArray(R.array.signed_in_drawer_menu);
        else
            mDrawerItems = getResources().getStringArray(R.array.signed_out_drawer_menu);

        DRAWER_CONTENTS =mDrawerItems;

        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mDrawerItems));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        final ActionBar actionBar = getActionBar();

        mPostFragmentPagerAdapter = new PostFragmentStatePagerAdapter(getFragmentManager());

        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(mPostFragmentPagerAdapter);

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        if(position < TABS_COUNT)
                            actionBar.setSelectedNavigationItem(position);
                    }
                });

        // Create a tab listener that is called when the user changes tabs.
        mTabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // When the tab is selected, switch to the
                // corresponding page in the ViewPager.
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };

        addTabsToActionBar(actionBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostFragmentInteraction() {
        // Create new fragment and transaction
        mItemFragment = ItemFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        mViewPager.setVisibility(View.GONE);
        getActionBar().removeAllTabs();
        transaction.replace(R.id.item_container, mItemFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onItemFragmentInteraction() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(mItemFragment);
        mViewPager.setVisibility(View.VISIBLE);
        addTabsToActionBar(getActionBar());
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            String menuItem = mDrawerItems[position];

            if(menuItem.equals("Sign In")) {
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            } else if(menuItem.equals("Sign Out")) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.remove("cookie");
                editor.remove("modHash");
                editor.commit();
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.farewell), Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(mItemFragment != null)
            onItemFragmentInteraction();
        
        super.onBackPressed();
    }

    private void addTabsToActionBar(ActionBar actionBar) {
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.hot_tab)).setTabListener(mTabListener));
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.new_tab)).setTabListener(mTabListener));
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.rising_tab)).setTabListener(mTabListener));
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.controversial_tab)).setTabListener(mTabListener));
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.top_tab)).setTabListener(mTabListener));
    }
}


