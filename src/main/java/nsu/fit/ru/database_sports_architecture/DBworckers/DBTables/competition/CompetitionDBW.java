package nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.MembersClubHistory;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Competition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.club_inf.MembersClubHistoryDBW;
import nsu.fit.ru.database_sports_architecture.models.club_inf.MembersClubHistoryModel;
import nsu.fit.ru.database_sports_architecture.models.competition.CompetitionModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.RefreshTableModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class CompetitionDBW {
    @FunctionalInterface
    public interface TriFunction<T, U, V, K, R> {
        R apply(T t, U u, V v, K k);
    }
    public static <T> void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, TriFunction<Competition, SportsFacilityInformation, Organizer, TypesSports, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table, constructor);
    }
    public static <T> void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, TriFunction<Competition, SportsFacilityInformation, Organizer, TypesSports, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table, constructor);
    }
    private static <T> void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ObservableList<T> newData, ResultSet rs, TableView<T> table, TriFunction<Competition, SportsFacilityInformation, Organizer, TypesSports, T> constructor){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData, constructor);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static <T> void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<T> data, ObservableList<T> newData, TriFunction<Competition, SportsFacilityInformation, Organizer, TypesSports, T> constructor) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            Competition competition = new Competition(rs.getInt("COM_ID"), rs.getInt("SFI_ID"),
                    rs.getInt("TS_ID"), rs.getInt("ORG_ID"),rs.getString("COM_NAME"),
                    rs.getDate("COM_START_DATE"),rs.getDate("COM_END_DATE"),
                    rs.getDate("COM_END_REG_DATE"),rs.getDate("COM_START_REG_DATE"));
            SportsFacilityInformation sportsFacilityInformation = new SportsFacilityInformation(
                    rs.getInt("SFI_ID"), null, rs.getString("SFI_ADDR"),
                    rs.getString("SFI_NAME"));
            Organizer organizer = new Organizer(rs.getInt("ORG_ID"),rs.getString("ORG_NAME"),
                    rs.getString("ORG_TEL"),rs.getString("ORG_S_MAIL"));
            TypesSports typesSports = new TypesSports(rs.getInt("TS_ID"), null,
                    rs.getString("TS_NAME"), null);
            T obj = constructor.apply(competition, sportsFacilityInformation, organizer, typesSports);
            data.add(obj);
            newData.add(obj);
            flag.getAndIncrement();
        }
    }

    public static <T> void clubName(Competition competition, Statement statement, String newValue, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE COMPETITION SET COM_NAME = '" + newValue + "' WHERE COM_ID = " + competition.getCOM_ID());
            CompetitionModel.updateName(competition, newValue, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void newStartDate(Competition competition, Statement statement, String newValue, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE COMPETITION SET COM_START_DATE = " + "TO_DATE( '" + newValue + "', 'YYYY-MM-DD') " +
                    "WHERE COM_ID = " + competition.getCOM_ID());
            CompetitionModel.newStartDate(competition, newValue, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void newStartRegDate(Competition competition, Statement statement, String newValue, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE COMPETITION SET COM_START_REG_DATE = " + "TO_DATE( '" + newValue + "', 'YYYY-MM-DD') " +
                    "WHERE COM_ID = " + competition.getCOM_ID());
            CompetitionModel.newStartRegDate(competition, newValue, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void newEndDate(Competition competition, Statement statement, String newValue, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            if (!newValue.equals("N/A") && !newValue.equals(""))
                statement1.executeUpdate("UPDATE COMPETITION SET COM_END_DATE = " + "TO_DATE( '" + newValue + "', 'YYYY-MM-DD') " +
                    "WHERE COM_ID = " + competition.getCOM_ID());
            else {
                statement1.executeUpdate("UPDATE COMPETITION SET COM_END_DATE = NULL " +
                        "WHERE COM_ID = " + competition.getCOM_ID());
                newValue = null;
            }
            CompetitionModel.newEndDate(competition, newValue, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void newEndRegDate(Competition competition, Statement statement, String newValue, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE COMPETITION SET COM_END_REG_DATE = " + "TO_DATE( '" + newValue + "', 'YYYY-MM-DD') " +
                    "WHERE COM_ID = " + competition.getCOM_ID());
            CompetitionModel.newEndRegDate(competition, newValue, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void newTS(Competition competition, TypesSports typesSports, Statement statement, String newValue, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet rs1 = statement1.executeQuery("SELECT TS_ID, TS_NAME FROM TYPES_SPORTS ts WHERE TS_NAME = '" + newValue + "'");
            rs1.next();
            int TS_ID = rs1.getInt("TS_ID");
            String NEW_TS_NAME = rs1.getString("TS_NAME");
            statement1.executeUpdate("UPDATE COMPETITION SET TS_ID = " + TS_ID + " WHERE COM_ID = " + competition.getCOM_ID());
            rs1.close();
            CompetitionModel.updateTS(competition, typesSports, newValue, TS_ID, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void newOrg(Competition competition, Organizer organizer, Organizer newOrganizer, Statement statement, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE COMPETITION SET ORG_ID = " + newOrganizer.getORG_ID() + " WHERE COM_ID = " + competition.getCOM_ID());
            CompetitionModel.updateOrg(competition, organizer, newOrganizer, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void newSFI(Competition competition, SportsFacilityInformation sportsFacilityInformation,
                                  SportsFacilityInformation newSportsFacilityInformation, Statement statement, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE COMPETITION SET SFI_ID = " + newSportsFacilityInformation.getSFI_ID() + " WHERE COM_ID = " + competition.getCOM_ID());
            CompetitionModel.updateSFI(competition, sportsFacilityInformation, newSportsFacilityInformation, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dop_tables(Statement statement, ObservableList<TypesSports> typesSports,
                                  ObservableList<SportsFacilityInformation> sportsFacilityInformation,
                                  ObservableList<Organizer> organizer,
                                  TableView<TypesSports> typesSportsTableView,
                                  TableView<SportsFacilityInformation> sportsFacilityInformationTableView,
                                  TableView<Organizer> organizerTableView){
        try(Statement statement1 = statement.getConnection().createStatement()){
            ResultSet resultSet = statement1.executeQuery("SELECT * FROM TYPES_SPORTS");
            while(resultSet.next()){
                TypesSports typesSports1 = new TypesSports(resultSet.getInt("TS_ID"), resultSet.getInt("TSI_ID"), resultSet.getString("TS_NAME"), null);
                typesSports.add(typesSports1);
            }
            resultSet.close();
            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM SPORTS_FACILITY_INFORMATION");
            while (resultSet1.next()){
                SportsFacilityInformation sportsFacilityInformation1 =
                        new SportsFacilityInformation(resultSet1.getInt("SFI_ID"),
                                resultSet1.getInt("TSI_ID"), resultSet1.getString("SFI_ADDR"),
                                resultSet1.getString("SFI_NAME"));
                sportsFacilityInformation.add(sportsFacilityInformation1);
            }
            resultSet1.close();
            ResultSet resultSet2 = statement1.executeQuery("SELECT * FROM ORGANIZER");
            while (resultSet2.next()){
                Organizer organizer1 = new Organizer(resultSet2.getInt("ORG_ID"),
                        resultSet2.getString("ORG_NAME"), resultSet2.getString("ORG_TEL"),
                        resultSet2.getString("ORG_S_MAIL"));
                organizer.add(organizer1);
            }
            resultSet2.close();
            CompetitionModel.dop_tables(typesSports, sportsFacilityInformation, organizer, typesSportsTableView,
                    sportsFacilityInformationTableView, organizerTableView);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static <T> void remove(Competition competition, Statement statement, AtomicInteger flag, T t, TableView<T> tableView, ObservableList<T> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM COMPETITION WHERE COM_ID = " + competition.getCOM_ID());
            EnterRemoveModel.remove(t, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static <T> void enter(Statement statement, String COM_NAME, String COM_START_DATE, String COM_END_DATE, String COM_START_REG_DATE,
                                 String COM_END_REG_DATE, String TS_NAME, String ORG_TEL, String ORG_S_MAIL, String SFI_ADDR, String SFI_NAME,  AtomicInteger flag, TableView<T> tableView,ObservableList<T> data,
                                 TriFunction<Competition, SportsFacilityInformation, Organizer, TypesSports, T> constructor){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet rs1 = ORG_TEL == null ? statement1.executeQuery("SELECT * FROM ORGANIZER o " +
                    "WHERE o.ORG_S_MAIL = '" + ORG_S_MAIL + "'") :
                    statement1.executeQuery("SELECT * FROM ORGANIZER o WHERE o.ORG_TEL = \'" + ORG_TEL + "\'");
            rs1.next();
            Organizer organizer = new Organizer(rs1.getInt("ORG_ID"), rs1.getString("ORG_NAME"),
                    rs1.getString("ORG_TEL"), rs1.getString("ORG_S_MAIL"));
            rs1.close();
            rs1 = statement1.executeQuery("SELECT * FROM SPORTS_FACILITY_INFORMATION WHERE SFI_ADDR = '" + SFI_ADDR +
                    "' AND SFI_NAME = '" + SFI_NAME + "'");
            rs1.next();
            SportsFacilityInformation sportsFacilityInformation =
                    new SportsFacilityInformation(rs1.getInt("SFI_ID"), null,
                            rs1.getString("SFI_ADDR"), rs1.getString("SFI_NAME"));
            rs1.close();
            rs1 = statement1.executeQuery("SELECT * FROM TYPES_SPORTS WHERE TS_NAME = '" + TS_NAME + "'");
            rs1.next();
            TypesSports typesSports = new TypesSports(rs1.getInt("TS_ID"),
                    null, rs1.getString("TS_NAME"), null);
            rs1.close();

            rs1 = statement1.executeQuery("SELECT MAX(COM_ID) as mx FROM COMPETITION");
            rs1.next();
            int index = rs1.getInt("mx") + 1;
            rs1.close();

            if(COM_END_DATE.equals("") || COM_END_DATE.equals("N/A"))
                statement1.executeUpdate("INSERT INTO COMPETITION(COM_ID, SFI_ID, TS_ID, ORG_ID, COM_NAME, COM_START_DATE, COM_END_DATE, COM_START_REG_DATE, COM_END_REG_DATE) VALUES " +
                        "(" + index + ", " + sportsFacilityInformation.getSFI_ID() + ", " + typesSports.getTS_ID() + ", " +
                organizer.getORG_ID() + ", '" + COM_NAME +"', " + "TO_DATE( '" + COM_START_DATE + "', 'YYYY-MM-DD'), " +
                     "NULL, " + "TO_DATE( '" + COM_START_REG_DATE + "', 'YYYY-MM-DD'), " + "TO_DATE( '" + COM_END_REG_DATE + "', 'YYYY-MM-DD'))" );
            else
                statement1.executeUpdate("INSERT INTO COMPETITION(COM_ID, SFI_ID, TS_ID, ORG_ID, COM_NAME, COM_START_DATE, COM_END_DATE, COM_START_REG_DATE, COM_END_REG_DATE) VALUES " +
                        "(" + index + ", " + sportsFacilityInformation.getSFI_ID() + ", " + typesSports.getTS_ID() + ", " +
                        organizer.getORG_ID() + ", '" + COM_NAME +"', " + "TO_DATE( '" + COM_START_DATE + "', 'YYYY-MM-DD'), " +
                        "TO_DATE( '" + COM_END_DATE + "', 'YYYY-MM-DD'), " + "TO_DATE( '" + COM_START_REG_DATE + "', 'YYYY-MM-DD'), " + "TO_DATE( '" + COM_END_REG_DATE + "', 'YYYY-MM-DD'))" );

            Competition competition = COM_END_DATE.equals("") || COM_END_DATE.equals("N/A") ? new Competition(index, sportsFacilityInformation.getSFI_ID(), typesSports.getTS_ID(),
                    organizer.getORG_ID(), COM_NAME, Date.valueOf(LocalDate.parse(COM_START_DATE)), null, Date.valueOf(LocalDate.parse(COM_END_REG_DATE)), Date.valueOf((LocalDate.parse(COM_START_REG_DATE)))) :
                    new Competition(index, sportsFacilityInformation.getSFI_ID(), typesSports.getTS_ID(),
                    organizer.getORG_ID(), COM_NAME, Date.valueOf(LocalDate.parse(COM_START_DATE)), Date.valueOf(LocalDate.parse(COM_END_DATE)), Date.valueOf(LocalDate.parse(COM_END_REG_DATE)), Date.valueOf((LocalDate.parse(COM_START_REG_DATE))));
            T t = constructor.apply(competition, sportsFacilityInformation, organizer, typesSports);



            EnterRemoveModel.enter(t, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void droppedOut(Competition competition, Statement statement, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE COMPETITION SET COM_END_DATE = SYSDATE WHERE COM_ID = " + competition.getCOM_ID());
            ResultSet resultSet = statement1.executeQuery("SELECT COM_END_DATE FROM COMPETITION WHERE COM_ID = " + competition.getCOM_ID());
            resultSet.next();
            competition.setCOM_END_DATE(resultSet.getDate("COM_END_DATE"));
            resultSet.close();
            RefreshTableModel.refresh(tableView);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
