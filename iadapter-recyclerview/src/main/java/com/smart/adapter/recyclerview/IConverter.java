package com.smart.adapter.recyclerview;


/**
 * IConverter
 *
 * @param <T> T
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