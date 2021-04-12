/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.sun.jmx.remote.internal.ServerCommunicatorAdmin;
import comunnication.CommunicationReasonsEnum;
import comunnication.ServerComunnicationModel;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import scenes.StartGameScreen;
import util.SingletonCommunication;

public class Main extends Application {

    //******************user*********************//
    public static String user = StartGameScreen.user;
    //*************snake player one*************//
    private List<Point> snakeBody = new ArrayList<>();
    private Point snakeHead;
    //*********snake player two****************//
    private List<Point> snakeBodyEnemy = new ArrayList<>();
    private Point snakeHeadEnemy;
    //****************************************//
    public static Integer sessionId;
    private String direction = "UP";
    //***************************************//
    //***********canvas settings**************//    
    private static final double WIDTH = 800;
    private static final double HEIGHT = WIDTH;
    private static final int ROWS = 20;
    private static final int COLUMNS = ROWS;
    private static final int SQUARE_SIZE = (int) (WIDTH / ROWS);
    public static boolean gameOver = false;
    private int speed = 120;
    private GraphicsContext gc;
    private int foodX;
    private int foodY;
    private Image foodImage;
    private Timeline timeline;
    private int score;
    private VBox root;
    private Stage primaryStage;
    private String winner;
    public static ServerComunnicationModel readModel;
    private SingletonCommunication singletonCommunication;

