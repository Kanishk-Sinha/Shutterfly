package com.kanishk.code.shutterfly.network;

import android.os.AsyncTask;

import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kanishk on 7/3/17.
 */

public class BaseAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private OnNetworkCallEndListener mListener;
    private Call<ResponseBody> call;

    public BaseAsyncTask(Call<ResponseBody> call) {
        this.call = call;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        call.clone().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                if (response.isSuccessful()) mListener.onSuccess(response);
                else mListener.onError("Something bad happened");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                handleNetworkFailure(t);
            }

            private void handleNetworkFailure(Throwable throwable) {
                throwable.printStackTrace();
                String connectivityIssue = "Hmm...Seems like a connection issue. Please check your connection";
                if (throwable instanceof UnknownHostException)
                    mListener.onFailure(connectivityIssue);
                else if (throwable instanceof SSLException)
                    mListener.onFailure(connectivityIssue);
                else if (throwable instanceof ConnectException)
                    mListener.onFailure(connectivityIssue);
                else if (throwable instanceof SocketTimeoutException)
                    mListener.onFailure(connectivityIssue);
                else if (throwable instanceof FileNotFoundException)
                    mListener.onFailure("Oops, looks like the file you just tried to upload was not found. Please try again.");
            }

        });
        return null;
    }

    public void setOnNetworkCallEnd(OnNetworkCallEndListener listener) {
        mListener = listener;
    }

    public interface OnNetworkCallEndListener {
        void onSuccess(Response<ResponseBody> response);
        void onError(String error);
        void onFailure(String message);
    }
}
