package nsu.fit.ru.database_sports_architecture.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Competition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.MembersCompetition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Winners;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesSportsInfrastructures;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesTSINames;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Ranks;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.ProfileInfoTrainer;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.Trainer;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.TrainerSportsmanHistory;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.club_inf.ClubController;
import nsu.fit.ru.database_sports_architecture.controllers.competition.CompetitionController;
import nsu.fit.ru.database_sports_architecture.controllers.club_inf.MembersClubHistoryController;
import nsu.fit.ru.database_sports_architecture.controllers.competition.MembersCompetitionController;
import nsu.fit.ru.database_sports_architecture.controllers.competition.OrganizerController;
import nsu.fit.ru.database_sports_architecture.controllers.competition.WinnersController;
import nsu.fit.ru.database_sports_architecture.controllers.sports_facility.general_sf.SportsFacilityInformationController;
import nsu.fit.ru.database_sports_architecture.controllers.sports_facility.general_sf.TypesSportsInfrastructuresController;
import nsu.fit.ru.database_sports_architecture.controllers.sports_facility.general_sf.TypesTSINamesController;
import nsu.fit.ru.database_sports_architecture.controllers.sportsman.RanksController;
import nsu.fit.ru.database_sports_architecture.controllers.sportsman.SportsmanController;
import nsu.fit.ru.database_sports_architecture.controllers.trainer.ProfileInfoTrainerController;
import nsu.fit.ru.database_sports_architecture.controllers.trainer.TrainerController;
import nsu.fit.ru.database_sports_architecture.controllers.trainer.TrainerSportsmanHistoryController;
import nsu.fit.ru.database_sports_architecture.controllers.types_sports.TypesSportsController;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;

public class MenuViewer {
    public static void disconnect(Scene scene) throws IOException {
        StarterApp.menuDisconnect(scene);
    }
    public static void club(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(Club.class.getResource("club.fxml"));
        loader.setController(new ClubController(statement, rs, login));
        view(scene, loader);
    }
    public static void clubHistory(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(Club.class.getResource("members_club_history.fxml"));
        loader.setController(new MembersClubHistoryController(statement, rs, login));
        view(scene, loader);
    }
    public static void competition(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(Competition.class.getResource("competition.fxml"));
        loader.setController(new CompetitionController(statement, rs, login));
        view(scene, loader);
    }
    public static void members_competition(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(MembersCompetition.class.getResource("members_competition.fxml"));
        loader.setController(new MembersCompetitionController(statement, rs, login));
        view(scene, loader);
    }
    public static void organizer(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(MembersCompetition.class.getResource("organizer.fxml"));
        loader.setController(new OrganizerController(statement, rs, login));
        view(scene, loader);
    }
    public static void sportsmans(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(Sportsman.class.getResource("sportsman.fxml"));
        loader.setController(new SportsmanController(statement, rs, login));
        view(scene, loader);
    }
    public static void trainer(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(Trainer.class.getResource("trainer.fxml"));
        loader.setController(new TrainerController(statement, rs, login));
        view(scene, loader);
    }
    public static void winners(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(Winners.class.getResource("winners.fxml"));
        loader.setController(new WinnersController(statement, rs, login));
        view(scene, loader);
    }
    public static void sportsFacilityInformation(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(SportsFacilityInformation.class.getResource("sports_facility_information.fxml"));
        loader.setController(new SportsFacilityInformationController(statement, rs, login));
        view(scene, loader);
    }
    public static void typeSports(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(TypesSports.class.getResource("types_sports.fxml"));
        loader.setController(new TypesSportsController(statement, rs, login));
        view(scene, loader);
    }
    public static void typesSportsInfrastructures(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(TypesSportsInfrastructures.class.getResource("types_sports_infrastructures.fxml"));
        loader.setController(new TypesSportsInfrastructuresController(statement, rs, login));
        view(scene, loader);
    }
    public static void typesTSINames(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(TypesTSINames.class.getResource("types_tsi_names.fxml"));
        loader.setController(new TypesTSINamesController(statement, rs, login));
        view(scene, loader);
    }
    public static void ranks(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(Ranks.class.getResource("ranks.fxml"));
        loader.setController(new RanksController(statement, rs, login));
        view(scene, loader);
    }
    public static void profileInfoTrainer(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(ProfileInfoTrainer.class.getResource("profile_info_trainer.fxml"));
        loader.setController(new ProfileInfoTrainerController(statement, rs, login));
        view(scene, loader);
    }
    public static void trainerSportsmanHistory(Statement statement, Scene scene, ResultSet rs, String login) throws IOException {
        FXMLLoader loader = getFXMLLoader(TrainerSportsmanHistory.class.getResource("trainer_sportsman_history.fxml"));
        loader.setController(new TrainerSportsmanHistoryController(statement, rs, login));
        view(scene, loader);
    }
    private static FXMLLoader getFXMLLoader(URL url){
        return new FXMLLoader(url);
    }
    public static void view(Scene scene, FXMLLoader fxmlLoader) throws IOException {
        scene.setRoot(fxmlLoader.load());
    }
}
