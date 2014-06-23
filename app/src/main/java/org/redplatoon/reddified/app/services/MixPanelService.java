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

    private static String apiKey;
    private Context context;

    /**
     * Factory method to create an instance of MixpanelAPI.
     * @param context
     * @return MixpanelAPI mixpanelAPI
     */
    public static MixpanelAPI createMixpanelAPIInstance(Context context) {
        MixPanelService mixpanelService = new MixPanelService(context);
        MixpanelAPI mixpanelAPI = MixpanelAPI.getInstance(context, apiKey);
        mixpanelService.initializeSuperProperties(mixpanelAPI);
        return mixpanelAPI;
    }

    public MixPanelService(Context context) {
        this.context = context;
        this.apiKey = context.getString(R.string.mixpanel_token);
    }

    /**
     * Sets super properties for mixpanel tags based on app flavor (debug or release)
     * and user type (paid or free)
     */
    public void initializeSuperProperties(MixpanelAPI mixpanelAPI) {
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
}
