package nsu.fit.ru.database_sports_architecture.views.club_inf;


import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;

public class MembersClubHistoryViewer {
    public static void dop_tables(ObservableList<Club> dop_club_data, ObservableList<Sportsman> dop_sportsman_data, TableView<Sportsman> dop_sportsman, TableView<Club> dop_club){
        dop_sportsman.setItems(dop_sportsman_data);
        dop_club.setItems(dop_club_data);
    }
}
