package org.redplatoon.reddified.app.services;

import android.media.Image;

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
}
