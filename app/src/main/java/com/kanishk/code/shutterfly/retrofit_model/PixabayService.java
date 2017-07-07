package com.kanishk.code.shutterfly.retrofit_model;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by kanishk on 7/3/17.
 */

public interface PixabayService {

    @GET("api/")
    Call<ResponseBody> getAllPhotos(@QueryMap(encoded = true) HashMap<String, String> query);

    @GET("api/")
    Call<ResponseBody> getAllPhotos(@Query("key") String key,
                                    @Query("response_group") String group,
                                    @Query("per_page") int count);

    @GET("api/")
    Call<ResponseBody> getPhotos(@QueryMap(encoded = true) HashMap<String, String> query);
}
