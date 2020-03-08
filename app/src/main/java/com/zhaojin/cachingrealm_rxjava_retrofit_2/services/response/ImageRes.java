package com.zhaojin.cachingrealm_rxjava_retrofit_2.services.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageRes {
    @SerializedName("totalCount")
    private int totalCount;


    @SerializedName("value")
    private List<ImageResponse>  value;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ImageResponse> getValue() {
        return value;
    }

    public void setValue(List<ImageResponse> value) {
        this.value = value;
    }
}
