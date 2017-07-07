package com.kanishk.code.shutterfly.presenter.adapter;

import android.databinding.BaseObservable;

import com.kanishk.code.shutterfly.model.UnsplashCollections;

/**
 * Created by kanishk on 7/5/17.
 */

public class CollectionAdapterPresenter extends BaseObservable {

    private UnsplashCollections collections;

    public CollectionAdapterPresenter(UnsplashCollections collections) {
        this.collections = collections;
    }

    public String getName() {
        return collections.getTitle();
    }

    public String getImage() {
        return collections.getImageUrl().getRegular();
    }

    public String getDescription() {
        return collections.getDescription();
    }

    public String getPhotoCount() {
        return collections.getTotal_photos() + " photos";
    }
}
