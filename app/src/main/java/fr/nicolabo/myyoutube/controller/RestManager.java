package fr.nicolabo.myyoutube.controller;

import fr.nicolabo.myyoutube.model.callback.VideoService;
import fr.nicolabo.myyoutube.model.helper.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nicolasboueme on 15/05/2016.
 */
public class RestManager {

    private VideoService videoService;

    public VideoService getVideoService() {
        if (videoService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            videoService = retrofit.create(VideoService.class);
        }

        return videoService;
    }
}
