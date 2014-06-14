package org.redplatoon.reddified.app.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nmiano on 4/18/14.
 */
public class Post {
    public String title ,author, id, subreddit, permalink, name;
    public int ups, downs, score, created;
    public boolean visited;

    private String url;

    @SerializedName("over_18")
    private boolean nsfw;
    @SerializedName("num_comments")
    public int numComments;
    @SerializedName("created_utc")
    public int createdUtc;

    private String thumbnail;

    //default settings
    private boolean isImage = false;
    private boolean isGif = false;
    private boolean isWebPage = true;
    private boolean isText = false;
    private boolean isVideo = false;

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

    public void setTypeFlag() {
        if(url.endsWith(".jpg") || url.endsWith(".png"))
            isImage = true;
        else if(url.endsWith(".gif"))
            isGif = true;
        else if(url.contains(permalink))
            isText = true;
        else if(url.contains("youtube.com/watch"))
            isVideo = true;
        else
            isWebPage = true;
    }

    public String getUrl() {
        return url;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public boolean isImage() {
        return isImage;
    }

    public boolean isGif() {
        return isGif;
    }

    public boolean isWebPage() {
        return isWebPage;
    }

    public boolean isText() {
        return isText;
    }

    public boolean isVideo() {
        return isVideo;
    }
    @Override
    public String toString() {
        return title + "\n" + author;
    }
}
