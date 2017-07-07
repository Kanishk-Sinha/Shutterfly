package com.kanishk.code.shutterfly.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.bumptech.glide.Glide;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.databinding.ActivityImageViewBinding;
import com.kanishk.code.shutterfly.databinding.LayoutImageViewActivityCtaBinding;
import com.kanishk.code.shutterfly.utils.CircleProgressDrawable;
import com.kanishk.code.shutterfly.utils.ImageDownloader;
import com.kanishk.code.shutterfly.utils.MakeToast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class ImageViewActivity extends AppCompatActivity {

    private String url;
    private SimpleDraweeView imageView;
    private String wallPath;
    private ProgressDialog downloadProgressDialog;
    private String type;
    private LayoutImageViewActivityCtaBinding ctaLayoutBinding;
    private ActivityImageViewBinding binding;
    private boolean isCTALayoutOpen = false;
    private String userName;
    private String userImageUrl;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(" ");

        getBundle();

        setViews();
    }

    private void getBundle() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        type = intent.getStringExtra("type");
        userName = intent.getStringExtra("user_name");
        userImageUrl = intent.getStringExtra("user_image_url");
        userId = intent.getStringExtra("user_id");
    }

    private void setViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_view);
        imageView = binding.image;
        imageView.setImageURI(Uri.parse(url));
        imageView.getHierarchy().setProgressBarImage(new CircleProgressDrawable());

        ctaLayoutBinding = binding.revealCTA;

        binding.cta.setOnClickListener(view -> new Thread(() -> runOnUiThread(() -> {
            if (isCTALayoutOpen) {
                isCTALayoutOpen = false;
                doCircularExit(ctaLayoutBinding.content);
            }
            else {
                isCTALayoutOpen = true;
                doCircularReveal(ctaLayoutBinding.content);
            }
        })).start());

        binding.userName.setText(userName);
        binding.userImage.setImageURI(Uri.parse(userImageUrl));

        ctaLayoutBinding.ctaDownload.setOnClickListener(v -> downloadImage(false));
        ctaLayoutBinding.ctaSetWallpaper.setOnClickListener(v -> setWallpaper(url));
    }

    private void downloadImage(boolean setWallpaper) {
        downloadProgressDialog = new ProgressDialog(ImageViewActivity.this);
        downloadProgressDialog.setTitle("Downloading Wallpaper");
        downloadProgressDialog.setMessage("Downloading in progress...");
        downloadProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        downloadProgressDialog.setProgress(0);

        ImageDownloader downloader = new ImageDownloader(downloadProgressDialog);
        downloader.execute(url);
        downloader.setOnImageDownloadListener(new ImageDownloader.OnImageDownloadListener() {

            @Override
            public void onDownloaded(String path) {
                MakeToast.setPositiveToast(ImageViewActivity.this, "Image has been downloaded!");
            }

            @Override
            public void onDownloadFailed() {
                MakeToast.setToast(ImageViewActivity.this, "Something bad happened!");
            }
        });
    }

    public void doCircularReveal(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int x = view.getWidth();
            int y = view.getHeight();
            int startRadius = 0;
            int hypotenuse = (int) Math.hypot(view.getWidth(), view.getHeight());
            Animator anim = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, hypotenuse);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(400);
            view.setVisibility(View.VISIBLE);
            anim.start();
        }
        else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void doCircularExit(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int x = view.getWidth();
            int y = view.getHeight();
            int startRadius = Math.max(view.getWidth(), view.getHeight());
            int endRadius = 0;
            Animator anim = ViewAnimationUtils.createCircularReveal(view, x, y, startRadius, endRadius);
            anim.setInterpolator(new AccelerateDecelerateInterpolator());
            anim.setDuration(400);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {

                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(View.GONE);
                }
            });
            anim.start();
        }
        else {
            view.setVisibility(View.GONE);
        }
    }

    private void setWallpaper(String wallPath) {
        final Bitmap[] bitmap = new Bitmap[1];
        try {
            new Thread(() -> {
                try {
                    final ProgressDialog[] progressDialog = new ProgressDialog[1];

                    runOnUiThread(() -> {
                        progressDialog[0] = new ProgressDialog(ImageViewActivity.this);
                        progressDialog[0].setMessage("Downloading wallpaper. Please wait...");
                        progressDialog[0].show();
                    });

                    bitmap[0] = Glide
                            .with(ImageViewActivity.this)
                            .load(wallPath)
                            .asBitmap()
                            .into(1920, 1080)
                            .get();
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(ImageViewActivity.this);
                    wallpaperManager.setBitmap(bitmap[0]);

                    runOnUiThread(() -> {
                        progressDialog[0].dismiss();
                        MakeToast.setPositiveToast(ImageViewActivity.this, "Wallpaper has been set successfully!");
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
