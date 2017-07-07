package com.kanishk.code.shutterfly.network;

import com.kanishk.code.shutterfly.model.Api;
import com.kanishk.code.shutterfly.retrofit_model.UnsplashService;

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
 * Created by kanishk on 7/5/17.
 */

public class UnsplashRetrofitCallProvider {

    private final OkHttpClient client;
    private final Retrofit service;
    private Call<ResponseBody> call;
    private HashMap<String, String> headerMap = new HashMap<>();

    public UnsplashRetrofitCallProvider() {
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
                .baseUrl(Api.UNSPLASH_BASE_URL)
                .client(client)
                .build();

        headerMap.put("Accept-Version", "v1");
        headerMap.put("Authorization", "Client-ID " + Api.UNSPLASH_CLIENT_ID);
    }

    public Call<ResponseBody> fetchServiceForRandomPhotos() {
        UnsplashService unsplashService = service.create(UnsplashService.class);
        call = unsplashService.getRandomPhotos(headerMap, 5);
        return call;
    }

    public Call<ResponseBody> fetchServiceForCollections() {
        UnsplashService unsplashService = service.create(UnsplashService.class);
        call = unsplashService.getCuratedCollections(headerMap, 30);
        return call;
    }

    public Call<ResponseBody> fetchServiceForCollectionPhotos(String collectionId) {
        UnsplashService unsplashService = service.create(UnsplashService.class);
        call = unsplashService.getCuratedCollectionPhotos(headerMap, collectionId);
        return call;
    }
}
