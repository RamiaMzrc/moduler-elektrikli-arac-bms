package com.example.evlogin;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;

public class BatteryMonitoringActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ui.prepareWindow(this);

        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(Color.rgb(237, 245, 248));
        setContentView(root);
    }
}
