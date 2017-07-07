package com.kanishk.code.shutterfly.view.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.model.PixabayImage;
import com.kanishk.code.shutterfly.network.BaseAsyncTask;
import com.kanishk.code.shutterfly.network.PixabayRetrofitCallProvider;
import com.kanishk.code.shutterfly.utils.GridItemDecoration;
import com.kanishk.code.shutterfly.view.adapter.EFImageAdapter;
import com.kanishk.code.shutterfly.view.fragment.SlideshowDialogFragment;
import com.kanishk.code.shutterfly.widgets.PrimaryTextStyleMedium;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class GenericPhotoActivity extends AppCompatActivity {

    private String filter;
    private RecyclerView recyclerView;
    private GridLayoutManager grid;
    private Toolbar toolbar;
    private PrimaryTextStyleMedium toolbatTitle;

    private String forWhich;
    private File[] listFile;
    private String[] FilePathStrings, FileNameStrings;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_photo);
        getBundle();
        setViews();
        if (forWhich.equalsIgnoreCase("category"))
            fetchData(filter);
        else if (forWhich.equalsIgnoreCase("downloaded"))
            showDownloaded();
    }

    private void setViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_light);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbatTitle = (PrimaryTextStyleMedium) findViewById(R.id.toolbar_title);
        toolbatTitle.setText(title);

        recyclerView = (RecyclerView) findViewById(R.id.container_rcview);
        grid = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(grid);
        int spanCount = 2;
        int spacing = 5;
        recyclerView.addItemDecoration(new GridItemDecoration(spanCount, spacing, true));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
    }

    private void getBundle() {
        filter = getIntent().getStringExtra("filter");
        forWhich = getIntent().getStringExtra("for_which");
        if (forWhich.equalsIgnoreCase("category"))
            title = "Showing photos for " + filter;
        else if (forWhich.equalsIgnoreCase("downloaded"))
            title = "Downloaded on your phone";
    }

    private void fetchData(String filter) {
        PixabayRetrofitCallProvider provider = new PixabayRetrofitCallProvider();
        Call<ResponseBody> call = provider.fetchServiceForQueriedPhotos(filter);
        BaseAsyncTask task = new BaseAsyncTask(call);
        task.execute();
        task.setOnNetworkCallEnd(new BaseAsyncTask.OnNetworkCallEndListener() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {
                try {
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
                    EFImageAdapter adapter = new EFImageAdapter(list, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    adapter.setOnAdapterItemClickListener(new EFImageAdapter.OnAdapterItemClickListener() {
                        @Override
                        public void onClicked(int position, String url, String userName, String userImageUrl, String userId) {
                            Intent intent = new Intent(GenericPhotoActivity.this, ImageViewActivity.class);
                            intent.putExtra("url", url);
                            intent.putExtra("type", url);
                            intent.putExtra("user_name", userName);
                            intent.putExtra("user_image_url", userImageUrl);
                            intent.putExtra("user_id", userId);
                            startActivityForResult(intent, 1);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void showDownloaded() {
        ArrayList<PixabayImage> list = new ArrayList<>();
        File file = new File(Environment.getExternalStorageDirectory(), "shutterfly");
        if (file.isDirectory()) {
            listFile = file.listFiles();
            FilePathStrings = new String[listFile.length];
            FileNameStrings = new String[listFile.length];
            for (File aListFile : listFile) {
                PixabayImage image = new PixabayImage();
                image.setFullHDURL(aListFile.getAbsolutePath());
                image.setWebformatURL(aListFile.getAbsolutePath());
                list.add(image);
            }
        }
        EFImageAdapter adapter = new EFImageAdapter(list, getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnAdapterItemClickListener(new EFImageAdapter.OnAdapterItemClickListener() {
            @Override
            public void onClicked(int position, String url, String userName, String userImageUrl, String userId) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", list);
                bundle.putInt("position", position);
                bundle.putString("for", "image");

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
