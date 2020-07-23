/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.turing.Isolation.Classes;

import com.turing.dataStructures.LargeBoolean;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 98920
 */
public class Manager {

    private int firtPlayerPos;
    private int secondPlayerPos;

    private boolean turn;
    private final Board map;

    private static final long TIME_LIMIT = 20000;

    private int currentMove;

//    private final Object lock = new Object();
    public static int coordinateToIndex(int x, int y) {
        return Board.BOARD_SIZE * y + x;
    }

    public static int getXByIndex(int index) {
        return index / Board.BOARD_SIZE;
    }

    public static int getYByIndex(int index) {
        return index % Board.BOARD_SIZE;
    }

    public Manager() {
        map = new Board();

        firtPlayerPos = 21;
        map.block(firtPlayerPos);
        secondPlayerPos = 27;
        map.block(secondPlayerPos);

        turn = true;
    }

    public boolean getTurn() {
        return turn;
    }

    public boolean getFirstTurn() {
        return true;
    }

    public boolean getSecondTurn() {
        return false;
    }

    public boolean isDone() {
        return false;
    }

    private boolean check(LargeBoolean vals, int move) {
        int size = Board.BOARD_SIZE * Board.BOARD_SIZE;
        for (int i = 0; i < size; i++) {
            if (vals.get(i) && i != move) {
                return true;
            }
        }

        return false;
    }

    private void takeAction(int move) {
        if (turn) {
            firtPlayerPos = move;
        } else {
            secondPlayerPos = move;
        }

        this.map.block(move);
        currentMove = move;
    }

    private long runFiles() {
        Runtime run = Runtime.getRuntime();
        long startingTime = System.currentTimeMillis();
        Process process;
        try {

            if (turn) {
                process = run.exec("blue.exe");
            } else {
                process = run.exec("red.exe");
            }

            process.waitFor();

            long endingTime = System.currentTimeMillis();

            return endingTime - startingTime;
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public WinEnum play() {
        FileManager.writeMap(map, firtPlayerPos, secondPlayerPos);
        FileManager.writePossition(true, firtPlayerPos);
        FileManager.writePossition(false, secondPlayerPos);

        long time = runFiles();

        int move = FileManager.readMove();

        LargeBoolean firstPlayerMoves = map.getMoves(firtPlayerPos);
        LargeBoolean secondPlayerMoves = map.getMoves(secondPlayerPos);
        
        if (turn) {
            if (firstPlayerMoves.get(move) && time <= TIME_LIMIT) {
                takeAction(move);
            } else {
                return WinEnum.Error;
            }
        } else if (secondPlayerMoves.get(move) && time <= TIME_LIMIT) {
            takeAction(move);
        } else {
            return WinEnum.Error;
        }

        boolean firstPlayerLose = check(firstPlayerMoves, move);
        boolean secondPlayeLose = check(secondPlayerMoves, move);

        if (!secondPlayeLose) {
            return WinEnum.firstPlayerWin;
        }

        if (!firstPlayerLose) {
            return WinEnum.secondPlayerWin;
        }

        turn = !turn;

        return WinEnum.noOne;
    }

    public int getFirtPlayerPos() {
        return firtPlayerPos;
    }

    public int getSecondPlayerPos() {
        return secondPlayerPos;
    }

    public Board getMap() {
        return map;
    }

    public boolean isBlocked(int node) {
        return map.isBlockesd(node);
    }

    public void print() {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                int index = i * Board.BOARD_SIZE + j;

                if (index == firtPlayerPos) {
                    System.out.print("f");
                } else if (index == secondPlayerPos) {
                    System.out.print("s");
                } else if (isBlocked(index)) {
                    System.out.print("0");
                } else {
                    System.out.print("1");
                }
            }
            System.out.println("");
        }
    }

    public int getCurrentMove() {
        return currentMove;
    }
}
