package com.wonders.xlab.cardbag.base;

import android.support.v7.widget.RecyclerView;

import java.util.HashSet;

/**
 * Created by hua on 16/8/29.
 * {@link Bean } should override equals and hashCode
 */
public abstract class MultiSelectionRecyclerViewAdapter<Bean, VH extends RecyclerView.ViewHolder> extends BaseRecyclerViewAdapter<Bean, VH> {
    private HashSet<Bean> mSelectedItemPositions;

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
        if (isSelectionMode()) {
            if (!holder.itemView.isSelected()) {
                holder.itemView.setSelected(true);
                mSelectedItemPositions.add(getBean(position));
            } else {
                holder.itemView.setSelected(false);
                mSelectedItemPositions.remove(getBean(position));
            }
        }
        return false;
    }

    @Override
    protected boolean onItemLongClick(VH holder, int position) {
        if (null != mOnSelectionModeChangeListener && !isSelectionMode()) {
            if (null == mSelectedItemPositions) {
                mSelectedItemPositions = new HashSet<>();
            } else {
                mSelectedItemPositions.clear();
            }
            mOnSelectionModeChangeListener.onSelectModeChange(true);
            mSelectedItemPositions.add(getBean(position));

            holder.itemView.setSelected(true);
        }
        setSelectionMode(true);
        return false;
    }

    public void deleteSelectedItems() {
        if (mSelectedItemPositions != null) {
            for (Bean selectedItemBean : mSelectedItemPositions) {
                getBeanList().remove(selectedItemBean);
            }
            notifyDataSetChanged();
            mSelectedItemPositions.clear();
            mSelectedItemPositions = null;
        }
    }

    public boolean isSelectionMode() {
        return mSelectionMode;
    }

    public void setSelectionMode(boolean selectionMode) {
        mSelectionMode = selectionMode;
        if (mSelectedItemPositions != null && !selectionMode) {
            mSelectedItemPositions.clear();
        }
        notifyDataSetChanged();
    }

    public HashSet<Bean> getSelectedItemPositions() {
        return mSelectedItemPositions;
    }
}
