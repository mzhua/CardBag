package com.wonders.xlab.cardbag.ui.cardmy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.base.BaseRecyclerViewAdapter;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.util.ImageViewUtil;

/**
 * Created by hua on 16/8/22.
 */

public class CardMyRVAdapter extends BaseRecyclerViewAdapter<CardEntity,CardMyRVAdapter.ItemViewHolder> {

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cb_my_card_rv_item, null);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindRecyclerViewHolder(ItemViewHolder holder, int position) {
        CardEntity cardEntity = getBean(position);
        holder.mTextView.setText(cardEntity.getCardName());
        ImageViewUtil.load(holder.itemView.getContext(),cardEntity.getImgUrl(),holder.mImageView);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView;

        ItemViewHolder(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.iv_card);
            mTextView = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
