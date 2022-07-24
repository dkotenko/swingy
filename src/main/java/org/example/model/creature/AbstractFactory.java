package org.example.model.creature;

public interface AbstractFactory<T> {
    T create(String name, String type) ;
}
