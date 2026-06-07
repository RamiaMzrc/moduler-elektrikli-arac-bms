package com.example.evlogin;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BatteryMonitoringActivity extends Activity {

    TextView txtStatus, txtSoc, txtVoltage, txtCurrent, txtTemperature, txtWarning, txtStation;
    ProgressBar progressSoc;
    Button btnNormal, btnWarning, btnFault, btnSwapComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_monitoring);
        txtStatus = findViewById(R.id.txtStatus);
        txtSoc = findViewById(R.id.txtSoc);
        txtVoltage = findViewById(R.id.txtVoltage);
        txtCurrent = findViewById(R.id.txtCurrent);
        txtTemperature = findViewById(R.id.txtTemperature);
        txtWarning = findViewById(R.id.txtWarning);
        txtStation = findViewById(R.id.txtStation);
        progressSoc = findViewById(R.id.progressSoc);

        btnNormal = findViewById(R.id.btnNormal);
        btnWarning = findViewById(R.id.btnWarning);
        btnFault = findViewById(R.id.btnFault);
        btnSwapComplete = findViewById(R.id.btnSwapComplete);

        setNormalMode();

        btnNormal.setOnClickListener(v -> setNormalMode());
        btnWarning.setOnClickListener(v -> setWarningMode());
        btnFault.setOnClickListener(v -> setFaultMode());
        btnSwapComplete.setOnClickListener(v -> setSwapCompleteMode());
    }

    private void setNormalMode() {
    updateDashboard(76, 48.2, 12.5, 34.7,
            "NORMAL",
            "Uyarı: Yok",
            "Swap İstasyonu: Gerekli değil",
            "#2E7D32",
            "#E8F5E9");
    }
    private void setWarningMode() {
        updateDashboard(18, 46.8, 14.2, 48.0,
                "WARNING",
                "Uyarı: Batarya seviyesi düşük. En yakın swap istasyonu önerilir.",
                "En Yakın Swap İstasyonu: Ataşehir Swap Station | Mesafe: 2.4 km | ETA: 6 dk",
                "#F9A825",
                "#FFF8E1");
    }

    private void setFaultMode() {
        updateDashboard(8, 42.1, 20.5, 58.0,
                "FAULT",
                "Kritik Uyarı: Batarya güvenlik sınırını aştı! Swap işlemi gerekli.",
                "Acil Yönlendirme: Ataşehir Swap Station | Mesafe: 2.4 km | Durum: Uygun",
                "#C62828",
                "#FFEBEE");
    }

    private void setSwapCompleteMode() {
        updateDashboard(95, 401.0, 35.0, 29.0,
                "NORMAL",
                "Batarya değişimi tamamlandı. Sistem normal duruma döndü.",
                "Swap İşlemi Tamamlandı: Araç kullanıma hazır.",
                "#2E7D32",
                "#E8F5E9");
    }
    private void updateDashboard(
            int soc,
            double voltage,
            double current,
            double temperature,
            String status,
            String warning,
            String stationInfo,
            String statusColor,
            String warningBackground
            ){
        txtStatus.setText(status);
        txtStatus.setBackgroundColor(Color.parseColor(statusColor));

        progressSoc.setProgress(soc);

        txtSoc.setText("SOC: " + soc + "%");
        txtVoltage.setText("Voltaj: " + voltage + " V");
        txtCurrent.setText("Akım: " + current + " A");
        txtTemperature.setText("Sıcaklık: " + temperature + " °C");

        txtWarning.setText(warning);
        txtWarning.setTextColor(Color.parseColor(statusColor));
        txtWarning.setBackgroundColor(Color.parseColor(warningBackground));

        txtStation.setText(stationInfo);
    }
}
