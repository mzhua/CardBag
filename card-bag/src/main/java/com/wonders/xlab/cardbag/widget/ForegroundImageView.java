package com.wonders.xlab.cardbag.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.util.DensityUtil;

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

    private int mForegroundCornerRadius;

    private TextPaint textPaint;
    private Paint mForegroundPaint;

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
        mForegroundTextColor = array.getColor(R.styleable.ForegroundImageView_fivForegroundTextColor, getResources().getColor(android.R.color.white));
        mShowForeground = array.getBoolean(R.styleable.ForegroundImageView_fivShowForeground, false);
        mForegroundTextSize = array.getDimensionPixelSize(R.styleable.ForegroundImageView_fivForegroundTextSize, DensityUtil.sp2px(getContext(), 14));
        mForegroundCornerRadius = array.getDimensionPixelSize(R.styleable.RatioView_rvForegroundCornerRadius, DensityUtil.dp2px(context, 8));
        mForegroundText = array.getString(R.styleable.ForegroundImageView_fivForegroundText);
        array.recycle();

        if (!TextUtils.isEmpty(mForegroundText)) {
            mShowForeground = true;

            textPaint = new TextPaint();
            textPaint.setAntiAlias(true);
            textPaint.setTextSize(mForegroundTextSize);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setStyle(Paint.Style.FILL);
            textPaint.setColor(mForegroundTextColor);
        }
        setupForegroundPaint();
    }

    private void setupForegroundPaint() {
        if (mShowForeground && mForegroundPaint == null) {
            mForegroundPaint = new Paint();
            mForegroundPaint.setAntiAlias(true);
            mForegroundPaint.setStyle(Paint.Style.FILL);
            mForegroundPaint.setColor(mForegroundColor);
        }
    }

    private RectF mRectF;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShowForeground) {
            if (null == mRectF || mRectF.isEmpty()) {
                mRectF = new RectF(0, 0, getWidth(), getHeight());
            }
            canvas.drawRoundRect(mRectF, mForegroundCornerRadius, mForegroundCornerRadius, mForegroundPaint);
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

}
