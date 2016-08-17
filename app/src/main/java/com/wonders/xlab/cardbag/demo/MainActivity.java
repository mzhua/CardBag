package com.wonders.xlab.cardbag.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wonders.xlab.cardbag.CBag;
import com.wonders.xlab.qrscanner.XQrScanner;
import com.wonders.xlab.qrscanner.XQrScannerActivity;

import static com.wonders.xlab.cardbag.ui.CBHomeActivity.REQUEST_CODE_SCAN;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openCardBag(View view) {
        CBag.getInstance()
                .start(this);
    }

    public void scan(View view) {
        XQrScanner.getInstance().startForResult(this, 1234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            String result = data.getStringExtra(XQrScannerActivity.EXTRA_RESULT);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }
}
