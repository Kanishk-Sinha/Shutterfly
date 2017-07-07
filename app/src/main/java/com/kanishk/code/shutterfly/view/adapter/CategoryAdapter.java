package com.kanishk.code.shutterfly.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.databinding.ItemCategoryAdapterBinding;
import com.kanishk.code.shutterfly.model.Category;
import com.kanishk.code.shutterfly.presenter.adapter.CategoryAdapterPresenter;

import java.util.ArrayList;

/**
 * Created by kanishk on 7/3/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryBindingHolder> {

    private ArrayList<Category> itemList;
    private Context ctx;
    private OnAdapterItemClickListener mListener;

    public CategoryAdapter(ArrayList<Category> list, Context context) {
        this.itemList = list;
        ctx = context;
    }

    @Override
    public CategoryBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCategoryAdapterBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_category_adapter,
                parent,
                false);
        return new CategoryBindingHolder(binding);
    }

    @Override
    public void onBindViewHolder(final CategoryBindingHolder holder, final int position) {
        ItemCategoryAdapterBinding binding = holder.binding;
        binding.setPresenter(new CategoryAdapterPresenter(ctx, itemList.get(position)));
        binding.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClicked(holder.getAdapterPosition(), binding.name.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener listener) {
        mListener = listener;
    }

    public interface OnAdapterItemClickListener {
        void onClicked(int position, String name);
    }

    class CategoryBindingHolder extends RecyclerView.ViewHolder {

        public ItemCategoryAdapterBinding binding;

        CategoryBindingHolder(ItemCategoryAdapterBinding binding) {
            super(binding.content);
            this.binding = binding;
        }
    }
}
