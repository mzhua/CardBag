package com.wonders.xlab.cardbag.ui.cardshow;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.MVPActivity;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.ui.cardedit.CardEditActivity;
import com.wonders.xlab.cardbag.util.ImageViewUtil;
import com.wonders.xlab.cardbag.widget.RatioImageView;
import com.wonders.xlab.cardbag.widget.XToolBarLayout;
import com.wonders.xlab.cardbag.widget.vpc.ShadowTransformer;

import java.util.List;

public class CardShowActivity extends MVPActivity<CardShowContract.Presenter> implements CardShowContract.View {

    private XToolBarLayout mToolBarLayout;
    private RatioImageView mIvCardImg;
    private TextView mTvCardName;
    private ViewPager mViewPager;

    private CardShowContract.Presenter mPresenter;
    private CardShowPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    private CardEntity mSelectedCardEntity;

    private boolean mShowMenu;

    @Override
    public CardShowContract.Presenter getPresenter() {
        if (null == mPresenter) {
            mPresenter = new CardShowPresenter(this, new CardShowModel());
        }
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_card_show_activity);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mToolBarLayout = (XToolBarLayout) findViewById(R.id.xtbl);
        mIvCardImg = (RatioImageView) findViewById(R.id.iv_card);
        mTvCardName = (TextView) findViewById(R.id.tv_card_name);

        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                final Configuration newMetrics = getResources().getConfiguration();
                CardEntity bean = mCardAdapter.getBean(position);
                mSelectedCardEntity = bean;

                if (newMetrics.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    mToolBarLayout.setTitle(getResources().getString(R.string.title_card_show));
                    if (null != bean) {
                        ImageViewUtil.load(CardShowActivity.this, bean.getImgUrl(), mIvCardImg);
                        mTvCardName.setText(bean.getCardName());
                    }
                } else {
                    if (null != bean) {
                        mToolBarLayout.setTitle(bean.getCardName());
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        setupActionBar(mToolBarLayout.getToolbar());

        getPresenter().getAllCards();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mShowMenu) {
            getMenuInflater().inflate(R.menu.card_show_activity, menu);
        }
        return mShowMenu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_card_show_edit) {
            Intent intent = new Intent(this, CardEditActivity.class);
            intent.putExtra("data", mSelectedCardEntity);
            startActivityForResult(intent, 12);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showCardViewPager(List<CardEntity> cardEntityList) {
        if (mCardAdapter == null) {
            mCardAdapter = new CardShowPagerAdapter();
            mViewPager.setAdapter(mCardAdapter);
            mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        }
        mCardAdapter.setData(cardEntityList);
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                mCardShadowTransformer.enableScaling(true);
            }
        });
    }

    @Override
    public void showMenu(boolean show) {
        mShowMenu = show;
        supportInvalidateOptionsMenu();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.clearOnPageChangeListeners();
        mCardAdapter = null;
    }
}
