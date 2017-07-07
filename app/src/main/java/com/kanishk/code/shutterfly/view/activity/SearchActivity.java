package com.kanishk.code.shutterfly.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.model.PixabayImage;
import com.kanishk.code.shutterfly.network.BaseAsyncTask;
import com.kanishk.code.shutterfly.network.PixabayRetrofitCallProvider;
import com.kanishk.code.shutterfly.utils.GridItemDecoration;
import com.kanishk.code.shutterfly.utils.MakeToast;
import com.kanishk.code.shutterfly.view.adapter.EFImageAdapter;
import com.kanishk.code.shutterfly.widgets.PrimaryEditTextStyle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchResultRCView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_light);
        toolbar.setNavigationOnClickListener(v -> finish());

        PrimaryEditTextStyle editText = (PrimaryEditTextStyle) findViewById(R.id.search_field);

        ImageView showResults = (ImageView) findViewById(R.id.search);
        showResults.setOnClickListener(v -> search(editText));

        searchResultRCView = (RecyclerView) findViewById(R.id.search_results_rcview);
        searchResultRCView.setHasFixedSize(true);
        GridLayoutManager grid = new GridLayoutManager(this, 2);
        searchResultRCView.setLayoutManager(grid);
        int spanCount = 2;
        int spacing = 5;
        searchResultRCView.addItemDecoration(new GridItemDecoration(spanCount, spacing, true));
        searchResultRCView.setNestedScrollingEnabled(false);
        searchResultRCView.setItemAnimator(new DefaultItemAnimator());
    }

    private void search(EditText editText) {
        String searchString = editText.getText().toString();
        if (!searchString.equalsIgnoreCase("") && !searchString.contains("   ")) {
            fetchResults(searchString);
        } else
            MakeToast.setToast(SearchActivity.this, "Please enter a valid keyword!");
    }

    private void fetchResults(String searchString) {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Searching for " + searchString + "...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        PixabayRetrofitCallProvider provider = new PixabayRetrofitCallProvider();
        Call<ResponseBody> call = provider.fetchServiceForQueriedPhotos(searchString);
        BaseAsyncTask task = new BaseAsyncTask(call);
        task.execute();
        task.setOnNetworkCallEnd(new BaseAsyncTask.OnNetworkCallEndListener() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {
                try {
                    pd.dismiss();
                    Gson gson = new Gson();
                    Type type = new TypeToken<PixabayImage>(){}.getType();
                    ArrayList<PixabayImage> list = new ArrayList<>();
                    String body = new String(response.body().bytes());
                    Log.e("resp", body);
                    JSONObject jsonObject = new JSONObject(body);
                    JSONArray hits = jsonObject.getJSONArray("hits");
                    for (int i = 0; i < hits.length() ; i++) {
                        list.add(gson.fromJson(hits.get(i).toString(), type));
                    }
                    showResults(list);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
            }

            @Override
            public void onFailure(String message) {
                pd.dismiss();
            }
        });
    }

    private void showResults(ArrayList<PixabayImage> list) {
        EFImageAdapter adapter = new EFImageAdapter(list, this);
        searchResultRCView.setAdapter(adapter);
        adapter.setOnAdapterItemClickListener(new EFImageAdapter.OnAdapterItemClickListener() {
            @Override
            public void onClicked(int position, String url, String userName, String userImageUrl, String userId) {
                Intent intent = new Intent(SearchActivity.this, ImageViewActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("type", url);
                intent.putExtra("user_name", userName);
                intent.putExtra("user_image_url", userImageUrl);
                intent.putExtra("user_id", userId);
                startActivityForResult(intent, 1);
            }
        });
    }
}
