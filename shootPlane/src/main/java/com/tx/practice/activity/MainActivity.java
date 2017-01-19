package com.tx.practice.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tx.practice.PlaneWar;
import com.tx.practice.R;

public class MainActivity extends AppCompatActivity {

    private PlaneWar planeWar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        planeWar = (PlaneWar) View.inflate(this, R.layout.activity_main, null);
        setContentView(planeWar);
        planeWar.start();
    }
}
