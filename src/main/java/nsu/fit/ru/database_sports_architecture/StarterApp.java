package nsu.fit.ru.database_sports_architecture;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StarterApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setMaximized(true);
        stage.setTitle("OrangeTange");
        FXMLLoader fxmlLoader = getFXMLLoader();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    private static FXMLLoader getFXMLLoader(){
        return new FXMLLoader(StarterApp.class.getResource("starter.fxml"));
    }
    public static void menuDisconnect(Scene scene) throws IOException {
        scene.setRoot(getFXMLLoader().load());
    }
    public static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void main(String[] args) {
        launch();
    }
}