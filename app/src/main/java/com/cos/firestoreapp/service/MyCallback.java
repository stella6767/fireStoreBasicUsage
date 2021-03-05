package com.cos.firestoreapp.service;

public interface MyCallback<T> {
    void back(T t); //동적 바인딩 활용
}
