package nsu.fit.ru.database_sports_architecture.models.competition;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Competition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.MembersCompetition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Winners;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.RefreshTableModel;
import nsu.fit.ru.database_sports_architecture.views.competition.MembersCompetitionViewer;
import nsu.fit.ru.database_sports_architecture.views.competition.WinnersViewer;

import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;

public class WinnersModel {
    public static <T> void dop_tables(ObservableList<T> tObservableList, TableView<T> tableView){

        WinnersViewer.dop_tables(tObservableList, tableView);
    }
    public static <T> void newCompetition(Winners winners, Competition competition, Competition competition1, TableView<T> tableView){
        winners.setCOM_ID(competition1.getCOM_ID());
        competition.setCOM_ID(competition1.getCOM_ID());
        competition.setCOM_NAME(competition1.getCOM_NAME());
        competition.setCOM_START_DATE(competition1.getCOM_START_DATE());
        competition.setCOM_START_REG_DATE(competition1.getCOM_START_REG_DATE());
        competition.setCOM_END_DATE(competition1.getCOM_END_DATE());
        competition.setCOM_END_REG_DATE(competition1.getCOM_END_REG_DATE());
        RefreshTableModel.refresh(tableView);
    }
    public static <T> void newSportsman(Winners winners, Sportsman sportsman, Sportsman sportsman1, TableView<T> tableView){
        winners.setS_ID(sportsman1.getS_ID());
        sportsman.setS_ID(sportsman1.getS_ID());
        sportsman.setS_NAME(sportsman1.getS_NAME());
        sportsman.setS_SURNAME(sportsman1.getS_SURNAME());
        sportsman.setS_PATRONYMIC(sportsman1.getS_PATRONYMIC());
        sportsman.setS_MAIL(sportsman1.getS_MAIL());
        sportsman.setS_TEL(sportsman1.getS_TEL());

        RefreshTableModel.refresh(tableView);
    }
    public static <T> void new_W_PLACE(Winners winners, int new_place, TableView<T> tableView){
        winners.setW_PLACE(new_place);
        RefreshTableModel.refresh(tableView);
    }
    public static <T> void new_W_DATE(Winners winners, String new_date, TableView<T> tableView){
        winners.setW_DATE(Date.valueOf(LocalDate.parse(new_date)));
        RefreshTableModel.refresh(tableView);
    }
}
