package com.kanishk.code.shutterfly.presenter.fragment;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.model.PixabayImage;
import com.kanishk.code.shutterfly.model.UnsplashImage;
import com.kanishk.code.shutterfly.network.BaseAsyncTask;
import com.kanishk.code.shutterfly.network.PixabayRetrofitCallProvider;
import com.kanishk.code.shutterfly.network.UnsplashRetrofitCallProvider;
import com.kanishk.code.shutterfly.view.adapter.MultipleImageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by kanishk on 7/3/17.
 */

public class ExploreFragmentPresenter extends BaseObservable {
    private View view;
    private Context context;
    private OnPresenterResultListener mListener;
    private CircleIndicator indicator;
    private ViewPager mViewPager;

    public ExploreFragmentPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void initBanner() {
        indicator = (CircleIndicator)view.findViewById(R.id.viewpager_indicator);
        mViewPager = (ViewPager)view.findViewById(R.id.banner_viewPager);

        fetchBannerData();
    }

    private void fetchBannerData() {
        UnsplashRetrofitCallProvider provider = new UnsplashRetrofitCallProvider();
        Call<ResponseBody> call = provider.fetchServiceForRandomPhotos();
        BaseAsyncTask task = new BaseAsyncTask(call);
        task.execute();
        task.setOnNetworkCallEnd(new BaseAsyncTask.OnNetworkCallEndListener() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        ArrayList<UnsplashImage> list = new ArrayList<>();
                        Gson gson = new Gson();
                        String bodyString = new String(response.body().bytes());
                        JSONArray array = new JSONArray(bodyString);
                        for (int i = 0; i < array.length() ; i++) {
                            JSONObject object = new JSONObject(array.get(i).toString());
                            UnsplashImage unsplashImage = new UnsplashImage();
                            UnsplashImage.ImageUrl imageUrl = gson.fromJson(object.getJSONObject("urls").toString(), UnsplashImage.ImageUrl.class);
                            UnsplashImage.User user = gson.fromJson(object.getJSONObject("user").toString(), UnsplashImage.User.class);
                            user.setProf_image(object.getJSONObject("user").getJSONObject("profile_image").getString("medium"));
                            user.setProf_link(object.getJSONObject("user").getJSONObject("links").getString("portfolio"));
                            unsplashImage.setUrls(imageUrl);
                            unsplashImage.setUser(user);
                            list.add(unsplashImage);
                        }
                        MultipleImageAdapter adapter = new MultipleImageAdapter(context, list);
                        mViewPager.setAdapter(adapter);
                        indicator.setViewPager(mViewPager);
                        adapter.registerDataSetObserver(indicator.getDataSetObserver());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onFailure(String message) {

            }
        });

    }

    public void fetchAllPhotos() {
        PixabayRetrofitCallProvider provider = new PixabayRetrofitCallProvider();
        Call<ResponseBody> call = provider.fetchServiceForAllPhotos();
        BaseAsyncTask task = new BaseAsyncTask(call);
        task.execute();
        task.setOnNetworkCallEnd(new BaseAsyncTask.OnNetworkCallEndListener() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<PixabayImage>(){}.getType();
                    ArrayList<PixabayImage> list = new ArrayList<>();
                    String body = new String(response.body().bytes());
                    Log.e("resp", body);
                    JSONObject jsonObject = new JSONObject(body);
                    JSONArray hits = jsonObject.getJSONArray("hits");
                    for (int i = 0; i < hits.length() ; i++) {
                        list.add(gson.fromJson(hits.get(i).toString(), type));
                    }
                    mListener.onPhotoList(list);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    public void setOnPresenterResultListener(OnPresenterResultListener listener) {
        mListener = listener;
    }

    public interface OnPresenterResultListener {
        void onPhotoList(ArrayList<PixabayImage> list);
        void onError(String error);
        void onFailure(String message);
    }


}
