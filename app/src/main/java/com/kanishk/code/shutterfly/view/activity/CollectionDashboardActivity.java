package com.kanishk.code.shutterfly.view.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.databinding.ActivityCollectionDashboardBinding;
import com.kanishk.code.shutterfly.model.UnsplashCollections;
import com.kanishk.code.shutterfly.presenter.activity.CollectionDashboardPresenter;

public class CollectionDashboardActivity extends AppCompatActivity {

    private ActivityCollectionDashboardBinding binding;
    private CollectionDashboardPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection_dashboard);
        presenter = new CollectionDashboardPresenter(binding, this, getCollection());
        binding.setPresenter(presenter);
        presenter.setCover();
        presenter.fetchData();
    }

    private UnsplashCollections getCollection() {
        String bundle = getIntent().getStringExtra("collection");
        Gson gson = new Gson();
        return gson.fromJson(bundle, UnsplashCollections.class);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
