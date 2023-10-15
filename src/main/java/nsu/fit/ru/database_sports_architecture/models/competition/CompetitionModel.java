package nsu.fit.ru.database_sports_architecture.models.competition;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.MembersClubHistory;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Competition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.RefreshTableModel;
import nsu.fit.ru.database_sports_architecture.views.club_inf.MembersClubHistoryViewer;
import nsu.fit.ru.database_sports_architecture.views.competition.CompetitionViewer;
import nsu.fit.ru.database_sports_architecture.views.special_assignments.EnterRemoveViewer;

import java.sql.Date;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class CompetitionModel {
    public static <T> void updateName(Competition competition, String new_name, TableView<T> tableView){
        competition.setCOM_NAME(new_name);
        RefreshTableModel.refresh(tableView);
    }
    public static <T> void newStartDate(Competition competition, String newValue, TableView<T> tableView){
        competition.setCOM_START_DATE(Date.valueOf(LocalDate.parse(newValue)));
        RefreshTableModel.refresh(tableView);
    }
    public static <T> void newStartRegDate(Competition competition, String newValue, TableView<T> tableView){
        competition.setCOM_START_REG_DATE(Date.valueOf(LocalDate.parse(newValue)));
        RefreshTableModel.refresh(tableView);
    }
    public static <T> void newEndDate(Competition competition, String newValue, TableView<T> tableView){
        if(newValue != null)
            competition.setCOM_END_DATE(Date.valueOf(LocalDate.parse(newValue)));
        else
            competition.setCOM_END_DATE(null);
        RefreshTableModel.refresh(tableView);
    }
    public static <T> void newEndRegDate(Competition competition, String newValue, TableView<T> tableView){
        competition.setCOM_END_REG_DATE(Date.valueOf(LocalDate.parse(newValue)));
        RefreshTableModel.refresh(tableView);
    }
    public static <T> void updateTS(Competition competition, TypesSports typesSports, String NEW_TS_NAME, int NEW_TS_ID, TableView<T> tableView){
        competition.setTS_ID(NEW_TS_ID);
        typesSports.setTS_ID(NEW_TS_ID);
        typesSports.setTS_NAME(NEW_TS_NAME);
        RefreshTableModel.refresh(tableView);
    }
    public static <T> void updateOrg(Competition competition, Organizer organizer, Organizer organizer1, TableView<T> tableView){
        competition.setORG_ID(organizer1.getORG_ID());
        organizer.setORG_ID(organizer1.getORG_ID());
        organizer.setORG_NAME(organizer1.getORG_NAME());
        organizer.setORG_TEL(organizer1.getORG_TEL());
        organizer.setORG_S_MAIL(organizer1.getORG_S_MAIL());
        RefreshTableModel.refresh(tableView);
    }
    public static <T> void updateSFI(Competition competition, SportsFacilityInformation sportsFacilityInformation,
                                     SportsFacilityInformation sportsFacilityInformation1, TableView<T> tableView){
        competition.setSFI_ID(sportsFacilityInformation1.getSFI_ID());
        sportsFacilityInformation.setSFI_ID(sportsFacilityInformation1.getSFI_ID());
        sportsFacilityInformation.setSFI_NAME(sportsFacilityInformation1.getSFI_NAME());
        sportsFacilityInformation.setSFI_ADDR(sportsFacilityInformation1.getSFI_ADDR());
        sportsFacilityInformation.setTSI_ID(sportsFacilityInformation1.getTSI_ID());
        RefreshTableModel.refresh(tableView);
    }
    public static void dop_tables(ObservableList<TypesSports> typesSports,
                                  ObservableList<SportsFacilityInformation> sportsFacilityInformation, ObservableList<Organizer> organizer,
                                  TableView<TypesSports> typesSportsTableView,
                                  TableView<SportsFacilityInformation> sportsFacilityInformationTableView, TableView<Organizer> organizerTableView){

        CompetitionViewer.dop_tables(typesSports,sportsFacilityInformation,organizer,
                typesSportsTableView, sportsFacilityInformationTableView, organizerTableView);
    }
}
