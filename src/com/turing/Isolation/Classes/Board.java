/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.turing.Isolation.Classes;

import com.turing.dataStructures.Graph;
import com.turing.dataStructures.LargeBoolean;
import com.turing.dataStructures.Stack;

/**
 *
 * @author 98920
 */
public class Board {

    public final static int BOARD_SIZE = 7;

    private final LargeBoolean blocked;

    public Graph getPaths(int node) {
        int size = BOARD_SIZE * BOARD_SIZE;
        Graph ans = new Graph(size);

        int nodeRow = node / Board.BOARD_SIZE;
        int nodeColumn = node % Board.BOARD_SIZE;

        for (int i = 0; i < BOARD_SIZE; i++) {//row
            for (int j = 0; j < BOARD_SIZE; j++) {//column

                int nodeIndex = i * BOARD_SIZE + j;

                int backRow = -1;
                int backDiagnol = -1;
                int forwardDiagnol = -1;
                int upColumn = -1;

                if (i - 1 >= 0 && j - 1 >= 0) {
                    backDiagnol = (i - 1) * BOARD_SIZE + (j - 1);
                }
                if (i - 1 >= 0) {
                    upColumn = (i - 1) * BOARD_SIZE + j;
                }
                if (j - 1 >= 0) {
                    forwardDiagnol = (i + 1) * BOARD_SIZE + (j - 1);
                    backRow = i * BOARD_SIZE + (j - 1);
                }

                if (nodeRow == i) {
                    ans.addEdge(nodeIndex, backRow);
                }

                if (nodeColumn == j) {
                    ans.addEdge(nodeIndex, upColumn);
                }

                if (nodeRow - i == nodeColumn - j) {
                    ans.addEdge(nodeIndex, backDiagnol);
                }

                if (nodeRow - i == j - nodeColumn) {
                    ans.addEdge(nodeIndex, forwardDiagnol);
                }
            }
        }

        return ans;
    }

    public Board() {
        blocked = new LargeBoolean();
        blocked.build(BOARD_SIZE * BOARD_SIZE);

    }

    private LargeBoolean DFS(Graph paths, int node) {
        int nodes = Board.BOARD_SIZE * Board.BOARD_SIZE;

        Stack<Integer> stack = new Stack<>();
        LargeBoolean flags = new LargeBoolean();
        flags.build(nodes);

        stack.push(node);
        flags.set(node, false);
        while (!stack.isEmpty()) {
            int vertex = stack.top();

            boolean possibility = false;
            for (int i = 0; i < nodes; i++) {
                if (paths.adjacency(vertex, i) && !flags.get(i) && !blocked.get(i)) {
                    stack.push(i);
                    possibility = true;
                    flags.set(i, true);

                    break;
                }
            }

            if (!possibility) {
                stack.pop();
            }
        }

        return flags;
    }

    public LargeBoolean getMoves(int node) {
        return DFS(getPaths(node), node);
    }

    public void block(int node) {
        this.blocked.set(node, true);
    }

    public void print(int firstPlayerPos, int secondPlayerPos) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                int index = j * BOARD_SIZE + i;
                if (index == firstPlayerPos) {
                    System.out.print("f");
                } else if (index == secondPlayerPos) {
                    System.out.print("s");
                } else if (blocked.get(index)) {
                    System.out.print("-");
                } else {
                    System.out.print("+");
                }
            }
            System.out.println("");
        }
    }

    public boolean isBlockesd(int node) {
        return this.blocked.get(node);
    }

}
