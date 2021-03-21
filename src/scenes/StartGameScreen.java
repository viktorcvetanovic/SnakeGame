/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Main;
import util.CommunicationWithComponentsInterface;

/**
 *
 * @author vikto
 */
public class StartGameScreen extends Application implements CommunicationWithComponentsInterface {

    private Integer score = 0;
    private static final int BUTTON_WIDTH = 150;
    private Button play;
    private Button skins;
    private Button scores;
    private Button quit;
    private Button login;
    private Button back;
    private BorderPane root;

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #123;");
        createMainMenu();
        Scene scene = new Scene(root, 400, 400);

        primaryStage.setTitle("PyGame");
        primaryStage.setScene(scene);
        primaryStage.show();

        play.setOnAction(e -> {
            new Main().start(primaryStage);
        });
        quit.setOnAction(e -> {
            Platform.exit();
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void createMainMenu() {
        //TOP 
        VBox vboxTop = new VBox();
        Label header = new Label("Dobrodosli u PyGame");
        vboxTop.setAlignment(Pos.CENTER);
        BorderPane.setMargin(vboxTop, new Insets(10));
        header.setTextFill(Color.web("#efcc00"));
        header.setFont(new Font("Digital-2", 24));
        Image image = new Image("/assets/snakelogo.png");
        ImageView imgView = new ImageView(image);
        imgView.setFitHeight(150);
        imgView.setFitWidth(150);

        vboxTop.getChildren().addAll(header, imgView);
        root.setTop(vboxTop);
        //CENTER
        VBox vboxCenter = new VBox();
        play = new Button("Igraj");
        play.setMinWidth(BUTTON_WIDTH);
        login = new Button("Prijavi se");
        login.setMinWidth(BUTTON_WIDTH);
        skins = new Button("Skinovi");
        skins.setMinWidth(BUTTON_WIDTH);
        scores = new Button("Rezultati");
        scores.setMinWidth(BUTTON_WIDTH);
        quit = new Button("Izadji");
        quit.setMinWidth(BUTTON_WIDTH);
        vboxCenter.getChildren().addAll(play, login, skins, scores, quit);
        vboxCenter.setAlignment(Pos.CENTER);
        vboxCenter.setSpacing(10);
        root.setCenter(vboxCenter);
        //BOTTOM
    }

    @Override
    public void setInformation(String string) {
        this.score = Integer.valueOf(string);
    }

    @Override
    public String getInformation() {
        return "";
    }

}
