package com.wonders.xlab.cardbag.base;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by hua on 16/8/19.
 */
public class BaseFragment extends Fragment {
    private ProgressDialog mProgressDialog;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;


    public void showShortToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    public void showProgressDialog(String title, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        if (title != null) {
            mProgressDialog.setTitle(title);
        }
        if (message != null) {
            mProgressDialog.setMessage(message);
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void showAlertDialog(String title, String message, String positiveBtnText, DialogInterface.OnClickListener positiveBtnListener, String negativeBtnText, DialogInterface.OnClickListener negativeBtnListener) {
        if (mBuilder == null) {
            mBuilder = new AlertDialog.Builder(getActivity());
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

    public void dismissAlertDialog() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        dismissAlertDialog();
        mBuilder = null;
        mAlertDialog = null;
        mProgressDialog = null;
    }
}
