package org.redplatoon.reddified.app.services;

import android.content.Context;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.redplatoon.reddified.app.R;

/**
 * Created by nmiano on 6/22/14 5:19 PM for Reddified
 */
public class MixPanelService implements Service {

    private String apiKey;
    private Context context;
    private MixpanelAPI mixpanelAPI;

    public MixPanelService(Context context) {

        this.context = context;
        this.apiKey = context.getString(R.string.mixpanel_token);
        this.mixpanelAPI = MixpanelAPI.getInstance(context, apiKey);
    }

    public void initializeSuperProperties() {
        if (context.getPackageName().endsWith(".debug")) {

            //Ion.getDefault(this).configure().setLogging("axial-ion", Log.DEBUG);
        } else {

        }
    }

    public MixpanelAPI getMixpanelAPI() {
        return mixpanelAPI;
    }
}
