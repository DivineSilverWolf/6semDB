package nsu.fit.ru.database_sports_architecture.models.competition;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Competition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.MembersCompetition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.RefreshTableModel;
import nsu.fit.ru.database_sports_architecture.views.competition.CompetitionViewer;
import nsu.fit.ru.database_sports_architecture.views.competition.MembersCompetitionViewer;

import java.sql.Date;
import java.time.LocalDate;

public class MembersCompetitionModel {
    public static <T> void newCompetition(MembersCompetition membersCompetition, Competition competition, Competition competition1, TableView<T> tableView){
        membersCompetition.setCOM_ID(competition1.getCOM_ID());

        competition.setCOM_ID(competition1.getCOM_ID());
        competition.setCOM_NAME(competition1.getCOM_NAME());
        competition.setCOM_START_DATE(competition1.getCOM_START_DATE());
        competition.setCOM_START_REG_DATE(competition1.getCOM_START_REG_DATE());
        competition.setCOM_END_DATE(competition1.getCOM_END_DATE());
        competition.setCOM_END_REG_DATE(competition1.getCOM_END_REG_DATE());
        RefreshTableModel.refresh(tableView);
    }
    public static <T> void newClub(MembersCompetition membersCompetition, Club club, Club club1, TableView<T> tableView){
        membersCompetition.setCL_ID(club1.getCL_ID());

        club.setCL_ID(club1.getCL_ID());
        club.setCL_NAME(club1.getCL_NAME());
        club.setCL_TEL(club1.getCL_TEL());
        RefreshTableModel.refresh(tableView);
    }
    public static <T> void newSportsman(MembersCompetition membersCompetition, Sportsman sportsman, Sportsman sportsman1, TableView<T> tableView){
        membersCompetition.setS_ID(sportsman1.getS_ID());

        sportsman.setS_ID(sportsman1.getS_ID());
        sportsman.setS_NAME(sportsman1.getS_NAME());
        sportsman.setS_SURNAME(sportsman1.getS_SURNAME());
        sportsman.setS_PATRONYMIC(sportsman1.getS_PATRONYMIC());
        sportsman.setS_MAIL(sportsman1.getS_MAIL());
        sportsman.setS_TEL(sportsman1.getS_TEL());

        RefreshTableModel.refresh(tableView);
    }
    public static <T> void newMC_REG_DATE(MembersCompetition membersCompetition, String newValue, TableView<T> tableView){
        membersCompetition.setMC_REG_DATE(Date.valueOf(LocalDate.parse(newValue)));
        RefreshTableModel.refresh(tableView);
    }
    public static <T> void dop_tables(ObservableList<Competition> competitions,
                                  ObservableList<T> tObservableList,
                                  TableView<Competition> competitionTableView, TableView<T> tableView){

        MembersCompetitionViewer.dop_tables(competitions,tObservableList, competitionTableView, tableView);
    }
}
