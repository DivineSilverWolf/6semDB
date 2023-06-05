package nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Competition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.MembersCompetition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Winners;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.models.competition.MembersCompetitionModel;
import nsu.fit.ru.database_sports_architecture.models.competition.WinnersModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class WinnersDBW {
    @FunctionalInterface
    public interface TriFunctions<T, U, V, R> {
        R apply(T t, U u, V v);
    }
    public static <T> void dop_tables(Statement statement,
                                      ObservableList<T> tObservableList, TableView<T> tableView, TriFunctions<Sportsman, Competition, MembersCompetition, T> constructor){
        try(Statement statement1 = statement.getConnection().createStatement()){
            ResultSet rs = statement1.executeQuery("SELECT mc.COM_ID,mc.S_ID, COM_NAME, COM_START_DATE, COM_END_DATE, COM_END_REG_DATE, COM_START_REG_DATE, S_NAME, S_SURNAME, S_PATRONYMIC, S_TEL, S_MAIL FROM Members_Competition mc JOIN Competition c ON mc.COM_ID = c.COM_ID JOIN Sportsman s on s.S_ID = mc.S_ID");
            while (rs.next()) {
                Competition competition = new Competition(rs.getInt("COM_ID"), null, null, null,rs.getString("COM_NAME"),rs.getDate("COM_START_DATE"),rs.getDate("COM_END_DATE"),rs.getDate("COM_END_REG_DATE"),rs.getDate("COM_START_REG_DATE"));
                MembersCompetition membersCompetition = new MembersCompetition(rs.getInt("COM_ID"), rs.getInt("S_ID"), null, null);
                Sportsman sportsman = new Sportsman(rs.getInt("S_ID"), rs.getString("S_NAME"),
                        rs.getString("S_SURNAME"), rs.getString("S_PATRONYMIC"), rs.getString("S_TEL"), rs.getString("S_MAIL"));
                T clubTypesSports = constructor.apply(sportsman, competition, membersCompetition);
                tObservableList.add(clubTypesSports);
            }
            rs.close();
            WinnersModel.dop_tables(tObservableList, tableView);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static <T> void change_entry(Winners winners, Competition competition, Competition competition1, Sportsman sportsman, Sportsman sportsman1, Statement statement, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE WINNERS SET COM_ID = " + competition1.getCOM_ID() + ", S_ID = " + sportsman1.getS_ID() + " WHERE COM_ID = " + competition.getCOM_ID() + " AND S_ID = " + sportsman.getS_ID());
            WinnersModel.newSportsman(winners,sportsman, sportsman1, tableView);
            WinnersModel.newCompetition(winners, competition, competition1, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, TriFunctions<Sportsman, Competition, Winners, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table, constructor);
    }
    public static <T> void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, TriFunctions<Sportsman, Competition, Winners, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table, constructor);
    }
    private static <T> void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ObservableList<T> newData, ResultSet rs, TableView<T> table, TriFunctions<Sportsman, Competition, Winners, T> constructor){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData, constructor);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static <T> void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<T> data, ObservableList<T> newData, TriFunctions<Sportsman, Competition, Winners, T> constructor) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            Winners winners = new Winners(rs.getInt("COM_ID"), rs.getInt("S_ID"), rs.getInt("W_PLACE"), rs.getDate("W_DATE"));
            Competition competition = new Competition(rs.getInt("COM_ID"), null, null, null,rs.getString("COM_NAME"),rs.getDate("COM_START_DATE"),rs.getDate("COM_END_DATE"),rs.getDate("COM_END_REG_DATE"),rs.getDate("COM_START_REG_DATE"));
            Sportsman sportsman = new Sportsman(rs.getInt("S_ID"),rs.getString("S_NAME"), rs.getString("S_SURNAME"), rs.getString("S_PATRONYMIC"), rs.getString("S_TEL"), rs.getString("S_MAIL") );
            T obj = constructor.apply(sportsman, competition, winners);
            data.add(obj);
            newData.add(obj);
            flag.getAndIncrement();
        }
    }

    public static <T> void new_W_PLACE(Winners winners, int new_place, Statement statement, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE WINNERS SET W_PLACE = " + new_place + " WHERE COM_ID = " + winners.getCOM_ID() + " AND S_ID = " + winners.getS_ID());
            WinnersModel.new_W_PLACE(winners,new_place, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void new_W_DATE(Winners winners, String new_Date, Statement statement, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE WINNERS SET W_DATE = " + "TO_DATE( '" + new_Date + "', 'YYYY-MM-DD')" + " WHERE COM_ID = " + winners.getCOM_ID() + " AND S_ID = " + winners.getS_ID());
            WinnersModel.new_W_DATE(winners,new_Date, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void enter(Statement statement, String COM_NAME, String COM_START_DATE, String COM_END_DATE, String COM_START_REG_DATE,
                                String COM_END_REG_DATE, String S_TEL, String S_MAIL, String W_DATE, int W_PLACE,  AtomicInteger flag, TableView<T> tableView,ObservableList<T> data,
                                 TriFunctions<Sportsman, Competition, Winners, T> constructor){

        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet resultSet;
            if(COM_END_DATE.equals("") || COM_END_DATE.equals("N/A")){
                resultSet = statement1.executeQuery("SELECT * FROM COMPETITION WHERE COM_NAME = '" + COM_NAME + "' AND COM_START_DATE = " + "TO_DATE( '" + COM_START_DATE + "', 'YYYY-MM-DD')" + " AND COM_END_DATE IS " + "NULL" + " AND COM_START_REG_DATE = " + "TO_DATE( '" + COM_START_REG_DATE + "', 'YYYY-MM-DD')" + " AND COM_END_REG_DATE = " + "TO_DATE( '" + COM_END_REG_DATE + "', 'YYYY-MM-DD')");
            }
            else{
                resultSet = statement1.executeQuery("SELECT * FROM COMPETITION WHERE COM_NAME = '" + COM_NAME + "' AND COM_START_DATE = " + "TO_DATE( '" + COM_START_DATE + "', 'YYYY-MM-DD')" + " AND COM_END_DATE = " + "TO_DATE( '" + COM_END_DATE + "', 'YYYY-MM-DD')" + " AND COM_START_REG_DATE = " + "TO_DATE( '" + COM_START_REG_DATE + "', 'YYYY-MM-DD')" + " AND COM_END_REG_DATE = " + "TO_DATE( '" + COM_END_REG_DATE + "', 'YYYY-MM-DD')");
            }
            resultSet.next();
            Competition competition = new Competition(resultSet.getInt("COM_ID"),null,null,null,resultSet.getString("COM_NAME"),resultSet.getDate("COM_START_DATE"),resultSet.getDate("COM_END_DATE"),resultSet.getDate("COM_END_REG_DATE"),resultSet.getDate("COM_START_REG_DATE"));
            resultSet.close();
            if(S_TEL.equals("") || S_TEL.equals("N/A")) {
                resultSet = statement1.executeQuery("SELECT * FROM SPORTSMAN WHERE S_MAIL = '" + S_MAIL + "'");
            }
            else{
                resultSet = statement1.executeQuery("SELECT * FROM SPORTSMAN WHERE S_TEL = '" + S_TEL + "'");
            }
            resultSet.next();
            Sportsman sportsman = new Sportsman(resultSet.getInt("S_ID"),resultSet.getString("S_NAME"),resultSet.getString("S_SURNAME"),resultSet.getString("S_PATRONYMIC"),resultSet.getString("S_TEL"),resultSet.getString("S_MAIL"));
            resultSet.close();
            Winners winners = new Winners(competition.getCOM_ID(), sportsman.getS_ID(), W_PLACE, Date.valueOf(LocalDate.parse(W_DATE)));
            statement1.executeUpdate("INSERT INTO WINNERS(COM_ID, S_ID, W_PLACE, W_DATE) VALUES (" + competition.getCOM_ID() + ", " + sportsman.getS_ID() + ", " + W_PLACE + ", " + "TO_DATE( '" + winners.getW_DATE().toString() + "', 'YYYY-MM-DD'))");
            T t = constructor.apply(sportsman, competition, winners);
            EnterRemoveModel.enter(t, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void remove(Winners winners, Statement statement, AtomicInteger flag, T t, TableView<T> tableView, ObservableList<T> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM WINNERS WHERE COM_ID = " + winners.getCOM_ID() + " AND S_ID = " + winners.getS_ID());
            EnterRemoveModel.remove(t, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
