package org.redplatoon.reddified.app;

import android.support.v13.app.FragmentStatePagerAdapter;
import android.app.Fragment;
import android.app.FragmentManager;

/**
 * Created by nmiano on 4/21/14.
 */
public class PostFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private int NUM_ITEMS = 6;

    public PostFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch(position) {
            case 0:  title = "HOT";
                break;
            case 1:  title = "NEW";
                break;
            case 2:  title = "RISING";
                break;
            case 3:  title = "CONTROVERSIAL";
                break;
            case 4:  title = "TOP";
                break;
        }
        return title;
    }

    @Override
    public Fragment getItem(int position) {
        String filter;
        switch(position) {
            case 0:  filter = "";
                     break;
            case 1:  filter = "new";
                     break;
            case 2:  filter = "rising";
                     break;
            case 3:  filter = "controversial";
                     break;
            case 4:  filter = "top";
                     break;
            default: filter = "";
                     break;
        }
        return PostFragment.newInstance(filter);
    }
}
