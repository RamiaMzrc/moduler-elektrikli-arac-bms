package com.example.evlogin;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPasswordActivity extends Activity {
    private EditText emailInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ui.prepareWindow(this);

        FrameLayout root = Ui.shell(this);
        LinearLayout card = Ui.glassCard(this, 410);

        TextView title = Ui.title(this, "Şifre Sıfırla", 25);
        card.addView(title);

        TextView subtitle = Ui.body(this,
                "Kayıtlı e-posta ile yeni erişim anahtarını tanımla.",
                14,
                Ui.MUTED);
        subtitle.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams subtitleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        subtitleParams.topMargin = Ui.dp(this, 8);
        subtitle.setLayoutParams(subtitleParams);
        card.addView(subtitle);

        emailInput = Ui.input(this, "E-posta", R.drawable.ic_mail, false);
        passwordInput = Ui.input(this, "Yeni Şifre", R.drawable.ic_lock, true);
        card.addView(emailInput);
        card.addView(passwordInput);

        TextView updateButton = Ui.primaryButton(this, "ŞİFREYİ GÜNCELLE");
        updateButton.setOnClickListener(view -> updatePassword());
        card.addView(updateButton);

        TextView back = Ui.link(this, "Giriş ekranına dön", false);
        LinearLayout.LayoutParams backParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        backParams.topMargin = Ui.dp(this, 8);
        back.setLayoutParams(backParams);
        back.setOnClickListener(view -> finish());
        card.addView(back);

        root.addView(card);
        setContentView(root);
    }

    private void updatePassword() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Lütfen alanları doldurun.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Yeni şifre en az 6 karakter olmalı.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (SessionStore.updatePassword(this, email, password)) {
            Toast.makeText(this, "Şifre güncellendi. Yeni şifre ile giriş yapabilirsiniz.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Bu e-posta için kayıt bulunamadı.", Toast.LENGTH_SHORT).show();
        }
    }
}
