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
public class Queue<T> extends List<T> {

    private void stepBack() {
        for (int i = 0; i < lastIndex - 1; i++) {
            this.set(i, this.getValue(i + 1));
        }
    }

    public T top() {
        return this.getValue(0);
    }

    public T pop() {
        T ans = this.getValue(0);

        stepBack();
        lastIndex--;

        return ans;
    }

    public boolean isEmpty() {
        return lastIndex == 0;
    }
}
