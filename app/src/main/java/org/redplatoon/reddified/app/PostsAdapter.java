package org.redplatoon.reddified.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.redplatoon.reddified.app.models.Post;
import org.redplatoon.reddified.app.services.MediaService;

import java.util.ArrayList;

/**
 * Created by nmiano on 4/18/14.
 */
public class PostsAdapter extends ArrayAdapter<Post> {

    private LayoutInflater mLayoutInflater;
    private MediaService mMediaService;
    private final Context mContext;
    private ArrayList<Post> mPosts = new ArrayList<Post>();
    private PostUpdater mPostUpdater;
    private int mCount;
    private String mAfter;

    public interface  PostUpdater {
        public void updatePosts(String after);
    }

    public PostsAdapter(PostUpdater postUpdater, Context context) {
        super(context, R.layout.fragment_post);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mMediaService = new MediaService();
        mPostUpdater = postUpdater;
        mPostUpdater.updatePosts(mAfter);
    }

    @Override
    public int getCount() { return mPosts.size();}

    @Override
    public Post getItem(int position) {
        return mPosts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if ((position + 10 == mPosts.size()) && !mAfter.equals("END"))
            mPostUpdater.updatePosts(mAfter);

        if (convertView == null) {
            mLayoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = mLayoutInflater.inflate(R.layout.fragment_post, parent, false);
        }

        Post currPost = mPosts.get(position);

        ViewHolder holder = new ViewHolder();

        holder.index = (TextView) convertView.findViewById(R.id.index);
        holder.index.setText(String.valueOf(position+1));

        holder.title = (TextView) convertView.findViewById(R.id.title);
        holder.title.setText(currPost.title);

        holder.author = (TextView) convertView.findViewById(R.id.author);
        holder.author.setText(currPost.author);

        holder.subreddit = (TextView) convertView.findViewById(R.id.subreddit);
        holder.subreddit.setText(currPost.subreddit);

        holder.score = (TextView) convertView.findViewById(R.id.score);
        holder.score.setText(String.valueOf(currPost.score));

        holder.thumb = (ImageView) convertView.findViewById(R.id.thumb);

        mMediaService.loadImage(holder.thumb, currPost.getThumbnail());

        convertView.setTag(holder);
        
        return convertView;
    }

    public void update(ArrayList<Post> posts, int count, String after) {
        mCount = count;
        mAfter = after;
        mPosts.addAll(posts);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView  title;
        TextView  author;
        TextView  subreddit;
        TextView  score;
        TextView  index;
        ImageView thumb;
    }
}
