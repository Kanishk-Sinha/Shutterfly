package com.kanishk.code.shutterfly.presenter.adapter;

import android.content.Context;
import android.databinding.BaseObservable;

import com.kanishk.code.shutterfly.model.PixabayImage;

/**
 * Created by kanishk on 7/3/17.
 */

public class EFImageAdapterPresenter extends BaseObservable {

    private Context context;
    private PixabayImage image;

    public EFImageAdapterPresenter(Context context, PixabayImage image) {
        this.context = context;
        this.image = image;
    }

    public String getImage() {
        return image.getWebformatURL();
    }

    public String getName() {
        return image.getUser();
    }
}
