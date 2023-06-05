package nsu.fit.ru.database_sports_architecture.DBworckers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.MenuController;
import nsu.fit.ru.database_sports_architecture.models.StarterModel;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class StarterBDW {
    public static void start(String ip, String port, String login, String pass, Scene scene, Label err){
        try {
            DriverManager.registerDriver (new oracle.jdbc.OracleDriver());
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Statement statement= bdPostGet(ip, port, login, pass);
            postModel(login, scene, err, null, statement);
        } catch (SQLException | ClassNotFoundException e) {
            postModel(login, scene, err, e.toString(), null);
            throw new RuntimeException(e);
        }

    }
    public static Statement bdPostGet(String ip, String port, String login, String pass) throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.setProperty("user", login);
        connectionProps.setProperty("password", pass);
        connectionProps.setProperty("oracle.net.CONNECT_TIMEOUT", "500"); // значение в миллисекундах
        connectionProps.setProperty("javax.net.connectTimeout", "5000"); // таймаут на подключение
        connectionProps.setProperty("oracle.jdbc.ReadTimeout", "5000"); // таймаут ожидания данных от сервера
        return DriverManager.getConnection("jdbc:oracle:thin:@" + ip + ":" + port + ":", connectionProps).createStatement();
    }
    public static void postModel(String login, Scene scene, Label err, String errStr, Statement statement){
        StarterModel.start(login,scene,err,errStr,statement);
    }

}
