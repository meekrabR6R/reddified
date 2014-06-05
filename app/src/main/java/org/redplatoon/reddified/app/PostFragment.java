package org.redplatoon.reddified.app;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.redplatoon.reddified.app.models.Post;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class PostFragment extends ListFragment implements PostsAdapter.PostUpdater {

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

    public static PostFragment newInstance(String filter) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();
        args.putString("filter", filter);
        fragment.setArguments(args);
//        fragment.updatePosts(filter);
        return fragment;
    }
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
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
           // mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
            Toast toast = Toast.makeText(getActivity(), "Click!", Toast.LENGTH_SHORT);
            toast.show();
        }
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    public void updatePosts(String after) {
        final ArrayList<Post> posts = new ArrayList<Post>();

        getActivity().setProgressBarIndeterminateVisibility(true);
        Ion.with(getActivity())
                .load(mUrl)
                .setHeader("User-Agent", mUserAgent)
                .setHeader("X-Modhash", mModHash)
                .setHeader("Cookie", "reddit_session="+mCookie)
                .addQuery("after", after)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        getActivity().setProgressBarIndeterminateVisibility(false);
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
                                posts.add(post);
                            }

                            JsonElement preRes = result.getAsJsonObject().get("data").getAsJsonObject().get("after");

                            String newAfter = "END";

                            if (preRes != null) {
                                newAfter = result.getAsJsonObject().get("data").getAsJsonObject().get("after").getAsString();
                            }


                            int tempCount = Integer.parseInt(mCount);
                            tempCount += posts.size();

                            mPostsAdapter.update(posts, tempCount, newAfter);
                        }
                    }
                });

    }

}
