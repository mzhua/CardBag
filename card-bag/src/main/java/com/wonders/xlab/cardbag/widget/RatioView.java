package com.wonders.xlab.cardbag.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.IntDef;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.util.DensityUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by hua on 16/8/22.
 */

public class RatioView extends View {
    private int mHorizontalWeight;
    private int mVerticalWeight;
    private int mBaseLine;
    private int mSpecifiedWidth, mSpecifiedHeight;

    private int mForegroundColor;
    private int mForegroundTextSize;
    private int mForegroundTextColor;
    private String mForegroundText;
    private boolean mShowForeground;
    private int mForegroundCornerRadius;

    private TextPaint textPaint;
    private Paint mForegroundPaint;

    public static final int WIDTH = 0;
    public static final int HEIGHT = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({WIDTH, HEIGHT})
    public @interface Base {
    }

    public RatioView(Context context) {
        super(context);
        init(context, null);
    }

    public RatioView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RatioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RatioView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioView);
            mHorizontalWeight = ta.getInt(R.styleable.RatioView_rvHorizontalWeight, 1);
            mVerticalWeight = ta.getInt(R.styleable.RatioView_rvVerticalWeight, 1);
            mBaseLine = ta.getInt(R.styleable.RatioView_rvBaseDirection, WIDTH);

            mForegroundColor = ta.getColor(R.styleable.RatioView_rvForegroundColor, 0xa9eceaea);
            mForegroundTextColor = ta.getColor(R.styleable.RatioView_rvForegroundTextColor, getResources().getColor(android.R.color.holo_red_dark));
            mShowForeground = ta.getBoolean(R.styleable.RatioView_rvShowForeground, false);
            mForegroundTextSize = ta.getDimensionPixelSize(R.styleable.RatioView_rvForegroundTextSize, DensityUtil.sp2px(context, 8));
            mForegroundCornerRadius = ta.getDimensionPixelSize(R.styleable.RatioView_rvForegroundCornerRadius, DensityUtil.dp2px(context, 8));
            mForegroundText = ta.getString(R.styleable.RatioView_rvForegroundText);

            ta.recycle();

            if (!TextUtils.isEmpty(mForegroundText)) {
                textPaint = new TextPaint();
                textPaint.setAntiAlias(true);
                textPaint.setTextSize(mForegroundTextSize);
                textPaint.setTextAlign(Paint.Align.CENTER);
                textPaint.setStyle(Paint.Style.FILL);
                textPaint.setColor(mForegroundTextColor);
            }
            setupForegroundPaint();
        }
    }

    private void setupForegroundPaint() {
        if (mShowForeground && mForegroundPaint == null) {
            mForegroundPaint = new Paint();
            textPaint.setAntiAlias(true);
            mForegroundPaint.setStyle(Paint.Style.FILL);
            mForegroundPaint.setColor(mForegroundColor);
        }
    }

    private RectF rect;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mShowForeground) {
            if (null == rect || rect.isEmpty()) {
                rect = new RectF(getLeft(), getTop(), getRight(), getBottom());
            }
            canvas.drawRoundRect(rect, mForegroundCornerRadius, mForegroundCornerRadius, mForegroundPaint);
            if (!TextUtils.isEmpty(mForegroundText)) {
                int xPos = (canvas.getWidth() / 2);
                int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
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

    public void showForeground(boolean showForeground) {
        mShowForeground = showForeground;
        setupForegroundPaint();
        invalidate();
    }

    public void setForegroundColor(int foregroundColor) {
        mForegroundColor = foregroundColor;
        showForeground(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 0, desiredHeight = 0;
        if (mBaseLine == WIDTH) {
            if (mSpecifiedWidth != 0) {
                desiredWidth = mSpecifiedWidth;
            } else {
                desiredWidth = MeasureSpec.getSize(widthMeasureSpec);
            }
            desiredHeight = (int) ((float) desiredWidth / mHorizontalWeight * mVerticalWeight);
        }

        if (mBaseLine == HEIGHT) {
            if (mSpecifiedHeight != 0) {
                desiredHeight = mSpecifiedHeight;
            } else {
                desiredHeight = MeasureSpec.getSize(heightMeasureSpec);
            }
            desiredWidth = (int) ((float) desiredHeight / mVerticalWeight * mHorizontalWeight);
        }

        setMeasuredDimension(desiredWidth, desiredHeight);
    }

    /**
     * 设定View的宽度,此时View的基准会变为{@link #WIDTH}
     */
    public void setWidth(int width) {
        mSpecifiedWidth = width;
        setBase(WIDTH);
    }

    /**
     * 设定View的高度,此时View的基准会变为{@link #HEIGHT}
     */
    public void setHeight(int height) {
        mSpecifiedHeight = height;
        setBase(HEIGHT);
    }

    /**
     * 设置按比例布局的基准线
     */
    public void setBase(@Base int base) {
        mBaseLine = base;
        requestLayout();
    }

    @Base
    /**获取当前的基准线*/
    public int getBase() {
        return mBaseLine;
    }
}
