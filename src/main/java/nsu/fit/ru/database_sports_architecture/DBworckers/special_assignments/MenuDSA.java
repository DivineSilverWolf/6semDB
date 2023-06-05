package nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments;

import javafx.scene.Scene;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.MenuMSA;

import java.sql.Statement;

public class MenuDSA {
    public static void menu(Statement statement, Scene scene, String login){
        MenuMSA.menu(statement, scene, login);
    }
}
