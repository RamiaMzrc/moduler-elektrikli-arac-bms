package com.example;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private TextView tvError;
    private Button btnLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("com.example.modulerbatarya.PREFS", Context.MODE_PRIVATE);

        // Check if session is already active
        boolean sessionActive = sharedPreferences.getBoolean("session_status", false);
        if (sessionActive) {
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            finish();
            return;
        }

        // Bind Views
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        tvError = findViewById(R.id.tv_error);
        btnLogin = findViewById(R.id.btn_login);

        // Login Action Listener
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Initially hide error
        tvError.setVisibility(View.GONE);

        // Validate credentials
        if ("admin".equals(username) && "1453".equals(password)) {
            // Correct - save session and token
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("session_status", true);
            editor.putString("jwt_token", "JWT_TOKEN_ADMIN_1453");
            editor.apply();

            // Navigate
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Wrong credentials
            etUsername.setText("");
            etPassword.setText("");
            tvError.setVisibility(View.VISIBLE);
        }
    }
}
