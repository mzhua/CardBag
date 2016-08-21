package com.wonders.xlab.cardbag.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wonders.xlab.cardbag.R;

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

    private static final int GRAVITY_TITLE_MASK = 1;
    public static final int GRAVITY_TITLE_LEFT = GRAVITY_TITLE_MASK << 1;
    public static final int GRAVITY_TITLE_CENTER = GRAVITY_TITLE_MASK << 2;

    private String mTitleText;
    private int mTitleGravity;
    private int mTitleSizeInPx;//px
    private int mTitleColor;
    private int mRightMenuIconResId;
    private String mRightMenuText;
    private int mLeftMenuIconResId;
    private String mLeftMenuText;
    private int mMenuSizeInPx;//px

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

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({GRAVITY_TITLE_LEFT, GRAVITY_TITLE_CENTER})
    public @interface TitleGravity {
    }

    public interface OnLeftMenuClickListener {
        void onClick(View view);
    }

    public interface OnRightMenuClickListener {
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

        initView(context);
    }

    private void initView(Context context) {
        setupMenuView();
        setupTitleView();
    }

    private void initAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CBTopBar, defStyleAttr, 0);
        mTitleText = array.getString(R.styleable.CBTopBar_titleText);
        mTitleGravity = array.getInt(R.styleable.CBTopBar_titleGravity, GRAVITY_TITLE_CENTER);
        mTitleSizeInPx = array.getDimensionPixelSize(R.styleable.CBTopBar_titleSize, sp2px(context, TITLE_SIZE_DEFAULT));
        mTitleColor = array.getColor(R.styleable.CBTopBar_titleColor, getResources().getColor(android.R.color.black));
        mRightMenuIconResId = array.getResourceId(R.styleable.CBTopBar_rightMenuIcon, RESOURCE_ID_NONE);
        mRightMenuText = array.getString(R.styleable.CBTopBar_rightMenuText);
        mLeftMenuIconResId = array.getResourceId(R.styleable.CBTopBar_leftMenuIcon, RESOURCE_ID_NONE);
        mLeftMenuText = array.getString(R.styleable.CBTopBar_leftMenuText);
        mMenuSizeInPx = array.getDimensionPixelSize(R.styleable.CBTopBar_menuSize, dp2px(context, MENU_SIZE_DEFAULT));
        array.recycle();

        mMenuHorizontalPaddingInPx = dp2px(context, MENU_HORIZONTAL_PADDING);
    }

    private void setupMenuView() {
        Context context = getContext();
        if (!TextUtils.isEmpty(mLeftMenuText) || RESOURCE_ID_NONE != mLeftMenuIconResId) {
            int w = mMenuSizeInPx + mMenuHorizontalPaddingInPx * 2;
            if (mLeftMenuView == null) {
                if (RESOURCE_ID_NONE != mLeftMenuIconResId) {
                    mLeftMenuView = new ImageView(context);
                    ((ImageView) mLeftMenuView).setImageResource(mLeftMenuIconResId);
                } else {
                    w = LayoutParams.WRAP_CONTENT;

                    mLeftMenuView = new TextView(context);
                    TextView textView = (TextView) mLeftMenuView;
                    textView.setText(mLeftMenuText);
                    textView.setTextColor(getTextColor());
                    textView.setGravity(Gravity.CENTER);
                }
                mLeftMenuView.setId(R.id.left_menu);
                mLeftMenuView.setPadding(mMenuHorizontalPaddingInPx, 0, mMenuHorizontalPaddingInPx, 0);
            }
            LayoutParams layoutParams = new LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            mLeftMenuView.setLayoutParams(layoutParams);
            if (mLeftMenuView != null) {
                removeView(mLeftMenuView);
            }
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

        if (!TextUtils.isEmpty(mRightMenuText) || RESOURCE_ID_NONE != mRightMenuIconResId) {
            int w = mMenuSizeInPx + mMenuHorizontalPaddingInPx * 2;
            if (mRightMenuView == null) {
                if (RESOURCE_ID_NONE != mRightMenuIconResId) {
                    mRightMenuView = new ImageView(context);
                    ((ImageView) mRightMenuView).setImageResource(mRightMenuIconResId);
                } else {
                    w = LayoutParams.WRAP_CONTENT;
                    mRightMenuView = new TextView(context);
                    TextView textView = (TextView) mRightMenuView;
                    textView.setText(mRightMenuText);
                    textView.setTextColor(getTextColor());
                    textView.setGravity(Gravity.CENTER);
                }
                mRightMenuView.setPadding(mMenuHorizontalPaddingInPx, 0, mMenuHorizontalPaddingInPx, 0);
            }
            LayoutParams layoutParams = new LayoutParams(w, LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            mRightMenuView.setLayoutParams(layoutParams);
            if (mRightMenuView != null) {
                removeView(mRightMenuView);
            }
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

    private int getTextColor() {
        if (getBackground() == null) {
            return getResources().getColor(android.R.color.white);
        } else {
            return mTitleColor;
        }
    }

    private void setupTitleView() {
        if (mTitleView == null) {
            mTitleView = new TextView(getContext());
            mTitleView.setText(TextUtils.isEmpty(mTitleText) ? getResources().getString(R.string.app_name) : mTitleText);
        }
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSizeInPx);
        mTitleView.setTextColor(getTextColor());
        if (getBackground() == null) {
            setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

        switch (mTitleGravity) {
            case GRAVITY_TITLE_LEFT:
                if (null != mLeftMenuView) {
                    layoutParams.addRule(RelativeLayout.RIGHT_OF, mLeftMenuView.getId());
                } else {
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    mTitleView.setPadding(mMenuHorizontalPaddingInPx, 0, 0, 0);
                }
                break;
            default:
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                break;
        }
        mTitleView.setLayoutParams(layoutParams);
        if (mTitleView != null) {
            removeView(mTitleView);
        }
        addView(mTitleView);
    }

    public void setTitleGravity(@TitleGravity int titleGravity) {
        this.mTitleGravity = titleGravity;
        setupTitleView();
    }

    public void setTitleSize(int titleSize) {
        this.mTitleSizeInPx = sp2px(getContext(), titleSize);
        setupTitleView();
    }

    public void setTitleColor(@ColorInt int titleColor) {
        this.mTitleColor = titleColor;
        setupTitleView();
    }

    public void setMenuSize(int menuSize) {
        this.mMenuSizeInPx = dp2px(getContext(), menuSize);
        setupMenuView();
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
