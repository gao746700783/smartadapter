package com.smart.adapter.pager.base;


/**
 * IConverter
 *
 * @param <T> T
 * @author che300
 */
public interface IPagerConverter<T> {

//    /**
//     * convert data to holder
//     *
//     * @param variableId variableId
//     * @param item       item
//     * @param position   position
//     */
//    void convert(int variableId, T item, int position);

    /**
     * @param position
     * @return
     */
    int getVariableId(int position);

    T getBindingData(int position, int viewType);

}