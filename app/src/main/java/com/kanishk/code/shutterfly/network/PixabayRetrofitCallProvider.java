package com.kanishk.code.shutterfly.network;

import com.kanishk.code.shutterfly.model.Api;
import com.kanishk.code.shutterfly.retrofit_model.PixabayService;

import java.util.Collections;
import java.util.HashMap;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by kanishk on 7/3/17.
 */

public class PixabayRetrofitCallProvider {

    private OkHttpClient client;
    private Call<ResponseBody> call;
    private String UUID;
    private String refreshToken;
    private HashMap<String, String> headerMap = new HashMap<>();
    private Retrofit service;

    public PixabayRetrofitCallProvider() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        ConnectionSpec spec = new
                ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();
        client = new OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .addInterceptor(interceptor).build();
        service = new Retrofit.Builder()
                .baseUrl(Api.PIXABAY_BASE_URL)
                .client(client)
                .build();
    }

    public Call<ResponseBody> fetchServiceForAllPhotos() {
        PixabayService pixabayService = service.create(PixabayService.class);
        call = pixabayService.getAllPhotos(Api.PIXABAY_API_KEY, "high_resolution", 50);
        return call;
    }

    public Call<ResponseBody> fetchServiceForQueriedPhotos(String query) {
        PixabayService pixabayService = service.create(PixabayService.class);
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put("key", Api.PIXABAY_API_KEY);
        queryMap.put("response_group", "high_resolution");
        queryMap.put("q", query);
        call = pixabayService.getAllPhotos(queryMap);
        return call;
    }

}
