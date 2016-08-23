package com.wonders.xlab.cardbag.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.wonders.xlab.cardbag.R;

/**
 * Created by hua on 16/4/13.
 * 在ImageView上层显示遮罩,并且可以设置提示文字
 */
public class ForegroundImageView extends ImageView {
    private int mForegroundColor;
    private float mForegroundTextSize;
    private int mForegroundTextColor;
    private String mForegroundText;
    private boolean mShowForeground;

    public ForegroundImageView(Context context) {
        this(context, null);
    }

    public ForegroundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ForegroundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ForegroundImageView);
        mForegroundColor = array.getColor(R.styleable.ForegroundImageView_fivForegroundColor, 0xa9eceaea);
        mForegroundTextColor = array.getColor(R.styleable.ForegroundImageView_fivForegroundTextColor, getResources().getColor(android.R.color.holo_red_dark));
        mShowForeground = array.getBoolean(R.styleable.ForegroundImageView_fivShowForeground, false);
        mForegroundTextSize = array.getDimensionPixelSize(R.styleable.ForegroundImageView_fivForegroundTextSize, sp2px(getContext(), 10));
        mForegroundText = array.getString(R.styleable.ForegroundImageView_fivForegroundText);
        array.recycle();

        if (!TextUtils.isEmpty(mForegroundText)) {
            textPaint = new TextPaint();
            textPaint.setAntiAlias(true);
            textPaint.setTextSize(mForegroundTextSize);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setColor(mForegroundTextColor);
        }
    }

    private TextPaint textPaint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShowForeground) {
            canvas.drawColor(mForegroundColor);
            if (!TextUtils.isEmpty(mForegroundText)) {
                int xPos = (canvas.getWidth() / 2);
                int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() +
                        textPaint.ascent()) / 2));
                canvas.drawText(mForegroundText, xPos, yPos, textPaint);
            }

        }
    }

    private float getTextHeight(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    private float getTextWidth(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.width();
    }

    public void setShowForeground(boolean showForeground) {
        mShowForeground = showForeground;
        invalidate();
    }

    public void setForegroundColor(int foregroundColor) {
        mForegroundColor = foregroundColor;
        invalidate();
    }

    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }
}
