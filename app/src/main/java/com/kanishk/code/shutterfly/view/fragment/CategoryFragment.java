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
import com.kanishk.code.shutterfly.model.Category;
import com.kanishk.code.shutterfly.model.ChipTag;
import com.kanishk.code.shutterfly.presenter.fragment.CategoryFragmentPresenter;
import com.kanishk.code.shutterfly.view.activity.GenericPhotoActivity;
import com.kanishk.code.shutterfly.view.adapter.CategoryAdapter;
import com.kanishk.code.shutterfly.view.adapter.MainChipViewAdapter;
import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;
import com.plumillonforge.android.chipview.ChipViewAdapter;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by kanishk on 7/3/17.
 */

public class CategoryFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static View tabView;

    private String mParam1;
    private String mParam2;
    private ArrayList<String> tagList = new ArrayList<>();

    private ExploreFragment.OnFragmentInteractionListener mListener;
    private InputMethodManager imm;
    private View view;
    private RecyclerView recyclerView;
    private GridLayoutManager grid;
    private CircleIndicator indicator;
    private ViewPager mViewPager;
    private CategoryFragmentPresenter presenter;
    private ChipView chipDefault;
    private ArrayList<Chip> chipList = new ArrayList<>();

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
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
        view = inflater.inflate(R.layout.fragment_category, container, false);
        view.hasNestedScrollingParent();
        presenter = new CategoryFragmentPresenter(view, getContext());
        setupViews(view);
        setTags();
        initTags();
        //initPresenter(presenter);
        return view;
    }

    private void setupViews(View view) {

        // SET AND DECORATE RECYCLER VIEW
        /*recyclerView = (RecyclerView) view.findViewById(R.id.category_rcview);
        recyclerView.setHasFixedSize(true);
        grid = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(grid);
        int spanCount = 2;
        int spacing = 5;
        recyclerView.addItemDecoration(new GridItemDecoration(spanCount, spacing, true));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());*/

    }

    public void initPresenter(CategoryFragmentPresenter presenter) {
        presenter.setOnPresenterResultListener(new CategoryFragmentPresenter.OnPresenterResultListener() {
            @Override
            public void onCategoryList(ArrayList<Category> list) {
                CategoryAdapter adapter = new CategoryAdapter(list, getContext());
                recyclerView.setAdapter(adapter);
                adapter.setOnAdapterItemClickListener((position, name) -> {
                    Intent intent = new Intent(getContext(), GenericPhotoActivity.class);
                    intent.putExtra("filter", name);
                    intent.putExtra("for_which", "category");
                    startActivityForResult(intent, 1);
                });
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
        presenter.fetchCategories();

    }

    private void setTags() {
        tagList.add("Art");
        tagList.add("Animals");
        tagList.add("Architecture");
        tagList.add("Backgrounds");
        tagList.add("Beauty");
        tagList.add("Business");
        tagList.add("Cars");
        tagList.add("Computers");
        tagList.add("Dogs");
        tagList.add("Drinks");
        tagList.add("Education");
        tagList.add("Emotions");
        tagList.add("Fashion");
        tagList.add("Food");
        tagList.add("Health");
        tagList.add("Horror");
        tagList.add("Industry");
        tagList.add("Landscapes");
        tagList.add("Music");
        tagList.add("Nature");
        tagList.add("People");
        tagList.add("Places");
        tagList.add("Religion");
        tagList.add("Science");
        tagList.add("Space");
        tagList.add("Sports");
        tagList.add("Textures");
        tagList.add("Transportation");
        tagList.add("Travel");
        tagList.add("Vacation");
    }

    private void initTags() {
        for (String s : tagList) {
            ChipTag tag = new ChipTag(s, 1);
            chipList.add(tag);
        }

        chipDefault = (ChipView) view.findViewById(R.id.chipview);

        ChipViewAdapter adapter = new MainChipViewAdapter(getActivity(), new MainChipViewAdapter.OnChipRemovedListener() {
            @Override
            public void onChipRemoved(ChipTag tag) {
                chipDefault.remove(tag);
            }

            @Override
            public void onChipSelect(ChipTag tag, int i) {
                Intent intent = new Intent(getContext(), GenericPhotoActivity.class);
                intent.putExtra("filter", tag.getText());
                intent.putExtra("for_which", "category");
                startActivityForResult(intent, 1);
            }

            @Override
            public void onChipDeselect(ChipTag tag, int i) {

            }
        });

        chipDefault.setAdapter(adapter);

        chipDefault.setChipList(chipList);
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
