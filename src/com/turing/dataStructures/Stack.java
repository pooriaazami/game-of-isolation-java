/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.turing.dataStructures;

/**
 *
 * @author pooriaazami
 * @param <T>
 */
public class Stack<T> extends List<T> {

    public T pop() {
        T ans = super.getValue(lastIndex - 1);

        super.lastIndex--;
        return ans;
    }

    public T top() {
        return super.getValue(lastIndex - 1);
    }

    public boolean isEmpty() {
        return lastIndex == 0;
    }

}
