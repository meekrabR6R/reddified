package org.redplatoon.reddified.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nmiano on 6/10/14 9:31 PM for Reddified
 */
public class Comment {
    String id, author, body, name, distinguished;
    //boolean saved, edited;
    int score, ups, downs, gilded, created;

    @SerializedName("parent_id")
    String parentId;
    @SerializedName("body_html")
    String bodyHtml;
    @SerializedName("link_id")
    String linkId;
    @SerializedName("author_flair_text")
    String flairText;
    @SerializedName("num_reports")
    String numReports;
    //@SerializedName("score_hidden")
    //boolean scoreHidden;
    @SerializedName("created_utc")
    int createdUtc;

    private ArrayList<Comment> children = new ArrayList<Comment>();

    public void setChildren(ArrayList<Comment> chillens) {
        this.children = chillens;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public ArrayList<Comment> getChildren() {
        return children;
    }
}
