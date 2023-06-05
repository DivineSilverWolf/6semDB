package nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.MembersClubHistory;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Competition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.MembersCompetition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.club_inf.MembersClubHistoryDBW;
import nsu.fit.ru.database_sports_architecture.models.competition.CompetitionModel;
import nsu.fit.ru.database_sports_architecture.models.competition.MembersCompetitionModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class MembersCompetitionDBW {
    @FunctionalInterface
    public interface TriFunction<T, U, V, K, R> {
        R apply(T t, U u, V v, K k);
    }
    @FunctionalInterface
    public interface TriFunctions<T, U, V, R> {
        R apply(T t, U u, V v);
    }
    public static <T> void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, TriFunction<MembersCompetition, Competition, Club, Sportsman, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table, constructor);
    }
    public static <T> void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, TriFunction<MembersCompetition, Competition, Club, Sportsman, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table, constructor);
    }
    private static <T> void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ObservableList<T> newData, ResultSet rs, TableView<T> table, TriFunction<MembersCompetition, Competition, Club, Sportsman, T> constructor){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData, constructor);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static <T> void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<T> data, ObservableList<T> newData, TriFunction<MembersCompetition, Competition, Club, Sportsman, T> constructor) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            MembersCompetition membersCompetition = new MembersCompetition(rs.getInt("COM_ID"), rs.getInt("S_ID"), rs.getInt("CL_ID"), rs.getDate("MC_REG_DATE"));
            Competition competition = new Competition(rs.getInt("COM_ID"), null, null, null,rs.getString("COM_NAME"),rs.getDate("COM_START_DATE"),rs.getDate("COM_END_DATE"),rs.getDate("COM_END_REG_DATE"),rs.getDate("COM_START_REG_DATE"));
            Club club = new Club(rs.getInt("CL_ID"), null, rs.getString("CL_NAME"), null, null, rs.getString("CL_TEL"));
            Sportsman sportsman = new Sportsman(rs.getInt("S_ID"),rs.getString("S_NAME"), rs.getString("S_SURNAME"), rs.getString("S_PATRONYMIC"), rs.getString("S_TEL"), rs.getString("S_MAIL") );
            T obj = constructor.apply(membersCompetition, competition, club, sportsman);
            data.add(obj);
            newData.add(obj);
            flag.getAndIncrement();
        }
    }

    public static <T> void newCompetition(MembersCompetition membersCompetition, Competition competition, Competition competition1, Statement statement, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE MEMBERS_COMPETITION SET COM_ID = " + competition1.getCOM_ID() + " WHERE COM_ID = " + membersCompetition.getCOM_ID() + " AND S_ID = " + membersCompetition.getS_ID());
            MembersCompetitionModel.newCompetition(membersCompetition,competition, competition1, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void newSportsmanClub(MembersCompetition membersCompetition, Club club, Club club1, Sportsman sportsman, Sportsman sportsman1, Statement statement, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE MEMBERS_COMPETITION SET CL_ID = " + club1.getCL_ID() + ", S_ID = " + sportsman1.getS_ID() + " WHERE COM_ID = " + membersCompetition.getCOM_ID() + " AND S_ID = " + membersCompetition.getS_ID());
            MembersCompetitionModel.newClub(membersCompetition,club, club1, tableView);
            MembersCompetitionModel.newSportsman(membersCompetition,sportsman, sportsman1, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void newMC_REG_DATE(MembersCompetition membersCompetition, String newValue, Statement statement, TableView<T> tableView){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE MEMBERS_COMPETITION SET MC_REG_DATE = " + "TO_DATE( '" + newValue + "', 'YYYY-MM-DD')" + " WHERE COM_ID = " + membersCompetition.getCOM_ID() + " AND S_ID = " + membersCompetition.getS_ID());
            MembersCompetitionModel.newMC_REG_DATE(membersCompetition,newValue, tableView);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void dop_tables(Statement statement, ObservableList<Competition> competitions,
                                  ObservableList<T> tObservableList,
                                  TableView<Competition> competitionTableView, TableView<T> tableView, TriFunctions<Club, Sportsman, MembersClubHistory, T> constructor){
        try(Statement statement1 = statement.getConnection().createStatement()){
            ResultSet resultSet = statement1.executeQuery("SELECT * FROM COMPETITION");
            while(resultSet.next()){
                Competition competition = new Competition(resultSet.getInt("COM_ID"), null, null, null,resultSet.getString("COM_NAME"),resultSet.getDate("COM_START_DATE"), resultSet.getDate("COM_END_DATE"), resultSet.getDate("COM_END_REG_DATE"), resultSet.getDate("COM_START_REG_DATE"));
                competitions.add(competition);
            }
            resultSet.close();
            ResultSet rs = statement1.executeQuery("SELECT m.MCH_ID, m.S_ID, m.CL_ID, MCH_START_DATE, MCH_END_DATE, CL_NAME, CL_TEL, S_NAME, S_SURNAME, S_PATRONYMIC, S_TEL, S_MAIL FROM MEMBERS_CLUB_HISTORY m JOIN Club c on c.CL_ID = m.CL_ID JOIN Sportsman s on s.S_ID = m.S_ID");
            while (rs.next()) {
                Club club = new Club(rs.getInt("CL_ID"), null,
                        rs.getString("CL_NAME"), null, null, rs.getString("CL_TEL"));
                MembersClubHistory membersClubHistory = new MembersClubHistory(rs.getInt("MCH_ID"), rs.getInt("S_ID"),
                        rs.getInt("CL_ID"), rs.getDate("MCH_START_DATE"),
                        rs.getDate("MCH_END_DATE"));
                Sportsman sportsman = new Sportsman(rs.getInt("S_ID"), rs.getString("S_NAME"),
                        rs.getString("S_SURNAME"), rs.getString("S_PATRONYMIC"), rs.getString("S_TEL"), rs.getString("S_MAIL"));
                T clubTypesSports = constructor.apply(club, sportsman, membersClubHistory);
                tObservableList.add(clubTypesSports);
            }
            rs.close();
            MembersCompetitionModel.dop_tables(competitions, tObservableList,
                    competitionTableView, tableView);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public static <T> void remove(MembersCompetition membersCompetition, Statement statement, AtomicInteger flag, T t, TableView<T> tableView, ObservableList<T> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM MEMBERS_COMPETITION WHERE COM_ID = " + membersCompetition.getCOM_ID() + " AND S_ID = " + membersCompetition.getS_ID());
            EnterRemoveModel.remove(t, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void enter(Statement statement, String COM_NAME, String COM_START_DATE, String COM_END_DATE, String COM_START_REG_DATE,
                                 String COM_END_REG_DATE, String S_TEL, String S_MAIL, String CL_NAME, String CL_TEL, String MC_REG_DATE,  AtomicInteger flag, TableView<T> tableView,ObservableList<T> data,
                                 TriFunction<MembersCompetition, Competition, Club, Sportsman, T> constructor){

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
            resultSet = statement1.executeQuery("SELECT * FROM CLUB WHERE CL_NAME = '" + CL_NAME + "' AND CL_TEL = '" + CL_TEL + "'");
            resultSet.next();
            Club club = new Club(resultSet.getInt("CL_ID"),null, resultSet.getString("CL_NAME"), null, null,resultSet.getString("CL_TEL"));
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
            MembersCompetition membersCompetition = new MembersCompetition(competition.getCOM_ID(),sportsman.getS_ID(),club.getCL_ID(), Date.valueOf(LocalDate.parse(MC_REG_DATE)));
            statement1.executeUpdate("INSERT INTO MEMBERS_COMPETITION(COM_ID, S_ID, CL_ID, MC_REG_DATE) VALUES (" + competition.getCOM_ID() + ", " + sportsman.getS_ID() + ", " + club.getCL_ID() + ", " + "TO_DATE( '" + membersCompetition.getMC_REG_DATE().toString() + "', 'YYYY-MM-DD'))");
            T t = constructor.apply(membersCompetition, competition, club, sportsman);
            EnterRemoveModel.enter(t, tableView, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
