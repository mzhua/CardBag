package com.wonders.xlab.cardbag.ui.cardmy;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.BaseRecyclerViewAdapter;
import com.wonders.xlab.cardbag.base.MVPActivity;
import com.wonders.xlab.cardbag.base.MultiSelectionRecyclerViewAdapter;
import com.wonders.xlab.cardbag.data.CardMyModel;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.ui.cardedit.CardEditActivity;
import com.wonders.xlab.cardbag.ui.cardsearch.CardSearchActivity;
import com.wonders.xlab.cardbag.util.DensityUtil;
import com.wonders.xlab.cardbag.widget.SideBar;
import com.wonders.xlab.cardbag.widget.XToolBarLayout;
import com.wonders.xlab.cardbag.widget.decoration.HorizontalDividerItemDecoration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class CardMyActivity extends MVPActivity<CardMyContract.Presenter> implements CardMyContract.View {

    private XToolBarLayout mXToolBarLayout;
    private ImageView mIvAdd;
    private RecyclerView mIconRecyclerView;
    private RecyclerView mListRecyclerView;
    private SideBar mSideBar;
    private CardMyIconRVAdapter mIconRVAdapter;
    private CardMyListRVAdapter mListRVAdapter;

    private CardMyContract.Presenter mPresenter;

    private final int MENU_MODE_LIST = 0;
    private final int MENU_MODE_ICON = 1;
    private final int MENU_MODE_DELETE = 2;

    @MenuMode
    private int mMenuMode = MENU_MODE_ICON;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MENU_MODE_LIST, MENU_MODE_ICON, MENU_MODE_DELETE})
    public @interface MenuMode {

    }

    @Override
    public CardMyContract.Presenter getPresenter() {
        if (mPresenter == null) {
            mPresenter = new CardMyPresenter(new CardMyModel(), this);
        }
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cb_my_card_activity);
        mXToolBarLayout = (XToolBarLayout) findViewById(R.id.xtbl);
        mSideBar = (SideBar) findViewById(R.id.side_bar);
        mIconRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mListRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_list);
        mIvAdd = (ImageView) findViewById(R.id.iv_add);

        setupActionBar(mXToolBarLayout.getToolbar());

        setupListener();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mIconRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        mIconRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mListRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        int decoMargin = DensityUtil.dp2px(this, 24);
        mListRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).color(getResources().getColor(R.color.cbDivider)).size(1).margin(decoMargin, decoMargin).build());
    }

    private void setupListener() {
        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CardMyActivity.this, CardSearchActivity.class));
            }
        });
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                mListRVAdapter.scrollTo(mListRecyclerView, s);
            }
        });
        mXToolBarLayout.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableSelectionModel();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().getMyCards();
    }

    @Override
    public void showMyCards(List<CardEntity> cardEntityList) {
        if (mIconRVAdapter == null) {
            mIconRVAdapter = new CardMyIconRVAdapter();
            mIconRVAdapter.setOnClickListener(new BaseRecyclerViewAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(CardMyActivity.this, CardEditActivity.class);
                    intent.putExtra("data", mIconRVAdapter.getBean(position));
                    startActivityForResult(intent, 12);
                }
            });
            mIconRVAdapter.setOnSelectionModeChangeListener(new MultiSelectionRecyclerViewAdapter.OnSelectionModeChangeListener() {
                @Override
                public void onSelectModeChange(boolean isSelectionMode) {
                    if (isSelectionMode) {
                        setMenuMode(MENU_MODE_DELETE);
                    }
                }
            });
            mIconRecyclerView.setAdapter(mIconRVAdapter);
        }
        mIconRVAdapter.setDatas(cardEntityList);
        if (mListRVAdapter == null) {
            mListRVAdapter = new CardMyListRVAdapter();
            mListRVAdapter.setOnClickListener(new BaseRecyclerViewAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(CardMyActivity.this, CardEditActivity.class);
                    intent.putExtra("data", mListRVAdapter.getBean(position));
                    startActivityForResult(intent, 21);
                }
            });
            mListRVAdapter.setOnSelectionModeChangeListener(new MultiSelectionRecyclerViewAdapter.OnSelectionModeChangeListener() {
                @Override
                public void onSelectModeChange(boolean isSelectionMode) {
                    if (isSelectionMode) {
                        setMenuMode(MENU_MODE_DELETE);
                    }
                }
            });
            mListRecyclerView.setAdapter(mListRVAdapter);
        }
        mListRVAdapter.setDatas(cardEntityList);
    }

    private void switchRecyclerView() {

        if (getMenuMode() == MENU_MODE_ICON) {
            mSideBar.setVisibility(View.INVISIBLE);
            mListRecyclerView.setVisibility(View.INVISIBLE);
            mIconRecyclerView.setVisibility(View.VISIBLE);
        } else if (getMenuMode() == MENU_MODE_LIST) {
            mSideBar.setVisibility(View.VISIBLE);
            mIconRecyclerView.setVisibility(View.INVISIBLE);
            mListRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void disableSelectionModel() {
        setMenuMode(mIconRecyclerView.getVisibility() == View.VISIBLE ? MENU_MODE_ICON : MENU_MODE_LIST);
        mXToolBarLayout.hideNavigationIcon();
        mIconRVAdapter.setSelectionMode(false);
        mListRVAdapter.setSelectionMode(false);
    }

    private int getMenuMode() {
        return mMenuMode;
    }

    private void setMenuMode(@MenuMode int menuMode) {
        mMenuMode = menuMode;

        if (menuMode == MENU_MODE_DELETE) {
            mXToolBarLayout.setNavigationIcon(R.drawable.ic_clear_black_24dp, ContextCompat.getColor(this, android.R.color.black));
        }

        supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.card_my_activity, menu);

        MenuItem menuItemCrop = menu.findItem(R.id.menu_card_my_delete);
        Drawable menuItemCropIcon = menuItemCrop.getIcon();
        if (menuItemCropIcon != null) {
            menuItemCropIcon.mutate();
            menuItemCropIcon.setColorFilter(ContextCompat.getColor(this, android.R.color.black), PorterDuff.Mode.SRC_ATOP);
            menuItemCrop.setIcon(menuItemCropIcon);
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_card_my_list).setVisible(getMenuMode() == MENU_MODE_ICON);
        menu.findItem(R.id.menu_card_my_icon).setVisible(getMenuMode() == MENU_MODE_LIST);
        menu.findItem(R.id.menu_card_my_delete).setVisible(getMenuMode() == MENU_MODE_DELETE);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_card_my_list) {
            setMenuMode(MENU_MODE_LIST);
            switchRecyclerView();
        } else if (item.getItemId() == R.id.menu_card_my_icon) {
            setMenuMode(MENU_MODE_ICON);
            switchRecyclerView();
        } else if (item.getItemId() == R.id.menu_card_my_delete) {
            if (mIconRecyclerView.getVisibility() == View.VISIBLE) {
                mPresenter.deleteCards(mIconRVAdapter.getSelectedItemPositions());
                mIconRVAdapter.deleteSelectedItems();
            }
            if (mListRecyclerView.getVisibility() == View.VISIBLE) {
                mPresenter.deleteCards(mListRVAdapter.getSelectedItemPositions());
                mListRVAdapter.deleteSelectedItems();
            }
            disableSelectionModel();
            mPresenter.getMyCards();
        }
        return super.onOptionsItemSelected(item);
    }
}
