package com.zhaojin.cachingrealm_rxjava_retrofit_2.db.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class ImagesRealm extends RealmObject  {
    @PrimaryKey
    private String url;
    private String name;
    private String thumbnail;
    private String imageWebSearchUrl;
    private String title;
    private String search;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImageWebSearchUrl() {
        return imageWebSearchUrl;
    }

    public void setImageWebSearchUrl(String imageWebSearchUrl) {
        this.imageWebSearchUrl = imageWebSearchUrl;
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
