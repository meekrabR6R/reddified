package org.redplatoon.reddified.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.redplatoon.reddified.app.libs.ReddifiedFragment;

/**
 * Comments
 * created by nmiano on 06/24/2014
 */
public class CommentsFragment extends ReddifiedFragment {

    private OnFragmentInteractionListener mListener;
    private String mCommentsLink;

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

        setListAdapter(new CommentsAdapter(getActivity()));
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
        public void onCommentsFragmentInteraction();
    }

}
