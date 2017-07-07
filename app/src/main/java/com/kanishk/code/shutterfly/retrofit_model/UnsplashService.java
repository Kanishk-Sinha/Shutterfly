package com.kanishk.code.shutterfly.retrofit_model;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by kanishk on 7/5/17.
 */

public interface UnsplashService {

    @GET("photos/random")
    Call<ResponseBody> getRandomPhotos(@HeaderMap HashMap<String, String> headerMap,
                                       @Query("count") int count);

    @GET("collections/featured")
    Call<ResponseBody> getCuratedCollections(@HeaderMap HashMap<String, String> headerMap,
                                             @Query("per_page") int count);

    @GET("collections/{id}/photos")
    Call<ResponseBody> getCuratedCollectionPhotos(@HeaderMap HashMap<String, String> headerMap,
                                                  @Path(value = "id", encoded = true) String collectionId);


}
