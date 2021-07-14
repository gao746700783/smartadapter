//package com.smart.adapter.pager.base;
//
//import android.support.v4.view.PagerAdapter;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.List;
//
///**
// * Project name:  QingAI
// * Package name:  com.qinggan.qingai.adapter.pager
// * Description:   (用一句话描述该文件做什么)
// * Copyright:     Copyright(C) 2017-2018
// * All rights Reserved, Designed By gaoxiaohui
// * Company        pateo.
// *
// * @author xiaohuigao
// * @version V1.0
// * Createdate:    19-12-14 上午10:10
// * <p>
// * Modification  History:
// * Date         Author        Version        Discription
// * -----------------------------------------------------------------------------------
// * gxh          1.0             1.0
// * Why & What is modified: <修改原因描述>
// */
//public class ViewPagerAdapter extends PagerAdapter {
//    private List<View> mViewList;
//    private List<String> mTitleList;
//
//    public ViewPagerAdapter(List<View> mViewList) {
//        this.mViewList = mViewList;
//    }
//
//    public ViewPagerAdapter(List<View> mViewList, List<String> titles) {
//        this.mViewList = mViewList;
//        this.mTitleList = titles;
//    }
//
//    public void setData(List<View> viewList) {
//        this.mViewList = viewList;
//    }
//
//    public void setTitles(List<String> titles) {
//        this.mTitleList = titles;
//    }
//
//    @Override
//    public int getCount() {
//        if (null == mViewList) {
//            return 0;
//        } else {
//            return mViewList.size();
//        }
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == object;
//    }
//
//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        container.addView(mViewList.get(position));
//        return mViewList.get(position);
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View) object);
//    }
//
//    @Override
//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        if (mTitleList != null && mTitleList.size() >= position) {
//            return mTitleList.get(position);
//        }
//        return super.getPageTitle(position);
//    }
//}
