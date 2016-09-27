package com.wonders.xlab.cardbag.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wonders.xlab.cardbag.R;

import java.io.File;

import static android.content.ContentValues.TAG;

/**
 * Created by hua on 16/8/22.
 */

public class ImageViewUtil {
    private static String TAG = ImageViewUtil.class.getName();

    private static RoundedCornersTransform ROUNDED_CORNERS_TRANSFORM;

    public static void load(final Context context, final Uri uri, final ImageView imageView) {
        if (context == null || uri == null || imageView == null) {
            return;
        }
        initTransform(context);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                if (imageView.getMeasuredWidth() <= 0 || imageView.getMeasuredHeight() <= 0) {
                    LogUtil.error(TAG,"At least one dimension has to be positive number of the ImageView");
                    return;
                }
                Picasso.with(context)
                        .load(uri)
                        .config(Bitmap.Config.RGB_565)
                        .resize(imageView.getMeasuredWidth(), imageView.getMeasuredHeight())
                        .centerCrop()
                        .transform(ROUNDED_CORNERS_TRANSFORM)
                        .into(imageView);
            }
        });

    }

    public static void load(Context context, File file, ImageView imageView) {
        if (context == null || file == null || imageView == null) {
            return;
        }
        initTransform(context);
        Picasso.with(context)
                .load(file)
                .config(Bitmap.Config.RGB_565)
                .transform(ROUNDED_CORNERS_TRANSFORM)
                .into(imageView);
    }

    public static void load(final Context context, final String path, final ImageView imageView) {
        if (context == null || TextUtils.isEmpty(path) || imageView == null) {
            return;
        }
        initTransform(context);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                if (imageView.getMeasuredWidth() <= 0 || imageView.getMeasuredHeight() <= 0) {
                    LogUtil.error(TAG,"At least one dimension has to be positive number of the ImageView");
                    return;
                }
                Picasso.with(context)
                        .load(path)
                        .config(Bitmap.Config.RGB_565)
                        .resize(imageView.getMeasuredWidth(), imageView.getMeasuredHeight())
                        .centerCrop()
                        .transform(ROUNDED_CORNERS_TRANSFORM)
                        .into(imageView);
            }
        });
    }

    public static void load(Context context, int resId, ImageView imageView) {
        if (context == null || imageView == null) {
            return;
        }
        initTransform(context);
        Picasso.with(context)
                .load(resId)
                .config(Bitmap.Config.RGB_565)
                .placeholder(R.color.cbImageBackgroundHolder)
                .transform(ROUNDED_CORNERS_TRANSFORM)
                .into(imageView);
    }

    private static void initTransform(Context context) {
        if (ROUNDED_CORNERS_TRANSFORM == null) {
            ROUNDED_CORNERS_TRANSFORM = new RoundedCornersTransform(DensityUtil.dp2px(context, 8), 0);
        }
    }
}
