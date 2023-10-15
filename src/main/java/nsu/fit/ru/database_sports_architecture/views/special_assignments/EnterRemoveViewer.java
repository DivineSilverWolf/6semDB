package nsu.fit.ru.database_sports_architecture.views.special_assignments;

import javafx.scene.control.TableView;

public class EnterRemoveViewer {

    public static <T> void remove(T x, TableView<T> table){
        table.getItems().remove(x);
    }
    public static <T> void enter(T x, TableView<T> table){
        table.getItems().add(x);
    }
}
