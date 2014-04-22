package org.redplatoon.reddified.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements PostFragment.OnFragmentInteractionListener {

    private ViewPager mViewPager;
    private PostFragmentStatePagerAdapter mPostFragmentPagerAdapter;
    private static final int TABS_COUNT = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //FragmentManager fragmentManager = getFragmentManager();
        //final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        final ActionBar actionBar = getActionBar();

        mPostFragmentPagerAdapter = new PostFragmentStatePagerAdapter(getFragmentManager());

        //mViewPager = new ViewPager(this);
        //mViewPager.setId(R.id.pager);
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
                System.out.println("CURRITEM: " + mViewPager.getCurrentItem());
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


