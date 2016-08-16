package com.wonders.xlab.qrscanner;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by hua on 16/8/16.
 */

public class QrScanner {
    private static final String EXTRA_PREFIX = BuildConfig.APPLICATION_ID;
    private static final Object object = new Object();
    private static QrScanner qrScanner;

    private QrScanner() {

    }

    /**
     * have to call the method before call start
     *
     */
    public static QrScanner getInstance() {
        if (qrScanner == null) {
            synchronized (object) {
                if (qrScanner == null) {
                    qrScanner = new QrScanner();
                }
            }
        }

        return qrScanner;
    }

    public QrScanner startForResult(Activity activity,int requestCode) {
        activity.startActivityForResult(new Intent(activity, QrScannerActivity.class),requestCode);
        return this;
    }

    public QrScanner startForResult(Fragment fragment,int requestCode) {
        fragment.startActivityForResult(new Intent(fragment.getContext(), QrScannerActivity.class),requestCode);
        return this;
    }
}
