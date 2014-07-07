package org.redplatoon.reddified.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import org.redplatoon.reddified.app.factories.CommentsFactory;
import org.redplatoon.reddified.app.libs.ReddifiedFragment;
import org.redplatoon.reddified.app.models.Comment;

import java.util.ArrayList;

/**
 * Comments
 * created by nmiano on 06/24/2014
 */
public class CommentsFragment extends ReddifiedFragment implements CommentsAdapter.CommentsUpdater,
                                                                   FutureCallback<JsonArray> {

    private OnFragmentInteractionListener mListener;
    private String mCommentsLink;
    private Activity mActivity;
    private CommentsAdapter mCommentsAdapter;
    private final ArrayList<Comment> mComments = new ArrayList<Comment>();

    public static CommentsFragment newInstance(String commentsLink) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putString("commentsLink", commentsLink);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory
     */
    public CommentsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCommentsLink = getArguments().getString("commentsLink");
        }

        mActivity = getActivity();
        setListAdapter(new CommentsAdapter(this, getActivity()));
        mCommentsAdapter = (CommentsAdapter) getListAdapter();
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
            mListener.onCommentsFragmentInteraction();
        }
    }

    /**
     * FutureCallback<T> callback method
     * @param e
     * @param result
     */
    @Override
    public void onCompleted(Exception e, JsonArray result) {

        if (e != null) {
            Log.d("HTTPERR", e.toString());
        } else {
            JsonObject comments = result.get(1).getAsJsonObject();
            ArrayList<Comment> res = CommentsFactory.newCommentsList(comments);
            mComments.addAll(res);

            mCommentsAdapter.update(mComments);

            mActivity.setProgressBarIndeterminateVisibility(false);
            mPullToRefreshLayout.setRefreshComplete(); //look into appropriate naming ~NM 06/12 01:20
        }
    }

    @Override
    public void updateComments() {
        mActivity.setProgressBarIndeterminateVisibility(true);
        mRedditService.loadComments(mCommentsLink, mActivity, this);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onCommentsFragmentInteraction();
    }

}
