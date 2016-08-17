package com.wonders.xlab.cardbag.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.qrscanner.BarCodeEncoder;
import com.wonders.xlab.qrscanner.XQrScanner;
import com.wonders.xlab.qrscanner.XQrScannerActivity;

public class CBHomeActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_SCAN = 1234;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_home_activity);
        mImageView = (ImageView) findViewById(R.id.cbscanner_iv);
    }

    public void scan(View view) {
        XQrScanner.getInstance()
                .startForResult(this, 1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            String result = data.getStringExtra(XQrScannerActivity.EXTRA_RESULT);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            BarCodeEncoder ecc = new BarCodeEncoder(mImageView.getWidth(), mImageView.getHeight());
            try {
                Bitmap bitm = ecc.barcode(result);
                mImageView.setImageBitmap(bitm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
