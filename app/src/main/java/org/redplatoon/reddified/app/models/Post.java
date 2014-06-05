package org.redplatoon.reddified.app.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nmiano on 4/18/14.
 */
public class Post {
    public String title ,author, url, id, subreddit, permalink, name;
    public int ups, downs, score, created;
    public boolean visited;

    @SerializedName("over_18")
    public boolean nsfw;
    @SerializedName("num_comments")
    public int numComments;
    @SerializedName("created_utc")
    public int createdUtc;

    private String thumbnail;

    public void setThumbnail(String path) {
        if(path.equals("nsfw")) {
            thumbnail = "http://www.reddit.com/static/nsfw2.png";
        } else if(path.equals("self")) {
            thumbnail = "http://www.reddit.com/static/self_default2.png";
        } else if(path.equals("default") || path == null || path.equals("")) {
            thumbnail = "http://www.reddit.com/static/noimage.png";
        } else {
            thumbnail = path;
        }
    }
    public String getThumbnail() {
        return thumbnail;
    }
    @Override
    public String toString() {
        return title + "\n" + author;
    }
}
