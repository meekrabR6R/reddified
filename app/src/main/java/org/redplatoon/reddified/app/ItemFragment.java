package org.redplatoon.reddified.app;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import org.redplatoon.reddified.app.models.Post;
import org.redplatoon.reddified.app.services.MediaService;


/**
 * Created by nmiano on 06/11/2014
 */
public class ItemFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private MediaService mMediaService;
    private String mPostUrl;
    private ImageView mImageOrGifView;
    private WebView mWebView;
    private boolean mIsImage = false;
    private boolean mIsGif = false;
    private boolean mIsWebPage = false;

    /**
     * Factory method
     */
    public static ItemFragment newInstance(Post post) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();

        args.putBoolean("IS_IMAGE", post.isImage());
        args.putBoolean("IS_GIF", post.isGif());
        args.putBoolean("IS_WEBPAGE", post.isWebPage());
        args.putString("POST_URL", post.getUrl());

        fragment.setArguments(args);
        return fragment;
    }

    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIsImage = getArguments().getBoolean("IS_IMAGE");
            mIsGif = getArguments().getBoolean("IS_GIF");
            mIsWebPage = getArguments().getBoolean("IS_WEBPAGE");
            mPostUrl = getArguments().getString("POST_URL");
        }
        mMediaService = new MediaService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item, container, false);
        mImageOrGifView = (ImageView) rootView.findViewById(R.id.image_or_gif);
        mWebView = (WebView) rootView.findViewById(R.id.webview);

        if(mIsGif || mIsImage) {
            mImageOrGifView.setVisibility(View.VISIBLE);

            if (mIsImage)
                mMediaService.loadImage(mImageOrGifView, mPostUrl);
            else if (mIsGif)
                mMediaService.loadGif(mImageOrGifView, mPostUrl);
        } else if(mIsWebPage) {
            mWebView.setVisibility(View.VISIBLE);
            mWebView.loadUrl(mPostUrl);
        }
        // Inflate the layout for this fragment
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onItemFragmentInteraction();
        }
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

    public interface OnFragmentInteractionListener {
        public void onItemFragmentInteraction();
    }

}
