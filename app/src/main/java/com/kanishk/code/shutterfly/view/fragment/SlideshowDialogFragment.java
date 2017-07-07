package com.kanishk.code.shutterfly.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.model.PixabayImage;
import com.kanishk.code.shutterfly.utils.UniversalImageBindingAdapter;
import com.kanishk.code.shutterfly.widgets.ZoomImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kanishk on 11/05/17.
 */

public class SlideshowDialogFragment extends DialogFragment {

    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ArrayList<?> images;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private TextView lblCount, lblTitle, lblDate;
    private int selectedPosition = 0;
    private String forObject;

    private boolean show = true;
    private boolean backToFrag;
    private CircleImageView lblUser;

    public static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        lblCount = (TextView) v.findViewById(R.id.lbl_count);
        lblTitle = (TextView) v.findViewById(R.id.title);
        lblDate = (TextView) v.findViewById(R.id.date);
        lblUser = (CircleImageView) v.findViewById(R.id.user_img);

        forObject = getArguments().getString("for");
        assert forObject != null;
        if (forObject.equals("image")) {
            images = (ArrayList<PixabayImage>) getArguments().getSerializable("images");
            selectedPosition = getArguments().getInt("position");
        } else if (forObject.equals("certificate")) {
            images = (ArrayList<PixabayImage>) getArguments().getSerializable("images");
            selectedPosition = getArguments().getInt("position");
        } else if (forObject.equals("image_return_to_frag")) {
            images = (ArrayList<PixabayImage>) getArguments().getSerializable("images");
            selectedPosition = getArguments().getInt("position");
            backToFrag = true;
        }

        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + images.size());
        Log.e(TAG, "for object: " + forObject);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        setCurrentItem(selectedPosition);

        return v;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //  page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void displayMetaInfo(int position) {
        if (forObject.equals("image")) {
            lblCount.setText((position + 1) + " of " + images.size());
            PixabayImage image = (PixabayImage) images.get(position);
        } else if (forObject.equals("image_return_to_frag")) {
            lblCount.setText((position + 1) + " of " + images.size());
            PixabayImage image = (PixabayImage) images.get(position);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //  adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);

            ZoomImageView imageViewPreview = (ZoomImageView) view.findViewById(R.id.image_preview);

            if (forObject.equals("image") || forObject.equals("image_return_to_frag")) {
                PixabayImage image = (PixabayImage) images.get(position);
                UniversalImageBindingAdapter.loadImage(imageViewPreview, image.getFullHDURL());
                /*UniversalImageBindingAdapter.loadImage(lblUser, image.getUserImageURL());
                lblTitle.setText(image.getUser());*/
                lblTitle.setVisibility(View.GONE);
                lblUser.setVisibility(View.GONE);
            }

            imageViewPreview.setOnClickListener(v1 -> {
                if (show) {
                    show = false;
                    lblCount.setVisibility(View.GONE);
                    lblTitle.setVisibility(View.GONE);
                } else {
                    show = true;
                    lblCount.setVisibility(View.VISIBLE);
                    lblTitle.setVisibility(View.VISIBLE);
                }
            });

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

