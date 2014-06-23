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
     * Factory method to create an instance of ReddifiedMixpanelAPI.
     * @param context
     * @return MixpanelAPI mixpanelAPI
     */
    public static ReddifiedMixpanelAPI createReddifiedMixpanelAPIInstance(Context context) {
        String apiKey = context.getString(R.string.mixpanel_token);
        ReddifiedMixpanelAPI reddifiedMixpanelAPI = new ReddifiedMixpanelAPI(context, apiKey);
        initializeSuperProperties(context, reddifiedMixpanelAPI.getMixpanelAPIInstance());
        return reddifiedMixpanelAPI;
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

    /**
     * Reddified MixpanelAPI wrapper class
     */
    public static class ReddifiedMixpanelAPI {
        private MixpanelAPI mixpanelAPI;
        private boolean isFromPausedState;
        private boolean logging;

        public ReddifiedMixpanelAPI(Context context, String apiKey) {
            mixpanelAPI = MixpanelAPI.getInstance(context, apiKey);
            isFromPausedState = false;

            if (context.getPackageName().endsWith(".debug"))
                setLogging(false);
            else
                setLogging(true);
        }

        /**
         * Track 'Launched' or 'Resumed' app opens
         */
        public void trackAppOpen() {

            String openType;
            if(isFromPausedState)
                openType = "Resumed";
            else
                openType = "Launched";

            try {
                JSONObject props = new JSONObject();
                props.put("Open Type", openType);
                track("App Opened", props);
            } catch(JSONException e) {
                Log.d("ReddifiedMixPanelTracker", e.getMessage());
            }
        }

        public void setLaunchStateToResume() {
            isFromPausedState = true;
        }

        public boolean isFromPausedState() {
            return isFromPausedState;
        }

        public void setLogging(boolean logging) {
            this.logging = logging;
        }

        public MixpanelAPI getMixpanelAPIInstance() {
            return mixpanelAPI;
        }

        /**
         * Generic mixpanel track method
         * @param key
         * @param props
         */
        private void track(String key, JSONObject props) {
            if (logging) {
                mixpanelAPI.track(key, props);
                Log.d("ReddifiedMixpanelTracker", "Open action tracked: " + key);
            } else
                Log.d("ReddifiedMixpanelTracker", "Tracking disabled");
        }
    }
}
