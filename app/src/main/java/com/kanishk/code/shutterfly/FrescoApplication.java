package com.kanishk.code.shutterfly;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by kanishk on 7/6/17.
 */

public class FrescoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
