package com.pigmy.demospringboot.dao;

import java.util.List;

public interface GenericDAO <E,K, M> {
    public void save(M model) ;
    public void delete(K id);
    public E find(K key);
    public List<E> getAll() ;
}
