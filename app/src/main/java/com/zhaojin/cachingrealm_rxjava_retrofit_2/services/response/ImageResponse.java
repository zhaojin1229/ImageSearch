package com.zhaojin.cachingrealm_rxjava_retrofit_2.services.response;

import com.google.gson.annotations.SerializedName;

/**
 */

public class ImageResponse {
    @SerializedName("url")
    private String url;
    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("name")
    private String name;

    @SerializedName("title")
    private String title;

    @SerializedName("imageWebSearchUrl")
    private String imageWebSearchUrl;


    public String getUrl() {
        return url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageWebSearchUrl() {
        return imageWebSearchUrl;
    }

    public void setImageWebSearchUrl(String imageWebSearchUrl) {
        this.imageWebSearchUrl = imageWebSearchUrl;
    }
}
