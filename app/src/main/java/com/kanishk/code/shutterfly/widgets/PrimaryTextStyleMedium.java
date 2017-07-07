package com.kanishk.code.shutterfly.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kanishk.code.shutterfly.utils.FontCache;

/**
 * Created by kanishk on 08/05/17.
 */

public class PrimaryTextStyleMedium extends TextView {

    public PrimaryTextStyleMedium(Context context) {
        super(context);
        applyFont(context);
    }

    public PrimaryTextStyleMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyFont(context);
    }

    public PrimaryTextStyleMedium(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PrimaryTextStyleMedium(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyFont(context);
    }

    private void applyFont(Context context) {
        //Typeface font = FontCache.getTypeface("fonts/Montserrat/Montserrat-Regular.ttf", context);
        Typeface font = FontCache.getTypeface("fonts/Cabin_Condensed/CabinCondensed-Regula.ttf", context);
        setTypeface(font);
    }
}
