package nsu.fit.ru.database_sports_architecture.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import nsu.fit.ru.database_sports_architecture.DBworckers.StarterBDW;

import java.net.URL;
import java.util.ResourceBundle;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class StarterController implements Initializable {
    @FXML
    PasswordField pass;
    @FXML
    TextField login;
    @FXML
    TextField port;
    @FXML
    TextField ip;
    @FXML
    Button start;
    @FXML
    Label err;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        start.setOnAction(actionEvent ->
            StarterBDW.start(ip.getText(), port.getText(), login.getText(), pass.getText(), start.getScene(), err));
    }
}