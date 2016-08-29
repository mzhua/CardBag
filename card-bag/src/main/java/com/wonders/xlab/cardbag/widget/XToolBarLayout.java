package com.wonders.xlab.cardbag.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.util.DensityUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by hua on 16/8/26.
 */
public class XToolBarLayout extends LinearLayout {
    private Context mContext;
    private final float DIVIDER_HEIGHT_DEFAULT = 0.8f;

    private static final int GRAVITY_TITLE_MASK = 1;
    public static final int GRAVITY_TITLE_LEFT = GRAVITY_TITLE_MASK << 1;
    public static final int GRAVITY_TITLE_CENTER = GRAVITY_TITLE_MASK << 2;

    private Toolbar mToolbar;
    private TextView mTitleView;
    private View mDividerView;

    private boolean mShowDivider;
    private int mTitleGravity;
    private int mTitleColor;
    private String mTitleText;
    private int mBackgroundColor;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({GRAVITY_TITLE_LEFT, GRAVITY_TITLE_CENTER})
    public @interface TitleGravity {
    }

    public XToolBarLayout(Context context) {
        this(context, null);
    }

    public XToolBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XToolBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public XToolBarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.setOrientation(VERTICAL);
        mContext = context;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.XToolBarLayout, defStyleAttr, defStyleRes);
        mTitleText = array.getString(R.styleable.XToolBarLayout_xtblTitleText);
        if (TextUtils.isEmpty(mTitleText)) {
            mTitleText = getResources().getString(R.string.app_name);
        }
        mTitleGravity = array.getInt(R.styleable.XToolBarLayout_xtblTitleGravity, GRAVITY_TITLE_CENTER);
        mTitleColor = array.getColor(R.styleable.XToolBarLayout_xtblTitleColor, ContextCompat.getColor(context,R.color.cbTopBarTitleColor));
        mBackgroundColor = array.getColor(R.styleable.XToolBarLayout_xtblBackgroundColor, ContextCompat.getColor(context,R.color.cbTopBarBackground));
        mShowDivider = array.getBoolean(R.styleable.XToolBarLayout_xtblShowDivider, false);
        array.recycle();

        mToolbar = (Toolbar) LayoutInflater.from(context).inflate(R.layout.cb_tool_bar, this, false);
        addView(mToolbar);
        mToolbar.setBackgroundColor(mBackgroundColor);

        setupTitleView();

        setupDividerView();
    }

    private void setupTitleView() {
        mTitleView = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mTitleView.setTextColor(mTitleColor);
        mTitleView.setText(mTitleText);
        Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) mTitleView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        switch (mTitleGravity) {
            case GRAVITY_TITLE_CENTER:
                layoutParams.gravity = Gravity.CENTER;
                break;
            case GRAVITY_TITLE_LEFT:
                layoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
                break;
        }
    }

    private void setupDividerView() {
        if (mShowDivider) {
            if (mDividerView == null) {
                mDividerView = new View(mContext);
                mDividerView.setId(R.id.divider);
                mDividerView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.cbDivider));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dp2px(mContext, DIVIDER_HEIGHT_DEFAULT));
                mDividerView.setLayoutParams(layoutParams);
                addView(mDividerView);
            }
        } else {
            if (mDividerView != null) {
                removeView(mDividerView);
            }
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setTitleGravity(int titleGravity) {
        mTitleGravity = titleGravity;
    }

    public void setTitleColor(int titleColor) {
        mTitleColor = titleColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    public void setNavigationIcon(@DrawableRes int drawableResId,int colorFilter) {
        Drawable stateButtonDrawable = ContextCompat.getDrawable(mContext, drawableResId).mutate();
        stateButtonDrawable.setColorFilter(colorFilter, PorterDuff.Mode.MULTIPLY);
        getToolbar().setNavigationIcon(stateButtonDrawable);
    }

    public void hideNavigationIcon() {
        getToolbar().setNavigationIcon(null);
    }
}
