/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customAlerts;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author vikto
 */
public class CustomAlert {

    public static void createCustomAlert(String headerText, String bodyText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(headerText);
        alert.setHeaderText(headerText);
        ImageView icon = new ImageView("/assets/snakelogo.png");
        icon.setFitHeight(48);
        icon.setFitWidth(48);
        alert.getDialogPane().setGraphic(icon);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/assets/snakelogo.png"));
        alert.setContentText(bodyText);

        alert.show();
    }
}
