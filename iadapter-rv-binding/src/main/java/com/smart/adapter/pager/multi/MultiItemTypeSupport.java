package com.smart.adapter.pager.multi;

/**
 * Description: MultiItemTypeSupport
 * <p>
 * User: qiangzhang <br/>
 * Date: 2017/6/23 上午11:21 <br/>
 *
 * @author che300
 */
public interface MultiItemTypeSupport<T> {
    int getLayoutId(int itemType);

    int getItemType(int position);
}