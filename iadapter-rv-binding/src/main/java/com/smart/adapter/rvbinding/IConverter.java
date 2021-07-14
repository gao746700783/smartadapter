package com.smart.adapter.rvbinding;


/**
 * IConverter
 *
 * @param <T> T
 * @author xhgao
 */
public interface IConverter<T> {

    /**
     * convert data to holder
     *
     * @param holder   holder
     * @param item     item
     * @param position position
     */
    void convert(IHolder holder, T item, int position);

    /**
     * get variableId
     *
     * @param viewType   viewType
     */
    int getVariableId(int viewType);

}