package nsu.fit.ru.database_sports_architecture.views.competition;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;

public class CompetitionViewer {
    public static void dop_tables(ObservableList<TypesSports> typesSports,
                                  ObservableList<SportsFacilityInformation> sportsFacilityInformation, ObservableList<Organizer> organizer,
                                  TableView<TypesSports> typesSportsTableView,
                                  TableView<SportsFacilityInformation> sportsFacilityInformationTableView, TableView<Organizer> organizerTableView){
        typesSportsTableView.setItems(typesSports);
        sportsFacilityInformationTableView.setItems(sportsFacilityInformation);
        organizerTableView.setItems(organizer);
    }
}
