package com.app.elect.auth.database.entity;

public interface BaseEntity<T> {

    T getId();
    
    void setId(T id);

}
