package nsu.fit.ru.database_sports_architecture.DBworckers.trainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.MembersClubHistory;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Competition;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.Trainer;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.TrainerSportsmanHistory;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.club_inf.MembersClubHistoryDBW;
import nsu.fit.ru.database_sports_architecture.models.club_inf.MembersClubHistoryModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.RefreshTableModel;
import nsu.fit.ru.database_sports_architecture.models.trainer.TrainerSportsmanHistoryModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

public class TrainerSportsmanHistoryDBW {


    @FunctionalInterface
    public interface TriFunction<T, U, V, P, R> {
        R apply(T t, U u, V v, P p);
    }
    public static <T> void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, TriFunction<TypesSports, Trainer, Sportsman, TrainerSportsmanHistory, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table, constructor);
    }
    public static <T> void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, TriFunction<TypesSports, Trainer, Sportsman, TrainerSportsmanHistory, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table, constructor);
    }
    private static <T> void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ObservableList<T> newData, ResultSet rs, TableView<T> table, TriFunction<TypesSports, Trainer, Sportsman, TrainerSportsmanHistory, T> constructor){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData, constructor);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static <T> void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<T> data, ObservableList<T> newData, TriFunction<TypesSports, Trainer, Sportsman, TrainerSportsmanHistory, T> constructor) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            Trainer trainer = new Trainer(rs.getInt("T_ID"), rs.getString("T_NAME"),
                    rs.getString("T_SURNAME"),rs.getString("T_PATRONYMIC"),rs.getString("T_TEL"),rs.getString("T_MAIL"));
            Sportsman sportsman = new Sportsman(rs.getInt("S_ID"), rs.getString("S_NAME"),
                    rs.getString("S_SURNAME"),rs.getString("S_PATRONYMIC"),rs.getString("S_TEL"),rs.getString("S_MAIL"));
            TrainerSportsmanHistory trainerSportsmanHistory = new TrainerSportsmanHistory(rs.getInt("TSH_ID"),rs.getInt("T_ID"), rs.getInt("TS_ID"), rs.getInt("S_ID"),rs.getDate("TSH_START_DATE"),rs.getDate("TSH_END_DATE"));
            TypesSports typesSports = new TypesSports(rs.getInt("TS_ID"),null,rs.getString("TS_NAME"), null);
            T clubTypesSports = constructor.apply(typesSports, trainer, sportsman, trainerSportsmanHistory);
            data.add(clubTypesSports);
            newData.add(clubTypesSports);
            flag.getAndIncrement();
        }
    }




    public static void dop_tables(Statement statement, ObservableList<Trainer> dop_trainer_data, ObservableList<Sportsman> dop_sportsman_data, ObservableList<TypesSports> dop_types_sports_data,TableView<Trainer> dop_trainer, TableView<Sportsman> dop_sportsman, TableView<TypesSports> dop_types_sports){
        try(Statement statement1 = statement.getConnection().createStatement()){
            ResultSet resultSet = statement1.executeQuery("SELECT * FROM TRAINER");
            while(resultSet.next()){
                Trainer trainer = new Trainer(resultSet.getInt("T_ID"),resultSet.getString("T_NAME"),resultSet.getString("T_SURNAME"),resultSet.getString("T_PATRONYMIC"), resultSet.getString("T_TEL"), resultSet.getString("T_MAIL"));
                dop_trainer_data.add(trainer);
            }
            resultSet.close();
            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM Sportsman");
            while (resultSet1.next()){
                Sportsman sportsman = new Sportsman(resultSet1.getInt("S_ID"), resultSet1.getString("S_NAME"),
                        resultSet1.getString("S_SURNAME"), resultSet1.getString("S_PATRONYMIC"),
                        resultSet1.getString("S_TEL"), resultSet1.getString("S_MAIL"));
                dop_sportsman_data.add(sportsman);
            }
            resultSet1.close();
            ResultSet resultSet2 = statement1.executeQuery("SELECT * FROM TYPES_SPORTS");
            while (resultSet2.next()){
                TypesSports typesSports = new TypesSports(resultSet2.getInt("TS_ID"), null,resultSet2.getString("TS_NAME"), null);
                dop_types_sports_data.add(typesSports);
            }
            resultSet1.close();
            TrainerSportsmanHistoryModel.dop_tables(dop_trainer_data,dop_sportsman_data, dop_types_sports_data ,dop_trainer,dop_sportsman, dop_types_sports);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static <T> void anotherSportsman(TrainerSportsmanHistory trainerSportsmanHistory, Sportsman sportsman, Statement statement, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE TRAINER_SPORTSMAN_HISTORY SET S_ID = " + sportsman.getS_ID() + " WHERE TSH_ID = " + trainerSportsmanHistory.getTSH_ID());
            RefreshTableModel.refresh(tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void anotherTrainer(TrainerSportsmanHistory trainerSportsmanHistory, Trainer trainer, Statement statement, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE TRAINER_SPORTSMAN_HISTORY SET T_ID = " + trainer.getT_ID() + " WHERE TSH_ID = " + trainerSportsmanHistory.getTSH_ID());
            RefreshTableModel.refresh(tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void anotherTypesSports(TrainerSportsmanHistory trainerSportsmanHistory, TypesSports typesSports, Statement statement, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE TRAINER_SPORTSMAN_HISTORY SET TS_ID = " + typesSports.getTS_ID() + " WHERE TSH_ID = " + trainerSportsmanHistory.getTSH_ID());
            RefreshTableModel.refresh(tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void start_date(TrainerSportsmanHistory trainerSportsmanHistory, Statement statement, String newValue){
        try(Statement statement1 = statement.getConnection().createStatement()){
            statement1.executeUpdate("UPDATE TRAINER_SPORTSMAN_HISTORY SET TSH_START_DATE = TO_DATE( '" + newValue + "', 'YYYY-MM-DD') WHERE TSH_ID = " + trainerSportsmanHistory.getTSH_ID());
            TrainerSportsmanHistoryModel.start_date(trainerSportsmanHistory, newValue);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void end_date(TrainerSportsmanHistory trainerSportsmanHistory, Statement statement, String newValue, TableView<T> tableView){


        try(Statement statement1 = statement.getConnection().createStatement()){
            if (!newValue.equals("N/A") && !newValue.equals(""))
                statement1.executeUpdate("UPDATE TRAINER_SPORTSMAN_HISTORY SET TSH_END_DATE = TO_DATE( '" + newValue + "', 'YYYY-MM-DD') WHERE TSH_ID = " + trainerSportsmanHistory.getTSH_ID());
            else {
                statement1.executeUpdate("UPDATE TRAINER_SPORTSMAN_HISTORY SET TSH_END_DATE = NULL WHERE TSH_ID = " + trainerSportsmanHistory.getTSH_ID());
                newValue = null;
            }
            TrainerSportsmanHistoryModel.end_date(trainerSportsmanHistory, newValue);
            RefreshTableModel.refresh(tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void droppedOut(TrainerSportsmanHistory trainerSportsmanHistory, Statement statement, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE TRAINER_SPORTSMAN_HISTORY SET TSH_END_DATE = SYSDATE WHERE TSH_ID = " + trainerSportsmanHistory.getTSH_ID());
            ResultSet resultSet = statement1.executeQuery("SELECT TSH_END_DATE FROM TRAINER_SPORTSMAN_HISTORY WHERE TSH_ID = " + trainerSportsmanHistory.getTSH_ID());
            resultSet.next();
            trainerSportsmanHistory.setTSH_END_DATE(resultSet.getDate("TSH_END_DATE"));
            resultSet.close();
            RefreshTableModel.refresh(tableView);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void remove(TrainerSportsmanHistory trainerSportsmanHistory, Statement statement, AtomicInteger flag, T sportsmansClubHistory, TableView<T> tableView, ObservableList<T> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM TRAINER_SPORTSMAN_HISTORY WHERE TSH_ID = " + trainerSportsmanHistory.getTSH_ID());
            EnterRemoveModel.remove(sportsmansClubHistory, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void enter(Statement statement, String sTel, String sMail, String tTel, String tMail, String sportName,
                                 String start_date, String end_date, AtomicInteger flag, TableView<T> tableView,ObservableList<T> data,
                                 TriFunction<TypesSports, Trainer, Sportsman, TrainerSportsmanHistory, T> constructor){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet rs1 = sTel == null ? statement1.executeQuery("SELECT * FROM Sportsman s " +
                    "WHERE s.S_MAIL = '" + sMail + "'") :
                    statement1.executeQuery("SELECT * FROM Sportsman s WHERE s.S_TEL = '" + sTel + "'");
            rs1.next();
            int NEW_S_ID = rs1.getInt("S_ID");
            String NEW_S_NAME = rs1.getString("S_NAME");
            String NEW_S_SURNAME = rs1.getString("S_SURNAME");
            String NEW_S_PATRONYMIC = rs1.getString("S_PATRONYMIC");
            String NEW_S_TEL = rs1.getString("S_TEL");
            String NEW_S_MAIL = rs1.getString("S_MAIL");
            Sportsman sportsman = new Sportsman(NEW_S_ID,NEW_S_NAME,NEW_S_SURNAME,NEW_S_PATRONYMIC,NEW_S_TEL,NEW_S_MAIL);
            rs1.close();
            rs1 = tTel == null ? statement1.executeQuery("SELECT * FROM TRAINER t " +
                    "WHERE t.T_MAIL = '" + tMail + "'") :
                    statement1.executeQuery("SELECT * FROM TRAINER t WHERE t.T_TEL = '" + tTel + "'");
            rs1.next();
            int NEW_T_ID = rs1.getInt("T_ID");
            String NEW_T_NAME = rs1.getString("T_NAME");
            String NEW_T_SURNAME = rs1.getString("T_SURNAME");
            String NEW_T_PATRONYMIC = rs1.getString("T_PATRONYMIC");
            String NEW_T_TEL = rs1.getString("T_TEL");
            String NEW_T_MAIL = rs1.getString("T_MAIL");
            Trainer trainer = new Trainer(NEW_T_ID,NEW_T_NAME,NEW_T_SURNAME,NEW_T_PATRONYMIC,NEW_T_TEL,NEW_T_MAIL);

            rs1 = statement1.executeQuery("SELECT * FROM TYPES_SPORTS WHERE TS_NAME = '" + sportName + "'");
            rs1.next();
            int NEW_TS_ID = rs1.getInt("TS_ID");
            String NEW_TS_NAME = rs1.getString("TS_NAME");
            TypesSports typesSports = new TypesSports(NEW_TS_ID, null, NEW_TS_NAME, null);
            rs1.close();
            start_date = start_date == null ? "SYSDATE" : "TO_DATE( '" + start_date + "', 'YYYY-MM-DD')";
            end_date = end_date == null ? "NULL" : "TO_DATE( '" + end_date + "', 'YYYY-MM-DD')";

            rs1 = statement1.executeQuery("SELECT MAX(TSH_ID) mx FROM TRAINER_SPORTSMAN_HISTORY");
            rs1.next();
            int NEW_TSH_ID = rs1.getInt("mx") + 1;
            rs1.close();
            statement1.executeUpdate("INSERT INTO TRAINER_SPORTSMAN_HISTORY(TSH_ID, T_ID, TS_ID, S_ID, TSH_START_DATE, TSH_END_DATE) VALUES (" + NEW_TSH_ID + ", " + NEW_T_ID + ", " + NEW_TS_ID + ", " + NEW_S_ID + ", " + start_date + ", " + end_date + ")");

            rs1 = statement1.executeQuery("SELECT * FROM TRAINER_SPORTSMAN_HISTORY WHERE TSH_ID = " + NEW_TSH_ID);
            rs1.next();
            TrainerSportsmanHistory trainerSportsmanHistory = new TrainerSportsmanHistory(rs1.getInt("TSH_ID"),rs1.getInt("T_ID"),rs1.getInt("TS_ID"),rs1.getInt("S_ID"),rs1.getDate("TSH_START_DATE"),rs1.getDate("TSH_END_DATE"));
            rs1.close();
            T t = constructor.apply(typesSports, trainer, sportsman, trainerSportsmanHistory);



            EnterRemoveModel.enter(t, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
