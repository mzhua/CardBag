package com.wonders.xlab.qrscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

import static com.wonders.xlab.qrscanner.R.id.toolbar;

public class XQrScannerActivity extends AppCompatActivity implements QRCodeView.Delegate {

    private final int PERMISSION_REQUEST_CODE_CAMERA = 1234;
    private final int GRAVITY_TITLE_LEFT = 1;
    private final int GRAVITY_TITLE_CENTER = 3;
    private ZXingView mZXingView;
    private Toolbar mToolbar;
    private TextView mTvTitle;

    private boolean mIsFlashLightOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getColor(R.color.qrTopBarBackground) == getResources().getColor(android.R.color.white) && Build.VERSION.SDK_INT >= 23) {
            setTheme(R.style.QRAppTheme_NoActionBar_LightStatusBar);
        } else {
            setTheme(R.style.QRAppTheme_NoActionBar);
        }
        super.onCreate(savedInstanceState);
        requestPermission();

        setContentView(R.layout.x_qr_scanner_activity);
        mToolbar = (Toolbar) findViewById(toolbar);
        mTvTitle = (TextView) findViewById(R.id.toolbar_title);
        mZXingView = (ZXingView) findViewById(R.id.zxingview);
        mZXingView.setDelegate(this);

        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) mTvTitle.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        switch (getResources().getInteger(R.integer.qrTopBarTitleGravity)) {
            case GRAVITY_TITLE_CENTER:
                layoutParams.gravity = Gravity.CENTER;
                break;
            case GRAVITY_TITLE_LEFT:
                layoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
                break;
        }

    }

    public void setNavigationIcon(@DrawableRes int drawableResId) {
        Drawable stateButtonDrawable = ContextCompat.getDrawable(this, drawableResId).mutate();
        stateButtonDrawable.setColorFilter(ContextCompat.getColor(this, R.color.qrTopBarTitleColor), PorterDuff.Mode.SRC_ATOP);
        mToolbar.setNavigationIcon(stateButtonDrawable);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE_CAMERA);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1234) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScan();
            } else {
                Toast.makeText(this, R.string.error_open_camera, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                return;
            }
        }
        startScan();
    }

    private void startScan() {
        mZXingView.startCamera();
        mZXingView.startSpotAndShowRect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_qr_scanner, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemOn = menu.findItem(R.id.menu_flash_on);
        MenuItem itemOff = menu.findItem(R.id.menu_flash_off);
        setupMenuIcon(itemOn);
        setupMenuIcon(itemOff);
        if (mIsFlashLightOpened) {
            itemOn.setVisible(false);
            itemOff.setVisible(true);
        } else {
            itemOn.setVisible(true);
            itemOff.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void setupMenuIcon(MenuItem menuItem) {
        Drawable menuItemDrawable = menuItem.getIcon();
        if (menuItemDrawable != null) {
            menuItemDrawable.mutate();
            menuItemDrawable.setColorFilter(getResources().getColor(R.color.qrTopBarTitleColor), PorterDuff.Mode.SRC_ATOP);
            menuItem.setIcon(menuItemDrawable);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_flash_on) {
            mZXingView.openFlashlight();
            mIsFlashLightOpened = true;
        } else if (item.getItemId() == R.id.menu_flash_off) {
            mZXingView.closeFlashlight();
            mIsFlashLightOpened = false;
        }

        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mIsFlashLightOpened) {
            mZXingView.closeFlashlight();
        }
        mZXingView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        mZXingView.startSpot();
        try {
            Intent data = new Intent();
            data.putExtra(XQrScanner.EXTRA_RESULT_BAR_OR_CODE_STRING, result);
            setResult(RESULT_OK, data);
            finish();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this, R.string.error_open_camera, Toast.LENGTH_SHORT).show();
    }

}
