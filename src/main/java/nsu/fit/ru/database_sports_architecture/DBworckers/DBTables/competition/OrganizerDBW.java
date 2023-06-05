package nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.MembersCompetition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.models.club_inf.ClubModel;
import nsu.fit.ru.database_sports_architecture.models.competition.OrganizerModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

public class OrganizerDBW {
    public static void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<Organizer> data, ResultSet rs, TableView<Organizer> table){
        ObservableList<Organizer> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table);
    }
    public static void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<Organizer> data, ResultSet rs, TableView<Organizer> table){
        ObservableList<Organizer> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table);
    }
    private static void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<Organizer> data, ObservableList<Organizer> newData, ResultSet rs, TableView<Organizer> table){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<Organizer> data, ObservableList<Organizer> newData) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            Organizer organizer = new Organizer(rs.getInt("ORG_ID"), rs.getString("ORG_NAME"),
                    rs.getString("ORG_TEL"), rs.getString("ORG_S_MAIL"));
            data.add(organizer);
            newData.add(organizer);
            flag.getAndIncrement();
        }
    }


    public static void org_NTM(Organizer organizer, Statement statement, String newName, String newTel, String newMail, TableView<Organizer> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE ORGANIZER SET ORG_NAME = '" + newName + "', ORG_TEL = '" + newTel +
                    "', ORG_S_MAIL = '" + newMail +  "' WHERE ORG_ID = " + organizer.getORG_ID());
            OrganizerModel.org_NTM(organizer, newName, newTel, newMail, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void remove(Statement statement, AtomicInteger flag, Organizer organizer, TableView<Organizer> tableView, ObservableList<Organizer> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM ORGANIZER WHERE ORG_ID = " + organizer.getORG_ID());
            EnterRemoveModel.remove(organizer, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void  enter(Statement statement, String newName, String newTel, String newMail,  AtomicInteger flag, TableView<Organizer> tableView,ObservableList<Organizer> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet resultSet = statement1.executeQuery("SELECT MAX(ORG_ID) as mx FROM ORGANIZER");
            resultSet.next();
            int newValue = resultSet.getInt("mx") + 1;
            resultSet.close();
            statement1.executeUpdate("INSERT INTO ORGANIZER(ORG_ID, ORG_NAME, ORG_TEL, ORG_S_MAIL) VALUES (" + newValue + ", '" + newName + "','" + newTel + "', '" + newMail + "')");
            resultSet = statement1.executeQuery("SELECT * FROM ORGANIZER WHERE ORG_ID = " + newValue);
            resultSet.next();
            Organizer organizer = new Organizer(resultSet.getInt("ORG_ID"), resultSet.getString("ORG_NAME"), resultSet.getString("ORG_TEL"), resultSet.getString("ORG_S_MAIL"));
            resultSet.close();
            EnterRemoveModel.enter(organizer, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
