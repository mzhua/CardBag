package com.wonders.xlab.cardbag.ui.cardmy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.MultiSelectionRecyclerViewAdapter;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.util.ImageViewUtil;

/**
 * Created by hua on 16/8/22.
 */

public class CardMyIconRVAdapter extends MultiSelectionRecyclerViewAdapter<Long, CardEntity, CardMyIconRVAdapter.ItemViewHolder> {

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cb_my_card_icon_rv_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public Long getIdentity(CardEntity cardEntity) {
        return cardEntity.getId();
    }

    @Override
    protected boolean onItemClick(ItemViewHolder holder, int position) {
        super.onItemClick(holder, position);
        return isSelectionMode();
    }

    @Override
    protected boolean onItemLongClick(ItemViewHolder holder, int position) {
        super.onItemLongClick(holder, position);
        return isSelectionMode();
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        CardEntity cardEntity = getBean(position);
        holder.mTextView.setText(cardEntity.getCardName());
        ImageViewUtil.load(holder.itemView.getContext(), cardEntity.getImgUrl(), holder.mImageView);
        holder.mCheckBox.setVisibility(isSelectionMode() ? View.VISIBLE : View.GONE);
        holder.mCheckBox.setChecked(isSelected(position));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        CheckBox mCheckBox;

        ItemViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_card);
            mTextView = (TextView) itemView.findViewById(R.id.tv_name);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.cb_card);
        }
    }
}