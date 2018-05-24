package com.smart.adapter.recyclerview;


/**
 * IConverter
 * @author che300
 * @param <T>
 */
@FunctionalInterface
public interface IConverter<T> {

    /**
     * convert data to holder
     * @param holder
     * @param item
     * @param position
     */
    void convert(IHolder holder, T item, int position);

}