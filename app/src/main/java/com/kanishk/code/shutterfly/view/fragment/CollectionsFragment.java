package com.kanishk.code.shutterfly.view.fragment;

import android.content.Context;
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
import com.kanishk.code.shutterfly.presenter.fragment.CollectionFragmentPresenter;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by kanishk on 7/3/17.
 */

public class CollectionsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static View tabView;

    private String mParam1;
    private String mParam2;

    private ExploreFragment.OnFragmentInteractionListener mListener;
    private InputMethodManager imm;
    private View view;
    private RecyclerView recyclerView;
    private GridLayoutManager grid;
    private CircleIndicator indicator;
    private ViewPager mViewPager;
    private CollectionFragmentPresenter presenter;

    public CollectionsFragment() {
        // Required empty public constructor
    }

    public static CollectionsFragment newInstance() {
        return new CollectionsFragment();
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
        view = inflater.inflate(R.layout.fragment_collection, container, false);
        view.hasNestedScrollingParent();
        presenter = new CollectionFragmentPresenter(view, getContext());
        initPresenter(presenter);
        return view;
    }

    public void initPresenter(CollectionFragmentPresenter presenter) {
        //presenter.fetchCollections();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExploreFragment.OnFragmentInteractionListener) {
            mListener = (ExploreFragment.OnFragmentInteractionListener) context;
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
