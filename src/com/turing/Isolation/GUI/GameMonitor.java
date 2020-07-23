package com.turing.Isolation.GUI;

import com.turing.Isolation.Classes.Board;
import com.turing.Isolation.Classes.Manager;
import com.turing.Isolation.Classes.WinEnum;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameMonitor extends Application {

    private final Group group;

    private final double radix;
    private final double margin;
    private final double distance;

    private final Manager game;

    private final Color blocked_color;
    private final Color allowed_color;
    private final Color first_player_color;
    private final Color second_player_color;
    private final Color first_player_blocked_color;
    private final Color second_player_blocked_color;

    private final SequentialTransition animation;

    private Circle firstPlayerDie;
    private Circle secondPlayerDie;

    private final ArrayList<Circle> seenDies;
    private final ArrayList<Circle> blocks;

    private final double time;

    private double calculateCoordinate(int row) {
        return margin + (row + 1) * (radix + distance) - distance;
    }

    private double calculateRowByIndex(int index) {
        return margin + ((index / Board.BOARD_SIZE) + 1) * (radix + distance) - distance;
    }

    private double calculateColumnByIndex(int index) {
        return margin + ((index % Board.BOARD_SIZE) + 1) * (radix + distance) - distance;
    }

    public GameMonitor() {
        group = new Group();
        game = new Manager();

        animation = new SequentialTransition();

        seenDies = new ArrayList<>();
        blocks = new ArrayList<>();

        //constants
        radix = 30;
        margin = 20;
        distance = 40;

        time = 500;

        blocked_color = new Color(0.5, 0.5, 0.5, 1);
        allowed_color = new Color(1, 1, 1, 1);
        first_player_color = new Color(1, 0, 0, 1);
        second_player_color = new Color(0, 0, 1, 1);
        first_player_blocked_color = new Color(1, 0, 0, 0.25);
        second_player_blocked_color = new Color(0, 0, 1, 0.25);
    }

    private void buildBackgroud() {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                double x = calculateCoordinate(i);
                double y = calculateCoordinate(j);

                Circle c = new Circle(x, y, radix);
                c.setFill(blocked_color);

                group.getChildren().add(c);
                blocks.add(c);

            }
        }
    }

    private void buildFrontBlocks() {
        for (int i = 0; i < Board.BOARD_SIZE * Board.BOARD_SIZE; i++) {
            Circle c = new Circle(calculateRowByIndex(i), calculateColumnByIndex(i), radix + 0.5);
            group.getChildren().add(c);
            c.setFill(allowed_color);

            seenDies.add(c);

            if (i == game.getFirtPlayerPos()) {
                firstPlayerDie = new Circle(calculateRowByIndex(i), calculateColumnByIndex(i), radix - 0.5);
                firstPlayerDie.setFill(first_player_color);

            } else if (i == game.getSecondPlayerPos()) {
                secondPlayerDie = new Circle(calculateRowByIndex(i), calculateColumnByIndex(i), radix - 0.5);
                secondPlayerDie.setFill(second_player_color);

            }

        }
    }

    private void initialBoard() {
        buildBackgroud();
        buildFrontBlocks();

        group.getChildren().add(firstPlayerDie);
        group.getChildren().add(secondPlayerDie);
    }

    private void animateMove(int pos, int move, boolean turn) {
        ParallelTransition subAnimation = new ParallelTransition();
        Duration duration = new Duration(time);
        TranslateTransition moveAnimation = new TranslateTransition(duration);
        FadeTransition fadeAnimation = new FadeTransition(duration);

        if (turn) {
            moveAnimation.setNode(firstPlayerDie);

            moveAnimation.setToX(calculateRowByIndex(move) - firstPlayerDie.getCenterX());
            moveAnimation.setToY(calculateColumnByIndex(move) - firstPlayerDie.getCenterY());

            blocks.get(pos).setFill(first_player_blocked_color);
        } else {
            moveAnimation.setNode(secondPlayerDie);

            moveAnimation.setToX(calculateRowByIndex(move) - secondPlayerDie.getCenterX());
            moveAnimation.setToY(calculateColumnByIndex(move) - secondPlayerDie.getCenterY());

            blocks.get(pos).setFill(second_player_blocked_color);
        }

        fadeAnimation.setNode(seenDies.get(pos));

        fadeAnimation.setToValue(0);

        subAnimation.getChildren().add(moveAnimation);
        subAnimation.getChildren().add(fadeAnimation);

        animation.getChildren().add(subAnimation);
    }

    private WinEnum play(Stage window) {

        WinEnum result;

        int step = 0;
        do {
            window.setTitle(String.valueOf(step++));
            int initialPos;
            int move;

            boolean turn = game.getTurn();
            if (turn) {
                initialPos = game.getFirtPlayerPos();
            } else {
                initialPos = game.getSecondPlayerPos();
            }
            result = game.play();
            move = game.getCurrentMove();

            animateMove(initialPos, move, turn);

        } while (result == WinEnum.noOne);

        window.setTitle("The end");
        return result;

    }

    private Label buildLabel(WinEnum result) {
        String message;
        if (result != WinEnum.Error) {
            message = String.format("The %s is winner", (result == WinEnum.firstPlayerWin) ? "Red" : "Blue");
        } else {
            message = String.format("The %s is winner", (game.getTurn()) ? "Red" : "Blue");
        }

        Label lblResult = new Label(message);

        lblResult.setRotate(-45);

        lblResult.setLayoutX(10);
        lblResult.setLayoutY(200);

        Font textFont = new Font(64);
        lblResult.setFont(textFont);

        return lblResult;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initialBoard();

        Scene root = new Scene(group, 520, 520);
        primaryStage.setScene(root);
        primaryStage.show();

        WinEnum result = play(primaryStage);
        animation.play();

        animation.setOnFinished((evt) -> {
            group.getChildren().add(buildLabel(result));
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

}
