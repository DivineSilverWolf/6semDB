package nsu.fit.ru.database_sports_architecture.DBworckers.trainer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Ranks;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.ProfileInfoTrainer;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.Trainer;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.DBworckers.sportsman.RanksDBW;
import nsu.fit.ru.database_sports_architecture.controllers.special_assignments.GetterTextOrNullTextField;
import nsu.fit.ru.database_sports_architecture.models.competition.OrganizerModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;
import nsu.fit.ru.database_sports_architecture.models.trainer.ProfileInfoTrainerModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class ProfileInfoTrainerDBW {
    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }
    public static void dop_tables(Statement statement, ObservableList<Trainer> trainers, TableView<Trainer> dop_table_T, ObservableList<TypesSports> typesSports, TableView<TypesSports> dop_table_TS){
        try(Statement statement1 = statement.getConnection().createStatement()){
            ResultSet resultSet = statement1.executeQuery("SELECT * FROM TRAINER");
            while (resultSet.next()){
                Trainer trainer = new Trainer(resultSet.getInt("T_ID"), resultSet.getString("T_NAME"), resultSet.getString("T_SURNAME"),resultSet.getString("T_PATRONYMIC"),resultSet.getString("T_TEL"),resultSet.getString("T_MAIL"));
                trainers.add(trainer);
            }
            resultSet.close();
            resultSet = statement1.executeQuery("SELECT * FROM TYPES_SPORTS");
            while (resultSet.next()){
                TypesSports typesSports1 = new TypesSports(resultSet.getInt("TS_ID"),null, resultSet.getString("TS_NAME"), null);
                typesSports.add(typesSports1);
            }
            dop_table_T.setItems(trainers);
            dop_table_TS.setItems(typesSports);
        }
        catch (SQLException e){
            throw new  RuntimeException(e);
        }
    }
    public static void newTrainer(ProfileInfoTrainer profileInfoTrainer, Trainer trainer, Statement statement){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE PROFILE_INFO_TRAINER SET T_ID = " + trainer.getT_ID() + " WHERE T_ID = " + profileInfoTrainer.getT_ID() + " AND TS_ID = " + profileInfoTrainer.getTS_ID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void newTypesSports(ProfileInfoTrainer profileInfoTrainer, TypesSports typesSports, Statement statement){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE PROFILE_INFO_TRAINER SET TS_ID = " + typesSports.getTS_ID() + " WHERE T_ID = " + profileInfoTrainer.getT_ID() + " AND TS_ID = " + profileInfoTrainer.getTS_ID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, TriFunction<TypesSports, Trainer, ProfileInfoTrainer, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table, constructor);
    }
    public static <T> void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, TriFunction<TypesSports, Trainer, ProfileInfoTrainer, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table, constructor);
    }
    private static <T> void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ObservableList<T> newData, ResultSet rs, TableView<T> table, TriFunction<TypesSports, Trainer, ProfileInfoTrainer, T> constructor){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData, constructor);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static <T> void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<T> data, ObservableList<T> newData, TriFunction<TypesSports, Trainer, ProfileInfoTrainer, T> constructor) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            TypesSports typesSports = new TypesSports(rs.getInt("TS_ID"), null, rs.getString("TS_NAME"), null);
            Trainer trainer = new Trainer(rs.getInt("T_ID"),rs.getString("T_NAME"), rs.getString("T_SURNAME"), rs.getString("T_PATRONYMIC"), rs.getString("T_TEL"), rs.getString("T_MAIL") );
            ProfileInfoTrainer profileInfoTrainer = new ProfileInfoTrainer(rs.getInt("T_ID"), rs.getInt("TS_ID"), rs.getInt("PIT_EXP_MONTHS"), rs.getString("PIT_TRAINER_ACTIVE"));
            T obj = constructor.apply(typesSports, trainer, profileInfoTrainer);
            data.add(obj);
            newData.add(obj);
            flag.getAndIncrement();
        }
    }
    public static <T> void remove(ProfileInfoTrainer profileInfoTrainer, Statement statement, AtomicInteger flag, T t, TableView<T> tableView, ObservableList<T> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM PROFILE_INFO_TRAINER WHERE T_ID = " + profileInfoTrainer.getT_ID() + " AND TS_ID = " + profileInfoTrainer.getTS_ID());
            EnterRemoveModel.remove(t, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void enter(Statement statement, String PIT_TRAINER_ACTIVE, String PIT_EXP_MONTHS, String T_TEL, String T_MAIL,  String types,  AtomicInteger flag, TableView<T> tableView,ObservableList<T> data,
                                 TriFunction<TypesSports, Trainer, ProfileInfoTrainer, T> constructor){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet resultSet;
            if(T_TEL == null) {
                resultSet = statement1.executeQuery("SELECT * FROM TRAINER WHERE T_MAIL = '" + T_MAIL + "'");
            }
            else{
                resultSet = statement1.executeQuery("SELECT * FROM TRAINER WHERE T_TEL = '" + T_TEL + "'");
            }
            resultSet.next();
            Trainer trainer = new Trainer(resultSet.getInt("T_ID"),resultSet.getString("T_NAME"),resultSet.getString("T_SURNAME"),resultSet.getString("T_PATRONYMIC"),resultSet.getString("T_TEL"),resultSet.getString("T_MAIL"));
            resultSet.close();
            resultSet = statement1.executeQuery("SELECT * FROM TYPES_SPORTS WHERE TS_NAME = '" + types + "'");
            resultSet.next();
            TypesSports typesSports = new TypesSports(resultSet.getInt("TS_ID"), null,resultSet.getString("TS_NAME"), null);
            resultSet.close();
            ProfileInfoTrainer profileInfoTrainer = new ProfileInfoTrainer(trainer.getT_ID(), typesSports.getTS_ID(), Integer.parseInt(PIT_EXP_MONTHS), PIT_TRAINER_ACTIVE);
            resultSet.close();
            statement1.executeUpdate("INSERT INTO PROFILE_INFO_TRAINER(T_ID, TS_ID, PIT_EXP_MONTHS, PIT_TRAINER_ACTIVE) VALUES (" + trainer.getT_ID() + ", " + typesSports.getTS_ID() + ", " + profileInfoTrainer.getPIT_EXP_MONTHS() + ", '" + profileInfoTrainer.getPIT_TRAINER_ACTIVE() + "'");
            T t = constructor.apply(typesSports, trainer, profileInfoTrainer);
            EnterRemoveModel.enter(t, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static <T> void new_AE(ProfileInfoTrainer profileInfoTrainer, Statement statement, String active, String exp, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE PROFILE_INFO_TRAINER SET PIT_TRAINER_ACTIVE = '" + active + "', PIT_EXP_MONTHS = " + exp +
                    " WHERE T_ID = " + profileInfoTrainer.getT_ID() + " AND TS_ID = " + profileInfoTrainer.getTS_ID());
            ProfileInfoTrainerModel.new_AE(profileInfoTrainer, active, exp, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
