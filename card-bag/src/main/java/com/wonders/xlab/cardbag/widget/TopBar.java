package com.wonders.xlab.cardbag.widget;

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
import android.widget.Toast;

import com.wonders.xlab.cardbag.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by hua on
 */
public class TopBar extends RelativeLayout {
    private Context mContext;
    private final float DIVIDER_HEIGHT_DEFAULT = 0.8f;
    private final float TITLE_SIZE_DEFAULT = 20;//sp
    private final int RESOURCE_ID_NONE = -1;
    private final float MENU_SIZE_DEFAULT = 24;//dp
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
    private boolean mShowDivider;

    private TextView mTitleView;
    private View mLeftMenuView;
    private View mRightMenuView;
    private View mDividerView;

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

    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        initAttributes(attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        setupDividerView();
        setupMenuView();
        setupTitleView();
        if (getBackground() == null) {
            setBackgroundColor(getResources().getColor(android.R.color.white));
        }
    }

    private void setupDividerView() {
        if (mShowDivider) {
            if (mDividerView == null) {
                mDividerView = new View(mContext);
                mDividerView.setId(R.id.divider);
                mDividerView.setBackgroundColor(getResources().getColor(R.color.cbDivider));
                LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(mContext, DIVIDER_HEIGHT_DEFAULT));
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                mDividerView.setLayoutParams(layoutParams);
                addView(mDividerView);
            }
        }
    }

    private void initAttributes(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.TopBar, defStyleAttr, 0);
        mTitleText = array.getString(R.styleable.TopBar_titleText);
        mTitleGravity = array.getInt(R.styleable.TopBar_titleGravity, GRAVITY_TITLE_CENTER);
        mTitleSizeInPx = array.getDimensionPixelSize(R.styleable.TopBar_titleSize, sp2px(mContext, TITLE_SIZE_DEFAULT));
        mTitleColor = array.getColor(R.styleable.TopBar_titleColor, getResources().getColor(android.R.color.black));
        mRightMenuIconResId = array.getResourceId(R.styleable.TopBar_rightMenuIcon, RESOURCE_ID_NONE);
        mRightMenuText = array.getString(R.styleable.TopBar_rightMenuText);
        mLeftMenuIconResId = array.getResourceId(R.styleable.TopBar_leftMenuIcon, RESOURCE_ID_NONE);
        mLeftMenuText = array.getString(R.styleable.TopBar_leftMenuText);
        mMenuSizeInPx = array.getDimensionPixelSize(R.styleable.TopBar_menuSize, dp2px(mContext, MENU_SIZE_DEFAULT));
        mShowDivider = array.getBoolean(R.styleable.TopBar_showDivider, true);
        array.recycle();

        mMenuHorizontalPaddingInPx = dp2px(mContext, MENU_HORIZONTAL_PADDING);
    }

    private void setupMenuView() {
        if (!TextUtils.isEmpty(mLeftMenuText) || RESOURCE_ID_NONE != mLeftMenuIconResId) {
            int w = mMenuSizeInPx + mMenuHorizontalPaddingInPx * 2;
            if (mLeftMenuView == null) {
                if (RESOURCE_ID_NONE != mLeftMenuIconResId) {
                    mLeftMenuView = new ImageView(mContext);
                    ((ImageView) mLeftMenuView).setImageResource(mLeftMenuIconResId);
                } else {
                    w = LayoutParams.WRAP_CONTENT;

                    mLeftMenuView = new TextView(mContext);
                    TextView textView = (TextView) mLeftMenuView;
                    textView.setText(mLeftMenuText);
                    textView.setTextColor(getTextColor());
                    textView.setGravity(Gravity.CENTER);
                }
                mLeftMenuView.setId(R.id.left_menu);
                mLeftMenuView.setPadding(mMenuHorizontalPaddingInPx, 0, mMenuHorizontalPaddingInPx, 0);
                mLeftMenuView.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (!TextUtils.isEmpty(mLeftMenuText)) {
                            Toast toast = Toast.makeText(mContext, mLeftMenuText, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.LEFT | Gravity.TOP,50, dp2px(mContext,56));
                            toast.show();
                        }
                        return true;
                    }
                });
            }
            LayoutParams layoutParams = new LayoutParams(w, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            if (mShowDivider) {
                layoutParams.addRule(RelativeLayout.ABOVE, mDividerView.getId());
            }
            mLeftMenuView.setLayoutParams(layoutParams);
            if (mLeftMenuView != null && mLeftMenuView.getParent() != null) {
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
                    mRightMenuView = new ImageView(mContext);
                    ((ImageView) mRightMenuView).setImageResource(mRightMenuIconResId);
                } else {
                    w = LayoutParams.WRAP_CONTENT;
                    mRightMenuView = new TextView(mContext);
                    TextView textView = (TextView) mRightMenuView;
                    textView.setText(mRightMenuText);
                    textView.setTextColor(getTextColor());
                    textView.setGravity(Gravity.CENTER);
                }
                mRightMenuView.setPadding(mMenuHorizontalPaddingInPx, 0, mMenuHorizontalPaddingInPx, 0);
                mRightMenuView.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if (!TextUtils.isEmpty(mRightMenuText)) {
                            Toast toast = Toast.makeText(mContext, mRightMenuText, Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.RIGHT | Gravity.TOP,50, dp2px(mContext,56));
                            toast.show();
                        }
                        return true;
                    }
                });
            }
            LayoutParams layoutParams = new LayoutParams(w, LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            if (mShowDivider) {
                layoutParams.addRule(RelativeLayout.ABOVE, mDividerView.getId());
            }
            mRightMenuView.setLayoutParams(layoutParams);
            if (mRightMenuView != null && mRightMenuView.getParent() != null) {
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
            return getResources().getColor(R.color.textBlack);
        } else {
            return mTitleColor;
        }
    }

    private void setupTitleView() {
        if (mTitleView == null) {
            mTitleView = new TextView(mContext);
            mTitleView.setText(TextUtils.isEmpty(mTitleText) ? getResources().getString(R.string.app_name) : mTitleText);
        }
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSizeInPx);
        mTitleView.setGravity(Gravity.CENTER);
        mTitleView.setTextColor(getTextColor());

        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        if (mShowDivider) {
            layoutParams.addRule(RelativeLayout.ABOVE, mDividerView.getId());
        }

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
        if (mTitleView != null && mTitleView.getParent() != null) {
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

    public void setTitle(String title) {
        if (this.mTitleView != null && !TextUtils.isEmpty(title)) {
            this.mTitleView.setText(title);
        }
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
