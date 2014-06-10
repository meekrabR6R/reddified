package org.redplatoon.reddified.app.services;

import android.app.Activity;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by nmiano on 6/10/14 12:35 AM for Reddified
 */
public class Reddit {

    private String userAgent;
    private String modHash;
    private String url;
    private String cookie;

    public Reddit(String userAgent, String modHash, String url, String cookie) {
        this.userAgent = userAgent;
        this.modHash = modHash;
        this.url = url;
        this.cookie = cookie;
    }

    public void updatePost(String after, Activity activity, FutureCallback<JsonObject> futureCallback) {
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
