package nsu.fit.ru.database_sports_architecture.views.special_assignments;

import javafx.scene.Scene;
import nsu.fit.ru.database_sports_architecture.views.StarterViewer;

import java.sql.Statement;

public class MenuVSA {
    public static void menu(Statement statement, Scene scene, String login){
        StarterViewer.start(login, scene, statement);
    }
}
