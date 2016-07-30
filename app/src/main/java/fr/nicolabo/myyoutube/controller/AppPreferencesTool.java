package fr.nicolabo.myyoutube.controller;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.nicolabo.myyoutube.model.User;
import fr.nicolabo.myyoutube.model.Video;

/**
 * Created by nicolasboueme on 23/07/2016.
 */
public class AppPreferencesTool {

    private SharedPreferences mPreference;
    private Context mContext;
    public static final String STRING_PREF_UNAVAILABLE = "string preference unavailable";

    public AppPreferencesTool(Context context) {
        this.mContext = context;
        this.mPreference = this.mContext.getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
    }

    /**
     * save the user authentication model to pref at sing up || sign in
     *
     * @param
     */
    public void saveUserAuthenticationInfo(User user) {
        mPreference.edit()
                .putString("id_user", user.getIdUser())
                .putString("email", user.getEmail())
                .putString("token", user.getToken())
                .putString("token_refresh", user.getTokenRefresh())
                .apply();
    }

    /**
     *
     * @return
     */
    public String getUserId() {
        return mPreference.getString("id_user", STRING_PREF_UNAVAILABLE);
    }

    /**
     * get access token
     *
     * @return
     */
    public String getAccessToken() {
        return mPreference.getString("token", STRING_PREF_UNAVAILABLE);
    }

    /**
     * get refresh token
     *
     * @return
     */
    public String getRefreshToken() {
        return mPreference.getString("token_refresh", STRING_PREF_UNAVAILABLE);
    }

    /**
     * detect is user sign in
     *
     * @return
     */
    public boolean isAuthorized() {
        return !getAccessToken().equals(STRING_PREF_UNAVAILABLE);
    }

    /**
     * remove all prefs in logout
     */
    public void removeAllPrefs() {
        mPreference.edit().clear().apply();
    }
}
