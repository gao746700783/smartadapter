package com.smart.adapter.rvbinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.adapter.rvbinding.extend.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: BaseBindingAdapter
 * <p>
 * User: gxh <br/>
 * Date: 2017/6/23 上午11:22 <br/>
 * // todo 借助  ObservableArrayList 实现数据自动更新 https://www.cnblogs.com/DoNetCoder/p/7243878.html
 *
 * @author gaoxiaohui
 */
public class BaseBindingAdapter<T, D extends ViewDataBinding>
        extends RecyclerView.Adapter<BindingViewHolder<D>>
        implements IAdapter<RecyclerView.Adapter, T> {

    protected Context mContext;

    protected int mLayoutId;

    protected LayoutInflater mInflater;

    protected List<? super T> mDataList;

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

        if (null == datas) {
            datas = new ArrayList<>();
        }
        this.mDataList = datas;

        this.mInflater = LayoutInflater.from(mContext);
    }

    /**
     * @return 返回的是adapter的viewHolder
     */
    @Override
    public BindingViewHolder<D> onCreateViewHolder(ViewGroup parent, int viewType) {
        D binding = DataBindingUtil.inflate(mInflater, mLayoutId, parent, false);
        BindingViewHolder<D> holder = new BindingViewHolder<>(mContext, binding);

        // todo
        setListener(parent, holder, viewType);

        return holder;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<D> holder, int position) {
        int variableId = mIConverter.getVariableId();
        D binding = holder.getBinding();
        binding.setVariable(variableId, getItem(position));
        binding.executePendingBindings();

        mIConverter.convert(holder, getItem(position), position);
    }

    public BaseBindingAdapter<T, D> layout(int layoutId) {
        this.mLayoutId = layoutId;
        return this;
    }

    public BaseBindingAdapter<T, D> list(List<T> datas) {
        this.mDataList = datas;
        return this;
    }

    public BaseBindingAdapter<T, D> bindViewAndData(IConverter<? super T> converter) {
        this.mIConverter = converter;
        return this;
    }

    public int getPosition(RecyclerView.ViewHolder viewHolder) {
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
    public void onViewAttachedToWindow(BindingViewHolder<D> holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(BindingViewHolder<D> holder) {
        super.onViewDetachedFromWindow(holder);
    }

    public T getItem(int position) {
        return (T) mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDataList.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public List<? super T> getDataList() {
        return this.mDataList;
    }

    @Override
    public void appendDataList(List<T> list) {
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public void notifyDataChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void setDataList(List<T> list) {
        this.mDataList = list;
        this.notifyDataSetChanged();
    }

    private OnItemClickListener<T> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * set click listener
     *
     * @param parent     parent
     * @param viewHolder viewHolder
     * @param viewType   viewType
     */
    protected void setListener(final ViewGroup parent, final BindingViewHolder viewHolder, int viewType) {
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
                    mOnItemClickListener.onItemClick(parent, v, getItem(position), position);
                }
            }
        });

        mConvertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    return mOnItemClickListener.onItemLongClick(parent, v, getItem(position), position);
                }
                return false;
            }
        });
    }

}
