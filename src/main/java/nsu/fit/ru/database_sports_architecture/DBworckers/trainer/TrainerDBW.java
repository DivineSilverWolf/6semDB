package nsu.fit.ru.database_sports_architecture.DBworckers.trainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.Trainer;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;
import nsu.fit.ru.database_sports_architecture.models.sportsman.SportsmanModel;
import nsu.fit.ru.database_sports_architecture.models.trainer.TrainerModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

public class TrainerDBW {
    public static void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<Trainer> data, ResultSet rs, TableView<Trainer> table){
        ObservableList<Trainer> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table);
    }
    public static void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<Trainer> data, ResultSet rs, TableView<Trainer> table){
        ObservableList<Trainer> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table);
    }
    private static void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<Trainer> data, ObservableList<Trainer> newData, ResultSet rs, TableView<Trainer> table){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<Trainer> data, ObservableList<Trainer> newData) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            Trainer trainer = new Trainer(rs.getInt("T_ID"), rs.getString("T_NAME"), rs.getString("T_SURNAME"), rs.getString("T_PATRONYMIC"), rs.getString("T_TEL"), rs.getString("T_MAIL"));
            data.add(trainer);
            newData.add(trainer);
            flag.getAndIncrement();
        }
    }
    public static void t_NSPTM(Trainer trainer, Statement statement, String newName, String newSurname, String newPatronymic, String newTel, String newMail, TableView<Trainer> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE TRAINER SET T_NAME = '" + newName + "', T_SURNAME ='" + newSurname + "', T_PATRONYMIC = '" + newPatronymic +
                    "', T_TEL = '" + newTel +
                    "', T_MAIL = '" + newMail +  "' WHERE T_ID = " + trainer.getT_ID());
            TrainerModel.t_NSPTM(trainer, newName, newSurname, newPatronymic, newTel, newMail, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void remove(Statement statement, AtomicInteger flag, Trainer trainer, TableView<Trainer> tableView, ObservableList<Trainer> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM TRAINER WHERE T_ID = " + trainer.getT_ID());
            EnterRemoveModel.remove(trainer, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void  enter(Statement statement, String newName, String newSurname, String newPatronymic, String newTel, String newMail,  AtomicInteger flag, TableView<Trainer> tableView,ObservableList<Trainer> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet resultSet = statement1.executeQuery("SELECT MAX(T_ID) as mx FROM TRAINER");
            resultSet.next();
            int newValue = resultSet.getInt("mx") + 1;
            resultSet.close();
            statement1.executeUpdate("INSERT INTO TRAINER(T_ID, T_NAME, T_SURNAME, T_PATRONYMIC, T_TEL, T_MAIL) VALUES (" + newValue + ", '" + newName + "', '" + newSurname + "', '" + newPatronymic + "', '" + newTel + "', '" + newMail + "')");
            resultSet = statement1.executeQuery("SELECT * FROM TRAINER WHERE T_ID = " + newValue);
            resultSet.next();
            Trainer trainer = new Trainer(resultSet.getInt("T_ID"), resultSet.getString("T_NAME"),resultSet.getString("T_SURNAME"),resultSet.getString("T_PATRONYMIC"), resultSet.getString("T_TEL"),resultSet.getString("T_MAIL"));
            resultSet.close();
            EnterRemoveModel.enter(trainer, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
