package org.redplatoon.reddified.app.services;

import android.content.Context;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

/**
 * Created by nmiano on 6/22/14 5:19 PM for Reddified
 */
public class MixPanelService implements Service {

    private String apiKey;
    private Context context;
    private MixpanelAPI mixpanelAPI;

    public MixPanelService(Context context, String apiKey) {
        this.apiKey = apiKey;
        this.context = context;
        mixpanelAPI = MixpanelAPI.getInstance(context, apiKey);
    }

    public MixpanelAPI getMixpanelAPI() {
        return mixpanelAPI;
    }
}
