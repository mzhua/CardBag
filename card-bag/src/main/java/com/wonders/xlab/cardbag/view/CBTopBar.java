package com.wonders.xlab.cardbag.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.util.LogUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by hua on
 */
public class CBTopBar extends RelativeLayout {
    private final float TITLE_SIZE_DEFAULT = 20;//sp
    private final int RESOURCE_ID_NONE = -1;
    private final float MENU_SIZE_DEFAULT = 16;//dp
    private final float MENU_HORIZONTAL_PADDING = 10;//dp

    private final int GRAVITY_TITLE_MASK = 1;
    private final int GRAVITY_TITLE_LEFT = GRAVITY_TITLE_MASK << 1;
    private final int GRAVITY_TITLE_CENTER = GRAVITY_TITLE_MASK << 2;

    private String titleText;
    private int titleGravity;
    private int titleSizeInPx;//px
    private int titleColor;
    private int rightMenuIconResId;
    private String rightMenuText;
    private int leftMenuIconResId;
    private String leftMenuText;
    private int menuSizeInPx;//px

    private TextView mTitleView;
    private View mLeftMenuView;
    private View mRightMenuView;

    private int mMenuHorizontalPaddingInPx;

    private OnLeftMenuClickListener mOnLeftMenuClickListener;
    private OnRightMenuClickListener mOnRightMenuClickListener;

    public void setOnLeftMenuClickListener(OnLeftMenuClickListener onLeftMenuClickListener) {
        mOnLeftMenuClickListener = onLeftMenuClickListener;
    }

    public void setOnRightMenuClickListener(OnRightMenuClickListener onRightMenuClickListener) {
        mOnRightMenuClickListener = onRightMenuClickListener;
    }

    @Retention(RetentionPolicy.CLASS)
    @IntDef({Gravity.LEFT, Gravity.CENTER})
    public @interface TitleGravity {
    }

    public interface OnLeftMenuClickListener{
        void onClick(View view);
    }

    public interface OnRightMenuClickListener{
        void onClick(View view);
    }

    public CBTopBar(Context context) {
        this(context, null);
    }

    public CBTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CBTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs, defStyleAttr);

        setupMenuView(context);
        setupTitleView(context);
    }

    private void initAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CBTopBar, defStyleAttr, 0);
        titleText = array.getString(R.styleable.CBTopBar_titleText);
        titleGravity = array.getInt(R.styleable.CBTopBar_titleGravity, GRAVITY_TITLE_CENTER);
        titleSizeInPx = array.getDimensionPixelSize(R.styleable.CBTopBar_titleSize, sp2px(context, TITLE_SIZE_DEFAULT));
        titleColor = array.getColor(R.styleable.CBTopBar_titleColor, getResources().getColor(android.R.color.black));
        rightMenuIconResId = array.getResourceId(R.styleable.CBTopBar_rightMenuIcon, RESOURCE_ID_NONE);
        rightMenuText = array.getString(R.styleable.CBTopBar_rightMenuText);
        leftMenuIconResId = array.getResourceId(R.styleable.CBTopBar_leftMenuIcon, RESOURCE_ID_NONE);
        leftMenuText = array.getString(R.styleable.CBTopBar_leftMenuText);
        menuSizeInPx = array.getDimensionPixelSize(R.styleable.CBTopBar_menuSize, dp2px(context, MENU_SIZE_DEFAULT));
        array.recycle();

        mMenuHorizontalPaddingInPx = dp2px(context, MENU_HORIZONTAL_PADDING);
    }

    private void setupMenuView(Context context) {
        if (!TextUtils.isEmpty(leftMenuText) || RESOURCE_ID_NONE != leftMenuIconResId) {
            if (mLeftMenuView == null) {
                if (!TextUtils.isEmpty(leftMenuText)) {
                    mLeftMenuView = new TextView(context);
                    ((TextView)mLeftMenuView).setText(leftMenuText);
                } else {
                    mLeftMenuView = new ImageView(context);
                    ((ImageView)mLeftMenuView).setImageResource(leftMenuIconResId);
                }
            }
            mLeftMenuView.setId(R.id.left_menu);
            mLeftMenuView.setPadding(mMenuHorizontalPaddingInPx,0,mMenuHorizontalPaddingInPx,0);
            LayoutParams layoutParams = new LayoutParams(menuSizeInPx + mMenuHorizontalPaddingInPx * 2, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            mLeftMenuView.setLayoutParams(layoutParams);
            addView(mLeftMenuView);
            mLeftMenuView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != mOnLeftMenuClickListener) {
                        mOnLeftMenuClickListener.onClick(view);
                    }
                }
            });
        }

        if (!TextUtils.isEmpty(rightMenuText) || RESOURCE_ID_NONE != rightMenuIconResId) {
            if (mRightMenuView == null) {
                if (!TextUtils.isEmpty(rightMenuText)) {
                    mRightMenuView = new TextView(context);
                    ((TextView)mRightMenuView).setText(rightMenuText);
                } else {
                    mRightMenuView = new ImageView(context);
                    ((ImageView)mRightMenuView).setImageResource(rightMenuIconResId);
                }
            }
            mRightMenuView.setPadding(mMenuHorizontalPaddingInPx,0, mMenuHorizontalPaddingInPx,0);
            LayoutParams layoutParams = new LayoutParams(menuSizeInPx + mMenuHorizontalPaddingInPx * 2, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            mRightMenuView.setLayoutParams(layoutParams);
            addView(mRightMenuView);
            mRightMenuView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != mOnRightMenuClickListener) {
                        mOnRightMenuClickListener.onClick(view);
                    }
                }
            });
        }
    }

    private void setupTitleView(Context context) {
        if (mTitleView == null) {
            mTitleView = new TextView(context);
        }
        mTitleView.setText(TextUtils.isEmpty(titleText) ? getResources().getString(R.string.app_name) : titleText);
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSizeInPx);
        mTitleView.setTextColor(titleColor);
        if (getBackground() == null) {
            setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mTitleView.setTextColor(getResources().getColor(android.R.color.white));
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

        switch (titleGravity) {
            case GRAVITY_TITLE_LEFT:
                if (null != mLeftMenuView) {
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, mLeftMenuView.getId());
                } else {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                }
                break;
            default:
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                break;
        }
        mTitleView.setLayoutParams(layoutParams);

        addView(mTitleView);
    }

    private int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    private int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
