package nsu.fit.ru.database_sports_architecture.views.special_assignments;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class ForwardBackViewer {
    public static <T> void viewTable(ObservableList<T> newData, TableView<T> table){
        if (newData.size() != 0)
            table.setItems(newData);
    }
}
