//package com.qinggan.qingai.adapter.pager;
//
//import android.content.Context;
//
//import com.qinggan.qingai.adapter.pager.base.BaseBindingPagerAdapter;
//import com.qinggan.qingai.adapter.pager.base.IPagerConverter;
//import com.qinggan.qingai.adapter.pager.multi.MultiItemTypeSupport;
//
//import java.util.List;
//
///**
// * Description: BaseBindingAdapter
// * <p>
// * User: gxh <br/>
// * Date: 2017/6/23 上午11:22 <br/>
// *
// * @author gaoxiaohui
// */
//public class CommonBDPagerAdapter<T> extends BaseBindingPagerAdapter<T> {
//
//    public static CommonBDPagerAdapter newBuild(Context ctx) {
//        return new CommonBDPagerAdapter(ctx);
//    }
//
//    public CommonBDPagerAdapter(Context context) {
//        super(context);
//    }
//
//    public CommonBDPagerAdapter(Context context, int layoutId) {
//        super(context, layoutId);
//    }
//
//    public CommonBDPagerAdapter(Context context, int layoutId, List<T> datas) {
//        super(context, layoutId, datas);
//    }
//
//    public CommonBDPagerAdapter(Context context, int layoutId, List<T> datas, List<String> titles) {
//        super(context, layoutId, datas, titles);
//    }
//
//    public CommonBDPagerAdapter<T> layout(int layoutId) {
//        this.mLayoutId = layoutId;
//        return this;
//    }
//
//    @Override
//    public CommonBDPagerAdapter<T> list(List<T> datas) {
//        this.mDataItems.clear();
//        this.mDataItems.addAll(datas);
//        return this;
//    }
//
//    @Override
//    public CommonBDPagerAdapter<T> titles(List<String> titles) {
//        this.mTitleList = titles;
//        return this;
//    }
//
//    @Override
//    public CommonBDPagerAdapter<T> bindViewAndData(IPagerConverter converter) {
//        this.mIConverter = converter;
//        return this;
//    }
//
//    @Override
//    public CommonBDPagerAdapter<T> multiItemTypeSupport(MultiItemTypeSupport<T> multiItemTypeSupport) {
//        this.mMultiItemTypeSupport = multiItemTypeSupport;
//        return this;
//    }
//
//}
