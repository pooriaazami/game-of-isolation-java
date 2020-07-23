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
public class BinaryMatrix {

    private final LargeBoolean[] matrix;

    private final int rows;
    private final int columns;

    public BinaryMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        matrix = new LargeBoolean[rows];

        for (int i = 0; i < rows; i++) {
            matrix[i] = new LargeBoolean();
            matrix[i].build(columns);
        }

    }

    public void print() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (get(i, j)) {
                    System.out.print("1 ");
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println("");
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int size() {
        return rows * columns;
    }

    public void set(int row, int column, boolean value) {
        this.matrix[row].set(column, value);
    }

    public boolean get(int row, int column) {
        return matrix[row].get(column);
    }
}
