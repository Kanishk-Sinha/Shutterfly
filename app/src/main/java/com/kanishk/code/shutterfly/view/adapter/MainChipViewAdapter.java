package com.kanishk.code.shutterfly.view.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.model.ChipTag;
import com.kanishk.code.shutterfly.widgets.PrimaryTextStyleMedium;
import com.plumillonforge.android.chipview.ChipViewAdapter;

/**
 * Created by kanishk on 05/04/17.
 */

public class MainChipViewAdapter extends ChipViewAdapter {

    private OnChipRemovedListener mListener;
    private static boolean isViewClicked = false;
    private boolean isReadOnly;

    public MainChipViewAdapter( Context context, OnChipRemovedListener listener) {
        super(context);
        mListener = listener;
    }

    public MainChipViewAdapter(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public int getLayoutRes(int position) {
        ChipTag tag = (ChipTag) getChip(position);
        switch (tag.getType()) {
            case 1:
                return R.layout.view_sample_tag_close;
            case 2:
                return R.layout.view_sample_tag_close;
            case 3:
                return R.layout.view_sample_tag_close;
            default:
                return 0;
        }
    }

    @Override
    public int getBackgroundRes(int position) {
        return 0;
    }

    @Override
    public int getBackgroundColor(int position) {
        ChipTag tag = (ChipTag) getChip(position);
        switch (tag.getType()) {
            case 1:
                return getColor(R.color.colorWhite);
            case 2:
                return getColor(R.color.colorAccent);
            case 3:
                return getColor(R.color.colorWhite);
            default:
                return 0;
        }
    }

    @Override
    public int getBackgroundColorSelected(int position) {
        return 0;
    }

    @Override
    public void onLayout(final View view, final int position) {
        final ChipTag tag = (ChipTag) getChip(position);
        switch (tag.getType()) {
            case 1:
                view.setBackgroundResource(R.drawable.token_custom_tags);
                PrimaryTextStyleMedium editClick = ((PrimaryTextStyleMedium) view.findViewById(android.R.id.text1));
                editClick.setOnClickListener(v -> mListener.onChipSelect(tag, position));
                break;
            case 2:
                view.setBackgroundResource(R.drawable.token_custom_tags_selected);
                EditText editClickTypeTwo = ((EditText) view.findViewById(android.R.id.text1));
                editClickTypeTwo.setTextColor(getColor(R.color.colorWhite));
                editClickTypeTwo.setOnClickListener(v -> mListener.onChipDeselect(tag, position));
                break;
            case 3:
                //view.setBackgroundResource(R.drawable.token_custom_tags);

                break;
            default:
                view.setBackgroundResource(R.drawable.token_custom_tags);
        }
    }

    public interface OnChipRemovedListener {
        void onChipRemoved(ChipTag tag);
        void onChipSelect(ChipTag tag, int i);
        void onChipDeselect(ChipTag tag, int i);
    }
}
