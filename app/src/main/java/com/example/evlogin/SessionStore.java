package com.example.evlogin;

import android.content.Context;
import android.content.SharedPreferences;

final class SessionStore {
    private static final String PREFS = "ev_auth";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String DEFAULT_EMAIL = "demo@moduler.ev";
    private static final String DEFAULT_PASSWORD = "123456";

    private SessionStore() {
    }

    static void saveUser(Context context, String name, String email, String password) {
        prefs(context).edit()
                .putString(KEY_NAME, name)
                .putString(KEY_EMAIL, email)
                .putString(KEY_PASSWORD, password)
                .apply();
    }

    static boolean canLogin(Context context, String email, String password) {
        if (DEFAULT_EMAIL.equalsIgnoreCase(email) && DEFAULT_PASSWORD.equals(password)) {
            return true;
        }

        String storedEmail = prefs(context).getString(KEY_EMAIL, "");
        String storedPassword = prefs(context).getString(KEY_PASSWORD, "");
        return storedEmail.equalsIgnoreCase(email) && storedPassword.equals(password);
    }

    static boolean updatePassword(Context context, String email, String newPassword) {
        String storedEmail = prefs(context).getString(KEY_EMAIL, "");
        if (storedEmail.equalsIgnoreCase(email)) {
            prefs(context).edit().putString(KEY_PASSWORD, newPassword).apply();
            return true;
        }

        if (DEFAULT_EMAIL.equalsIgnoreCase(email)) {
            saveUser(context, "Demo Kullanıcı", DEFAULT_EMAIL, newPassword);
            return true;
        }

        return false;
    }

    static String displayName(Context context, String email) {
        String storedEmail = prefs(context).getString(KEY_EMAIL, "");
        if (storedEmail.equalsIgnoreCase(email)) {
            String name = prefs(context).getString(KEY_NAME, "");
            return name.isEmpty() ? "Kullanıcı" : name;
        }
        if (DEFAULT_EMAIL.equalsIgnoreCase(email)) {
            return "Demo Kullanıcı";
        }
        return "Kullanıcı";
    }

    static String defaultEmail() {
        return DEFAULT_EMAIL;
    }

    static String defaultPassword() {
        return DEFAULT_PASSWORD;
    }

    private static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }
}
