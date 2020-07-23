/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.turing.Isolation.Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 98920
 */
public class FileManager {

    private final static String MAP_PATH = ".\\map.dat";
    private final static String MOVES_PATH = ".\\move.dat";
    private final static String FIRST_POS_PATH = ".\\blue.dat";
    private final static String SECOND_POS_PATH = ".\\red.dat";

    public static int readMove() {
        File moveFile = new File(MOVES_PATH);

        try (Scanner fileInput = new Scanner(moveFile);) {
            int move = fileInput.nextInt();
            return move;
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }

        return -1;
    }

    public static void writeMap(Board board, int firstPlayerPos, int secondPlayerPos) {
        File mapFile = new File(MAP_PATH);

        try (Formatter fileOutput = new Formatter(mapFile);) {

            for (int i = 0; i < Board.BOARD_SIZE; i++) {
                for (int j = 0; j < Board.BOARD_SIZE; j++) {
                    int pos = Manager.coordinateToIndex(i, j);

                    if (pos == firstPlayerPos) {
                        fileOutput.format("f", (Object) null);
                    } else if (pos == secondPlayerPos) {
                        fileOutput.format("s", (Object) null);
                    } else if (board.isBlockesd(pos)) {
                        fileOutput.format("0", (Object) null);
                    } else {
                        fileOutput.format("1", (Object) null);
                    }

                }
                fileOutput.format("\n", (Object) null);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void writePossition(boolean turn, int pos) {
        File posFile;

        if (turn) {
            posFile = new File(FIRST_POS_PATH);
        } else {
            posFile = new File(SECOND_POS_PATH);
        }

        try (Formatter posFormatter = new Formatter(posFile);) {

            posFormatter.format("%d", pos);
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
