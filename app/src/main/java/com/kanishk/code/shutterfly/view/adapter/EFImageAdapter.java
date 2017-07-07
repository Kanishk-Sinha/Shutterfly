package com.kanishk.code.shutterfly.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.databinding.ItemEfImageAdapterBinding;
import com.kanishk.code.shutterfly.model.PixabayImage;
import com.kanishk.code.shutterfly.presenter.adapter.EFImageAdapterPresenter;
import com.kanishk.code.shutterfly.utils.CircleProgressDrawable;

import java.util.ArrayList;

/**
 * Created by kanishk on 10/05/17.
 */

public class EFImageAdapter extends RecyclerView.Adapter<EFImageAdapter.ImageBindingHolder> {

    private ArrayList<PixabayImage> itemList;
    private Context ctx;
    private OnAdapterItemClickListener mListener;

    public EFImageAdapter(ArrayList<PixabayImage> list, Context context) {
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
        ItemEfImageAdapterBinding binding = holder.binding;
        binding.setPresenter(new EFImageAdapterPresenter(ctx, itemList.get(position)));
        String url = itemList.get(position).getWebformatURL();
        Uri uri;
        if (url.startsWith("/storage/emulated"))
            uri = Uri.parse("file://" + itemList.get(position).getWebformatURL());
        else
            uri = Uri.parse(itemList.get(position).getWebformatURL());
        holder.imageView.setImageURI(uri);
        holder.imageView.getHierarchy().setProgressBarImage(new CircleProgressDrawable());
        binding.content.setOnClickListener(v -> mListener.onClicked(position, itemList.get(position).getFullHDURL(), itemList.get(position).getUser(), itemList.get(position).getUserImageURL(), itemList.get(position).getUser_id()));
        binding.deletePicture.setVisibility(View.GONE);
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
