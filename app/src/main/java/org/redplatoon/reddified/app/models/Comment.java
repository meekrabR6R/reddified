package org.redplatoon.reddified.app.models;

import android.text.Html;
import android.text.Spanned;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nmiano on 6/10/14 9:31 PM for Reddified
 */
public class Comment {
    String author, body, name, distinguished;
    private String id;
    //boolean saved, edited;
    int score, ups, downs, gilded, created;

    @SerializedName("parent_id")
    private String parentId;
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

    public String getParentId() {
        return parentId;
    }

    public String getId() {
        return id;
    }

    public void setChildren(ArrayList<Comment> chillens) {
        this.children = chillens;
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }

    public Spanned getBody() {
        return Html.fromHtml(body);
    }

    public String getAuthor() {
        return author;
    }

    public String getAgoText() {
        return String.valueOf(created);
    }

    public String getPoints() {
        return String.valueOf(ups - downs) + " points";
    }

    public ArrayList<Comment> getChildren() {
        return children;
    }
}
