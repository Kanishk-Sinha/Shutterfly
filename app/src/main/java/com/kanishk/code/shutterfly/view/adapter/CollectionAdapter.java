package com.kanishk.code.shutterfly.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.databinding.ItemUnsplashCollectionBinding;
import com.kanishk.code.shutterfly.model.UnsplashCollections;
import com.kanishk.code.shutterfly.presenter.adapter.CollectionAdapterPresenter;

import java.util.ArrayList;

/**
 * Created by kanishk on 7/5/17.
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.CollectionBindingHolder> {

    private ArrayList<UnsplashCollections> itemList;
    private Context ctx;
    private OnAdapterItemClickListener mListener;

    public CollectionAdapter(ArrayList<UnsplashCollections> list, Context context) {
        this.itemList = list;
        ctx = context;
    }

    @Override
    public CollectionBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemUnsplashCollectionBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_unsplash_collection,
                parent,
                false);
        return new CollectionBindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(final CollectionBindingHolder holder, final int position) {
        ItemUnsplashCollectionBinding binding = holder.binding;
        UnsplashCollections collection = itemList.get(position);
        binding.setPresenter(new CollectionAdapterPresenter(collection));
        binding.content.setOnClickListener(v -> mListener.onClicked(position, collection));
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        mListener = listener;
    }

    public interface OnAdapterItemClickListener {
        void onClicked(int position, UnsplashCollections collection);
    }

    class CollectionBindingHolder extends RecyclerView.ViewHolder {

        public ItemUnsplashCollectionBinding binding;

        CollectionBindingHolder(ItemUnsplashCollectionBinding binding) {
            super(binding.content);
            this.binding = binding;
        }
    }
}
