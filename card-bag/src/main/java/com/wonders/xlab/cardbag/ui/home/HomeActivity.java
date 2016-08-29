package com.wonders.xlab.cardbag.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.manager.RealmManager;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyActivity;
import com.wonders.xlab.cardbag.widget.TopBar;
import com.wonders.xlab.cardbag.widget.XToolBarLayout;

public class HomeActivity extends Activity {
    private XToolBarLayout mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_home_activity);
        mTopBar = (XToolBarLayout) findViewById(R.id.xtbl);

        RealmManager.init(getApplicationContext());
    }

    public void manageCard(View view) {
        startActivity(new Intent(this, CardMyActivity.class));
    }

    public void useCard(View view) {
    }
}
