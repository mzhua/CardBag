package com.wonders.xlab.cardbag.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.ui.cardmy.CardMyActivity;
import com.wonders.xlab.cardbag.util.MyMigration;
import com.wonders.xlab.cardbag.widget.TopBar;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.wonders.xlab.cardbag.R.integer.schemaVersion;

public class HomeActivity extends Activity {
    private TopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_home_activity);
        mTopBar = (TopBar) findViewById(R.id.top_bar);

        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(getApplicationContext())
                .schemaVersion(schemaVersion)
                .migration(new MyMigration())
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public void manageCard(View view) {
        startActivity(new Intent(this, CardMyActivity.class));
    }

    public void useCard(View view) {
    }
}
