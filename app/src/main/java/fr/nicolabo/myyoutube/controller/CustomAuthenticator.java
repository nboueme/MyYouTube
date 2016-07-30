package fr.nicolabo.myyoutube.controller;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import fr.nicolabo.myyoutube.model.User;
import fr.nicolabo.myyoutube.model.callback.VideoService;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

/**
 * Created by nicolasboueme on 23/07/2016.
 */
public class CustomAuthenticator implements Authenticator {

    int refreshToken;
    Context context;
    AppPreferencesTool appPreferencesTool;

    public CustomAuthenticator(Context context) {
        this.context = context;
        appPreferencesTool = new AppPreferencesTool(context);
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        VideoService service = RestManager.createServiceWithoutToken(VideoService.class);

        Call<User> refreshTokenResult = service.refreshUserToken(appPreferencesTool.getAccessToken(),appPreferencesTool.getRefreshToken());

        //this is synchronous retrofit request
        User refreshResult = refreshTokenResult.execute().body();
        appPreferencesTool.saveUserAuthenticationInfo(refreshResult);
        Log.e("CustomAuthenticator", refreshResult.getTokenRefresh());

        //check if response equals 400 , mean empty response
        if(refreshResult != null) {
            // appPreferenceTools.saveTokenModel(refreshResult.getRefresh_token());
            // save new access and refresh token
            // than create a new request and modify it accordingly using the new token
            return response.request().newBuilder()
                    .header("Authorization", refreshResult.getToken())
                    .build();
        } else {
            //we got empty response and return null
            //if we don't return null this method is trying to make so many request
            //to get new access token
            return null;
        }
    }
}
