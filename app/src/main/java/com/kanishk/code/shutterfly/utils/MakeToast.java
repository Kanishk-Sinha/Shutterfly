package com.kanishk.code.shutterfly.utils;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.kanishk.code.shutterfly.R;
import com.kanishk.code.shutterfly.widgets.PrimaryTextStyleMedium;

/**
 * Created by Kanishk on 6/23/17.
 */

public class MakeToast {
    public static void setToast(Context context, String message) {
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        LayoutInflater inflater = LayoutInflater.from(context);
        View toastRoot = inflater.inflate(R.layout.toast_custom_main, null);
        PrimaryTextStyleMedium messageView = (PrimaryTextStyleMedium) toastRoot.findViewById(R.id.message_toast);
        messageView.setText(message);
        toast.setView(toastRoot);
        toast.show();
        final Handler handler = new Handler();
        handler.postDelayed(() -> toast.cancel(), 900);
    }

    public static void setPositiveToast(Context context, String message) {
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        LayoutInflater inflater = LayoutInflater.from(context);
        View toastRoot = inflater.inflate(R.layout.toast_custom_positive, null);
        PrimaryTextStyleMedium messageView = (PrimaryTextStyleMedium) toastRoot.findViewById(R.id.message_toast);
        messageView.setText(message);
        toast.setView(toastRoot);
        toast.show();
        final Handler handler = new Handler();
        handler.postDelayed(() -> toast.cancel(), 900);
    }

}
