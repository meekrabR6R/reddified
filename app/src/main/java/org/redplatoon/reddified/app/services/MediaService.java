package org.redplatoon.reddified.app.services;

import android.media.Image;
import android.widget.ImageView;

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

    public ArrayList<Image> getPics() {
        return pics;
    }

    public ArrayList<Gif> getGifs() {
        return gifs;
    }

    public void loadImage(ImageView imageView, String url) {
        Ion.with(imageView)
                .placeholder(R.drawable.alien_thumb)
                .error(R.drawable.alien_thumb)
                //.animateLoad(spinAnimation)
                //.animateIn(fadeInAnimation)
                .load(url);
    }

    public void loadGif(ImageView imageView, String url) {
        Ion.with(imageView)
                .resize(512, 512) //change?
                .centerCrop()
                .load(url);
    }
}
