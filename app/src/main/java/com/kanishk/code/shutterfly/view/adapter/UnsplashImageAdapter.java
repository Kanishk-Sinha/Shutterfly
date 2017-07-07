package com.kanishk.code.shutterfly.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.databinding.ItemEfImageAdapterBinding;
import com.kanishk.code.shutterfly.model.UnsplashImage;
import com.kanishk.code.shutterfly.utils.CircleProgressDrawable;

import java.util.ArrayList;

/**
 * Created by kanishk on 7/6/17.
 */

public class UnsplashImageAdapter extends RecyclerView.Adapter<UnsplashImageAdapter.ImageBindingHolder> {

    private ArrayList<UnsplashImage> itemList;
    private Context ctx;
    private OnAdapterItemClickListener mListener;

    public UnsplashImageAdapter(ArrayList<UnsplashImage> list, Context context) {
        this.itemList = list;
        ctx = context;
    }

    @Override
    public ImageBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemEfImageAdapterBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_ef_image_adapter,
                parent,
                false);
        return new ImageBindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ImageBindingHolder holder, final int position) {
        Uri uri = Uri.parse(itemList.get(position).getUrls().getRegular());
        holder.imageView.setImageURI(uri);
        holder.imageView.getHierarchy().setProgressBarImage(new CircleProgressDrawable());
        holder.imageView.setOnClickListener(v -> mListener.onClicked(
                position,
                itemList.get(position).getUrls().getFull(),
                itemList.get(position).getUser().getName(),
                itemList.get(position).getUser().getProf_image(),
                itemList.get(position).getUser().getPortfolio_url()));
        //binding.content.setOnClickListener(v -> mListener.onClicked(position, itemList.get(position).getWebformatURL()));
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        mListener = listener;
    }

    public interface OnAdapterItemClickListener {
        void onClicked(int position,
                       String url,
                       String userName,
                       String userImageUrl,
                       String userId);
    }

    class ImageBindingHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView imageView;
        public ItemEfImageAdapterBinding binding;

        ImageBindingHolder(ItemEfImageAdapterBinding binding) {
            super(binding.content);
            this.binding = binding;
            this.imageView = binding.image;
        }
    }
}
