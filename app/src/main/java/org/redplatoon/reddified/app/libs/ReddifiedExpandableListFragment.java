package org.redplatoon.reddified.app.libs;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import org.redplatoon.reddified.app.R;
import org.redplatoon.reddified.app.services.RedditService;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

/**
 * Created by nmiano on 7/8/14 11:03 PM for Reddified
 */
public abstract class ReddifiedExpandableListFragment extends Fragment implements OnRefreshListener {


    protected PullToRefreshLayout mPullToRefreshLayout;
    protected static final String USER_CREDS = "ReddifiedUser";
    protected SharedPreferences mSettings;
    protected String mUserAgent;
    protected String mModHash;
    protected String mCookie;
    protected RedditService mRedditService;

    public ReddifiedExpandableListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = getActivity().getSharedPreferences(USER_CREDS, Context.MODE_PRIVATE);
        mUserAgent = getString(R.string.user_agent);

        if(mSettings.contains("modHash"))
            mModHash = mSettings.getString("modHash", "");

        if(mSettings.contains("cookie"))
            mCookie = mSettings.getString("cookie", "");

        mRedditService = new RedditService(mUserAgent, mModHash, mCookie);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        // This is the View which is created by ListFragment
        ViewGroup viewGroup = (ViewGroup) view;

        // We need to create a PullToRefreshLayout manually
        mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());

        // We can now setup the PullToRefreshLayout
        ActionBarPullToRefresh.from(getActivity())

                // We need to insert the PullToRefreshLayout into the Fragment's ViewGroup
                .insertLayoutInto(viewGroup)

                        // We need to mark the ListView and it's Empty View as pullable
                        // This is because they are not dirent children of the ViewGroup
                .theseChildrenArePullable(getExpandableListView(), getExpandableListView().getEmptyView())

                        // We can now complete the setup as desired
                .listener(this)
                        //.options(...)
                .setup(mPullToRefreshLayout);
    }

    @Override
    public void onRefreshStarted(View view) {
        //Toast.makeText(getActivity(), "REFRESH!", Toast.LENGTH_LONG).show();
    }

    /**
     * Method to get ExpandableListView
     */
    protected abstract ExpandableListView getExpandableListView();

    //public abstract void onExpandableListItemClick(ListView l, View v, int position, long id);
}
