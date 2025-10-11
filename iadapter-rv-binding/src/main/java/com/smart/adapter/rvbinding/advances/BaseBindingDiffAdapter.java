package com.smart.adapter.rvbinding.advances;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.smart.adapter.rvbinding.BindingViewHolder;
import com.smart.adapter.rvbinding.IAdapter;
import com.smart.adapter.rvbinding.IConverter;
import com.smart.adapter.rvbinding.extend.OnItemClickListener;
import com.smart.adapter.rvbinding.multi.MultiItemTypeSupport;

import java.util.List;

/**
 * Description: BaseBindingAdapter
 * <p>
 * User: gxh <br/>
 * Date: 2017/6/23 上午11:22 <br/>
 *
 * @author gaoxiaohui
 */

public class BaseBindingDiffAdapter<T> extends RecyclerView.Adapter<BindingViewHolder>
        implements IAdapter<T> {

    protected Context mContext;

    protected int mLayoutId;

    protected LayoutInflater mInflater;

    protected AsyncListDiffer<T> mListDiffer;

    protected IConverter<? super T> mIConverter;

    protected BaseBindingDiffAdapter(Context context) {
        this(context, -1);
    }

    protected BaseBindingDiffAdapter(Context context, int layoutId) {
        this(context, layoutId, null);
    }

    protected BaseBindingDiffAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        this.mLayoutId = layoutId;

        this.mInflater = LayoutInflater.from(mContext);
    }

    /**
     * @return 返回的是adapter的viewHolder
     */
    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mMultiItemTypeSupport != null) {
            int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
            ViewDataBinding binding2 = DataBindingUtil.inflate(mInflater, layoutId, parent, false);

            BindingViewHolder holder2 = new BindingViewHolder(mContext, binding2);
            setListener(holder2, viewType);
            return holder2;
        }

        ViewDataBinding binding = DataBindingUtil.inflate(mInflater, mLayoutId, parent, false);
        BindingViewHolder holder = new BindingViewHolder(mContext, binding);
        // set click && long click listener
        setListener(holder, viewType);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
//        int viewType = getItemViewType(position);
//        int variableId = mIConverter.getVariableId(viewType);
//        ViewDataBinding binding = holder.getBinding();
//        binding.setVariable(variableId, getItem(position));
//        binding.executePendingBindings();

        mIConverter.convert(holder, getItem(position), position);
    }

    protected BaseBindingDiffAdapter<T> layout(int layoutId) {
        this.mLayoutId = layoutId;
        return this;
    }

    protected BaseBindingDiffAdapter<T> list(List<T> datas, DiffUtil.ItemCallback<T> itemCallback) {
        this.mListDiffer = new AsyncListDiffer<T>(this,itemCallback);
        this.mListDiffer.submitList(datas);
        return this;
    }

    protected BaseBindingDiffAdapter<T> bindViewAndData(IConverter<? super T> converter) {
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
        return mListDiffer.getCurrentList().get(position);
    }

    @Override
    public long getItemId(int position) {
        T t = getItem(position);
        return t.hashCode();
    }

    @Override
    public int getItemCount() {
        return mListDiffer.getCurrentList().size();
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
        this.mListDiffer.submitList(list);
    }

    @Override
    public void resetDataList(ObservableArrayList<T> newItems) {
        this.mListDiffer.submitList(newItems);
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

    protected BaseBindingDiffAdapter<T> multiItemTypeSupport(MultiItemTypeSupport<T> multiItemTypeSupport) {
        this.mMultiItemTypeSupport = multiItemTypeSupport;
        return this;
    }
    // region end

}
