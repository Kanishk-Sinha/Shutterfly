package com.kanishk.code.shutterfly.presenter.fragment;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

import com.kanishk.code.shutterfly.model.Category;

import java.util.ArrayList;

/**
 * Created by kanishk on 7/3/17.
 */

public class CategoryFragmentPresenter extends BaseObservable {

    private View view;
    private Context context;
    private OnPresenterResultListener mListener;

    public CategoryFragmentPresenter(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void fetchCategories() {
        ArrayList<Category> list = new ArrayList<>();
        list.add(new Category("Art", "https://cdn.pixabay.com/photo/2016/01/18/20/09/old-age-1147283_960_720.jpg"));
        list.add(new Category("Animals", "https://cdn.pixabay.com/photo/2015/09/23/00/30/richmond-952744_960_720.jpg"));
        list.add(new Category("Architecture", "https://cdn.pixabay.com/photo/2016/08/13/12/07/office-building-1590598_960_720.jpg"));
        list.add(new Category("Backgrounds", "https://cdn.pixabay.com/photo/2016/12/17/17/44/presents-1913987_960_720.jpg"));
        list.add(new Category("Beauty", "https://cdn.pixabay.com/photo/2016/09/01/18/24/rk5161-1636868_960_720.jpg"));
        list.add(new Category("Business", "https://cdn.pixabay.com/photo/2016/03/26/13/09/organic-1280537_960_720.jpg"));
        list.add(new Category("Cars", "http://www.hdwallpapers.in/walls/rolls_royce_dawn_black_badge_2017_4k-wide.jpg"));
        list.add(new Category("Computer", "https://cdn.pixabay.com/photo/2016/06/28/05/10/laptop-1483974_960_720.jpg"));
        list.add(new Category("Education", "https://cdn.pixabay.com/photo/2014/09/05/18/32/old-books-436498_960_720.jpg"));
        list.add(new Category("Emotions", "https://cdn.pixabay.com/photo/2016/01/18/20/09/old-age-1147283_960_720.jpg"));
        list.add(new Category("Food", "https://cdn.pixabay.com/photo/2016/08/30/18/45/grilled-1631727_960_720.jpg"));
        list.add(new Category("Health", "https://cdn.pixabay.com/photo/2014/04/26/04/25/fitness-332278_960_720.jpg"));
        list.add(new Category("Industry", "https://cdn.pixabay.com/photo/2016/05/17/20/56/locomotive-1399080_960_720.jpg"));
        list.add(new Category("Music", "https://cdn.pixabay.com/photo/2015/09/19/01/05/country-946706_960_720.jpg"));
        list.add(new Category("Nature", "https://cdn.pixabay.com/photo/2016/09/01/18/24/rk5161-1636868_960_720.jpg"));
        list.add(new Category("People", "https://cdn.pixabay.com/photo/2016/09/01/18/24/rk5161-1636868_960_720.jpg"));
        list.add(new Category("Places", "https://cdn.pixabay.com/photo/2016/09/01/18/24/rk5161-1636868_960_720.jpg"));
        list.add(new Category("Religion", "https://cdn.pixabay.com/photo/2016/09/01/18/24/rk5161-1636868_960_720.jpg"));
        list.add(new Category("Science", "https://cdn.pixabay.com/photo/2016/09/01/18/24/rk5161-1636868_960_720.jpg"));
        list.add(new Category("Sports", "https://cdn.pixabay.com/photo/2016/09/01/18/24/rk5161-1636868_960_720.jpg"));
        list.add(new Category("Transportation", "https://cdn.pixabay.com/photo/2016/09/01/18/24/rk5161-1636868_960_720.jpg"));
        list.add(new Category("Travel", "https://cdn.pixabay.com/photo/2016/09/01/18/24/rk5161-1636868_960_720.jpg"));
        list.add(new Category("Vacation", "https://cdn.pixabay.com/photo/2016/09/01/18/24/rk5161-1636868_960_720.jpg"));
        mListener.onCategoryList(list);
    }

    public void setOnPresenterResultListener(OnPresenterResultListener listener) {
        mListener = listener;
    }

    public interface OnPresenterResultListener {
        void onCategoryList(ArrayList<Category> list);
        void onError(String error);
        void onFailure(String message);
    }
}
