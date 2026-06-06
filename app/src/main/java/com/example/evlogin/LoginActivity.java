package com.example.evlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
    private EditText emailInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ui.prepareWindow(this);

        FrameLayout root = Ui.shell(this);
        LinearLayout card = Ui.glassCard(this, 448);

        FrameLayout vehicleFrame = new FrameLayout(this);
        vehicleFrame.setBackground(Ui.glass(this, 12, Ui.PANEL_SOFT, Ui.STROKE, 1));
        LinearLayout.LayoutParams vehicleParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                Ui.dp(this, 238)
        );
        vehicleFrame.setLayoutParams(vehicleParams);
        vehicleFrame.addView(new VehiclePanelView(this), Ui.match());
        card.addView(vehicleFrame);

        emailInput = Ui.input(this, "E-posta", R.drawable.ic_mail, false);
        passwordInput = Ui.input(this, "Şifre", R.drawable.ic_lock, true);
        passwordInput.setImeOptions(android.view.inputmethod.EditorInfo.IME_ACTION_DONE);
        card.addView(emailInput);
        card.addView(passwordInput);

        TextView loginButton = Ui.primaryButton(this, "GİRİŞ YAP");
        loginButton.setOnClickListener(view -> attemptLogin());
        card.addView(loginButton);

        LinearLayout links = new LinearLayout(this);
        links.setOrientation(LinearLayout.HORIZONTAL);
        links.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams linksParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linksParams.topMargin = Ui.dp(this, 10);
        links.setLayoutParams(linksParams);

        TextView forgot = Ui.link(this, "Şifremi Unuttum?", false);
        TextView register = Ui.link(this, "Hesabınız yok mu?  Kayıt Ol", true);
        links.addView(forgot, weighted());
        links.addView(register, weighted());
        card.addView(links);

        forgot.setOnClickListener(view -> Ui.open(this, ForgotPasswordActivity.class));
        register.setOnClickListener(view -> Ui.open(this, RegisterActivity.class));

        root.addView(card);
        setContentView(root);
    }

    private LinearLayout.LayoutParams weighted() {
        return new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
    }

    private void attemptLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Lütfen e-posta ve şifre girin.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!SessionStore.canLogin(this, email, password)) {
            Toast.makeText(this, "Bilgiler hatalı. Demo: " + SessionStore.defaultEmail() + " / " + SessionStore.defaultPassword(), Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("email", email);
        Ui.open(this, intent);
    }
}
