package fr.nicolabo.myyoutube.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Video implements Parcelable, Serializable {

    @SerializedName("id_video")
    @Expose
    private String idVideo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;

    /**
     *
     * @param in
     */
    public Video(Parcel in) {
        idVideo = in.readString();
        name = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        videoUrl = in.readString();
    }

    /**
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *
     * @param out
     * @param i
     */
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(idVideo);
        out.writeString(name);
        out.writeString(description);
        out.writeString(imageUrl);
        out.writeString(videoUrl);
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    /**
     *
     * @return
     * The id
     */
    public String getIdVideo() {
        return idVideo;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setIdVideo(String id) {
        this.idVideo = id;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl
     * The image_url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     *
     * @return
     * The videoUrl
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     *
     * @param videoUrl
     * The video_url
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

}