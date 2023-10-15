package nsu.fit.ru.database_sports_architecture.DBworckers.sportsman;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.models.competition.OrganizerModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;
import nsu.fit.ru.database_sports_architecture.models.sportsman.SportsmanModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

public class SportsmanDBW {

    public static void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<Sportsman> data, ResultSet rs, TableView<Sportsman> table){
        ObservableList<Sportsman> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table);
    }
    public static void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<Sportsman> data, ResultSet rs, TableView<Sportsman> table){
        ObservableList<Sportsman> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table);
    }
    private static void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<Sportsman> data, ObservableList<Sportsman> newData, ResultSet rs, TableView<Sportsman> table){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<Sportsman> data, ObservableList<Sportsman> newData) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            Sportsman sportsman = new Sportsman(rs.getInt("S_ID"), rs.getString("S_NAME"), rs.getString("S_SURNAME"), rs.getString("S_PATRONYMIC"), rs.getString("S_TEL"), rs.getString("S_MAIL"));
            data.add(sportsman);
            newData.add(sportsman);
            flag.getAndIncrement();
        }
    }
    public static void s_NSPTM(Sportsman sportsman, Statement statement, String newName, String newSurname, String newPatronymic, String newTel, String newMail, TableView<Sportsman> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE SPORTSMAN SET S_NAME = '" + newName + "', S_SURNAME ='" + newSurname + "', S_PATRONYMIC = '" + newPatronymic +
                    "', S_TEL = '" + newTel +
                    "', S_MAIL = '" + newMail +  "' WHERE S_ID = " + sportsman.getS_ID());
            SportsmanModel.s_NSPTM(sportsman, newName, newSurname, newPatronymic, newTel, newMail, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void remove(Statement statement, AtomicInteger flag, Sportsman sportsman, TableView<Sportsman> tableView, ObservableList<Sportsman> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM SPORTSMAN WHERE S_ID = " + sportsman.getS_ID());
            EnterRemoveModel.remove(sportsman, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void  enter(Statement statement, String newName, String newSurname, String newPatronymic, String newTel, String newMail,  AtomicInteger flag, TableView<Sportsman> tableView,ObservableList<Sportsman> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet resultSet = statement1.executeQuery("SELECT MAX(S_ID) as mx FROM SPORTSMAN");
            resultSet.next();
            int newValue = resultSet.getInt("mx") + 1;
            resultSet.close();
            statement1.executeUpdate("INSERT INTO SPORTSMAN(S_ID, S_NAME, S_SURNAME, S_PATRONYMIC, S_TEL, S_MAIL) VALUES (" + newValue + ", '" + newName + "', '" + newSurname + "', '" + newPatronymic + "', '" + newTel + "', '" + newMail + "')");
            resultSet = statement1.executeQuery("SELECT * FROM SPORTSMAN WHERE S_ID = " + newValue);
            resultSet.next();
            Sportsman sportsman = new Sportsman(resultSet.getInt("S_ID"), resultSet.getString("S_NAME"),resultSet.getString("S_SURNAME"),resultSet.getString("S_PATRONYMIC"), resultSet.getString("S_TEL"),resultSet.getString("S_MAIL"));
            resultSet.close();
            EnterRemoveModel.enter(sportsman, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
