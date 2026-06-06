package com.example.evlogin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class VehiclePanelView extends View {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rect = new RectF();
    private Bitmap toggCar;

    public VehiclePanelView(Context context) {
        super(context);
        init(context);
    }

    public VehiclePanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        toggCar = BitmapFactory.decodeResource(context.getResources(), R.drawable.togg_car);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float w = getWidth();
        float h = getHeight();
        if (w <= 0 || h <= 0) {
            return;
        }

        drawPanel(canvas, w, h);
        drawHudDots(canvas, w, h);
        drawVehicle(canvas, w, h);
        drawTelemetry(canvas, w, h);
        drawLock(canvas, w, h);
    }

    private void drawPanel(Canvas canvas, float w, float h) {
        paint.setShader(new LinearGradient(0, 0, w, h,
                new int[]{Color.rgb(14, 19, 25), Color.rgb(21, 32, 39), Color.rgb(9, 13, 18)},
                null,
                Shader.TileMode.CLAMP));
        rect.set(0, 0, w, h);
        canvas.drawRoundRect(rect, Ui.dp(getContext(), 12), Ui.dp(getContext(), 12), paint);

        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Ui.dp(getContext(), 1));
        paint.setColor(Color.argb(110, 255, 255, 255));
        canvas.drawRoundRect(rect, Ui.dp(getContext(), 12), Ui.dp(getContext(), 12), paint);
        paint.setStyle(Paint.Style.FILL);
    }

    private void drawHudDots(Canvas canvas, float w, float h) {
        paint.setShader(null);
        paint.setColor(Color.argb(130, 126, 232, 209));
        float startX = w * 0.05f;
        float startY = h * 0.12f;
        float gap = Ui.dp(getContext(), 5);
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 10; col++) {
                if ((row + col) % 3 != 0) {
                    canvas.drawCircle(startX + col * gap, startY + row * gap, 1.25f, paint);
                }
            }
        }

        paint.setColor(Color.argb(80, 255, 255, 255));
        for (int i = 0; i < 7; i++) {
            canvas.drawRect(w * 0.88f, h * 0.12f + i * Ui.dp(getContext(), 5), w * 0.98f, h * 0.125f + i * Ui.dp(getContext(), 5), paint);
        }
    }

    private void drawVehicle(Canvas canvas, float w, float h) {
        paint.setShader(new RadialGradient(w * 0.55f, h * 0.58f, w * 0.38f,
                new int[]{Color.argb(130, 94, 227, 215), Color.argb(0, 94, 227, 215)},
                null,
                Shader.TileMode.CLAMP));
        canvas.drawOval(w * 0.18f, h * 0.22f, w * 0.86f, h * 0.86f, paint);
        paint.setShader(null);

        if (toggCar != null) {
            float ratio = (float) toggCar.getWidth() / (float) toggCar.getHeight();
            float maxWidth = w * 0.86f;
            float maxHeight = h * 0.78f;
            float carWidth = Math.min(maxWidth, maxHeight * ratio);
            float carHeight = carWidth / ratio;
            float left = (w - carWidth) * 0.5f;
            float top = h * 0.12f;
            rect.set(left, top, left + carWidth, top + carHeight);

            paint.setStyle(Paint.Style.FILL);
            paint.setAlpha(242);
            canvas.drawBitmap(toggCar, null, rect, paint);
            paint.setAlpha(255);
        }

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Ui.dp(getContext(), 2));
        paint.setColor(Color.argb(220, 110, 240, 232));
        canvas.drawLine(w * 0.12f, h * 0.76f, w * 0.88f, h * 0.72f, paint);
        canvas.drawLine(w * 0.16f, h * 0.36f, w * 0.28f, h * 0.25f, paint);
        canvas.drawLine(w * 0.74f, h * 0.30f, w * 0.88f, h * 0.39f, paint);

        paint.setStyle(Paint.Style.FILL);
    }

    private void drawTelemetry(Canvas canvas, float w, float h) {
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Ui.dp(getContext(), 1));
        paint.setColor(Color.argb(120, 255, 255, 255));
        rect.set(w * 0.75f, h * 0.73f, w * 0.97f, h * 0.92f);
        canvas.drawRect(rect, paint);
        paint.setColor(Color.argb(190, 96, 221, 154));
        canvas.drawCircle(w * 0.86f, h * 0.83f, Ui.dp(getContext(), 16), paint);
        paint.setColor(Color.argb(110, 255, 255, 255));
        canvas.drawLine(w * 0.78f, h * 0.78f, w * 0.94f, h * 0.78f, paint);
        canvas.drawLine(w * 0.78f, h * 0.88f, w * 0.94f, h * 0.88f, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(Ui.dp(getContext(), 8));
        paint.setColor(Color.argb(145, 238, 245, 250));
        canvas.drawText("EV-SECURE", w * 0.78f, h * 0.965f, paint);
    }

    private void drawLock(Canvas canvas, float w, float h) {
        float cx = w * 0.5f;
        float cy = h * 0.5f;
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(new RadialGradient(cx, cy, Ui.dp(getContext(), 46),
                new int[]{Color.argb(230, 145, 157, 165), Color.argb(145, 50, 58, 66)},
                null,
                Shader.TileMode.CLAMP));
        canvas.drawCircle(cx, cy, Ui.dp(getContext(), 38), paint);
        paint.setShader(null);
        paint.setColor(Color.argb(230, 238, 242, 244));
        rect.set(cx - Ui.dp(getContext(), 16), cy - Ui.dp(getContext(), 1), cx + Ui.dp(getContext(), 16), cy + Ui.dp(getContext(), 22));
        canvas.drawRoundRect(rect, Ui.dp(getContext(), 5), Ui.dp(getContext(), 5), paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Ui.dp(getContext(), 4));
        rect.set(cx - Ui.dp(getContext(), 11), cy - Ui.dp(getContext(), 21), cx + Ui.dp(getContext(), 11), cy + Ui.dp(getContext(), 7));
        canvas.drawArc(rect, 200, 140, false, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(215, 77, 89, 101));
        canvas.drawCircle(cx, cy + Ui.dp(getContext(), 9), Ui.dp(getContext(), 3.5f), paint);
        rect.set(cx - Ui.dp(getContext(), 2), cy + Ui.dp(getContext(), 9), cx + Ui.dp(getContext(), 2), cy + Ui.dp(getContext(), 17));
        canvas.drawRect(rect, paint);
    }
}
