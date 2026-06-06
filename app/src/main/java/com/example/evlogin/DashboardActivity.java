package com.example.evlogin;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DashboardActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ui.prepareWindow(this);

        String email = getIntent().getStringExtra("email");
        if (email == null || email.trim().isEmpty()) {
            email = SessionStore.defaultEmail();
        }
        String name = SessionStore.displayName(this, email);

        FrameLayout root = Ui.shell(this);
        LinearLayout panel = Ui.glassCard(this, 800);
        panel.setPadding(Ui.dp(this, 30), Ui.dp(this, 24), Ui.dp(this, 30), Ui.dp(this, 24));

        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);
        panel.addView(header, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        LinearLayout titleBlock = new LinearLayout(this);
        titleBlock.setOrientation(LinearLayout.VERTICAL);
        header.addView(titleBlock, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        TextView title = Ui.title(this, "Araç Kontrol Paneli", 26);
        title.setGravity(Gravity.START);
        titleBlock.addView(title);

        TextView subtitle = Ui.body(this, name + " • " + email, 14, Ui.MUTED);
        subtitle.setGravity(Gravity.START);
        titleBlock.addView(subtitle);

        TextView status = pill("SUNUCU BAĞLANTISI  ÇEVRİMİÇİ", Ui.GREEN);
        header.addView(status);

        LinearLayout rowOne = row();
        rowOne.setPadding(0, Ui.dp(this, 22), 0, 0);
        rowOne.addView(metric("Dynamic Range", "100 km", "Şarj menzili aktif", Ui.BLUE), weighted());
        rowOne.addView(metric("SOC", "%50", "Batarya dengeli", Ui.GREEN), weighted());
        rowOne.addView(metric("Şebeke Entegrasyonu", "Beklemede", "Evirici bankası hazır", Ui.GREEN), weighted());
        panel.addView(rowOne);

        LinearLayout rowTwo = row();
        rowTwo.setPadding(0, Ui.dp(this, 14), 0, 0);
        rowTwo.addView(metric("Performans Kayıtları", "Sistem Stabil", "Son 5 modül verisi normal", Ui.GREEN), weighted());
        rowTwo.addView(metric("Modüler Pil Dizisi", "12/12", "Hücre iletişimi çevrimiçi", Ui.CYAN), weighted());
        rowTwo.addView(metric("Konum Haritası", "Rota Hazır", "Şarj ağı bağlantısı aktif", Ui.BLUE), weighted());
        panel.addView(rowTwo);

        TextView batteryPanelButton = Ui.primaryButton(this, "BATARYA İZLEME PANELİ");
        batteryPanelButton.setOnClickListener(view -> Ui.open(this, BatteryMonitoringActivity.class));
        LinearLayout.LayoutParams batteryPanelParams = new LinearLayout.LayoutParams(
                Ui.dp(this, 260),
                Ui.dp(this, 54)
        );
        batteryPanelParams.gravity = Gravity.CENTER_HORIZONTAL;
        batteryPanelParams.topMargin = Ui.dp(this, 22);
        batteryPanelButton.setLayoutParams(batteryPanelParams);
        panel.addView(batteryPanelButton);

        root.addView(panel);
        setContentView(root);
    }

    private LinearLayout row() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        row.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        return row;
    }

    private LinearLayout.LayoutParams weighted() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, Ui.dp(this, 126), 1f);
        params.leftMargin = Ui.dp(this, 6);
        params.rightMargin = Ui.dp(this, 6);
        return params;
    }

    private TextView pill(String text, int color) {
        TextView view = new TextView(this);
        view.setText(text);
        view.setTextColor(color);
        view.setTextSize(13);
        view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        view.setGravity(Gravity.CENTER);
        view.setPadding(Ui.dp(this, 18), 0, Ui.dp(this, 18), 0);
        GradientDrawable background = Ui.glass(this, 22, Color.argb(165, 12, 18, 24), Color.argb(140, 255, 255, 255), 1);
        view.setBackground(background);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                Ui.dp(this, 44)
        );
        view.setLayoutParams(params);
        return view;
    }

    private LinearLayout metric(String title, String value, String note, int accent) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(Ui.dp(this, 16), Ui.dp(this, 14), Ui.dp(this, 16), Ui.dp(this, 14));
        card.setBackground(Ui.glass(this, 12, Color.argb(188, 12, 17, 22), Color.argb(105, 255, 255, 255), 1));

        TextView titleView = Ui.body(this, title, 12, Ui.MUTED);
        titleView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        titleView.setAllCaps(true);
        card.addView(titleView);

        TextView valueView = Ui.body(this, value, 22, accent);
        valueView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        valueView.setIncludeFontPadding(false);
        LinearLayout.LayoutParams valueParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        valueParams.topMargin = Ui.dp(this, 5);
        valueView.setLayoutParams(valueParams);
        card.addView(valueView);

        FrameLayout bar = new FrameLayout(this);
        GradientDrawable base = new GradientDrawable();
        base.setColor(Color.argb(70, 255, 255, 255));
        base.setCornerRadius(Ui.dp(this, 6));
        bar.setBackground(base);
        LinearLayout.LayoutParams barParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                Ui.dp(this, 8)
        );
        barParams.topMargin = Ui.dp(this, 10);
        bar.setLayoutParams(barParams);

        View fill = new View(this);
        GradientDrawable fillDrawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{Ui.BLUE, accent}
        );
        fillDrawable.setCornerRadius(Ui.dp(this, 6));
        fill.setBackground(fillDrawable);
        FrameLayout.LayoutParams fillParams = new FrameLayout.LayoutParams(
                Ui.dp(this, 132),
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        fill.setLayoutParams(fillParams);
        bar.addView(fill);
        card.addView(bar);

        TextView noteView = Ui.body(this, note, 12, Ui.MUTED);
        LinearLayout.LayoutParams noteParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        noteParams.topMargin = Ui.dp(this, 8);
        noteView.setLayoutParams(noteParams);
        card.addView(noteView);

        return card;
    }
}
