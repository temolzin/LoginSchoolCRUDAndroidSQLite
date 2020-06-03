package com.example.practicaloginsqlite.dao;

import java.util.ArrayList;

public interface Crud<T> {
    public void create();
    public ArrayList<T> read();
    public T readbyid(Object id);
    public boolean update(T obj);
    public boolean delete(int id);
    public boolean insert(T obj);
}
