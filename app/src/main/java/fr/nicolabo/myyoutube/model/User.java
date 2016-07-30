package fr.nicolabo.myyoutube.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("token_refresh")
    @Expose
    private String tokenRefresh;

    /**
     *
     * @return
     * The idUser
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     *
     * @param idUser
     * The id_user
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     * The tokenRefresh
     */
    public String getTokenRefresh() {
        return tokenRefresh;
    }

    /**
     *
     * @param tokenRefresh
     * The token_refresh
     */
    public void setTokenRefresh(String tokenRefresh) {
        this.tokenRefresh = tokenRefresh;
    }

}