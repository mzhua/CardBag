package com.wonders.xlab.cardbag.util;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hua on 16/8/24.
 */

public class FileUtil {

    private static final String ERROR_CREATE_FILE_FAILED = "保存文件失败,请确认是否授权读写存储卡的权限以及存储卡是否正确挂载";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd_HHmmss");

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    /**
     * create temp file for camera capture
     * @param context
     * @param fileName
     * @return
     * @throws IOException
     */
    public static File createTempFile(Context context, String fileName) throws IOException {
        if (isExternalStorageWritable()) {
            // Get the directory for the app's private pictures directory.
            File file = getInternalDirectory(context);
            return new File(file.getAbsolutePath() + File.separator + fileName + DATE_FORMAT.format(new Date()));
        }
        Toast.makeText(context, ERROR_CREATE_FILE_FAILED, Toast.LENGTH_SHORT).show();
        return null;
    }

    @NonNull
    private static File getPublicDirectory() {
        File file = new File(Environment.getExternalStorageDirectory(), "cardbag");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    private static File getInternalDirectory(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    }
}
