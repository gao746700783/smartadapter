package com.smart.adapter.abslistview;


/**
 * IConverter
 *
 * @param <T>
 * @author che300
 */
@FunctionalInterface
public interface IConverter<T> {

    /**
     * convert data to holder
     *
     * @param holder   holder
     * @param item     item
     * @param position position
     */
    void convert(IHolder holder, T item, int position);

}