package com.kanishk.code.shutterfly.presenter.adapter;

import android.content.Context;
import android.databinding.BaseObservable;

import com.kanishk.code.shutterfly.model.Category;
import com.kanishk.code.shutterfly.model.PixabayImage;

/**
 * Created by kanishk on 7/3/17.
 */

public class CategoryAdapterPresenter extends BaseObservable {

    private Context context;
    private Category category;

    public CategoryAdapterPresenter(Context context, Category category) {
        this.context = context;
        this.category = category;
    }

    public String getImage() {
        return category.getImage();
    }

    public String getName() {
        return category.getName();
    }
}