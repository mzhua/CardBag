package com.wonders.xlab.cardbag.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wonders.xlab.cardbag.CBag;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openCardBag(View view) {
        CBag.init(getApplication())
                .start(this);
    }
}
