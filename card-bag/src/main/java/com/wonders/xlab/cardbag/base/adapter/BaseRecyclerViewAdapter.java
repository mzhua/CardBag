package com.wonders.xlab.cardbag.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hua on 16/8/22.
 * {@link Bean } should override equals and hashCode
 */
public abstract class BaseRecyclerViewAdapter<Bean, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private List<Bean> mDatas;

    private OnClickListener mOnClickListener;

    private OnLongClickListener mOnLongClickListener;

    /**
     * @param holder
     * @param position
     * @return true: won't call the {@link #mOnClickListener}
     */
    protected abstract boolean onItemClick(VH holder, int position);

    /**
     * @param holder
     * @param position
     * @return true: won't call the {@link #mOnLongClickListener}
     */
    protected abstract boolean onItemLongClick(VH holder, int position);

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }

    public interface OnLongClickListener {
        void onItemLongClick(int position);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClick(holder, holder.getAdapterPosition())) {
                    return true;
                } else {
                    if (null != mOnLongClickListener) {
                        mOnLongClickListener.onItemLongClick(holder.getAdapterPosition());
                    }
                    return false;
                }

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnClickListener && !onItemClick(holder, holder.getAdapterPosition())) {
                    mOnClickListener.onItemClick(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void insertToFist(Bean bean) {
        if (bean == null) {
            return;
        }
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.add(0, bean);
        notifyItemInserted(0);
    }

    public void appendToLast(Bean bean) {
        if (bean == null) {
            return;
        }
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.add(bean);
        notifyItemInserted(mDatas.size());
    }

    public void remove(int position) {
        if (mDatas != null && mDatas.size() > position) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * {@link Bean } should override equals and hashCode
     *
     * @param bean
     */
    public void remove(Bean bean) {
        if (mDatas != null) {
            int i = mDatas.indexOf(bean);
            if (i >= 0) {
                mDatas.remove(bean);
                notifyItemRemoved(i);
            }
        }
    }

    public void clear() {
        if (mDatas != null) {
            int size = mDatas.size();
            mDatas.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    /**
     * 因为这里需要有移动的动画效果，所以采用了遍历的方式来交换位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void swap(int fromPosition, int toPosition) {
        if (this.mDatas == null || this.mDatas.size() > fromPosition || this.mDatas.size() > toPosition) {
            return;
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(this.mDatas, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(this.mDatas, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     *
     * @param srcData source data list
     * @param clear true:clear datas before add
     */
    private void initDatasWithSrc(List<Bean> srcData, boolean clear) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        } else if (clear) {
            this.mDatas.clear();
        }
        if (srcData == null) {
            return;
        }
        this.mDatas.addAll(srcData);
    }

    public void setDatas(List<Bean> mBeanList) {
        initDatasWithSrc(mBeanList,true);
        notifyDataSetChanged();
    }

    public void appendDatas(List<Bean> mBeanList) {
        int oldSize = this.mDatas.size();
        initDatasWithSrc(mBeanList,false);
        notifyItemRangeInserted(oldSize, mBeanList.size());
    }

    public List<Bean> getDatas() {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        return mDatas;
    }

    public Bean getBean(int position) {
        if (mDatas != null && mDatas.size() > position) {
            return mDatas.get(position);
        }
        return null;
    }
}
