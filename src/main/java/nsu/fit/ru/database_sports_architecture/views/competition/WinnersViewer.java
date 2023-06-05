package nsu.fit.ru.database_sports_architecture.views.competition;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class WinnersViewer {
    public static <T> void dop_tables(ObservableList<T> tObservableList, TableView<T> tableView){
        tableView.setItems(tObservableList);
    }
}
