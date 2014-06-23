package org.redplatoon.reddified.app.services;

import android.content.Context;
import android.util.Log;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.json.JSONException;
import org.json.JSONObject;
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
        initializeSuperProperties();
    }

    /**
     * Sets super properties for mixpanel tags based on app flavor (debug or release)
     * and user type (paid or free)
     */
    public void initializeSuperProperties() {
        try {
            if (context.getPackageName().endsWith(".debug")) {
                JSONObject props = new JSONObject();
                props.put("App Flavor", "Debug");
                mixpanelAPI.registerSuperProperties(props);
            } //else {
                //TODO: release version mixpanel settings
            //}
        }  catch(JSONException e) {
            Log.d("MixpanelSuperPropError", e.getMessage());
        }
    }

    /**
     * Get MixpanelAPI instance
     * @return MixpanelAPI mixpanelAPI
     */
    public MixpanelAPI getMixpanelAPI() {
        return mixpanelAPI;
    }
}
