/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.turing.dataStructures;

/**
 *
 * @author pooriaazami
 */
public class Graph {

    private final BinaryMatrix adjacentMatrix;
    private final int nodes;
    private int edges;

    public Graph(int nodex) {
        this.nodes = nodex;

        adjacentMatrix = new BinaryMatrix(nodex, nodex);
    }

    public void addEdge(int u, int v) {
        if (u < nodes && v < nodes && u >= 0 && v >= 0) {
            this.adjacentMatrix.set(v, u, true);
            this.adjacentMatrix.set(u, v, true);
        }

        edges++;
    }

    public void removeEdge(int u, int v) {
        if (u < nodes && v < nodes && u >= 0 && v >= 0) {
            this.adjacentMatrix.set(v, u, false);
            this.adjacentMatrix.set(u, v, false);
        }

        edges--;
    }

    public boolean adjacency(int u, int v) {
        return adjacentMatrix.get(u, v);
    }

    public int getNodes() {
        return nodes;
    }

    public int getEdges() {
        return edges;
    }

    public int degree(int node) {
        int deg = 0;

        for (int i = 0; i < this.nodes; i++) {
            if (adjacency(node, i)) {
                deg++;
            }
        }

        return deg;
    }

    public LargeBoolean getAdjacentVertices(int node) {
        LargeBoolean ans = new LargeBoolean();

        for (int i = 0; i < this.nodes; i++) {
            ans.set(i, adjacency(node, i));
        }

        return ans;
    }

    public BinaryMatrix getAdjacentMatrix() {
        return adjacentMatrix;
    }

    public int smallestDegree() {
        int ans = this.nodes, temp;

        for (int i = 0; i < this.nodes; i++) {
            temp = degree(i);
            ans = (ans > temp) ? temp : ans;
        }

        return ans;
    }

    public int largestDegree() {
        int ans = 0, temp;

        for (int i = 0; i < this.nodes; i++) {
            temp = degree(i);
            ans = (ans < temp) ? temp : ans;
        }

        return ans;
    }

    public void print() {
        this.adjacentMatrix.print();
    }

    public int[] BFS(int node) {
        int[] ans = new int[this.nodes];
        LargeBoolean flags = new LargeBoolean();
        Queue<Integer> queue = new Queue<>();
        flags.build(this.nodes);
        
        queue.push(node);
        flags.set(node, true);
        ans[node] = 0;
        while (!queue.isEmpty()) {
            int vertex = queue.top();

            boolean possibility = false;
            for (int i = 0; i < this.nodes; i++) {
                if (this.adjacency(vertex, i) && !flags.get(i)) {
                    queue.push(i);
                    possibility = true;
                    flags.set(i, true);

                    ans[i] = ans[vertex] + 1;
                }
            }

            if (!possibility) {
                queue.pop();
            }
        }

        return ans;
    }

    public int[] DFS(int node) {
        int[] ans = new int[this.nodes];
        Stack<Integer> stack = new Stack<>();
        LargeBoolean flags = new LargeBoolean();
        flags.build(this.nodes);
        
        stack.push(node);
        flags.set(node, true);
        ans[node] = -1;
        while (!stack.isEmpty()) {
            int vertex = stack.top();

            boolean possibility = false;
            for (int i = 0; i < this.nodes; i++) {
                if (adjacency(vertex, i) && !flags.get(i)) {
                    stack.push(i);
                    possibility = true;
                    flags.set(i, true);

                    ans[i] = vertex;
                    break;
                }
            }

            if (!possibility) {
                stack.pop();
            }
        }
        return ans;
    }
}
