package org.redplatoon.reddified.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import org.redplatoon.reddified.app.libs.ReddifiedFragment;
import org.redplatoon.reddified.app.models.Post;
import org.redplatoon.reddified.app.services.RedditService;

import java.util.ArrayList;

/**
 * List of posts
 * created by nmiano
 */
public class PostFragment extends ReddifiedFragment implements PostsAdapter.PostUpdater {

    public static final String USER_CREDS = "ReddifiedUser";
    private PostsAdapter mPostsAdapter;
    private String mCount = String.valueOf(0);
    private String mUrl;
    private String mFilter;
    private SharedPreferences mSettings;
    private OnFragmentInteractionListener mListener;
    private String mUserAgent;
    private String mModHash;
    private String mCookie;
    private RedditService mRedditService;
    private final ArrayList<Post> mPosts = new ArrayList<Post>();

    public static PostFragment newInstance(String filter) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString("filter", filter);
        fragment.setArguments(args);
//        fragment.updatePosts(filter);
        return fragment;
    }
    /**
     * Mandatory
     */
    public PostFragment() {
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
        if (getArguments() != null) {
            mFilter = getArguments().getString("filter");
        } else {
            mFilter = "";
        }
        mUrl = String.format(getString(R.string.reddit_url))+mFilter+".json";

        mRedditService = new RedditService(mUserAgent, mModHash, mUrl, mCookie);
        //mSettings = getSharedPreferences(USER_CREDS, Context.MODE_PRIVATE);
        setListAdapter(new PostsAdapter(this, getActivity()));
        mPostsAdapter = (PostsAdapter) getListAdapter();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onPostFragmentInteraction((Post)l.getItemAtPosition(position));

        }
    }

    @Override
    public void onRefreshStarted(View view) {
        updatePosts(null);
        super.onRefreshStarted(view);
    }

    public void updatePosts(String after) {

        final Activity activity = getActivity();
        activity.setProgressBarIndeterminateVisibility(true);
        mRedditService.loadPosts(after, getActivity(), new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                activity.setProgressBarIndeterminateVisibility(false);
                //progress.setVisibility(8);
                if(e != null) {
                    Log.d("HTTPERR", e.toString());
                } else {
                    JsonArray children = result
                            .get("data")
                            .getAsJsonObject()
                            .get("children")
                            .getAsJsonArray();

                    for (JsonElement child : children) {
                        Gson gson = new Gson();
                        Post post = gson.fromJson(child.getAsJsonObject().get("data"), Post.class);
                        post.setThumbnail(child.getAsJsonObject().get("data").getAsJsonObject().get("thumbnail").getAsString());
                        post.setTypeFlag(); //totally kludgy way to handle this.. but GSON makes it weird, so.. ~NM 06/14/2014
                        mPosts.add(post);
                    }

                    JsonElement after = result.getAsJsonObject().get("data").getAsJsonObject().get("after");

                    //String newAfter = "END";

                    if (after != null) {
                        int tempCount = Integer.parseInt(mCount);
                        tempCount += mPosts.size();

                        mPostsAdapter.update(mPosts, tempCount, after.getAsString());//newAfter);
                        //newAfter = result.getAsJsonObject().get("data").getAsJsonObject().get("after").getAsString();
                    }

                    mPullToRefreshLayout.setRefreshComplete(); //look into appropriate naming ~NM 06/12 01:20
                }
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onPostFragmentInteraction(Post post);
    }

}
