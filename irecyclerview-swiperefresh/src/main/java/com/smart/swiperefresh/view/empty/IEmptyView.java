package com.smart.swiperefresh.view.empty;

import android.view.View;

/**
 * Project name:  smartrecyclerview-master
 * Package name:  com.smart.swiperefresh.view.empty
 * Description:   ${todo}(用一句话描述该文件做什么)
 * Copyright:     Copyright(C) 2017-2018
 * All rights Reserved, Designed By gaoxiaohui
 * Company
 *
 * @author che300
 * @version V1.0
 * Createdate:    2018-08-14-13:42
 * <p>
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * ${date}       che300         1.0             1.0
 * Why & What is modified: <修改原因描述>
 */
public interface IEmptyView {

    public IEmptyView emptyImage(int imageResId);

    public IEmptyView emptyText(String text);

    public IEmptyView emptyClick(View.OnClickListener clickListener);

    public IEmptyView emptyView(int viewResId);

    public IEmptyView emptyView(View view);

}
