package com.kanishk.code.shutterfly.presenter.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.model.UnsplashCollections;
import com.kanishk.code.shutterfly.model.UnsplashImage;
import com.kanishk.code.shutterfly.network.BaseAsyncTask;
import com.kanishk.code.shutterfly.network.UnsplashRetrofitCallProvider;
import com.kanishk.code.shutterfly.utils.GridItemDecoration;
import com.kanishk.code.shutterfly.view.activity.CollectionDashboardActivity;
import com.kanishk.code.shutterfly.view.adapter.CollectionAdapter;
import com.kanishk.code.shutterfly.view.adapter.MultipleImageAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by kanishk on 7/5/17.
 */

public class CollectionFragmentPresenter extends BaseObservable {

    private Context context;
    private View view;
    private RecyclerView recyclerView;
    private GridLayoutManager grid;

    public CollectionFragmentPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
        setupViews(view);
    }

    private void setupViews(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.collection_rcview);
        recyclerView.setHasFixedSize(true);
        grid = new GridLayoutManager(view.getContext(), 1);
        recyclerView.setLayoutManager(grid);
        int spanCount = 1;
        int spacing = 6;
        recyclerView.addItemDecoration(new GridItemDecoration(spanCount, spacing, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void fetchCollections() {
        UnsplashRetrofitCallProvider provider = new UnsplashRetrofitCallProvider();
        Call<ResponseBody> call = provider.fetchServiceForCollections();
        BaseAsyncTask task = new BaseAsyncTask(call);
        task.execute();
        task.setOnNetworkCallEnd(new BaseAsyncTask.OnNetworkCallEndListener() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        ArrayList<UnsplashCollections> list = new ArrayList<>();
                        Gson gson = new Gson();
                        String bodyString = new String(response.body().bytes());
                        JSONArray array = new JSONArray(bodyString);
                        for (int i = 0; i < array.length() ; i++) {
                            UnsplashCollections unsplashCollections = new UnsplashCollections();
                            JSONObject object = new JSONObject(array.get(i).toString());
                            unsplashCollections = gson.fromJson(array.get(i).toString(), UnsplashCollections.class);
                            JSONObject coverPhotoObject = object.getJSONObject("cover_photo");
                            UnsplashImage.ImageUrl imageUrl = gson.fromJson(coverPhotoObject.getJSONObject("urls").toString(), UnsplashImage.ImageUrl.class);
                            UnsplashImage.User user = gson.fromJson(coverPhotoObject.getJSONObject("user").toString(), UnsplashImage.User.class);
                            unsplashCollections.setImageUrl(imageUrl);
                            unsplashCollections.setUser(user);
                            list.add(unsplashCollections);
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

    private void setRecyclerView(ArrayList<UnsplashCollections> list) {
        CollectionAdapter adapter = new CollectionAdapter(list, context);
        recyclerView.setAdapter(adapter);
        adapter.setOnAdapterItemClickListener((position, collection) -> {
            Intent intent = new Intent(context, CollectionDashboardActivity.class);
            Gson gson = new Gson();
            intent.putExtra("collection", gson.toJson(collection));
            context.startActivity(intent);
        });
    }
}
