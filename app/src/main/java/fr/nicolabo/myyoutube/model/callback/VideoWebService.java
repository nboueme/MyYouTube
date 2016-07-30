package fr.nicolabo.myyoutube.model.callback;

import android.content.Context;

import fr.nicolabo.myyoutube.controller.RestManager;

/**
 * Created by nicolasboueme on 23/07/2016.
 */
public class VideoWebService {

    static VideoWebService INSTANCE;
    private final VideoService immersionServiceWithoutToken;
    private final VideoService immersionServiceWithToken;

    private VideoWebService(Context context) {
        this.immersionServiceWithoutToken = RestManager.createServiceWithoutToken(VideoService.class);
        this.immersionServiceWithToken = RestManager.createServiceProvider(VideoService.class, context);
    }

    public static VideoService getInstanceWithoutToken(Context context) {
        if(INSTANCE == null){
            INSTANCE = new VideoWebService(context);
        }
        return INSTANCE.immersionServiceWithoutToken;
    }

    public static VideoService getInstanceWithToken(Context context) {
        if(INSTANCE == null){
            INSTANCE = new VideoWebService(context);
        }
        return INSTANCE.immersionServiceWithToken;
    }

}
