package com.wonders.xlab.cardbag.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wonders.xlab.cardbag.CBag;
import com.wonders.xlab.cardbag.CBagEvent;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.db.CBDataSyncHelper;
import com.wonders.xlab.qrscanner.XQrScanner;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SCAN = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter(CBagEvent.getInstance().getActionOfEventBroadcast(this));
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, CBagEvent.getInstance().getEventData(context,intent).getEvent() + ":" + CBagEvent.getInstance().getEventData(context,intent).getName(), Toast.LENGTH_SHORT).show();
            }
        }, filter);
    }

    public void openCardBag(View view) {
        CBag.get().setCardSearchModel(null)
                .start(this);
    }

    public void scan(View view) {
        XQrScanner.getInstance().startForResult(this, REQUEST_CODE_SCAN);
    }

    /**
     * 获取SDK中缓存的数据
     *
     * @param view
     */
    public void fetchData(View view) {
        List<CardEntity> allCardsInfo = CBDataSyncHelper.getInstance(this).getAllCardsInfo();
    }

    /**
     * 将服务器保存的数据同步缓存到SDK中
     *
     * @param view
     */
    public void saveData(View view) {
        List<CardEntity> cardEntities = new ArrayList<>();
        CBDataSyncHelper.getInstance(this).updateCardsInfo(cardEntities);
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