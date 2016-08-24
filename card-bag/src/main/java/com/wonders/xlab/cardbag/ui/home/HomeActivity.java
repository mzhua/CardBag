package com.wonders.xlab.cardbag.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyActivity;
import com.wonders.xlab.cardbag.widget.TopBar;

public class HomeActivity extends Activity {
    private TopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_home_activity);
        mTopBar = (TopBar) findViewById(R.id.top_bar);
        mTopBar.setOnRightMenuClickListener(new TopBar.OnRightMenuClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "right click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void manageCard(View view) {
        startActivity(new Intent(this, CardMyActivity.class));
    }

    public void useCard(View view) {
    }
}
