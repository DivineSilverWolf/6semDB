package nsu.fit.ru.database_sports_architecture.models.special_assignments;

import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.views.special_assignments.RefreshTableViewer;

public class RefreshTableModel {
    public static <T> void refresh(TableView<T> tableView){
        RefreshTableViewer.refresh(tableView);
    }
}
