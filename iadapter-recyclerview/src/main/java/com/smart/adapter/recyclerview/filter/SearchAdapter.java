package com.smart.adapter.recyclerview.filter;

import android.content.Context;
import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;

import com.smart.adapter.recyclerview.BaseAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Project name:  smartrecyclerview-master
 * Package name:  com.smart.adapter.recyclerview.filter
 * Description:
 * Copyright:     Copyright(C) 2017-2018
 * All rights Reserved, Designed By gaoxiaohui
 * Company
 *
 * @author che300
 * @version V1.0
 * Createdate:    2018/5/25-19:32
 * <p>
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * ${date}       che300         1.0             1.0
 * Why & What is modified: <修改原因描述>
 */
public class SearchAdapter<T> extends BaseAdapter<T> implements Filterable {

    private List<? super T> filteredList;

    //    private SearchFilter<T> userFilter;
    private Method filterMethod;
    private String filterType;// Class or String
    private boolean filterRegistered;

    private boolean ignoreCase;

    public SearchAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);

        this.filteredList = new ArrayList<>();
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<T> filtered = new ArrayList<>();

                try {
                    for (int i = 0; i < mDataList.size(); i++) {
                        T object = (T) mDataList.get(i);

                        String value = "";
                        if ("class".equals(filterType)) {
                            if (null != filterMethod) {
                                value = (String) filterMethod.invoke(object);
                            }
                        } else if ("string".equals(filterType)) {
                            value = (String) object;
                        }

                        if (checkContains(value, constraint)) filtered.add(object);
                    }

                    filterResults.count = filtered.size();
                    filterResults.values = filtered;
                    return filterResults;
                } catch (InvocationTargetException e) {
                    Log.e("", e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    Log.e("", e.getMessage(), e);
                }
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (List<T>) results.values;
                setDataList(filteredList);
            }
        };
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private boolean checkContains(String str1, CharSequence str2) {
        if (ignoreCase) return str1.toLowerCase().contains(str2.toString().toLowerCase());
        return str1.contains(str2);
    }

    public SearchAdapter<T> registerFilter(Class clazz, String filterKey) {

        if (clazz == String.class) {
            filterType = "string";
        } else {
            filterType = "class";
            try {
                filterMethod = clazz.getClass().getMethod(capitalize(filterKey));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        filterRegistered = true;

        return this;
    }

    public void filter(String filterStr) {
        if (filterRegistered) getFilter().filter(filterStr);
    }

    public void filter(CharSequence filterStr) {
        if (filterRegistered) getFilter().filter(filterStr);
    }

}
