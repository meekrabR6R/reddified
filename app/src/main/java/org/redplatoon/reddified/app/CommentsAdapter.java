package org.redplatoon.reddified.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.redplatoon.reddified.app.models.Comment;
import org.redplatoon.reddified.app.services.MediaService;

import java.util.ArrayList;

/**
 * Created by nmiano on 6/24/14 11:35 PM for Reddified
 */
public class CommentsAdapter extends ArrayAdapter<Comment> {

    private LayoutInflater mLayoutInflater;
    private MediaService mMediaService;
    private final Context mContext;
    private ArrayList<Comment> mComments = new ArrayList<Comment>();

    public interface  PostUpdater {
        public void updatePosts(String after);
    }

    public CommentsAdapter(Context context) {
        super(context, R.layout.fragment_comment);
        mContext = context;
    }

    @Override
    public int getCount() { return mComments.size();}

    @Override
    public Comment getItem(int position) {
        return mComments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            mLayoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mLayoutInflater.inflate(R.layout.fragment_post, parent, false);
        }

        ViewHolder holder = new ViewHolder();

        convertView.setTag(holder);

        return convertView;
    }

    static class ViewHolder {
    }
}
