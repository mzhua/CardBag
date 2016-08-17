package com.wonders.xlab.cardbag.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.qrscanner.BarCodeEncoder;
import com.wonders.xlab.qrscanner.XQrScanner;
import com.wonders.xlab.xcontacts.XContacts;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

public class CBHomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SCAN = 1234;
    private ImageView mImageView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_home_activity);
        mImageView = (ImageView) findViewById(R.id.cbscanner_iv);
        mTextView = (TextView) findViewById(R.id.text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        OkHttpUtils.get()
                .url("https://api.leancloud.cn/1.1/classes/cards")
                .addHeader("X-LC-Id","27bHde5iXz1tTpQmbwRcenxg-gzGzoHsz ")
                .addHeader("X-LC-Key","THX77SeFRWJpGzL4QXwlWSCQ")
                .addHeader("Content-Type","application/json")
                .tag(this)
                .build()
                .execute(new Callback<CardsEntity>(){

                    @Override
                    public CardsEntity parseNetworkResponse(Response response, int id) throws Exception {
                        String body = response.body().string();
                        return new Gson().fromJson(body,CardsEntity.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mTextView.setText(e.getMessage());
                    }

                    @Override
                    public void onResponse(CardsEntity response, int id) {
                        mTextView.setText(response.getResults().get(0).getCard_name());
                    }
                });
        OkHttpUtils.get()
                .url("http://img5.imgtn.bdimg.com/it/u=1864751896,666907639&fm=21&gp=0.jpg")
                .tag(this)
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        Toast.makeText(CBHomeActivity.this, "progress:" + progress, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(CBHomeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        mImageView.setImageBitmap(response);
                    }
                });
    }

    public void scan(View view) {
        XQrScanner.getInstance()
                .startForResult(this, REQUEST_CODE_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            String result = data.getStringExtra(XQrScanner.EXTRA_RESULT_BAR_OR_CODE_STRING);
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

    public void contacts(View view) {
        XContacts.getInstance()
                .start(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
