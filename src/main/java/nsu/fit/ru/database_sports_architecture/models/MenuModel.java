package nsu.fit.ru.database_sports_architecture.models;

import javafx.scene.Scene;
import nsu.fit.ru.database_sports_architecture.views.MenuViewer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

public class MenuModel {
    public static void disconnect(Scene scene) throws IOException {
        MenuViewer.disconnect(scene);
    }
    public static void club(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.club(statement,scene,rs, login);
    }
    public static void clubHistory(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.clubHistory(statement,scene,rs, login);
    }
    public static void competition(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.competition(statement,scene,rs, login);
    }
    public static void members_competition(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.members_competition(statement,scene,rs, login);
    }
    public static void organizer(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.organizer(statement,scene,rs, login);
    }
    public static void sportsmans(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.sportsmans(statement,scene,rs, login);
    }
    public static void trainer(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.trainer(statement,scene,rs, login);
    }
    public static void winners(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.winners(statement,scene,rs, login);
    }
    public static void sportsFacilityInformation(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.sportsFacilityInformation(statement,scene,rs, login);
    }
    public static void typeSports(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.typeSports(statement,scene,rs, login);
    }
    public static void typesSportsInfrastructures(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.typesSportsInfrastructures(statement,scene,rs, login);
    }
    public static void typesTSINames(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.typesTSINames(statement,scene,rs, login);
    }
    public static void ranks(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.ranks(statement,scene,rs, login);
    }
    public static void profileInfoTrainer(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.profileInfoTrainer(statement,scene,rs, login);
    }
    public static void trainerSportsmanHistory(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        MenuViewer.trainerSportsmanHistory(statement,scene,rs, login);
    }
}
