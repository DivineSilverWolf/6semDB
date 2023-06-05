package nsu.fit.ru.database_sports_architecture.models;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import nsu.fit.ru.database_sports_architecture.views.StarterViewer;

import java.sql.Statement;

public class StarterModel {
    public static void start(String login, Scene scene, Label err, String errStr, Statement statement){
        if (statement != null)
            StarterViewer.start(login,scene, statement);
        else if (errStr != null) {
            StarterViewer.err(err, errStr);
        }
    }
}
