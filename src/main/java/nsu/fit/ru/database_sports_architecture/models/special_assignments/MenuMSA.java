package nsu.fit.ru.database_sports_architecture.models.special_assignments;

import javafx.scene.Scene;
import nsu.fit.ru.database_sports_architecture.views.special_assignments.MenuVSA;

import java.sql.Statement;

public class MenuMSA {
    public static void menu(Statement statement, Scene scene, String login){
        MenuVSA.menu(statement, scene, login);
    }
}
