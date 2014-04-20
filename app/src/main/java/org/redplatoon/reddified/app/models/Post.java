package org.redplatoon.reddified.app.models;

/**
 * Created by nmiano on 4/18/14.
 */
public class Post {
    public String title ,author, url, thumbnail, id, subreddit, permaLink, name;
    public int ups, downs, score, numComments, created, createdUtc;
    public boolean visited, nsfw;

    @Override
    public String toString() {
        return title + "\n" + author;
    }
}
