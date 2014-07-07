package org.redplatoon.reddified.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.redplatoon.reddified.app.models.Comment;
import org.redplatoon.reddified.app.services.MediaService;

import java.util.ArrayList;

/**
 * Created by nmiano on 6/24/14 11:35 PM for Reddified
 */
public class CommentsAdapter extends ArrayAdapter<Comment> {

    private LayoutInflater mLayoutInflater;
    private MediaService mMediaService;
    private CommentsUpdater mCommentsUpdater;
    private final Context mContext;
    private ArrayList<Comment> mComments = new ArrayList<Comment>();

    public interface  CommentsUpdater {
        public void updateComments();
    }

    public CommentsAdapter(CommentsUpdater commentsUpdater, Context context) {
        super(context, R.layout.fragment_comment);
        mContext = context;
        mCommentsUpdater = commentsUpdater;
        mCommentsUpdater.updateComments();
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

            convertView = mLayoutInflater.inflate(R.layout.fragment_comment, parent, false);
        }
        Comment currComment = mComments.get(position);
        ViewHolder holder = new ViewHolder();

        holder.author = (TextView) convertView.findViewById(R.id.author);
        holder.author.setText(currComment.getAuthor());

        holder.ago = (TextView) convertView.findViewById(R.id.ago);
        holder.ago.setText(currComment.getAgoText());

        holder.points = (TextView) convertView.findViewById(R.id.points);
        holder.points.setText(currComment.getPoints());

        holder.body = (TextView) convertView.findViewById(R.id.body);
        holder.body.setText(currComment.getBody());

        convertView.setTag(holder);

        return convertView;
    }

    public void update(ArrayList<Comment> comments) {
        mComments.clear();
        mComments.addAll(comments);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView author;
        TextView ago;
        TextView points;
        TextView body;
    }
}
