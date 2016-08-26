package com.wonders.xlab.cardbag.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wonders.xlab.cardbag.R;

import java.io.File;

/**
 * Created by hua on 16/8/22.
 */

public class ImageViewUtil {

    private static final RoundedCornersTransform ROUNDED_CORNERS_TRANSFORM = new RoundedCornersTransform();

    public static void load(Context context, Uri uri, ImageView imageView) {
        Picasso.with(context)
                .load(uri)
                .transform(ROUNDED_CORNERS_TRANSFORM)
                .into(imageView);
    }

    public static void load(Context context, File file, ImageView imageView) {
        Picasso.with(context)
                .load(file)
                .into(imageView);
    }

    public static void load(Context context, String path, ImageView imageView) {
        Picasso.with(context)
                .load(path)
                .transform(ROUNDED_CORNERS_TRANSFORM)
                .into(imageView);
    }

    public static void load(Context context,int resId, ImageView imageView) {
        Picasso.with(context)
                .load(resId)
                .placeholder(R.color.imageBackgroundHolder)
                .transform(ROUNDED_CORNERS_TRANSFORM)
                .into(imageView);
    }
}
