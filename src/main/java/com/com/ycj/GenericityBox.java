package com.com.ycj;

/**
 * Created by Mtime on 2017/7/10.
 */
public interface GenericityBox<T> {

    T getObj();

    void putObj(T t);

    boolean borrowObj(T t);

    boolean returnObj(T t);

}
