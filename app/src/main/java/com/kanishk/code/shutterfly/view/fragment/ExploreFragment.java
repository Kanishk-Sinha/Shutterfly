package com.kanishk.code.shutterfly.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.model.PixabayImage;
import com.kanishk.code.shutterfly.presenter.fragment.ExploreFragmentPresenter;
import com.kanishk.code.shutterfly.utils.GridItemDecoration;
import com.kanishk.code.shutterfly.utils.MakeToast;
import com.kanishk.code.shutterfly.view.activity.ImageViewActivity;
import com.kanishk.code.shutterfly.view.adapter.EFImageAdapter;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by kanishk on 7/3/17.
 */

public class ExploreFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static View tabView;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private InputMethodManager imm;
    private View view;
    private RecyclerView recyclerView;
    private GridLayoutManager grid;
    private CircleIndicator indicator;
    private ViewPager mViewPager;
    private ExploreFragmentPresenter presenter;

    public ExploreFragment() {
        // Required empty public constructor
    }

    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        this.setRetainInstance(true);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_explore, container, false);
        view.hasNestedScrollingParent();
        presenter = new ExploreFragmentPresenter(view, getContext());
        setupViews(view);
        //initPresenter(presenter);
        return view;
    }

    private void setupViews(View view) {

        // SET AND DECORATE RECYCLER VIEW
        recyclerView = (RecyclerView) view.findViewById(R.id.images_rcview);

        presenter.initBanner();

    }

    public void initPresenter(ExploreFragmentPresenter presenter) {
        presenter.fetchAllPhotos();
        presenter.setOnPresenterResultListener(new ExploreFragmentPresenter.OnPresenterResultListener() {
            @Override
            public void onPhotoList(ArrayList<PixabayImage> list) {
                EFImageAdapter adapter = new EFImageAdapter(list, getContext());
                recyclerView.setAdapter(adapter);
                grid = new GridLayoutManager(view.getContext(), 2);
                recyclerView.setLayoutManager(grid);
                int spanCount = 2;
                int spacing = 6;
                recyclerView.addItemDecoration(new GridItemDecoration(spanCount, spacing, false));
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setHasFixedSize(true);
                adapter.setOnAdapterItemClickListener(new EFImageAdapter.OnAdapterItemClickListener() {
                    @Override
                    public void onClicked(int position, String url, String userName, String userImageUrl, String userId) {
                        Intent intent = new Intent(getContext(), ImageViewActivity.class);
                        intent.putExtra("url", url);
                        intent.putExtra("type", url);
                        intent.putExtra("user_name", userName);
                        intent.putExtra("user_image_url", userImageUrl);
                        intent.putExtra("user_id", userId);
                        startActivityForResult(intent, 1);
                    }
                });
            }

            @Override
            public void onError(String error) {
                MakeToast.setToast(getContext(), error);
            }

            @Override
            public void onFailure(String message) {
                MakeToast.setToast(getContext(), message);
            }
        });
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
