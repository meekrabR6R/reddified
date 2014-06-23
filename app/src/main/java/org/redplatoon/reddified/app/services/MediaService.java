package org.redplatoon.reddified.app.services;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.redplatoon.reddified.app.R;
import org.redplatoon.reddified.app.models.Gif;

import java.util.ArrayList;

/**
 * Created by nmiano on 6/14/14 5:18 PM for Reddified
 */
public class MediaService implements Service {
    private ArrayList<Image> pics = new ArrayList<Image>();
    private ArrayList<Gif> gifs = new ArrayList<Gif>();
    private Activity activity;

    public ArrayList<Image> getPics() {
        return pics;
    }

    public ArrayList<Gif> getGifs() {
        return gifs;
    }

    public static void setGlobalIonDebugger(Context context) {
        if (context.getPackageName().endsWith(".debug")) {
            Ion.getDefault(context).configure().setLogging("Reddified", Log.DEBUG);
        } else {
            //TODO
        }
    }

    public void loadImage(ImageView imageView, String url) {

        Ion.with(imageView)
                .animateLoad(R.anim.rotation)
                .animateIn(R.anim.fadein)
                .load(url);

    }

    public void loadImageWithCallback(ImageView imageView, String url, FutureCallback<ImageView> futureCallback) {
        Ion.with(imageView)
                //.animateLoad(R.anim.rotation)
                .animateIn(R.anim.fadein)
                .load(url)
                .setCallback(futureCallback);

    }
    public void loadGif(ImageView imageView, String url) {
        Ion.with(imageView)
                //.resize(512, 512) //change?
                //.centerCrop()
                .load(url);
    }

    public void loadGifWithCallback(ImageView imageView, String url, FutureCallback<ImageView> futureCallback) {
        Ion.with(imageView)
                //.resize(512, 512) //change?
                //.centerCrop()
                .load(url)
                .setCallback(futureCallback);
    }
}
