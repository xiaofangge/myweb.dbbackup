package com.mapper;

public interface BaseMapper<T, K> {
    void save(T t);

    T queryById(K k);
}
