package nsu.fit.ru.database_sports_architecture.models.club_inf;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.MembersClubHistory;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.RefreshTableModel;
import nsu.fit.ru.database_sports_architecture.views.club_inf.ClubViewer;
import nsu.fit.ru.database_sports_architecture.views.club_inf.MembersClubHistoryViewer;

import java.sql.Date;
import java.time.LocalDate;

public class MembersClubHistoryModel {
    public static <T> void updateCLNameCLTel(MembersClubHistory membersClubHistory, Club club, String NEW_CL_NAME, int NEW_CL_ID, String NEW_CL_TEL, TableView<T> tableView){
        membersClubHistory.setCL_ID(NEW_CL_ID);
        club.setCL_NAME(NEW_CL_NAME);
        club.setCL_TEL(NEW_CL_TEL);
        RefreshTableModel.refresh(tableView);
    }
    public static <T> void anotherSportsman(MembersClubHistory membersClubHistory, Sportsman sportsman, String NEW_S_NAME, String NEW_S_SURNAME, String NEW_S_PATRONYMIC, String NEW_S_TEL, String NEW_S_MAIL, int NEW_S_ID, TableView<T> tableView){
        membersClubHistory.setS_ID(NEW_S_ID);
        sportsman.setS_ID(NEW_S_ID);
        sportsman.setS_MAIL(NEW_S_MAIL);
        sportsman.setS_TEL(NEW_S_TEL);
        sportsman.setS_NAME(NEW_S_NAME);
        sportsman.setS_SURNAME(NEW_S_SURNAME);
        sportsman.setS_PATRONYMIC(NEW_S_PATRONYMIC);
        RefreshTableModel.refresh(tableView);
    }
    public static void start_date(MembersClubHistory membersClubHistory, String newValue){
        membersClubHistory.setMCH_START_DATE(Date.valueOf(LocalDate.parse(newValue)));
    }
    public static void end_date(MembersClubHistory membersClubHistory, String newValue){
        if (newValue != null)
            membersClubHistory.setMCH_END_DATE(Date.valueOf(LocalDate.parse(newValue)));
        else
            membersClubHistory.setMCH_END_DATE(null);
    }
    public static void dop_tables(ObservableList<Club> dop_club_data, ObservableList<Sportsman> dop_sportsman_data, TableView<Sportsman> dop_sportsman, TableView<Club> dop_club){
        MembersClubHistoryViewer.dop_tables(dop_club_data,dop_sportsman_data,dop_sportsman,dop_club);
    }
}
