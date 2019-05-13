package com.example.emrekacan.exampleretrofit;

public class GenericEmploye<T> {

    private T object;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
