package fr.nicolabo.myyoutube.model.callback;

import java.util.List;

import fr.nicolabo.myyoutube.model.Video;
import retrofit2.Call;
import retrofit2.http.GET;

public interface VideoService {

    @GET("/florent37/MyYoutube/master/myyoutube.json")
    Call<List<Video>> getAllVideos();
}
