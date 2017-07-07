package com.kanishk.code.shutterfly.presenter.adapter;

import android.databinding.BaseObservable;

import com.kanishk.code.shutterfly.model.UnsplashImage;

/**
 * Created by kanishk on 7/6/17.
 */

public class UnsplashImageAdapterPresenter extends BaseObservable {

    private UnsplashImage image;

    public UnsplashImageAdapterPresenter(UnsplashImage image) {
        this.image = image;
    }

}
