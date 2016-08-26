package com.wonders.xlab.cardbag.ui.cardsearch;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.BaseRecyclerViewAdapter;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.util.ImageViewUtil;

import java.util.List;

/**
 * Created by hua on 16/8/22.
 */

public class CardSearchRVAdapter extends BaseRecyclerViewAdapter<CardEntity, RecyclerView.ViewHolder> {

    private final int ITEM_TYPE_CARD = 1;
    private final int ITEM_TYPE_NOT_FOUND = 0;

    @Override
    public void setDatas(List<CardEntity> mBeanList) {
        super.setDatas(mBeanList);
        appendToLast(new CardEntity());//手动添加卡片
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM_TYPE_NOT_FOUND:
                viewHolder = new NotFoundViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cb_card_search_rv_not_found_item, parent, false));
                break;
            case ITEM_TYPE_CARD:
                viewHolder = new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cb_my_card_icon_rv_item, parent, false));
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        CardEntity bean = getBean(position);
        return TextUtils.isEmpty(bean.getCardName()) ? ITEM_TYPE_NOT_FOUND : ITEM_TYPE_CARD;
    }

    @Override
    public void onBindRecyclerViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_TYPE_CARD) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            CardEntity cardEntity = getBean(position);
            viewHolder.mTextView.setText(cardEntity.getCardName());
            ImageViewUtil.load(holder.itemView.getContext(), cardEntity.getImgUrl(), viewHolder.mImageView);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        ItemViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_card);
            mTextView = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    private class NotFoundViewHolder extends RecyclerView.ViewHolder {

        public NotFoundViewHolder(View itemView) {
            super(itemView);
        }
    }
}
