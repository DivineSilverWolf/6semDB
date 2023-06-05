package nsu.fit.ru.database_sports_architecture.views.special_assignments;

import javafx.scene.control.TableView;

public class RefreshTableViewer {
    public static <T> void refresh(TableView<T> tableView){
        tableView.refresh();
    }
}
