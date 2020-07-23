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
public class BaseLargeBoolean {

    private int compaced;

    public BaseLargeBoolean() {
        compaced = 0;
    }

    public boolean get(int index) {
        return (compaced & (1 << index)) != 0;
    }

    public void set(int index, boolean value) {
        if (index < 32) {
            this.compaced = this.compaced | (1 << index);
            if (!value) {
                this.compaced = this.compaced ^ (1 << index);
            }
        }
    }

    public void reset() {
        this.compaced = 0;
    }

}
