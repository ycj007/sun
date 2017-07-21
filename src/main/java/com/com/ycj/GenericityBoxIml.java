package com.com.ycj;

/**
 * Created by Mtime on 2017/7/10.
 */
public class GenericityBoxIml<T> implements GenericityBox<T>{

    T obj;
    public T getObj() {
        return this.obj;
    }

    public void putObj( T t) {
        this.obj= t;


    }

    public boolean borrowObj(T t) {

        if(obj!=null){
            obj=null;
            return true;
        }

        return false;
    }

    public boolean returnObj(T t) {
        if(obj!=null){
            return false;
        }
        this.obj=t;
        return true;
    }
}
