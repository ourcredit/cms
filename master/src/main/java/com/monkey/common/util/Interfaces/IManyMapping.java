package com.monkey.common.util.Interfaces;

import java.util.Collection;

/**
 * @author zhaojiwei IManyMapping 2015年4月16日
 */
public interface IManyMapping<T, E> {

    Collection<E> selectMany(T t);
}
