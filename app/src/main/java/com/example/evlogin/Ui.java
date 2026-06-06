package com.example.evlogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

final class Ui {
    static final int GREEN = Color.rgb(103, 228, 142);
    static final int BLUE = Color.rgb(99, 184, 255);
    static final int CYAN = Color.rgb(103, 239, 232);
    static final int TEXT = Color.rgb(244, 247, 250);
    static final int MUTED = Color.rgb(178, 188, 198);
    static final int PANEL = Color.argb(214, 15, 19, 24);
    static final int PANEL_SOFT = Color.argb(176, 16, 22, 28);
    static final int STROKE = Color.argb(125, 255, 255, 255);

    private Ui() {
    }

    static int dp(Context context, float value) {
        return Math.round(value * context.getResources().getDisplayMetrics().density);
    }

    static void prepareWindow(Activity activity) {
        Window window = activity.getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.rgb(8, 11, 14));
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.getAttributes().layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        View decor = window.getDecorView();
        decor.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }

    static FrameLayout shell(Activity activity) {
        FrameLayout root = new FrameLayout(activity);
        root.setBackgroundColor(Color.rgb(10, 13, 16));

        ImageView background = new ImageView(activity);
        background.setImageResource(R.drawable.ev_login_background);
        background.setScaleType(ImageView.ScaleType.CENTER_CROP);
        root.addView(background, match());

        View shade = new View(activity);
        shade.setBackground(new ColorDrawable(Color.argb(86, 0, 0, 0)));
        root.addView(shade, match());

        return root;
    }

    static FrameLayout.LayoutParams match() {
        return new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
    }

    static LinearLayout glassCard(Context context, int maxWidthDp) {
        LinearLayout card = new LinearLayout(context);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(context, 28), dp(context, 22), dp(context, 28), dp(context, 24));
        card.setBackground(glass(context, 24, PANEL, STROKE, 1.1f));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            card.setElevation(dp(context, 12));
        }

        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        int width = Math.min(dp(context, maxWidthDp), screenWidth - dp(context, 52));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                width,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER;
        card.setLayoutParams(params);
        return card;
    }

    static GradientDrawable glass(Context context, float radiusDp, int fill, int stroke, float strokeDp) {
        GradientDrawable drawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{Color.argb(218, 35, 41, 48), fill, Color.argb(218, 12, 15, 19)}
        );
        drawable.setCornerRadius(dp(context, radiusDp));
        drawable.setStroke(Math.max(1, dp(context, strokeDp)), stroke);
        return drawable;
    }

    static GradientDrawable inputBackground(Context context) {
        GradientDrawable drawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{Color.argb(200, 14, 18, 23), Color.argb(185, 20, 27, 34)}
        );
        drawable.setCornerRadius(dp(context, 10));
        drawable.setStroke(dp(context, 1), Color.argb(110, 255, 255, 255));
        return drawable;
    }

    static TextView title(Context context, String text, float sizeSp) {
        TextView view = new TextView(context);
        view.setText(text);
        view.setTextColor(TEXT);
        view.setTextSize(sizeSp);
        view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        view.setGravity(Gravity.CENTER);
        view.setIncludeFontPadding(false);
        return view;
    }

    static TextView body(Context context, String text, float sizeSp, int color) {
        TextView view = new TextView(context);
        view.setText(text);
        view.setTextColor(color);
        view.setTextSize(sizeSp);
        view.setIncludeFontPadding(true);
        return view;
    }

    static EditText input(Context context, String hint, int iconRes, boolean password) {
        EditText editText = new EditText(context);
        editText.setSingleLine(true);
        editText.setHint(hint);
        editText.setHintTextColor(Color.argb(160, 235, 241, 247));
        editText.setTextColor(TEXT);
        editText.setTextSize(18);
        editText.setBackground(inputBackground(context));
        editText.setPadding(dp(context, 16), 0, dp(context, 16), 0);
        editText.setSelectAllOnFocus(false);
        editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        editText.setInputType(password
                ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        Drawable left = tinted(context, iconRes, Color.argb(210, 96, 221, 154));
        Drawable right = password ? tinted(context, R.drawable.ic_visibility_off, Color.argb(185, 225, 232, 239)) : null;
        editText.setCompoundDrawablePadding(dp(context, 14));
        editText.setCompoundDrawables(left, null, right, null);

        if (password) {
            installPasswordToggle(context, editText);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(context, 58)
        );
        params.topMargin = dp(context, 14);
        editText.setLayoutParams(params);
        return editText;
    }

    private static void installPasswordToggle(Context context, EditText editText) {
        final boolean[] visible = {false};
        editText.setOnTouchListener((view, event) -> {
            if (event.getAction() != MotionEvent.ACTION_UP) {
                return false;
            }
            Drawable right = editText.getCompoundDrawables()[2];
            if (right == null) {
                return false;
            }
            int touchStart = editText.getWidth() - editText.getPaddingRight() - right.getBounds().width() - dp(context, 18);
            if (event.getX() < touchStart) {
                return false;
            }

            visible[0] = !visible[0];
            editText.setTransformationMethod(visible[0]
                    ? HideReturnsTransformationMethod.getInstance()
                    : PasswordTransformationMethod.getInstance());
            Drawable icon = tinted(context,
                    visible[0] ? R.drawable.ic_visibility : R.drawable.ic_visibility_off,
                    Color.argb(185, 225, 232, 239));
            editText.setCompoundDrawables(editText.getCompoundDrawables()[0], null, icon, null);
            editText.setSelection(editText.getText().length());
            return true;
        });
    }

    static TextView primaryButton(Context context, String text) {
        TextView button = new TextView(context);
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setTextSize(17);
        button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        button.setGravity(Gravity.CENTER);
        button.setAllCaps(false);
        button.setClickable(true);
        button.setFocusable(true);
        Drawable arrow = tinted(context, R.drawable.ic_arrow_right, Color.WHITE);
        button.setCompoundDrawablePadding(dp(context, 10));
        button.setCompoundDrawables(null, null, arrow, null);
        button.setBackground(ripple(context, gradientButton(context)));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(context, 58)
        );
        params.topMargin = dp(context, 18);
        button.setLayoutParams(params);
        return button;
    }

    static TextView link(Context context, String text, boolean bold) {
        TextView view = new TextView(context);
        view.setText(text);
        view.setTextColor(bold ? TEXT : Color.rgb(218, 224, 230));
        view.setTextSize(15);
        view.setGravity(Gravity.CENTER);
        view.setTypeface(Typeface.DEFAULT, bold ? Typeface.BOLD : Typeface.NORMAL);
        view.setClickable(true);
        view.setFocusable(true);
        view.setPadding(dp(context, 6), dp(context, 8), dp(context, 6), dp(context, 8));
        return view;
    }

    static View spacer(Context context, int heightDp) {
        View view = new View(context);
        view.setLayoutParams(new LinearLayout.LayoutParams(1, dp(context, heightDp)));
        return view;
    }

    static Drawable tinted(Context context, int resId, int color) {
        Drawable drawable = context.getDrawable(resId).mutate();
        drawable.setBounds(0, 0, dp(context, 24), dp(context, 24));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.setTint(color);
        }
        return drawable;
    }

    static Drawable ripple(Context context, Drawable content) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return content;
        }
        return new RippleDrawable(
                android.content.res.ColorStateList.valueOf(Color.argb(80, 255, 255, 255)),
                content,
                null
        );
    }

    static GradientDrawable gradientButton(Context context) {
        GradientDrawable drawable = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{Color.rgb(34, 125, 81), Color.rgb(65, 188, 105)}
        );
        drawable.setCornerRadius(dp(context, 10));
        drawable.setStroke(dp(context, 1), Color.argb(170, 118, 245, 161));
        return drawable;
    }

    static void open(Activity from, Class<?> destination) {
        from.startActivity(new Intent(from, destination));
        from.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    static void open(Activity from, Intent intent) {
        from.startActivity(intent);
        from.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
