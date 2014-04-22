package org.redplatoon.reddified.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity implements PostFragment.OnFragmentInteractionListener {

    private ViewPager mViewPager;
    private PostFragmentStatePagerAdapter mPostFragmentPagerAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ArrayList<String> mDrawerItems = new ArrayList<String>();

    private static final int TABS_COUNT = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //FragmentManager fragmentManager = getFragmentManager();
        //final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //TEMP!!
        //mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerItems.add("Sign In");

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                               R.layout.drawer_list_item, mDrawerItems));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
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

        // Set the drawer toggle as the DrawerListener
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
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
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

        actionBar.addTab(actionBar.newTab().setText(getString(R.string.hot_tab)).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.new_tab)).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.rising_tab)).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.controversial_tab)).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText(getString(R.string.top_tab)).setTabListener(tabListener));
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

    public void onFragmentInteraction(String id) {
        //TODO: Something..
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            //selectItem(position);
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


    //private void signIn() {
        /*
        final Button mSignout = (Button) findViewById(R.id.sign_out);
        final Map<String, ?> prefs = settings.getAll();

        if(prefs.containsKey("cookie") && prefs.containsKey("modHash")) {
            mSignout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.remove("cookie");
                    editor.remove("modHash");
                    editor.commit();
                    Toast toast = Toast.makeText(getApplicationContext(), "See ya", Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(this, SigninActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        */
    //}
}


