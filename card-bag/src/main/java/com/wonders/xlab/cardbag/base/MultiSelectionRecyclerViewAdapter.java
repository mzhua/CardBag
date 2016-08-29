package com.wonders.xlab.cardbag.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by hua on 16/8/29.
 */
public abstract class MultiSelectionRecyclerViewAdapter<Bean, VH extends RecyclerView.ViewHolder> extends BaseRecyclerViewAdapter<Bean, VH> {
    private ArrayList<Integer> mSelectedItemPositions;

    private boolean mSelectionMode;

    private OnSelectionModeChangeListener mOnSelectionModeChangeListener;

    public void setOnSelectionModeChangeListener(OnSelectionModeChangeListener onSelectionModeChangeListener) {
        mOnSelectionModeChangeListener = onSelectionModeChangeListener;
    }

    public interface OnSelectionModeChangeListener {
        void onSelectModeChange(boolean isSelectionMode);
    }

    @Override
    protected boolean onItemClick(VH holder, int position) {
        return false;
    }

    @Override
    protected boolean onItemLongClick(VH holder, int position) {
        if (null != mOnSelectionModeChangeListener && !mSelectionMode) {
            mOnSelectionModeChangeListener.onSelectModeChange(true);
            holder.itemView.setSelected(true);
        }
        if (null == mSelectedItemPositions) {
            mSelectedItemPositions = new ArrayList<>();
        }
        setSelectionMode(true);
        return true;
    }

    public boolean isSelectionMode() {
        return mSelectionMode;
    }

    public void setSelectionMode(boolean selectionMode) {
        mSelectionMode = selectionMode;
        notifyDataSetChanged();
    }
}
