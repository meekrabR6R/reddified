package org.redplatoon.reddified.app.services;

import android.app.Activity;
import android.content.Context;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.redplatoon.reddified.app.R;

/**
 * Created by nmiano on 6/10/14 12:35 AM for Reddified
 */
public class RedditService implements Service {

    private String userAgent;
    private String modHash;
    private String cookie;

    public RedditService(String userAgent, String modHash, String cookie) {
        this.userAgent = userAgent;
        this.modHash = modHash;
        this.cookie = cookie;
    }

    public void loadPosts(String after, Activity activity, FutureCallback<JsonObject> futureCallback, String filter) {
        String url = String.format(activity.getString(R.string.reddit_url))+filter+".json";
        load(after, activity, futureCallback, url);
    }

    public void loadComments(String permalink, Activity activity, FutureCallback<JsonObject> futureCallback) {
        String url = String.format(activity.getString(R.string.reddit_url))+permalink+".json";
        load(null, activity, futureCallback, url);
    }

    public void signIn(Context context, String user, String passwd, FutureCallback<JsonObject> futureCallback) {
        String url = context.getString(R.string.reddit_url)+"/api/login";

        Ion.with(context)
                .load(url)
                .setHeader("User-Agent", userAgent)
                .setBodyParameter("user", user)
                .setBodyParameter("passwd", passwd)
                .setBodyParameter("api_type", "json")
                .asJsonObject()
                .setCallback(futureCallback);
    }

    private void load(String after, Activity activity, FutureCallback<JsonObject> futureCallback, String url) {
        //if(urlMod != null)
            //url = url + "" + urlMod;

        Ion.with(activity)
                .load(url)
                .setHeader("User-Agent", userAgent)
                .setHeader("X-Modhash", modHash)
                .setHeader("Cookie", "reddit_session="+ cookie)
                .addQuery("after", after)
                .asJsonObject()
                .setCallback(futureCallback);
    }

}
