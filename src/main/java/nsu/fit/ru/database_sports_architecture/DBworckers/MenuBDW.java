package nsu.fit.ru.database_sports_architecture.DBworckers;

import javafx.scene.Scene;
import nsu.fit.ru.database_sports_architecture.models.MenuModel;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MenuBDW {
    public static void disconnect(Statement statement, Scene scene) throws SQLException, IOException {
        MenuModel.disconnect(scene);
        statement.close();
    }

    public static void club(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM CLUB c JOIN TYPES_SPORTS ts on c.TS_ID = ts.TS_ID");
        MenuModel.club(statement,scene,rs, login);
    }
    public static void clubHistory(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT m.MCH_ID, m.S_ID, m.CL_ID, MCH_START_DATE, MCH_END_DATE, CL_NAME, CL_TEL, S_NAME, S_SURNAME, S_PATRONYMIC, S_TEL, S_MAIL FROM MEMBERS_CLUB_HISTORY m JOIN Club c on c.CL_ID = m.CL_ID JOIN Sportsman s on s.S_ID = m.S_ID");
        MenuModel.clubHistory(statement,scene,rs, login);
    }
    public static void competition(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM Competition c JOIN organizer o on c.ORG_ID = o.ORG_ID" +
                " JOIN Types_Sports ts on ts.TS_ID = c.TS_ID" +
                " JOIN Sports_Facility_Information sfi on sfi.SFI_ID = c.SFI_ID");
        MenuModel.competition(statement,scene,rs, login);
    }

    public static void members_competition(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM MEMBERS_COMPETITION mc JOIN SPORTSMAN s on mc.S_ID = s.S_ID JOIN Club c on c.CL_ID = mc.CL_ID JOIN COMPETITION com on com.COM_ID = mc.COM_ID");
        MenuModel.members_competition(statement,scene,rs, login);
    }
    public static void organizer(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM ORGANIZER");
        MenuModel.organizer(statement,scene,rs, login);
    }
    public static void sportsmans(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM SPORTSMAN");
        MenuModel.sportsmans(statement,scene,rs, login);
    }
    public static void trainer(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM TRAINER");
        MenuModel.trainer(statement,scene,rs, login);
    }
    public static void winners(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM WINNERS JOIN COMPETITION C on C.COM_ID = WINNERS.COM_ID JOIN SPORTSMAN S on WINNERS.S_ID = S.S_ID");
        MenuModel.winners(statement,scene,rs, login);
    }
    public static void sportsFacilityInformation(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM SPORTS_FACILITY_INFORMATION sfi JOIN TYPES_SPORTS_INFRASTRUCTURES tsi on sfi.TSI_ID = tsi.TSI_ID");
        MenuModel.sportsFacilityInformation(statement,scene,rs, login);
    }
    public static void typeSports(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM TYPES_SPORTS ts JOIN TYPES_SPORTS_INFRASTRUCTURES tsi on ts.TSI_ID = tsi.TSI_ID");
        MenuModel.typeSports(statement,scene,rs, login);
    }
    public static void typesSportsInfrastructures(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM TYPES_SPORTS_INFRASTRUCTURES");
        MenuModel.typesSportsInfrastructures(statement,scene,rs, login);
    }
    public static void typesTSINames(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM TYPES_TSI_NAMES");
        MenuModel.typesTSINames(statement,scene,rs, login);
    }
    public static void ranks(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM RANKS JOIN SPORTSMAN S on S.S_ID = RANKS.S_ID JOIN TYPES_SPORTS TS on RANKS.TS_ID = TS.TS_ID");
        MenuModel.ranks(statement,scene,rs, login);
    }
    public static void profileInfoTrainer(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM PROFILE_INFO_TRAINER JOIN TRAINER T on T.T_ID = PROFILE_INFO_TRAINER.T_ID JOIN TYPES_SPORTS TS on PROFILE_INFO_TRAINER.TS_ID = TS.TS_ID");
        MenuModel.profileInfoTrainer(statement,scene,rs, login);
    }
    public static void trainerSportsmanHistory(Statement statement, Scene scene, String login) throws SQLException, IOException {
        ResultSet rs = statement.executeQuery("SELECT * FROM TRAINER_SPORTSMAN_HISTORY JOIN SPORTSMAN S on TRAINER_SPORTSMAN_HISTORY.S_ID = S.S_ID JOIN TRAINER T on TRAINER_SPORTSMAN_HISTORY.T_ID = T.T_ID  JOIN TYPES_SPORTS TS on TRAINER_SPORTSMAN_HISTORY.TS_ID = TS.TS_ID");
        MenuModel.trainerSportsmanHistory(statement,scene,rs, login);
    }
}
