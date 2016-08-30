package com.wonders.xlab.cardbag.ui.cardshow;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wonders.xlab.cardbag.R;
import com.wonders.xlab.cardbag.data.entity.CardEntity;
import com.wonders.xlab.cardbag.widget.RatioImageView;
import com.wonders.xlab.cardbag.widget.vpc.CardAdapter;
import com.wonders.xlab.qrscanner.BarCodeEncoder;

import java.util.ArrayList;
import java.util.List;

public class CardShowPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardEntity> mData;
    private float mBaseElevation;

    public CardShowPagerAdapter() {
        mViews = new ArrayList<>();
        mData = new ArrayList<>();
    }

    public CardEntity getBean(int position) {
        if (mData != null && mData.size() > position) {
            return mData.get(position);
        }
        return null;
    }

    public void setData(List<CardEntity> data) {
        if (mData != null) {
            mData.clear();
        } else {
            mData = new ArrayList<>();
        }
        if (data != null && data.size() > 0) {
            mData.addAll(data);
            for (int i = 0; i < data.size(); i++) {
                mViews.add(null);
            }
            notifyDataSetChanged();
        }
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews != null && mViews.size() > position ? mViews.get(position) : null;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.cb_card_show_item, container, false);
        final RatioImageView imageView = (RatioImageView) view.findViewById(R.id.iv_bar_code);
        TextView textView = (TextView) view.findViewById(R.id.tv_bar_code);

        if (!TextUtils.isEmpty(mData.get(position).getBarCode())) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    BarCodeEncoder ecc = new BarCodeEncoder(imageView.getWidth(), imageView.getHeight());
                    try {
                        imageView.setImageBitmap(ecc.barcode(mData.get(position).getBarCode()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            textView.setText(mData.get(position).getBarCode());
        }

        container.addView(view);
        CardView cardView = (CardView) view.findViewById(R.id.card_view);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

}
