package com.smart.adapter.rvbinding;


/**
 * IConverter
 *
 * @param <T> T
 * @author che300
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
     * @return variableId
     * todo 按照 view type 类型不同 返回不同值
     */
    int getVariableId();

}