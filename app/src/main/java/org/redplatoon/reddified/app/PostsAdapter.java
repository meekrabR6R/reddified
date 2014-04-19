package org.redplatoon.reddified.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

import org.redplatoon.reddified.app.models.Post;

import java.util.ArrayList;

/**
 * Created by nmiano on 4/18/14.
 */
public class PostsAdapter extends ArrayAdapter<Post> {

    private final Context context;
    private final ArrayList<Post> posts;

    public PostsAdapter(Context context, ArrayList<Post> posts) {
        super(context, R.layout.post, posts);
        this.context = context;
        this.posts = posts;
        System.out.println("SIZE 2: " + this.posts.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View post = inflater.inflate(R.layout.post, parent, false);

        Post currPost = posts.get(position);
        TextView titleView = (TextView) post.findViewById(R.id.title);
        titleView.setText(currPost.title);

        /*
        TextView authorView = (TextView) post.findViewById(R.id.author);
        authorView.setText(currPost.author);
        */
        ImageView imageView = (ImageView) post.findViewById(R.id.thumb);
        System.out.println("CURRPOST: " + currPost.thumbnail);
        if (currPost.thumbnail.length() > 7) {
            Ion.with(imageView)
                    .placeholder(R.drawable.alien_thumb)
                    .error(R.drawable.alien_thumb)
                    .load(currPost.thumbnail);
        }
        return post;
    }
}
