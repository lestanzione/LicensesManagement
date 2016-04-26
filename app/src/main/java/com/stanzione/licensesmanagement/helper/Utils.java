package com.stanzione.licensesmanagement.helper;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by lstanzione on 4/26/2016.
 */
public class Utils {

    public static boolean isOnline(Activity activity) {
        ConnectivityManager cm =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
