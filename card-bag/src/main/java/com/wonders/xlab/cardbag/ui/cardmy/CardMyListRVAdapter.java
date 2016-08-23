package com.wonders.xlab.cardbag.ui.cardmy;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.BaseRecyclerViewAdapter;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.util.ImageViewUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hua on 16/8/22.
 */

public class CardMyListRVAdapter extends BaseRecyclerViewAdapter<CardEntity, CardMyListRVAdapter.ItemViewHolder> {

    @Override
    public void setDatas(List<CardEntity> mBeanList) {
        Collections.sort(mBeanList, new Comparator<CardEntity>() {
            @Override
            public int compare(CardEntity lhs, CardEntity rhs) {
                return Pinyin.toPinyin(lhs.getCardName().charAt(0)).toUpperCase().compareTo(Pinyin.toPinyin(rhs.getCardName().charAt(0)).toUpperCase());
            }
        });
        super.setDatas(mBeanList);
    }

    public void scrollTo(RecyclerView recyclerView, String c) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (c.equals("#")) {
            layoutManager.scrollToPosition(0);
        } else {
            for (CardEntity entity : getBeanList()) {
                if (Pinyin.toPinyin(entity.getCardName().charAt(0)).toUpperCase().charAt(0) == c.toUpperCase().charAt(0)) {
                    if (layoutManager instanceof LinearLayoutManager) {
                        ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(getBeanList().indexOf(entity), 0);
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        ((StaggeredGridLayoutManager) layoutManager).scrollToPositionWithOffset(getBeanList().indexOf(entity), 0);
                    } else {
                        layoutManager.scrollToPosition(getBeanList().indexOf(entity));
                    }
                    break;
                }
            }
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cb_my_card_list_rv_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindRecyclerViewHolder(ItemViewHolder holder, int position) {
        CardEntity cardEntity = getBean(position);
        holder.mTextView.setText(cardEntity.getCardName());
        ImageViewUtil.load(holder.itemView.getContext(), cardEntity.getImgUrl(), holder.mImageView);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;

        ItemViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_card);
            mTextView = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
