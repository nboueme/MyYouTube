package fr.nicolabo.myyoutube.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoResponseList {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("video")
    @Expose
    private List<Video> video = new ArrayList<>();
    @SerializedName("paging")
    @Expose
    private String paging;

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
    public List<Video> getVideo() {
        return video;
    }

    /**
     *
     * @param video
     * The video
     */
    public void setVideo(List<Video> video) {
        this.video = video;
    }

    /**
     *
     * @return
     * The paging
     */
    public String getPaging() {
        return paging;
    }

    /**
     *
     * @param paging
     * The paging
     */
    public void setPaging(String paging) {
        this.paging = paging;
    }

}