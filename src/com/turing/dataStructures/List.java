/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.turing.dataStructures;

/**
 *
 * @author 98920
 * @param <T>
 */
public class List<T> {

    private final int INITIAL_CAPACITY = 2;
    private final int INCREASE_SIZE = 5;

    protected int lastIndex;
    protected int size;

    private Object[] data;

    public List() {
        size = INITIAL_CAPACITY;
        data = new Object[INITIAL_CAPACITY];
    }

    private void copyArray() {
        Object[] newArray = new Object[size + INCREASE_SIZE];
        System.arraycopy(this.data, 0, newArray, 0, size);

        this.data = newArray;
    }

    public boolean push(T value) {
        if (lastIndex < size) {
            data[lastIndex++] = value;
            return false;
        } else {
            copyArray();
            size += INCREASE_SIZE;
            data[lastIndex++] = value;
            return true;
        }

    }

    protected T getValue(int index) {
        return (T) data[index];
    }

    protected void set(int index, T value) {
        this.data[index] = value;
    }

    protected int size() {
        return this.lastIndex;
    }

    protected int maximum() {
        return this.size;
    }

}
