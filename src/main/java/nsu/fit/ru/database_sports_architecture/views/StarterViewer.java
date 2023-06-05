package nsu.fit.ru.database_sports_architecture.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.MenuController;

import java.io.IOException;
import java.sql.Statement;

public class StarterViewer {
    public static void
    start(String login, Scene scene, Statement statement){
        FXMLLoader fxmlLoader = new FXMLLoader(StarterApp.class.getResource("menu.fxml"));
        fxmlLoader.setController(new MenuController(login, statement));
        try {
            scene.setRoot(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void err(Label err, String errStr){
        err.setText(errStr);
    }
}
