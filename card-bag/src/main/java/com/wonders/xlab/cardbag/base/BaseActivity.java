package com.wonders.xlab.cardbag.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by hua on 16/8/19.
 */
public class BaseActivity extends Activity implements BaseContract.View{
    private ProgressDialog mProgressDialog;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;
    private Toast mToast;

    protected void showShortToast(String message) {
        showToast(message, true);
    }

    private void showToast(String message, boolean isShort) {
        if (mToast == null) {
            mToast = Toast.makeText(this, message, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        } else {
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(message);
        }
        mToast.show();
    }

    protected void showLongToast(String message) {
        showToast(message, false);
    }

    protected void showProgressDialog(String title, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        if (title != null) {
            mProgressDialog.setTitle(title);
        }
        if (message != null) {
            mProgressDialog.setMessage(message);
        }
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void showAlertDialog(String title, String message, String positiveBtnText, DialogInterface.OnClickListener positiveBtnListener, String negativeBtnText, DialogInterface.OnClickListener negativeBtnListener) {
        if (mBuilder == null) {
            mBuilder = new AlertDialog.Builder(this);
        }
        if (!TextUtils.isEmpty(title)) {
            mBuilder.setTitle(title);
        }
        if (!TextUtils.isEmpty(message)) {
            mBuilder.setMessage(message);
        }
        if (!TextUtils.isEmpty(positiveBtnText) && null != positiveBtnListener) {
            mBuilder.setPositiveButton(positiveBtnText, positiveBtnListener);
        }
        if (!TextUtils.isEmpty(negativeBtnText) && null != negativeBtnListener) {
            mBuilder.setNegativeButton(negativeBtnText, negativeBtnListener);
        }

        if (mAlertDialog == null) {
            mAlertDialog = mBuilder.create();
        }
        mAlertDialog.show();
    }

    protected void dismissAlertDialog() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
    }

    protected void hideKeyboardForce(IBinder token) {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(token, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        dismissAlertDialog();
        mBuilder = null;
        mAlertDialog = null;
        mProgressDialog = null;
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    @Override
    public void showToastMessage(String message) {
        showShortToast(message);
    }
}
