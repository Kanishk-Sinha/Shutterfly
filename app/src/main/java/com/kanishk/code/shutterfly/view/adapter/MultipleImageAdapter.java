package com.kanishk.code.shutterfly.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.databinding.ItemPagerImageBinding;
import com.kanishk.code.shutterfly.model.UnsplashImage;
import com.kanishk.code.shutterfly.presenter.adapter.MultipleImageAdapterPresenter;

import java.util.ArrayList;

/**
 * Created by cyphertree on 7/3/17.
 */

public class MultipleImageAdapter extends PagerAdapter {

    private static final String TAG = "GalleryAdapter";

    private final ArrayList<UnsplashImage> list;
    private final LayoutInflater mLayoutInflater;
    private Context context;
    private OnPagerItemClickListener mListener;

    public MultipleImageAdapter(@NonNull Context context, @NonNull ArrayList<UnsplashImage> media) {
        super();
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = media;
        this.context = context;
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ItemPagerImageBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_pager_image,
                container,
                false);
        binding.setPresenter(new MultipleImageAdapterPresenter(context, list.get(position)));
        View v = binding.getRoot();
        container.addView(v);
        v.setOnClickListener(v1 -> mListener.onItemClicked(position, list));
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        try {
            container.removeView((View) object);
            unbindDrawables((View) object);
        } catch (Exception e) {
            Log.w(TAG, "destroyItem: failed to destroy item and clear it's used resources", e);
        }
    }

    protected void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    public interface OnPagerItemClickListener {
        void onItemClicked(int position, ArrayList<UnsplashImage> list);
    }

    public void setOnPagerItemClickListener(OnPagerItemClickListener listener) {
        mListener = listener;
    }

}
