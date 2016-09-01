package com.wonders.xlab.cardbag.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wonders.xlab.cardbag.CBag;
import com.wonders.xlab.qrscanner.XQrScanner;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SCAN = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openCardBag(View view) {
        CBag.get().setCardSearchModel(new CardSearchModelImpl())
                .start(this);
    }

    public void scan(View view) {
        XQrScanner.getInstance().startForResult(this, REQUEST_CODE_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            String result = data.getStringExtra(XQrScanner.EXTRA_RESULT_BAR_OR_CODE_STRING);
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        }
    }
}