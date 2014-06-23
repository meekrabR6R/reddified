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

    /**
     * Factory method to create an instance of MixpanelAPI.
     * @param context
     * @return MixpanelAPI mixpanelAPI
     */
    public static MixpanelAPI createMixpanelAPIInstance(Context context) {
        String apiKey = context.getString(R.string.mixpanel_token);
        MixpanelAPI mixpanelAPI = MixpanelAPI.getInstance(context, apiKey);
        initializeSuperProperties(context, mixpanelAPI);
        return mixpanelAPI;
    }

    /**
     * Sets super properties for mixpanel tags based on app flavor (debug or release)
     * and user type (paid or free)
     */
    public static void initializeSuperProperties(Context context, MixpanelAPI mixpanelAPI) {
        String packageName = context.getPackageName();
        JSONObject props = new JSONObject();

        try {
            if (packageName.contains(".free")) {
                props.put("App Version", "Free");
            }
            if (packageName.contains(".paid")) {
                props.put("App Version", "Paid");
            }
            if (packageName.endsWith(".debug")) {
                props.put("App Flavor", "Debug");
            }
            if (packageName.endsWith(".release")) {
                props.put("App Flavor", "Release");
            }
            mixpanelAPI.registerSuperProperties(props);
            Log.d("MixpanelSuperProps", "Super properties registered");
        }  catch(JSONException e) {
            Log.d("MixpanelSuperPropError", e.getMessage());
        }
    }
}
