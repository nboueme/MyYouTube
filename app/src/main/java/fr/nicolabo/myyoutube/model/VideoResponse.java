package fr.nicolabo.myyoutube.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicolasboueme on 23/07/2016.
 */
public class VideoResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("video")
    @Expose
    private Video video;

    /**
     *
     * @return
     * The error
     */
    public Boolean getError() {
        return error;
    }

    /**
     *
     * @param error
     * The error
     */
    public void setError(Boolean error) {
        this.error = error;
    }

    /**
     *
     * @return
     * The video
     */
    public Video getVideo() {
        return video;
    }

    /**
     *
     * @param video
     * The video
     */
    public void setVideo(Video video) {
        this.video = video;
    }

}
