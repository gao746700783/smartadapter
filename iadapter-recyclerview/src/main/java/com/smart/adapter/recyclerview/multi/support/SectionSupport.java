package com.smart.adapter.recyclerview.multi.support;

/**
 * Created by zhy on 16/4/9.
 */
public interface SectionSupport<T> {
    public int sectionHeaderLayoutId();

    public int sectionTitleTextViewId();

    public String getTitle(T t);
}
