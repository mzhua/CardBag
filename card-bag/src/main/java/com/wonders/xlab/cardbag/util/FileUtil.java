package com.wonders.xlab.cardbag.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by hua on 16/8/24.
 */

public class FileUtil {
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public static File createFile(Context context, String fileName) {
        if (isExternalStorageWritable()) {
            // Get the directory for the app's private pictures directory.
            File file = new File(Environment.getExternalStorageDirectory(), context.getApplicationInfo().name);
            if (!file.mkdirs()) {

            }

            return file;
        }

        return null;
    }
}
