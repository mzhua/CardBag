package com.wonders.xlab.cardbag.ui.cardshow;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wonders.xlab.cardbag.CBagEventConstant;
import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.MVPActivity;
import com.wonders.xlab.cardbag.data.CardModel;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.ui.cardedit.CardEditActivity;
import com.wonders.xlab.cardbag.util.ImageViewUtil;
import com.wonders.xlab.cardbag.widget.RatioImageView;
import com.wonders.xlab.cardbag.widget.XToolBarLayout;
import com.wonders.xlab.cardbag.widget.vpc.ShadowTransformer;

import java.util.List;

public class CardShowActivity extends MVPActivity<CardShowContract.Presenter> implements CardShowContract.View {

    private final int REQUEST_CODE_EDIT = 12;
    private XToolBarLayout mToolBarLayout;
    private RatioImageView mIvCardImg;
    private TextView mTvCardName;
    private ViewPager mViewPager;

    private CardShowContract.Presenter mPresenter;
    private CardShowPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    /**
     * cache the selected card info
     */
    private CardEntity mSelectedCardEntity;

    /**
     * if there is no card data, then hide the menu, or show the edit menu
     */
    private boolean mShowMenu;

    @Override
    public CardShowContract.Presenter getPresenter() {
        if (null == mPresenter) {
            mPresenter = new CardShowPresenter(this, new CardModel(this));
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

        initViewPager();

        setupActionBar(mToolBarLayout.getToolbar());

        mToolBarLayout.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getPresenter().getAllCards();
        sendBroadcast(CBagEventConstant.EVENT_PAGE_CREATE_CARD_SHOW, getResources().getString(R.string.cb_title_card_show));
    }

    private void initViewPager() {
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mSelectedCardEntity = mCardAdapter.getBean(position);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                final Configuration newMetrics = getResources().getConfiguration();
                CardEntity bean = mCardAdapter.getBean(position);
                mSelectedCardEntity = bean;

                if (newMetrics.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    mToolBarLayout.setTitle(getResources().getString(R.string.cb_title_card_show));
                    if (null != bean) {
                        setupCardNameAndImg(bean.getImgUrl(), bean.getCardName());
                    }
                } else {
                    if (null != bean) {
                        mToolBarLayout.setTitle(bean.getCardName());
                    }
                }
            }
        });
    }

    @Override
    public void showCardViewPager(List<CardEntity> cardEntityList) {
        if (mCardAdapter == null) {
            mCardAdapter = new CardShowPagerAdapter();
            mViewPager.setAdapter(mCardAdapter);
            mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        }
        mCardAdapter.setData(cardEntityList);

        if (cardEntityList != null && cardEntityList.size() > mViewPager.getCurrentItem()) {
            setupCardNameAndImg(cardEntityList.get(mViewPager.getCurrentItem()).getImgUrl(), cardEntityList.get(mViewPager.getCurrentItem()).getCardName());
        }
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                mCardShadowTransformer.enableScaling(true);
            }
        });
    }

    /**
     * when the orientation is landscape, mTvCardName and mIvCardImg don't exist
     *
     * @param imgUrl
     * @param cardName
     */
    private void setupCardNameAndImg(String imgUrl, String cardName) {
        if (mTvCardName == null || mIvCardImg == null) {
            return;
        }
        ImageViewUtil.load(CardShowActivity.this, imgUrl, mIvCardImg);
        mTvCardName.setText(cardName);
    }

    @Override
    public void noCardData() {
        showLongToast(getString(R.string.cb_card_show_no_card_data_notice));
    }

    @Override
    public void showMenu(boolean show) {
        mShowMenu = show;
        supportInvalidateOptionsMenu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            getPresenter().getAllCards();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mShowMenu) {
            getMenuInflater().inflate(R.menu.card_show_activity, menu);
        }
        return mShowMenu;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mShowMenu) {
            setupMenuIcon(menu.findItem(R.id.menu_card_show_edit));
        }
        return mShowMenu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_card_show_edit) {
            Intent intent = new Intent(this, CardEditActivity.class);
            intent.putExtra("data", mSelectedCardEntity);
            startActivityForResult(intent, REQUEST_CODE_EDIT);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.clearOnPageChangeListeners();
        mCardAdapter = null;
        sendBroadcast(CBagEventConstant.EVENT_PAGE_DESTROY_CARD_SHOW, getResources().getString(R.string.cb_title_card_show));
    }
}
