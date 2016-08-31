package com.wonders.xlab.cardbag.base;

import android.support.v7.widget.RecyclerView;

import java.util.HashSet;

/**
 * Created by hua on 16/8/29.
 * {@link Bean } should override equals and hashCode
 */
public abstract class MultiSelectionRecyclerViewAdapter<ID, Bean, VH extends RecyclerView.ViewHolder> extends BaseRecyclerViewAdapter<Bean, VH> {
    private HashSet<ID> mSelectedItemIdentities;

    private boolean mSelectionMode;

    private OnSelectionModeChangeListener mOnSelectionModeChangeListener;

    public abstract ID getIdentity(Bean bean);

    public void setOnSelectionModeChangeListener(OnSelectionModeChangeListener onSelectionModeChangeListener) {
        mOnSelectionModeChangeListener = onSelectionModeChangeListener;
    }

    public interface OnSelectionModeChangeListener {
        void onSelectModeChange(boolean isSelectionMode);
    }

    @Override
    protected boolean onItemClick(VH holder, int position) {
        if (isSelectionMode()) {
            updateSelectedItemIdentities(getIdentity(getBean(position)));
            this.notifyItemChanged(position);
        }
        return false;
    }

    @Override
    protected boolean onItemLongClick(VH holder, int position) {
        if (null != mOnSelectionModeChangeListener && !isSelectionMode()) {
            if (null == mSelectedItemIdentities) {
                mSelectedItemIdentities = new HashSet<>();
            } else {
                mSelectedItemIdentities.clear();
            }

            mOnSelectionModeChangeListener.onSelectModeChange(true);

            updateSelectedItemIdentities(getIdentity(getBean(position)));
            setSelectionMode(true);

            this.notifyItemChanged(position);
        }
        return false;
    }

    /**
     * @param identity
     */
    private void updateSelectedItemIdentities(ID identity) {
        if (!mSelectedItemIdentities.contains(identity)) {
            mSelectedItemIdentities.add(identity);
        } else {
            mSelectedItemIdentities.remove(identity);
        }
    }

    protected boolean isSelectionMode() {
        return mSelectionMode;
    }

    protected boolean isSelected(int position) {
        return !(mSelectedItemIdentities == null || mSelectedItemIdentities.size() <= 0) && mSelectedItemIdentities.contains(getIdentity(getBean(position)));
    }

    public void setSelectionMode(boolean selectionMode) {
        mSelectionMode = selectionMode;
        if (mSelectedItemIdentities != null && !selectionMode) {
            mSelectedItemIdentities.clear();
        }
        notifyDataSetChanged();
    }

    public HashSet<ID> getSelectedItemIdentities() {
        return mSelectedItemIdentities;
    }
}
