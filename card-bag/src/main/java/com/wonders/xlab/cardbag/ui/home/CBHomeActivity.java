package com.wonders.xlab.cardbag.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.view.CBTopBar;

public class CBHomeActivity extends Activity {
    private static final int REQUEST_CODE_SCAN = 1234;
    private CBTopBar mCBTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_home_activity);
        mCBTopBar = (CBTopBar) findViewById(R.id.topbar);
        mCBTopBar.setOnRightMenuClickListener(new CBTopBar.OnRightMenuClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CBHomeActivity.this, "right click", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /*@Override
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
    }*/

    public void manageCard(View view) {
    }

    public void useCard(View view) {
    }
}
