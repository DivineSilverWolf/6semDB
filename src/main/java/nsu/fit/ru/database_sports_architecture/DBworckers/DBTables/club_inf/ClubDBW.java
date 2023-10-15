package nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.club_inf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.models.club_inf.ClubModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public class ClubDBW {
    public static <T> void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, BiFunction<Club, TypesSports, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table, constructor);
    }
    public static <T> void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, BiFunction<Club, TypesSports, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table, constructor);
    }
    private static <T> void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ObservableList<T> newData, ResultSet rs, TableView<T> table, BiFunction<Club, TypesSports, T> constructor){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData, constructor);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static <T> void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<T> data, ObservableList<T> newData, BiFunction<Club, TypesSports, T> constructor) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            Club club = new Club(rs.getInt("CL_ID"), rs.getInt("TS_ID"),
                    rs.getString("CL_NAME"), rs.getDate("CL_HAIR_DATE"),
                    rs.getString("CL_Founder"), rs.getString("CL_TEL"));
            TypesSports typesSports = new TypesSports(rs.getInt("TS_ID"), null,
                    rs.getString("TS_NAME"), null);
            T clubTypesSports = constructor.apply(club, typesSports);
            data.add(clubTypesSports);
            newData.add(clubTypesSports);
            flag.getAndIncrement();
        }
    }

    public static void founder(Club club, Statement statement, String newValue){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE Club SET CL_FOUNDER = \'" + newValue + "\' WHERE CL_ID = " + club.getCL_ID());
            ClubModel.founder(club, newValue);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void tel(Club club, Statement statement, String newValue){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE Club SET CL_TEL = \'" + newValue + "\' WHERE CL_ID = " + club.getCL_ID());
            ClubModel.tel(club,newValue);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void date(Club club, Statement statement, String newValue){
        try(Statement statement1 = statement.getConnection().createStatement()){
            statement1.executeUpdate("UPDATE Club SET CL_HAIR_DATE = TO_DATE( '" + newValue + "', 'YYYY-MM-DD') WHERE CL_ID = " + club.getCL_ID());
            ClubModel.date(club, newValue);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void clubName(Club club, Statement statement, String newValue){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE Club SET CL_NAME = \'" + newValue + "\' WHERE CL_ID = " + club.getCL_ID());
            ClubModel.clubName(club, newValue);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void typesSport(Club club, TypesSports typesSports, Statement statement, String newValue){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet rs1 = statement1.executeQuery("SELECT TS_ID FROM TYPES_SPORTS ts WHERE ts.TS_NAME = \'" + newValue + "\'");
            rs1.next();
            int NEW_TS_ID = rs1.getInt("TS_ID");
            statement1.executeUpdate("UPDATE Club SET TS_ID = " + NEW_TS_ID + " WHERE CL_ID = " + club.getCL_ID());
            rs1.close();
            ClubModel.typesSport(typesSports, club, newValue, NEW_TS_ID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void remove(Club club, Statement statement, AtomicInteger flag, T clubTypesSports, TableView<T> table, ObservableList<T> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM Club WHERE CL_ID = " + club.getCL_ID());
            EnterRemoveModel.remove(clubTypesSports, table, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void enter(Statement statement, String clubName, String founderName, String tel, String date,
                                 String typesSportsName, AtomicInteger flag, TableView<T> table,ObservableList<T> data,
                                 BiFunction<Club, TypesSports, T> constructor){

        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet rs1 = statement1.executeQuery("SELECT TS_ID FROM TYPES_SPORTS ts WHERE ts.TS_NAME = \'" + typesSportsName + "\'");
            rs1.next();
            int NEW_TS_ID = rs1.getInt("TS_ID");
            rs1 = statement1.executeQuery("SELECT MAX(CL_ID) as mx FROM CLUB");
            rs1.next();
            int NEW_CL_ID = rs1.getInt("mx") + 1;
            rs1.close();
            Club club = new Club(NEW_CL_ID, NEW_TS_ID,clubName,Date.valueOf(LocalDate.parse(date)), founderName,tel);
            TypesSports typesSports = new TypesSports(NEW_TS_ID, null, typesSportsName, null);
            T clubTypesSports = constructor.apply(club, typesSports);

            statement1.executeUpdate("INSERT INTO CLUB values (" + club.getCL_ID() + ", " + club.getTS_ID()
                    + ", '" + club.getCL_NAME() + "', " + "TO_DATE( '" + club.getCL_HAIR_DATE() + "', 'YYYY-MM-DD'), '"
                    + club.getCL_FOUNDER() + "', '" + club.getCL_TEL() + "')");


            EnterRemoveModel.enter(clubTypesSports, table, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void dop_table(Statement statement, ObservableList<TypesSports> typesSportsList, TableView<TypesSports> dop_table){
        try(Statement statement1 = statement.getConnection().createStatement()){
            ResultSet resultSet = statement1.executeQuery("SELECT TS_ID, TS_NAME FROM TYPES_SPORTS");
            while (resultSet.next()){
                TypesSports typesSports = new TypesSports(resultSet.getInt("TS_ID"),null,
                        resultSet.getString("TS_NAME"),null);
                typesSportsList.add(typesSports);
                dop_table.setItems(typesSportsList);
            }
        }
        catch (SQLException e){
            throw new  RuntimeException(e);
        }
    }
}
