package com.wonders.xlab.cardbag.ui.cardmy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.wonders.xlab.cardbag.CBagEventConstant;
import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.MVPActivity;
import com.wonders.xlab.cardbag.base.adapter.BaseRecyclerViewAdapter;
import com.wonders.xlab.cardbag.base.adapter.MultiSelectionRecyclerViewAdapter;
import com.wonders.xlab.cardbag.data.CardModel;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.db.CBCardBagDB;
import com.wonders.xlab.cardbag.ui.cardedit.CardEditActivity;
import com.wonders.xlab.cardbag.ui.cardsearch.CardSearchActivity;
import com.wonders.xlab.cardbag.util.DensityUtil;
import com.wonders.xlab.cardbag.widget.SideBar;
import com.wonders.xlab.cardbag.widget.XToolBarLayout;
import com.wonders.xlab.cardbag.widget.decoration.HorizontalDividerItemDecoration;
import com.wonders.xlab.cardbag.widget.rvwrapper.EmptyWrapper;

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

    private static final int MENU_MODE_LIST = 0;
    private static final int MENU_MODE_ICON = 1;
    private static final int MENU_MODE_DELETE = 2;

    @MenuMode
    private int mMenuMode = MENU_MODE_ICON;
    private EmptyWrapper mIconEmptyWrapper;
    private EmptyWrapper mListEmptyWrapper;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MENU_MODE_LIST, MENU_MODE_ICON, MENU_MODE_DELETE})
    public @interface MenuMode {

    }


    @Override
    public CardMyContract.Presenter getPresenter() {
        if (mPresenter == null) {
            mPresenter = new CardMyPresenter(new CardModel(CBCardBagDB.getInstance(this)), this);
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
        sendBroadcast(CBagEventConstant.EVENT_PAGE_CREATE_CARD_MY, getResources().getString(R.string.cb_title_card_my));
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
                if (getMenuMode() == MENU_MODE_DELETE) {
                    disableSelectionModel();
                } else {
                    finish();
                }
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
            mIconEmptyWrapper = new EmptyWrapper(mIconRVAdapter);
            mIconEmptyWrapper.setEmptyView(R.layout.cb_empty_view);

            mIconRVAdapter.setOnClickListener(new BaseRecyclerViewAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    goToEditActivity(mIconRVAdapter.getBean(position), 12);
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
        }
        mIconRecyclerView.setAdapter(mIconEmptyWrapper);
        mIconRVAdapter.setDatas(cardEntityList);
        if (mListRVAdapter == null) {
            mListRVAdapter = new CardMyListRVAdapter();
            mListEmptyWrapper = new EmptyWrapper(mListRVAdapter);
            mListEmptyWrapper.setEmptyView(R.layout.cb_empty_view);
            mListRVAdapter.setOnClickListener(new BaseRecyclerViewAdapter.OnClickListener() {
                @Override
                public void onItemClick(int position) {
                    goToEditActivity(mListRVAdapter.getBean(position), 21);
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
        }
        mListRecyclerView.setAdapter(mListEmptyWrapper);
        mListRVAdapter.setDatas(cardEntityList);
    }

    /**
     * start {@link CardEditActivity}
     * @param bean
     * @param requestCode
     */
    private void goToEditActivity(CardEntity bean, int requestCode) {
        Intent intent = new Intent(CardMyActivity.this, CardEditActivity.class);
        intent.putExtra("data", bean);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void deleteSuccess(String message) {
        showToastMessage(message);
        mPresenter.getMyCards();
    }

    @Override
    public void noCardWillBeDeleted() {
        showShortToast(getString(R.string.toast_card_my_no_card_be_deleted));
    }

    /**
     * show list or icon views
     * @param menuMode
     */
    private void switchViewMode(int menuMode) {

        if (menuMode == MENU_MODE_ICON) {
            mSideBar.setVisibility(View.INVISIBLE);
            mListRecyclerView.setVisibility(View.INVISIBLE);
            mIconRecyclerView.setVisibility(View.VISIBLE);
        } else if (menuMode == MENU_MODE_LIST) {
            mSideBar.setVisibility(View.VISIBLE);
            mIconRecyclerView.setVisibility(View.INVISIBLE);
            mListRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void disableSelectionModel() {
        setMenuMode(mIconRecyclerView.getVisibility() == View.VISIBLE ? MENU_MODE_ICON : MENU_MODE_LIST);
        mIconRVAdapter.setSelectionMode(false);
        mListRVAdapter.setSelectionMode(false);
    }

    private int getMenuMode() {
        return mMenuMode;
    }

    private void setMenuMode(@MenuMode int menuMode) {
        mMenuMode = menuMode;

        if (menuMode == MENU_MODE_DELETE) {
            mXToolBarLayout.setNavigationIcon(R.drawable.ic_clear_black_24dp);
            mIvAdd.setVisibility(View.GONE);
        } else {
            mXToolBarLayout.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            mIvAdd.setVisibility(View.VISIBLE);
        }

        supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.card_my_activity, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItemList = menu.findItem(R.id.menu_card_my_list);
        menuItemList.setVisible(getMenuMode() == MENU_MODE_ICON);
        MenuItem menuItemIcon = menu.findItem(R.id.menu_card_my_icon);
        menuItemIcon.setVisible(getMenuMode() == MENU_MODE_LIST);
        MenuItem itemDelete = menu.findItem(R.id.menu_card_my_delete);
        itemDelete.setVisible(getMenuMode() == MENU_MODE_DELETE);

        setupMenuIcon(itemDelete);
        setupMenuIcon(menuItemIcon);
        setupMenuIcon(menuItemList);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_card_my_list) {
            setMenuMode(MENU_MODE_LIST);
            switchViewMode(getMenuMode());
        } else if (item.getItemId() == R.id.menu_card_my_icon) {
            setMenuMode(MENU_MODE_ICON);
            switchViewMode(getMenuMode());
        } else if (item.getItemId() == R.id.menu_card_my_delete) {
            if (mIconRecyclerView.getVisibility() == View.VISIBLE) {
                mPresenter.deleteCards(mIconRVAdapter.getSelectedItemIdentities());
            } else if (mListRecyclerView.getVisibility() == View.VISIBLE) {
                mPresenter.deleteCards(mListRVAdapter.getSelectedItemIdentities());
            }
            disableSelectionModel();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getMenuMode() == MENU_MODE_DELETE) {
            disableSelectionModel();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIconRVAdapter = null;
        mListRVAdapter = null;
        mIconEmptyWrapper = null;
        mListEmptyWrapper = null;
        sendBroadcast(CBagEventConstant.EVENT_PAGE_DESTROY_CARD_MY, getResources().getString(R.string.cb_title_card_my));
    }

}
