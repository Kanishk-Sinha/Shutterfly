package com.kanishk.code.shutterfly.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kanishk on 7/4/17.
 */

public class ImageDownloader extends AsyncTask<String, Integer, String> {

    private OnImageDownloadListener mListener;
    private ProgressDialog downloadProgressDialog;
    private String path;

    public ImageDownloader(ProgressDialog downloadProgressDialog) {
        if (downloadProgressDialog != null) {
            this.downloadProgressDialog = downloadProgressDialog;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (downloadProgressDialog != null)
            downloadProgressDialog.show();
        System.out.println("Starting download");
    }

    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            System.out.println("Downloading");
            URL url = new URL(f_url[0]);

            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();
            // 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File imagesFolder = new File(Environment.getExternalStorageDirectory(), "shutterfly");
            imagesFolder.mkdirs();

            path = imagesFolder + "/" + timeStamp + ".jpg";
            OutputStream output = new FileOutputStream(path);
            byte data[] = new byte[1024];

            Integer total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress((int)((total*100)/lenghtOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values[0]);
        if (downloadProgressDialog != null)
            downloadProgressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String file_url) {
        if (downloadProgressDialog != null)
            downloadProgressDialog.hide();
        mListener.onDownloaded(path);
    }

    public void setOnImageDownloadListener(OnImageDownloadListener listener) {
        mListener = listener;
    }

    public interface OnImageDownloadListener {
        void onDownloaded(String url);
        void onDownloadFailed();
    }

}
