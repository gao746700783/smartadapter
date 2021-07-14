package com.smart.adapter.rvbinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.adapter.rvbinding.extend.OnDataSetChangedCallback;
import com.smart.adapter.rvbinding.extend.OnItemClickListener;
import com.smart.adapter.rvbinding.multi.MultiItemTypeSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: BaseBindingAdapter
 * <p>
 * User: gxh <br/>
 * Date: 2017/6/23 上午11:22 <br/>
 *
 * @author gaoxiaohui
 */

public class BaseBindingAdapter<T> extends RecyclerView.Adapter<BindingViewHolder>
        implements IAdapter<T> {

    protected Context mContext;

    protected int mLayoutId;

    protected LayoutInflater mInflater;

    protected ObservableArrayList<T> mDataItems = new ObservableArrayList<>();
    protected OnDataSetChangedCallback<T> dataChangedCallback;

    protected IConverter<? super T> mIConverter;

    protected BaseBindingAdapter(Context context) {
        this(context, -1);
    }

    protected BaseBindingAdapter(Context context, int layoutId) {
        this(context, layoutId, null);
    }

    protected BaseBindingAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        this.mLayoutId = layoutId;

        this.mInflater = LayoutInflater.from(mContext);

        if (null != datas && datas.size() > 0) {
            this.mDataItems.addAll(datas);
        }
        this.dataChangedCallback = new OnDataSetChangedCallback<T>(this/*, this*/);
    }

    /**
     * @return 返回的是adapter的viewHolder
     */
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mMultiItemTypeSupport != null) {
            int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
            ViewDataBinding binding2 = DataBindingUtil.inflate(mInflater, layoutId, parent, false);

            BindingViewHolder holder2 = new BindingViewHolder<>(mContext, binding2);
            setListener(holder2, viewType);
            return holder2;
        }

        ViewDataBinding binding = DataBindingUtil.inflate(mInflater, mLayoutId, parent, false);
        BindingViewHolder holder = new BindingViewHolder<>(mContext, binding);
        // set click && long click listener
        setListener(holder, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        int variableId = mIConverter.getVariableId(viewType);
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(variableId, getItem(position));
        binding.executePendingBindings();

        mIConverter.convert(holder, getItem(position), position);
    }

    protected BaseBindingAdapter<T> layout(int layoutId) {
        this.mLayoutId = layoutId;
        return this;
    }

    protected BaseBindingAdapter<T> list(List<T> datas) {
        this.mDataItems.addAll(datas);
        return this;
    }

    protected BaseBindingAdapter<T> list(ArrayList<T> datas) {
        resetDataList(datas);
        return this;
    }

    protected BaseBindingAdapter<T> bindViewAndData(IConverter<? super T> converter) {
        this.mIConverter = converter;
        return this;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    /**
     * is or not enabled
     * need to override in SectionAdapter
     *
     * @param viewType viewType
     * @return true for default
     */
    protected boolean isEnabled(int viewType) {
        return true;
    }

    @Override
    public void onViewAttachedToWindow(BindingViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(BindingViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
    }

    public T getItem(int position) {
        if (position >= mDataItems.size()) {
            return null;
        }
        return mDataItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDataItems.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return mDataItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemTypeSupport != null) {
            return mMultiItemTypeSupport.getItemViewType(position, getItem(position));
        }
        return 0;
    }

    @Override
    public void resetDataList(List<T> list) {
        if (this.mDataItems != null && list != null) {
            this.mDataItems.clear();
            this.mDataItems.addAll(list);
        }
    }

    @Override
    public void resetDataList(ArrayList<T> list) {
        if (this.mDataItems != null && list != null) {
            this.mDataItems.clear();
            this.mDataItems.addAll(list);
        }
    }

    @Override
    public void resetDataList(ObservableArrayList<T> newItems) {
        this.mDataItems = newItems;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mDataItems.addOnListChangedCallback(dataChangedCallback);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.mDataItems.removeOnListChangedCallback(dataChangedCallback);
    }

    private OnItemClickListener<T> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * set click listener
     *
     * @param viewHolder viewHolder
     * @param viewType   viewType
     */
    private void setListener(final BindingViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) {
            return;
        }
        View mConvertView = viewHolder.getConvertView();
        if (null == mConvertView) {
            return;
        }

        mConvertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    mOnItemClickListener.onItemClick(v, getItem(position), position);
                }
            }
        });

        mConvertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    return mOnItemClickListener.onItemLongClick(v, getItem(position), position);
                }
                return false;
            }
        });
    }

    // region multiItem support
    protected MultiItemTypeSupport<T> mMultiItemTypeSupport = null;

    protected BaseBindingAdapter<T> multiItemTypeSupport(MultiItemTypeSupport<T> multiItemTypeSupport) {
        this.mMultiItemTypeSupport = multiItemTypeSupport;
        return this;
    }
    // region end

}
