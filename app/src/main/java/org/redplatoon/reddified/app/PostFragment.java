package org.redplatoon.reddified.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.redplatoon.reddified.app.dummy.DummyContent;
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

    public static PostFragment newInstance(String filter) {
        System.out.println("FILTER: " + filter);
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
        Ion.with(getActivity())
                .load(mUrl)
                .addQuery("after", after)
                .setLogging("MyLogs", Log.DEBUG)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        JsonArray children = result
                                .get("data")
                                .getAsJsonObject()
                                .get("children")
                                .getAsJsonArray();

                        System.out.println("CHITLINS: " + mUrl);

                        for(JsonElement child: children) {
                            Post post = new Post();
                            post.title = child.getAsJsonObject().get("data").getAsJsonObject().get("title").toString();
                            post.author = child.getAsJsonObject().get("data").getAsJsonObject().get("author").toString();
                            post.url = child.getAsJsonObject().get("data").getAsJsonObject().get("url").toString().trim();

                            post.thumbnail = child.getAsJsonObject()
                                    .get("data")
                                    .getAsJsonObject()
                                    .get("thumbnail")
                                    .toString()
                                    .replaceAll("^\\p{Graph}", "")
                                    .replaceAll("\"", "");

                            post.id = child.getAsJsonObject().get("data").getAsJsonObject().get("id").toString();
                            post.subreddit = child.getAsJsonObject().get("data").getAsJsonObject().get("subreddit").toString();
                            post.permaLink = child.getAsJsonObject().get("data").getAsJsonObject().get("permalink").toString();
                            post.name = child.getAsJsonObject().get("data").getAsJsonObject().get("name").toString();

                            post.ups = child.getAsJsonObject().get("data").getAsJsonObject().get("ups").getAsInt();
                            post.downs = child.getAsJsonObject().get("data").getAsJsonObject().get("downs").getAsInt();
                            post.score = child.getAsJsonObject().get("data").getAsJsonObject().get("score").getAsInt();
                            post.created = child.getAsJsonObject().get("data").getAsJsonObject().get("created").getAsInt();
                            post.createdUtc = child.getAsJsonObject().get("data").getAsJsonObject().get("created_utc").getAsInt();
                            post.numComments = child.getAsJsonObject().get("data").getAsJsonObject().get("num_comments").getAsInt();

                            post.visited = child.getAsJsonObject().get("data").getAsJsonObject().get("visited").getAsBoolean();
                            post.nsfw = child.getAsJsonObject().get("data").getAsJsonObject().get("over_18").getAsBoolean();

                            posts.add(post);
                        }

                        JsonElement preRes = result
                                            .getAsJsonObject()
                                            .get("data")
                                            .getAsJsonObject()
                                            .get("after");

                        String newAfter = "END";
                        System.out.println("PRERES: " + preRes.toString());
                        if(preRes != null) {
                            newAfter = result
                                       .getAsJsonObject()
                                       .get("data")
                                       .getAsJsonObject()
                                       .get("after")
                                       .toString()
                                       .replaceAll("^\\p{Graph}", "")
                                       .replaceAll("\"", "");
                        }



                        int tempCount = Integer.parseInt(mCount);
                        tempCount += posts.size();
                        System.out.println("NEWAFTER: " + newAfter);
                        mPostsAdapter.update(posts, tempCount, newAfter);
                    }
                });
    }

}