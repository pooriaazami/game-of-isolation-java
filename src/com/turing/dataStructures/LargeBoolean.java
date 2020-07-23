/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.turing.dataStructures;

/**
 *
 * @author 98920
 */
public class LargeBoolean extends List<BaseLargeBoolean> {

    private int length = 0;

    public LargeBoolean() {
        super.push(new BaseLargeBoolean());
    }

    private void buildIndex(int index) {
        while (super.size() * 32 - 1 < index) {
            super.push(new BaseLargeBoolean());
        }
    }

    public void set(int index, boolean value) {
        buildIndex(index);
        super.getValue(index / 32).set(index % 32, value);

        this.length = (this.length > index) ? this.length : index;
    }

    public boolean get(int index) {
        return super.getValue(index / 32).get(index % 32);
    }

    public void build(int count) {
        buildIndex(count);
    }

}
