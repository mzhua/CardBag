package com.wonders.xlab.qrscanner;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;

/**
 * Created by hua on 16/8/16.
 */

public class XQrScanner {
    public static final String EXTRA_RESULT_BAR_OR_CODE_STRING = "extra_result_bar_or_code_string";

    private static final Object object = new Object();
    private static XQrScanner XQrScanner;

    private XQrScanner() {

    }

    /**
     * have to call the method before call start
     */
    public static XQrScanner getInstance() {
        if (XQrScanner == null) {
            synchronized (object) {
                if (XQrScanner == null) {
                    XQrScanner = new XQrScanner();
                }
            }
        }

        return XQrScanner;
    }

    public XQrScanner startForResult(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, XQrScannerActivity.class), requestCode);
        return this;
    }

    public XQrScanner startForResult(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(new Intent(fragment.getActivity(), XQrScannerActivity.class), requestCode);
        return this;
    }
}
