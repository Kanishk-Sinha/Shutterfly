package com.kanishk.code.shutterfly.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.kanishk.code.shutterfly.databinding.ActivityCollectionDashboardBinding;
import com.kanishk.code.shutterfly.model.UnsplashCollections;
import com.kanishk.code.shutterfly.model.UnsplashImage;
import com.kanishk.code.shutterfly.network.BaseAsyncTask;
import com.kanishk.code.shutterfly.network.UnsplashRetrofitCallProvider;
import com.kanishk.code.shutterfly.utils.GridItemDecoration;
import com.kanishk.code.shutterfly.view.activity.GenericPhotoActivity;
import com.kanishk.code.shutterfly.view.activity.ImageViewActivity;
import com.kanishk.code.shutterfly.view.adapter.MultipleImageAdapter;
import com.kanishk.code.shutterfly.view.adapter.UnsplashImageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by kanishk on 7/6/17.
 */

public class CollectionDashboardPresenter extends BaseObservable {

    private UnsplashCollections collection;
    private ActivityCollectionDashboardBinding binding;
    private Context context;

    public CollectionDashboardPresenter(ActivityCollectionDashboardBinding binding, Context context, UnsplashCollections collection) {
        this.binding = binding;
        this.context = context;
        this.collection = collection;
        Gson gson = new Gson();
    }

    public String getCollectionName() {
        return collection.getTitle();
    }

    public String getCollectionDescription() {
        return collection.getDescription();
    }

    public String getPhotoCount() {
        return collection.getTotal_photos() + " photos";
    }

    public void setCover() {
        SimpleDraweeView coverPhoto = binding.coverPhoto;
        coverPhoto.setImageURI(Uri.parse(collection.getImageUrl().getRegular()));
    }

    public void fetchData() {
        UnsplashRetrofitCallProvider provider = new UnsplashRetrofitCallProvider();
        Call<ResponseBody> call = provider.fetchServiceForCollectionPhotos(collection.getId());
        BaseAsyncTask task = new BaseAsyncTask(call);
        task.execute();
        task.setOnNetworkCallEnd(new BaseAsyncTask.OnNetworkCallEndListener() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {
                Gson gson = new Gson();
                if (response.isSuccessful()) {
                    try {
                        ArrayList<UnsplashImage> list = new ArrayList<>();
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
                        setRecyclerView(list);
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

    private void setRecyclerView(ArrayList<UnsplashImage> list) {
        GridLayoutManager grid = new GridLayoutManager(context, 2);
        RecyclerView recyclerView = binding.collectionPhotoRcview;

        recyclerView.setLayoutManager(grid);
        int spanCount = 2;
        int spacing = 6;
        recyclerView.addItemDecoration(new GridItemDecoration(spanCount, spacing, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        UnsplashImageAdapter adapter = new UnsplashImageAdapter(list, context);
        recyclerView.setAdapter(adapter);
        adapter.setOnAdapterItemClickListener(new UnsplashImageAdapter.OnAdapterItemClickListener() {
            @Override
            public void onClicked(int position, String url, String userName, String userImageUrl, String userId) {
                Intent intent = new Intent(context, ImageViewActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("type", url);
                intent.putExtra("user_name", userName);
                intent.putExtra("user_image_url", userImageUrl);
                intent.putExtra("user_id", userId);
                context.startActivity(intent);
            }
        });
    }
}
