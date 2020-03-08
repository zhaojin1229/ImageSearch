package com.zhaojin.cachingrealm_rxjava_retrofit_2.services;

import com.zhaojin.cachingrealm_rxjava_retrofit_2.services.response.ImageRes;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * A interface uses to request API.
 *
 */
public interface ApiService {

    @GET("Search/ImageSearchAPI")
    Observable<ImageRes> getImageList(@Header("x-rapidapi-host") String x, @Header("x-rapidapi-key") String y, @Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize, @Query("q") String q, @Query("safeSearch") boolean safeSearch);
}
