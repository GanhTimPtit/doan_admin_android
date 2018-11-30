package com.example.king.managebook.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



public class UserAuth {

    public static void saveLoginState(Context context, String userID) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.PRE_USER_ID, userID);
        editor.apply();
    }
    public static boolean isUserLoggedIn(Context context) {
        return Constants.LOGIN_TRUE.equals(Utils.getSharePreferenceValues(context,Constants.STATUS_LOGIN));
    }
    public static String getBearerToken(Context context) {
        return Constants.BEARER_TOKEN_PREFIX +
                Utils.getSharePreferenceValues(context,Constants.PRE_ACCESS_TOKEN_LOGIN);
    }

    public static String getUserID(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.PRE_USER_ID, null);
    }



    public static void saveAccessToken(Context context, String name) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(name==null) {
            editor.putString(Constants.ADMIN_NAME, null);
            editor.putString(Constants.STATUS_LOGIN, Constants.LOGIN_FAIL);
        } else {
            editor.putString(Constants.ADMIN_NAME, name);
            editor.putString(Constants.STATUS_LOGIN, Constants.LOGIN_TRUE);
        }
        editor.apply();
    }

}
