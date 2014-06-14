package org.redplatoon.reddified.app.services;

import android.app.Activity;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by nmiano on 6/14/14 12:53 AM for Reddified
 */
public class Imgur {
    private String url;
    private String clientID;

    public Imgur(String cid) {
        this.clientID = cid;
    }

    private void load(Activity activity, FutureCallback<JsonObject> futureCallback) {

        Ion.with(activity)
                .load(url)
                .setHeader("Client-ID", clientID)
                .asJsonObject()
                .setCallback(futureCallback);
    }
}
