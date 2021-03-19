/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import scenes.EndGameScreen;
import util.CommunicationWithComponentsInterface;

/**
 *
 * @author vikto
 */
public class Main extends Application implements CommunicationWithComponentsInterface {

    private List<Point> snakeBody = new ArrayList<>();
    private Point snakeHead;
    private static final double WIDTH = 800;
    private static final double HEIGHT = WIDTH;
    private static final int ROWS = 20;
    private static final int COLUMNS = ROWS;
    private static final int SQUARE_SIZE = (int) (WIDTH / ROWS);
    private boolean gameOver = false;
    private int speed = 120;
    private String direction = "UP";
    private GraphicsContext gc;
    private int foodX;
    private int foodY;
    private Image foodImage;
    private Timeline timeline;
    private int score;
    private VBox root;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {

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

        generateFood();
        timeline = new Timeline(new KeyFrame(Duration.millis(speed), e -> run()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        primaryStage.setTitle("PyGAME");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/assets/snakelogo.png"));
        primaryStage.show();

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
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void run() {

        makeGameHarder();
        isGameOver();
        eatFood();
        drawBackround();
        drawFood();
        drawSnake();
        drawScore();
        if (gameOver == true) {
            endMenu();
        }
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }
        switch (direction) {
            case "UP":
                snakeHead.y--;
                break;
            case "DOWN":
                snakeHead.y++;
                break;
            case "RIGHT":
                snakeHead.x++;
                break;
            case "LEFT":
                snakeHead.x--;
                break;
            default:
                break;
        }
    }

    public void drawBackround() {
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

    public void generateFood() {
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

    public void eatFood() {
        if (snakeHead.x == foodX && snakeHead.y == foodY) {
            snakeBody.add(new Point(-1, -1));
            generateFood();
            score += 10;
        }
    }

    public void drawFood() {
        gc.drawImage(foodImage, foodX * SQUARE_SIZE, foodY * SQUARE_SIZE);
    }

    public void drawSnake() {
        gc.setFill(Color.BLUE);

        for (int i = 0; i < snakeBody.size(); i++) {
            if (i == 0) {
                gc.drawImage(new Image("/assets/snakeHead.png"), snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
            } else {
                gc.drawImage(new Image("/assets/snakeBody.png"), snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1);
            }
        }
    }

    public void isGameOver() {
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
        }
    }

    public void endMenu() {
        timeline.stop();
        EndGameScreen endGameScreen = new EndGameScreen();
        endGameScreen.setInformation(getInformation());
        endGameScreen.start(primaryStage);
    }

    public void drawScore() {
        gc.setFill(Color.GREEN);
        gc.setFont(new Font("Digital-2", 35));
        gc.fillText("Score: " + score, 10, 35);
    }

    public void makeGameHarder() {
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

    @Override
    public void setInformation(String string) {

    }

    @Override
    public String getInformation() {
        return String.valueOf(this.score);
    }
}
