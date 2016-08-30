package com.wonders.xlab.cardbag.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by hua on 16/8/30.
 */
public class RatioViewGroup extends FrameLayout {
    public RatioViewGroup(Context context) {
        super(context);
    }

    public RatioViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RatioViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
