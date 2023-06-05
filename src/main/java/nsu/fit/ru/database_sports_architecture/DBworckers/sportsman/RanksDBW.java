package nsu.fit.ru.database_sports_architecture.DBworckers.sportsman;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Competition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.MembersCompetition;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesSportsInfrastructures;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesTSINames;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Ranks;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.ProfileInfoTrainer;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition.MembersCompetitionDBW;
import nsu.fit.ru.database_sports_architecture.controllers.special_assignments.GetterTextOrNullTextField;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;
import nsu.fit.ru.database_sports_architecture.models.sportsman.RanksModel;
import nsu.fit.ru.database_sports_architecture.models.trainer.ProfileInfoTrainerModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class RanksDBW {
    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }
    public static void dop_tables(Statement statement, ObservableList<Sportsman> sportsmen, TableView<Sportsman> dop_table_S, ObservableList<TypesSports> typesSports, TableView<TypesSports> dop_table_TS){
        try(Statement statement1 = statement.getConnection().createStatement()){
            ResultSet resultSet = statement1.executeQuery("SELECT * FROM SPORTSMAN");
            while (resultSet.next()){
                Sportsman sportsman = new Sportsman(resultSet.getInt("S_ID"), resultSet.getString("S_NAME"), resultSet.getString("S_SURNAME"),resultSet.getString("S_PATRONYMIC"),resultSet.getString("S_TEL"),resultSet.getString("S_MAIL"));
                sportsmen.add(sportsman);
            }
            resultSet.close();
            resultSet = statement1.executeQuery("SELECT * FROM TYPES_SPORTS");
            while (resultSet.next()){
                TypesSports typesSports1 = new TypesSports(resultSet.getInt("TS_ID"),null, resultSet.getString("TS_NAME"), null);
                typesSports.add(typesSports1);
            }
            dop_table_S.setItems(sportsmen);
            dop_table_TS.setItems(typesSports);
        }
        catch (SQLException e){
            throw new  RuntimeException(e);
        }
    }
    public static void newSportsman(Ranks ranks, Sportsman sportsman, Statement statement){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE RANKS SET S_ID = " + sportsman.getS_ID() + " WHERE RANK_ID = " + ranks.getRANK_ID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void newTypesSports(Ranks ranks, TypesSports typesSports, Statement statement){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE RANKS SET TS_ID = " + typesSports.getTS_ID() + " WHERE RANK_ID = " + ranks.getRANK_ID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, TriFunction<TypesSports, Sportsman, Ranks, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table, constructor);
    }
    public static <T> void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, TriFunction<TypesSports, Sportsman, Ranks, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table, constructor);
    }
    private static <T> void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ObservableList<T> newData, ResultSet rs, TableView<T> table, TriFunction<TypesSports, Sportsman, Ranks, T> constructor){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData, constructor);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static <T> void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<T> data, ObservableList<T> newData, TriFunction<TypesSports, Sportsman, Ranks, T> constructor) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            TypesSports typesSports = new TypesSports(rs.getInt("TS_ID"), null, rs.getString("TS_NAME"), null);
            Sportsman sportsman = new Sportsman(rs.getInt("S_ID"),rs.getString("S_NAME"), rs.getString("S_SURNAME"), rs.getString("S_PATRONYMIC"), rs.getString("S_TEL"), rs.getString("S_MAIL") );
            Ranks ranks = new Ranks(rs.getInt("RANK_ID"),rs.getInt("S_ID"),rs.getInt("TS_ID"),rs.getInt("RANK_SPORT"), rs.getString("RANK_NAME"), null, rs.getDate("RANK_DATE"));
            T obj = constructor.apply(typesSports, sportsman, ranks);
            data.add(obj);
            newData.add(obj);
            flag.getAndIncrement();
        }
    }
    public static <T> void remove(Ranks ranks, Statement statement, AtomicInteger flag, T t, TableView<T> tableView, ObservableList<T> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM RANKS WHERE RANK_ID = " + ranks.getRANK_ID());
            EnterRemoveModel.remove(t, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void enter(Statement statement, String R_PLACE, String R_DATE, String R_NAME, String S_TEL, String S_MAIL,  String types,  AtomicInteger flag, TableView<T> tableView,ObservableList<T> data,
                                 TriFunction<TypesSports, Sportsman, Ranks, T> constructor){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet resultSet;
            if(S_TEL == null) {
                resultSet = statement1.executeQuery("SELECT * FROM SPORTSMAN WHERE S_MAIL = '" + S_MAIL + "'");
            }
            else{
                resultSet = statement1.executeQuery("SELECT * FROM SPORTSMAN WHERE S_TEL = '" + S_TEL + "'");
            }
            resultSet.next();
            Sportsman sportsman = new Sportsman(resultSet.getInt("S_ID"),resultSet.getString("S_NAME"),resultSet.getString("S_SURNAME"),resultSet.getString("S_PATRONYMIC"),resultSet.getString("S_TEL"),resultSet.getString("S_MAIL"));
            resultSet.close();
            resultSet = statement1.executeQuery("SELECT * FROM TYPES_SPORTS WHERE TS_NAME = '" + types + "'");
            resultSet.next();
            TypesSports typesSports = new TypesSports(resultSet.getInt("TS_ID"), null,resultSet.getString("TS_NAME"), null);
            resultSet.close();
            resultSet = statement1.executeQuery("SELECT MAX(RANK_ID) as mx FROM RANKS");
            resultSet.next();
            int NEW_RANK_ID = 1 + resultSet.getInt("mx");
            Ranks ranks = new Ranks(NEW_RANK_ID, sportsman.getS_ID(), typesSports.getTS_ID(),Integer.parseInt(R_PLACE),R_NAME,null,Date.valueOf(LocalDate.parse(R_DATE)));
            resultSet.close();
            statement1.executeUpdate("INSERT INTO RANKS(RANK_ID, S_ID, TS_ID, RANK_SPORT, RANK_NAME, RANK_DESCRIPTION, RANK_DATE) VALUES (" + ranks.getRANK_ID() + ", " + sportsman.getS_ID() + ", " + typesSports.getTS_ID() + ", " + ranks.getRANK_SPORT() + ", '" + R_NAME + "', NULL, " + "TO_DATE( '" + R_DATE + "', 'YYYY-MM-DD'))");
            T t = constructor.apply(typesSports, sportsman, ranks);
            EnterRemoveModel.enter(t, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static <T> void new_NDP(Ranks ranks, Statement statement, String r_name, String r_date, String r_place, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE RANKS SET RANK_NAME = '" + r_name + "', RANK_DATE = " + "TO_DATE( '" + r_date + "', 'YYYY-MM-DD'), RANK_SPORT = " +  r_place +
                    " WHERE RANK_ID = " + ranks.getRANK_ID());
            RanksModel.new_NDP(ranks, r_name, r_date, r_place, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
