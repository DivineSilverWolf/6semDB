package nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.club_inf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.MembersClubHistory;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.controllers.club_inf.MembersClubHistoryController;
import nsu.fit.ru.database_sports_architecture.models.club_inf.ClubModel;
import nsu.fit.ru.database_sports_architecture.models.club_inf.MembersClubHistoryModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.RefreshTableModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;


public class MembersClubHistoryDBW {
    @FunctionalInterface
    public interface TriFunction<T, U, V, R> {
        R apply(T t, U u, V v);
    }
    public static <T> void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, TriFunction<Club, Sportsman, MembersClubHistory, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table, constructor);
    }
    public static <T> void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, TriFunction<Club, Sportsman, MembersClubHistory, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table, constructor);
    }
    private static <T> void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ObservableList<T> newData, ResultSet rs, TableView<T> table, TriFunction<Club, Sportsman, MembersClubHistory, T> constructor){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData, constructor);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static <T> void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<T> data, ObservableList<T> newData, TriFunction<Club, Sportsman, MembersClubHistory, T> constructor) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            Club club = new Club(rs.getInt("CL_ID"), null,
                    rs.getString("CL_NAME"), null, null, rs.getString("CL_TEL"));
            MembersClubHistory membersClubHistory = new MembersClubHistory(rs.getInt("MCH_ID"), rs.getInt("S_ID"),
                    rs.getInt("CL_ID"),rs.getDate("MCH_START_DATE"),
                    rs.getDate("MCH_END_DATE"));
            Sportsman sportsman = new Sportsman(rs.getInt("S_ID"), rs.getString("S_NAME"),
                    rs.getString("S_SURNAME"),rs.getString("S_PATRONYMIC"),rs.getString("S_TEL"),rs.getString("S_MAIL"));
            T clubTypesSports = constructor.apply(club, sportsman, membersClubHistory);
            data.add(clubTypesSports);
            newData.add(clubTypesSports);
            flag.getAndIncrement();
        }
    }

    public static <T> void clubName(MembersClubHistory membersClubHistory, Club club, Statement statement, String newValue, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet rs1 = statement1.executeQuery("SELECT CL_ID, CL_TEL FROM Club c WHERE c.CL_NAME = \'" + newValue + "\'");
            rs1.next();
            int NEW_CL_ID = rs1.getInt("CL_ID");
            String NEW_CL_TEL = rs1.getString("CL_TEL");
            statement1.executeUpdate("UPDATE MEMBERS_CLUB_HISTORY SET CL_ID = " + NEW_CL_ID + " WHERE MCH_ID = " + membersClubHistory.getMCH_ID());
            rs1.close();
            MembersClubHistoryModel.updateCLNameCLTel(membersClubHistory, club, newValue, NEW_CL_ID, NEW_CL_TEL, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void clubTel(MembersClubHistory membersClubHistory, Club club, Statement statement, String newValue, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet rs1 = statement1.executeQuery("SELECT CL_ID, CL_NAME FROM Club c WHERE c.CL_TEL = \'" + newValue + "\'");
            rs1.next();
            int NEW_CL_ID = rs1.getInt("CL_ID");
            String NEW_CL_NAME = rs1.getString("CL_NAME");
            statement1.executeUpdate("UPDATE MEMBERS_CLUB_HISTORY SET CL_ID = " + NEW_CL_ID + " WHERE MCH_ID = " + membersClubHistory.getMCH_ID());
            rs1.close();
            MembersClubHistoryModel.updateCLNameCLTel(membersClubHistory, club, NEW_CL_NAME, NEW_CL_ID, newValue, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void start_date(MembersClubHistory membersClubHistory, Statement statement, String newValue){
        try(Statement statement1 = statement.getConnection().createStatement()){
            statement1.executeUpdate("UPDATE MEMBERS_CLUB_HISTORY SET MCH_START_DATE = TO_DATE( '" + newValue + "', 'YYYY-MM-DD') WHERE MCH_ID = " + membersClubHistory.getMCH_ID());
            MembersClubHistoryModel.start_date(membersClubHistory, newValue);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void end_date(MembersClubHistory membersClubHistory, Statement statement, String newValue, TableView<T> tableView){


        try(Statement statement1 = statement.getConnection().createStatement()){
            if (!newValue.equals("N/A") && !newValue.equals(""))
                statement1.executeUpdate("UPDATE MEMBERS_CLUB_HISTORY SET MCH_END_DATE = TO_DATE( '" + newValue + "', 'YYYY-MM-DD') WHERE MCH_ID = " + membersClubHistory.getMCH_ID());
            else {
                statement1.executeUpdate("UPDATE MEMBERS_CLUB_HISTORY SET MCH_END_DATE = NULL WHERE MCH_ID = " + membersClubHistory.getMCH_ID());
                newValue = null;
            }
            MembersClubHistoryModel.end_date(membersClubHistory, newValue);
            RefreshTableModel.refresh(tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void anotherSportsman(MembersClubHistory membersClubHistory, Sportsman sportsman, Statement statement, String newMail , String newTel, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet rs1 = newTel == null ? statement1.executeQuery("SELECT * FROM SPORTSMAN s WHERE s.S_MAIL = \'" + newMail + "\'")
                    : statement1.executeQuery("SELECT * FROM SPORTSMAN s WHERE s.S_TEL = \'" + newTel + "\'");
            rs1.next();
            int NEW_S_ID = rs1.getInt("S_ID");
            String NEW_S_NAME = rs1.getString("S_NAME");
            String NEW_S_SURNAME = rs1.getString("S_SURNAME");
            String NEW_S_PATRONYMIC = rs1.getString("S_PATRONYMIC");
            String NEW_S_TEL = rs1.getString("S_TEL");
            String NEW_S_MAIL = rs1.getString("S_MAIL");
            statement1.executeUpdate("UPDATE MEMBERS_CLUB_HISTORY SET S_ID = " + NEW_S_ID + " WHERE MCH_ID = " + membersClubHistory.getMCH_ID());
            rs1.close();
            MembersClubHistoryModel.anotherSportsman(membersClubHistory, sportsman, NEW_S_NAME, NEW_S_SURNAME, NEW_S_PATRONYMIC, NEW_S_TEL, NEW_S_MAIL, NEW_S_ID, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void droppedOut(MembersClubHistory membersClubHistory, Statement statement, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE Members_Club_History SET MCH_ID = " + membersClubHistory.getMCH_ID() + " WHERE MCH_ID = " + membersClubHistory.getMCH_ID());
            ResultSet resultSet = statement1.executeQuery("SELECT * FROM Members_Club_History WHERE MCH_ID = " + membersClubHistory.getMCH_ID());
            resultSet.next();
            MembersClubHistoryModel.end_date(membersClubHistory, resultSet.getDate("MCH_END_DATE").toString());
            resultSet.close();
            RefreshTableModel.refresh(tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void remove(MembersClubHistory membersClubHistory, Statement statement, AtomicInteger flag, T sportsmansClubHistory, TableView<T> tableView, ObservableList<T> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM MEMBERS_CLUB_HISTORY WHERE MCH_ID = " + membersClubHistory.getMCH_ID());
            EnterRemoveModel.remove(sportsmansClubHistory, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void enter(Statement statement, String sTel, String sMail, String cTel, String clName,
                                 String start_date, String end_date, AtomicInteger flag, TableView<T> tableView,ObservableList<T> data,
                                 TriFunction<Club, Sportsman, MembersClubHistory, T> constructor){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet rs1 = sTel == null ? statement1.executeQuery("SELECT * FROM Sportsman s " +
                    "WHERE s.S_MAIL = \'" + sMail + "\'") :
                    statement1.executeQuery("SELECT * FROM Sportsman s WHERE s.S_TEL = \'" + sTel + "\'");
            rs1.next();
            int NEW_S_ID = rs1.getInt("S_ID");
            String NEW_S_NAME = rs1.getString("S_NAME");
            String NEW_S_SURNAME = rs1.getString("S_SURNAME");
            String NEW_S_PATRONYMIC = rs1.getString("S_PATRONYMIC");
            String NEW_S_TEL = rs1.getString("S_TEL");
            String NEW_S_MAIL = rs1.getString("S_MAIL");
            rs1 = cTel == null ? statement1.executeQuery("SELECT * FROM CLUB c WHERE CL_NAME = '" + clName + "'"):
            statement1.executeQuery("SELECT * FROM CLUB c WHERE CL_TEL = '" + cTel + "'");
            rs1.next();
            int NEW_CL_ID = rs1.getInt("CL_ID");
            String NEW_CL_NAME = rs1.getString("CL_NAME");
            String NEW_CL_TEL = rs1.getString("CL_TEL");
            if(start_date == null && end_date == null)
                statement1.executeUpdate("INSERT INTO Members_Club_History(S_ID, CL_ID) VALUES(" + NEW_S_ID + ", " +
                    NEW_CL_ID +")");
            else if(start_date !=null && end_date != null){
                statement1.executeUpdate("INSERT INTO Members_Club_History(S_ID, CL_ID, MCH_START_DATE, MCH_END_DATE) VALUES(" + NEW_S_ID + ", " +
                        NEW_CL_ID + ", " + "TO_DATE( '" + start_date + "', 'YYYY-MM-DD'), " + "TO_DATE( '" + end_date + "', 'YYYY-MM-DD')" + ")");
            }
            else if(start_date != null)
                statement1.executeUpdate("INSERT INTO Members_Club_History(S_ID, CL_ID, MCH_START_DATE) VALUES(" + NEW_S_ID + ", " +
                        NEW_CL_ID + ", " + "TO_DATE( '" + start_date + "', 'YYYY-MM-DD') )");
            else if (end_date != null) {
                statement1.executeUpdate("INSERT INTO Members_Club_History(S_ID, CL_ID, MCH_END_DATE) VALUES(" + NEW_S_ID + ", " +
                        NEW_CL_ID + ", " + "TO_DATE( '" + end_date + "', 'YYYY-MM-DD') )");
            }
            Club club = new Club(NEW_CL_ID,null,NEW_CL_NAME, null, null, NEW_CL_TEL);
            Sportsman sportsman = new Sportsman(NEW_S_ID,NEW_S_NAME,NEW_S_SURNAME,NEW_S_PATRONYMIC,NEW_S_TEL,NEW_S_MAIL);
            rs1 = statement1.executeQuery("WITH mx as(SELECT MAX(MCH_ID) as mxmch FROM MEMBERS_CLUB_HISTORY) " +
                    "SELECT * FROM MEMBERS_CLUB_HISTORY m JOIN mx ON m.MCH_ID = mx.mxmch");
            rs1.next();
            int NEW_MCH_ID = rs1.getInt("MCH_ID");
            Date NEW_START_DATE = rs1.getDate("MCH_START_DATE");
            Date NEW_END_DATE = rs1.getDate("MCH_END_DATE");

            rs1.close();
            MembersClubHistory membersClubHistory = new MembersClubHistory(NEW_MCH_ID, NEW_S_ID, NEW_CL_ID,
                    NEW_START_DATE,NEW_END_DATE);
            T sportsmansClubHistory = constructor.apply(club, sportsman, membersClubHistory);



            EnterRemoveModel.enter(sportsmansClubHistory, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void dop_tables(Statement statement, ObservableList<Club> dop_club_data, ObservableList<Sportsman> dop_sportsman_data, TableView<Sportsman> dop_sportsman, TableView<Club> dop_club){
        try(Statement statement1 = statement.getConnection().createStatement()){
            ResultSet resultSet = statement1.executeQuery("SELECT * FROM CLUB");
            while(resultSet.next()){
                Club club = new Club(resultSet.getInt("CL_ID"), null,
                        resultSet.getString("CL_NAME"), null, null,
                        resultSet.getString("CL_TEL"));
                dop_club_data.add(club);
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
            MembersClubHistoryModel.dop_tables(dop_club_data,dop_sportsman_data,dop_sportsman,dop_club);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
