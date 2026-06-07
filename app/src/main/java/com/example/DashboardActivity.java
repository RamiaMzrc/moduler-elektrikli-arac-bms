package com.example;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvSocValue;
    private ProgressBar pbSoc;
    private TextView tvVoltageValue;
    private TextView tvCurrentValue;
    private TextView tvTempValue;
    private TextView tvLockValue;
    private TextView tvStatusValue;
    private TextView tvWarningValue;
    private LinearLayout llStatusContainer;
    private View vLockIndicator;

    private Button btnStartSim;
    private Button btnStopSim;
    private Button btnLogout;

    private Button btnTestNormal;
    private Button btnTestWarning;
    private Button btnTestFault;

    // Simulation states
    private double currentSoc = 100.0;
    private double currentVoltage = 380.0;
    private double currentCurrent = 20.0;
    private double currentTemperature = 25.0;
    private boolean isSimulating = false;

    private final Handler simHandler = new Handler();
    private SharedPreferences sharedPreferences;

    private final Runnable simRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isSimulating) return;

            // Draining discharge simulation loop:
            // 1. SOC decreases slowly
            currentSoc -= 0.1;
            if (currentSoc < 0) currentSoc = 0;

            // 2. Temperature fluctuates slightly
            double tempChange = (Math.random() * 0.8) - 0.2; // random between -0.2 and +0.6 °C
            currentTemperature += tempChange;

            // 3. Voltage decreases in proportion to depth of discharge
            currentVoltage = 380.0 - (100.0 - currentSoc) * 0.8;
            if (currentVoltage < 250) currentVoltage = 250;

            // 4. Current drawing fluctuates randomly
            currentCurrent = 10.0 + (Math.random() * 15.0); // random between 10 and 25 A

            // Update main view
            updateDashboard(currentSoc, currentVoltage, currentCurrent, currentTemperature);

            // Reschedule every 2000 milliseconds
            simHandler.postDelayed(this, 2000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Security check
        sharedPreferences = getSharedPreferences("com.example.modulerbatarya.PREFS", Context.MODE_PRIVATE);
        boolean sessionActive = sharedPreferences.getBoolean("session_status", false);
        if (!sessionActive) {
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
            return;
        }

        // Bind dashboard values
        tvSocValue = findViewById(R.id.tv_soc_value);
        pbSoc = findViewById(R.id.pb_soc);
        tvVoltageValue = findViewById(R.id.tv_voltage_value);
        tvCurrentValue = findViewById(R.id.tv_current_value);
        tvTempValue = findViewById(R.id.tv_temp_value);
        tvLockValue = findViewById(R.id.tv_lock_value);
        tvStatusValue = findViewById(R.id.tv_status_value);
        tvWarningValue = findViewById(R.id.tv_warning_value);
        llStatusContainer = findViewById(R.id.ll_status_container);
        vLockIndicator = findViewById(R.id.v_lock_indicator);

        // Bind control buttons
        btnStartSim = findViewById(R.id.btn_start_sim);
        btnStopSim = findViewById(R.id.btn_stop_sim);
        btnLogout = findViewById(R.id.btn_logout);

        // Bind test suite buttons
        btnTestNormal = findViewById(R.id.btn_test_normal);
        btnTestWarning = findViewById(R.id.btn_test_warning);
        btnTestFault = findViewById(R.id.btn_test_fault);

        // Set action listeners for simulation controls
        btnStartSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If we are currently in a FAULT state or drained, let's reset to clean start
                if (currentSoc <= 10.0 || currentTemperature >= 60.0) {
                    currentSoc = 100.0;
                    currentVoltage = 380.0;
                    currentCurrent = 20.0;
                    currentTemperature = 25.0;
                }
                startSimulation();
            }
        });

        btnStopSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSimulation();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogout();
            }
        });

        // Set action listeners for state override buttons
        btnTestNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSoc = 76.0;
                currentVoltage = 360.0;
                currentCurrent = 12.5;
                currentTemperature = 25.0;
                updateDashboard(currentSoc, currentVoltage, currentCurrent, currentTemperature);
            }
        });

        btnTestWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSoc = 18.0;
                currentVoltage = 330.0;
                currentCurrent = 14.2;
                currentTemperature = 52.0;
                updateDashboard(currentSoc, currentVoltage, currentCurrent, currentTemperature);
            }
        });

        btnTestFault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSoc = 8.0;
                currentVoltage = 290.0;
                currentCurrent = 20.5;
                currentTemperature = 61.0;
                updateDashboard(currentSoc, currentVoltage, currentCurrent, currentTemperature);
            }
        });

        // Start default state
        updateDashboard(currentSoc, currentVoltage, currentCurrent, currentTemperature);
        startSimulation();
    }

    private void startSimulation() {
        if (!isSimulating) {
            isSimulating = true;
            simHandler.post(simRunnable);
            btnStartSim.setEnabled(false);
            btnStopSim.setEnabled(true);
        }
    }

    private void stopSimulation() {
        if (isSimulating) {
            isSimulating = false;
            simHandler.removeCallbacks(simRunnable);
            btnStartSim.setEnabled(true);
            btnStopSim.setEnabled(false);
        }
    }

    private void updateDashboard(double soc, double volt, double current, double temp) {
        // Prepare data model
        BatteryData data = new BatteryData(soc, volt, current, temp, "NORMAL", "", false);

        // Process through safety decision engine
        BatteryData result = DecisionEngine.process(data);

        // Store back simulated states
        this.currentSoc = result.getSoc();
        this.currentVoltage = result.getVoltage();
        this.currentCurrent = result.getCurrent();
        this.currentTemperature = result.getTemperature();

        // Update UI Text values
        tvSocValue.setText(String.format(Locale.getDefault(), "%%%d", (int) result.getSoc()));
        pbSoc.setProgress((int) result.getSoc());

        tvVoltageValue.setText(String.format(Locale.getDefault(), "%.1f", result.getVoltage()));
        tvCurrentValue.setText(String.format(Locale.getDefault(), "%.1f", result.getCurrent()));
        tvTempValue.setText(String.format(Locale.getDefault(), "%.1f", result.getTemperature()));
        
        tvStatusValue.setText("SİSTEM DURUMU: " + result.getSystemStatus());
        tvWarningValue.setText(result.getWarningMessage());
        
        if (result.isSecurityLock()) {
            tvLockValue.setText("AKTİF");
        } else {
            tvLockValue.setText("PASİF");
        }

        // Apply corresponding state styles
        int colorRes;
        int bgDrawableRes;
        int dotDrawableRes;
        
        switch (result.getSystemStatus()) {
            case "FAULT":
                colorRes = R.color.fault_red;
                bgDrawableRes = R.drawable.status_fault_bg;
                dotDrawableRes = R.drawable.dot_red;
                tvLockValue.setTextColor(ContextCompat.getColor(this, R.color.fault_red));
                tvSocValue.setTextColor(ContextCompat.getColor(this, R.color.fault_red));
                
                // Highlight the fault test button
                btnTestFault.setAlpha(1.0f);
                btnTestNormal.setAlpha(0.40f);
                btnTestWarning.setAlpha(0.40f);
                
                // Automatically pause simulation if a fault is encountered
                stopSimulation();
                break;
                
            case "WARNING":
                colorRes = R.color.warning_orange;
                bgDrawableRes = R.drawable.status_warning_bg;
                dotDrawableRes = R.drawable.dot_orange;
                tvLockValue.setTextColor(ContextCompat.getColor(this, R.color.warning_orange));
                tvSocValue.setTextColor(ContextCompat.getColor(this, R.color.warning_orange));
                
                // Highlight the warning test button
                btnTestNormal.setAlpha(0.40f);
                btnTestWarning.setAlpha(1.0f);
                btnTestFault.setAlpha(0.40f);
                break;
                
            case "NORMAL":
            default:
                colorRes = R.color.normal_green;
                bgDrawableRes = R.drawable.status_normal_bg;
                dotDrawableRes = R.drawable.dot_green;
                tvLockValue.setTextColor(ContextCompat.getColor(this, R.color.normal_green));
                tvSocValue.setTextColor(ContextCompat.getColor(this, R.color.normal_green));
                
                // Highlight the normal test button
                btnTestNormal.setAlpha(1.0f);
                btnTestWarning.setAlpha(0.40f);
                btnTestFault.setAlpha(0.40f);
                break;
        }

        // Apply colors to Status Container, Lock Indicator, and Progress Bar
        llStatusContainer.setBackground(ContextCompat.getDrawable(this, bgDrawableRes));
        vLockIndicator.setBackground(ContextCompat.getDrawable(this, dotDrawableRes));
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pbSoc.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(this, colorRes)));
        }
    }

    private void performLogout() {
        stopSimulation();
        
        // Wipe shared preferences session data
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Return to login activity
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSimulation();
    }
}
