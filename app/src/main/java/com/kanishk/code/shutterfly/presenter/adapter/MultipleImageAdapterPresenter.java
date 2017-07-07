package com.kanishk.code.shutterfly.presenter.adapter;

import android.content.Context;
import android.databinding.BaseObservable;

import com.kanishk.code.shutterfly.model.Image;
import com.kanishk.code.shutterfly.model.PixabayImage;
import com.kanishk.code.shutterfly.model.UnsplashImage;

/**
 * Created by Kanishk on 7/3/17.
 */

public class MultipleImageAdapterPresenter extends BaseObservable {
    private Context context;
    private UnsplashImage image;

    public MultipleImageAdapterPresenter(Context context, UnsplashImage image) {
        this.context = context;
        this.image = image;
    }

    public String getImage() {
        return this.image.getUrls().getFull();
    }
}