    @Override
    public void start(Stage primaryStage) {
        /**
         ********************
         */
        singletonCommunication = SingletonCommunication.getInstance();

        Map<String, String> map = new HashMap<>();
        map.put("user", user);
        map.put("direction", direction);
        ServerComunnicationModel model = new ServerComunnicationModel(CommunicationReasonsEnum.MOVE, map);

//************************************************************************************************//
        singletonCommunication.sendInfoToServer(StartGameScreen.socket, model);
        readModel = singletonCommunication.readInfoFromServer(StartGameScreen.socket);
//************************************************************************************************//

        /**
         * *****************
         */
        this.primaryStage = primaryStage;
        root = new VBox();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(5, ROWS / 2));
        }
        snakeHead = snakeBody.get(0);

        for (int i = 0; i < 3; i++) {
            snakeBodyEnemy.add(new Point(1, ROWS / 2));
        }
        snakeHeadEnemy = snakeBodyEnemy.get(0);

        generateFood();

        timeline = new Timeline(new KeyFrame(Duration.millis(speed), e -> run()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        primaryStage.setTitle("PyGame");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/assets/snakelogo.png"));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        //eventsForPlayer
        scene.setOnKeyPressed(e -> {

            switch (e.getCode()) {
                case UP:
                    direction = "UP";
                    break;
                case DOWN:
                    direction = "DOWN";
                    break;
                case RIGHT:
                    direction = "RIGHT";
                    break;
                case LEFT:
                    direction = "LEFT";
                    break;

                default:
                    break;
            }
            // TODO UMESTO DA SE  PRIMAJU INFORMACIJE O KRETANJU PRI KLIKU TO RADITI SA  THREADOM
//************************************************************************************************//
            map.put("direction", direction);
            singletonCommunication.sendInfoToServer(StartGameScreen.socket, model);
            readModel = singletonCommunication.readInfoFromServer(StartGameScreen.socket);
//************************************************************************************************//

        });
    }

    public void run() {

        isGameOver(snakeHead, snakeBody);
        isGameOver(snakeHeadEnemy, snakeBodyEnemy);
        if (gameOver) {
            endMenu();
        }
        makeGameHarder();

        eatFood(snakeHead, snakeBody);
        eatFood(snakeHeadEnemy, snakeBodyEnemy);

        drawBackround();
        drawFood();
        drawSnake(snakeBody);
        drawSnake(snakeBodyEnemy);
        drawScore();

        moveSnakes(snakeBody, snakeBodyEnemy);
        makeSwitchDirection(direction, snakeHead, snakeHeadEnemy);

    }

    private void drawBackround() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.AQUA);
                } else {
                    gc.setFill(Color.AZURE);
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    private void generateFood() {
        start:
        while (true) {
            foodX = (int) (Math.random() * ROWS);
            foodY = (int) (Math.random() * COLUMNS);

            for (Point snake : snakeBody) {
                if (snake.getX() == foodX && snake.getY() == foodY) {
                    continue start;
                }
            }
            foodImage = new Image("/assets/apple.png");
            break;
        }

    }

    private void eatFood(Point snakeHead, List<Point> snakeBody) {
        if (snakeHead.x == foodX && snakeHead.y == foodY) {
            snakeBody.add(new Point(-1, -1));
            generateFood();
            score += 10;
        }
    }

    private void drawFood() {
        gc.drawImage(foodImage, foodX * SQUARE_SIZE, foodY * SQUARE_SIZE);
    }

    private void drawSnake(List<Point> snakeBody) {

        for (int i = 0; i < snakeBody.size(); i++) {
            if (i == 0) {
                gc.drawImage(new Image("/assets/snakeHead.png"), snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
            } else {
                gc.drawImage(new Image("/assets/snakeBody.png"), snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
            }
        }
    }

    private void isGameOver(Point snakeHead, List<Point> snakeBody) {
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * SQUARE_SIZE >= WIDTH || snakeHead.y * SQUARE_SIZE >= HEIGHT) {
            gameOver = true;
            return;
        }
        if (snakeBody.size() > 3) {
            for (int i = 1; i < snakeBody.size(); i++) {
                if (snakeHead.x == snakeBody.get(i).x && snakeHead.y == snakeBody.get(i).y) {
                    gameOver = true;

                    break;
                }
            }
            for (int i = 1; i < this.snakeBodyEnemy.size(); i++) {
                if (this.snakeHead.x == snakeBodyEnemy.get(i).x && this.snakeHead.y == snakeBodyEnemy.get(i).y) {
                    gameOver = true;
                    break;
                }
            }

        }

    }

    private void endMenu() {
        try {
            primaryStage.close();
            timeline.stop();
            gameOver = false;
            new StartGameScreen().start(primaryStage);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void drawScore() {
        gc.setFill(Color.GREEN);
        gc.setFont(new Font("Digital-2", 35));
        gc.fillText("Score: " + score, 10, 35);
    }

    private void makeGameHarder() {
        if (score > 50) {
            speed = 100;
        } else if (score > 100) {
            speed = 90;
        } else if (score > 160) {
            speed = 80;
        } else if (score > 190) {
            speed = 70;
        } else if (score > 240) {
            speed = 60;
        }
    }

    private static void makeSwitchDirection(String direction, Point snakeHead, Point snakeHeadEnemy) {

        switch (direction) {

            case "UP":
                if (readModel.getMap().get("user").equals(user)) {
                    snakeHead.y--;
                } else {
                    snakeHeadEnemy.y--;
                }
                break;
            case "DOWN":
                if (readModel.getMap().get("user").equals(user)) {
                    snakeHead.y++;
                } else {
                    snakeHeadEnemy.y++;
                }
                break;
            case "RIGHT":
                if (readModel.getMap().get("user").equals(user)) {
                    snakeHead.x++;
                } else {
                    snakeHeadEnemy.x++;
                }
                break;
            case "LEFT":
                if (readModel.getMap().get("user").equals(user)) {
                    snakeHead.x--;
                } else {
                    snakeHeadEnemy.x--;
                }
                break;
            default:
                break;
        }

    }

    private static void moveSnakes(List<Point> snakeBody, List<Point> snakeBodyEnemy) {
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        for (int i = snakeBodyEnemy.size() - 1; i >= 1; i--) {
            snakeBodyEnemy.get(i).x = snakeBodyEnemy.get(i - 1).x;
            snakeBodyEnemy.get(i).y = snakeBodyEnemy.get(i - 1).y;
        }
    }
}
