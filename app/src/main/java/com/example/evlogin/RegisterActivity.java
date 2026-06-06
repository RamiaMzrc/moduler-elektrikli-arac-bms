package com.example.evlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ui.prepareWindow(this);

        FrameLayout root = Ui.shell(this);
        LinearLayout card = Ui.glassCard(this, 420);

        TextView title = Ui.title(this, "Yeni Hesap", 25);
        card.addView(title);

        TextView subtitle = Ui.body(this,
                "EV modül ağına güvenli erişim için kullanıcı profilini oluştur.",
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

        nameInput = Ui.input(this, "Ad Soyad", R.drawable.ic_person, false);
        nameInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        emailInput = Ui.input(this, "E-posta", R.drawable.ic_mail, false);
        passwordInput = Ui.input(this, "Şifre", R.drawable.ic_lock, true);
        card.addView(nameInput);
        card.addView(emailInput);
        card.addView(passwordInput);

        TextView registerButton = Ui.primaryButton(this, "KAYIT OL");
        registerButton.setOnClickListener(view -> register());
        card.addView(registerButton);

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

    private void register() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            Toast.makeText(this, "Geçerli bir e-posta girin.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Şifre en az 6 karakter olmalı.", Toast.LENGTH_SHORT).show();
            return;
        }

        SessionStore.saveUser(this, name, email, password);
        Toast.makeText(this, "Kayıt tamamlandı.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("email", email);
        Ui.open(this, intent);
        finish();
    }
}
