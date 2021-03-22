/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scenes;

import static customAlerts.CustomAlert.createCustomAlert;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.Main;
import util.CommunicateWithComponentsInterface;
import util.CommunicateWithServerInterface;

/**
 *
 * @author vikto
 */
public class StartGameScreen extends Application implements CommunicateWithComponentsInterface, CommunicateWithServerInterface {
    //******************user*********************//

    String user = null;
    private Integer score = 0;

    //mainMenuButtons
    private static final int BUTTON_WIDTH = 150;
    private final Button play = new Button("Igraj");
    private final Button skins = new Button("Skinovi");
    private final Button scores = new Button("Rezultati");
    private final Button quit = new Button("Izadji");
    private final Button login = new Button("Prijavi se");
    private final Button back = new Button("back");
    private BorderPane root;

    //login page Buttons
    Button register = new Button("Registruj se");
    Button loginChecker = new Button("Prijavi se");

    //register page Buttons
    Button registerChecker = new Button("Registruj se");

    @Override

    public void start(Stage primaryStage) {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #123;");
        createMainMenu();
        Scene scene = new Scene(root, 400, 400);

        primaryStage.setTitle("PyGame");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/assets/snakelogo.png"));
        primaryStage.show();

        play.setOnAction(e -> {
            if (user != null) {
                new Main().start(primaryStage);
            } else {
                createCustomAlert("Greska", "Morate biti ulogovani da bi igrali");
            }

        });

        login.setOnAction(e -> {
            createLogin();
        });
        quit.setOnAction(e -> {
            Platform.exit();
        });

        back.setOnAction(e -> {
            createMainMenu();
        });
        register.setOnAction(e -> {
            createRegister();
        });

        //buttons for checking info
        loginChecker.setOnAction(e -> {

        });
        registerChecker.setOnAction(e -> {

        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void createMainMenu() {
        root.getChildren().clear();
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

        play.setMinWidth(BUTTON_WIDTH);
        login.setMinWidth(BUTTON_WIDTH);
        skins.setMinWidth(BUTTON_WIDTH);

        scores.setMinWidth(BUTTON_WIDTH);

        quit.setMinWidth(BUTTON_WIDTH);
        vboxCenter.getChildren().addAll(play, login, skins, scores, quit);
        vboxCenter.setAlignment(Pos.CENTER);
        vboxCenter.setSpacing(10);
        root.setCenter(vboxCenter);
        //BOTTOM
    }

    public void createLogin() {
        root.getChildren().clear();
        VBox vbox = new VBox();
        Label usernameLabel = new Label("Unesite username");
        usernameLabel.setTextFill(Color.web("#efcc00"));
        usernameLabel.setFont(new Font("Digital-2", 18));
        usernameLabel.setMinSize(150, 50);
        TextField username = new TextField();
        username.setMinWidth(150);
        username.setPrefWidth(150);
        username.setMaxWidth(150);
        Label passwordLabel = new Label("Unesite sifru");
        passwordLabel.setTextFill(Color.web("#efcc00"));
        passwordLabel.setFont(new Font("Digital-2", 18));
        passwordLabel.setMinSize(150, 50);
        PasswordField password = new PasswordField();
        password.setMinWidth(150);
        password.setPrefWidth(150);
        password.setMaxWidth(150);

        //butons
        HBox hbox = new HBox();

        hbox.getChildren().addAll(register, loginChecker);
        hbox.setSpacing(8);
        hbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(8);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(back, usernameLabel, username, passwordLabel, password, hbox);
        vbox.setAlignment(Pos.CENTER);
        root.setTop(vbox);
    }

    public void createRegister() {
        root.getChildren().clear();
        VBox vbox = new VBox();
        Label usernameLabel = new Label("Unesite username");
        usernameLabel.setTextFill(Color.web("#efcc00"));
        usernameLabel.setFont(new Font("Digital-2", 18));
        usernameLabel.setMinSize(150, 50);
        TextField username = new TextField();
        username.setMinWidth(150);
        username.setPrefWidth(150);
        username.setMaxWidth(150);
        Label passwordLabel = new Label("Unesite sifru");
        passwordLabel.setTextFill(Color.web("#efcc00"));
        passwordLabel.setFont(new Font("Digital-2", 18));
        passwordLabel.setMinSize(150, 50);
        PasswordField password = new PasswordField();
        password.setMinWidth(150);
        password.setPrefWidth(150);
        password.setMaxWidth(150);
        Label emailLabel = new Label("Unesite email");
        emailLabel.setTextFill(Color.web("#efcc00"));
        emailLabel.setFont(new Font("Digital-2", 18));
        emailLabel.setMinSize(150, 50);
        PasswordField email = new PasswordField();
        email.setMinWidth(150);
        email.setPrefWidth(150);
        email.setMaxWidth(150);
        vbox.setSpacing(8);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(back, usernameLabel, username, passwordLabel, password, emailLabel, email, registerChecker);
        vbox.setAlignment(Pos.CENTER);
        root.setTop(vbox);
    }

    @Override
    public void setInformation(String string) {
        this.score = Integer.valueOf(string);
    }

    @Override
    public String getInformation() {
        return "";
    }

    @Override
    public void sendInfoToServer(Socket socket) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void readInfoFromServer(Socket socket) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
