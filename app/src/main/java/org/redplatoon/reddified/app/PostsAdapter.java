package org.redplatoon.reddified.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import org.redplatoon.reddified.app.models.Post;

import java.util.ArrayList;

/**
 * Created by nmiano on 4/18/14.
 */
public class PostsAdapter extends ArrayAdapter<Post> {

    private LayoutInflater mLayoutInflater;
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

        mLayoutInflater = (LayoutInflater) mContext
                          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View post = mLayoutInflater.inflate(R.layout.fragment_post, parent, false);

        Post currPost = mPosts.get(position);

        TextView titleView = (TextView) post.findViewById(R.id.title);
        titleView.setText(currPost.title);

        TextView authorView = (TextView) post.findViewById(R.id.author);
        authorView.setText(currPost.author);

        TextView subredditView = (TextView) post.findViewById(R.id.subreddit);
        subredditView.setText(currPost.subreddit);

        ImageView imageView = (ImageView) post.findViewById(R.id.thumb);
        String thumbnail = currPost.getThumbnail();
        if (thumbnail.length() > 7) {
            Ion.with(imageView)
                    .placeholder(R.drawable.alien_thumb)
                    .error(R.drawable.alien_thumb)
                    .load(thumbnail);
        }
        return post;
    }

    public void update(ArrayList<Post> posts, int count, String after) {
        mCount = count;
        mAfter = after;
        mPosts.addAll(posts);
        notifyDataSetChanged();
    }
}
