package nsu.fit.ru.database_sports_architecture.views.competition;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Competition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;

public class MembersCompetitionViewer {
    public static <T> void dop_tables(ObservableList<Competition> competitions,
                                  ObservableList<T> tObservableList,
                                  TableView<Competition> competitionTableView, TableView<T> tableView){
       competitionTableView.setItems(competitions);
       tableView.setItems(tObservableList);
    }
}
