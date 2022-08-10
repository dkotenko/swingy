package org.example.model.entity;

public interface AbstractFactory<T> {
    T create(String name, String type) ;
    T create(String type) ;
}
