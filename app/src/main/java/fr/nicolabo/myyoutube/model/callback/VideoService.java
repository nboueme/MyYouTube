package fr.nicolabo.myyoutube.model.callback;

import fr.nicolabo.myyoutube.model.User;
import fr.nicolabo.myyoutube.model.UserResponse;
import fr.nicolabo.myyoutube.model.VideoResponse;
import fr.nicolabo.myyoutube.model.VideoResponseList;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface VideoService {

    @FormUrlEncoded
    @POST("/login")
    Call<UserResponse> loginUser(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("/refreshToken")
    Call<User> refreshUserToken(@Field("token") String token, @Field("token_refresh") String tokenRefresh);

    @GET("/getVideos/1")
    Call<VideoResponseList> getAllVideos();

    @GET
    Call<VideoResponseList> getAllVideosNext(@Url String url);

    @FormUrlEncoded
    @POST("/addVideoUser")
    Call<VideoResponse> addVideoUser(@Field("id_video") String idVideo);

    @DELETE("/deleteVideoUser/{id}")
    Call<VideoResponse> deleteVideoUser(@Path("id") String idVideo);

    @GET("/getVideosUser/{id}")
    Call<VideoResponseList> getAllVideosUser(@Path("id") String idUser);
}
