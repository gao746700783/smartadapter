//package com.smart.adapter.recyclerview.filter;
//
//import android.text.TextUtils;
//import android.widget.Filter;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * Project name:  smartrecyclerview-master
// * Package name:  com.smart.adapter.recyclerview.filter
// * Description:
// * Copyright:     Copyright(C) 2017-2018
// * All rights Reserved, Designed By gaoxiaohui
// * Company
// *
// * @author che300
// * @version V1.0
// * Createdate:    2018/5/25-19:33
// * <p>
// * Modification  History:
// * Date         Author        Version        Discription
// * -----------------------------------------------------------------------------------
// * ${date}       che300         1.0             1.0
// * Why & What is modified: <修改原因描述>
// */
//public class SearchFilter<T> extends Filter {
//    private final SearchAdapter<T> adapter;
//
//    private final List<? super T> originalList;
//
//    private final List<T> filteredList;
//
//    public SearchFilter(SearchAdapter<T> adapter, List<? super T> originalList) {
//        super();
//        this.adapter = adapter;
//        this.originalList = originalList;
//        this.filteredList = new ArrayList<>();
//    }
//
//    @Override
//    protected Filter.FilterResults performFiltering(CharSequence constraint) {
//        filteredList.clear();
//        final Filter.FilterResults results = new Filter.FilterResults();
//
//        if (TextUtils.isEmpty(constraint) || constraint.length() == 0) {
//            filteredList.addAll(originalList);
//        } else {
////            final String filterPattern = constraint.toString().toLowerCase().trim();
////
////            for (final String user : originalList) {
////                if (user.contains(filterPattern)) {
////                    filteredList.add(user);
////                }
////            }
//        }
//        results.values = filteredList;
//        results.count = filteredList.size();
//        return results;
//    }
//
//    @Override
//    protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
//        adapter.filteredList.clear();
//        adapter.filteredList.addAll(results.values);
//
//        astdapter.setDataList(adapter.filteredLi);
//    }
//}
