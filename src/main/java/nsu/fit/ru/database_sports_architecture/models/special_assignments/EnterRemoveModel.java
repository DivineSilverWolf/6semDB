package nsu.fit.ru.database_sports_architecture.models.special_assignments;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.views.club_inf.ClubViewer;
import nsu.fit.ru.database_sports_architecture.views.special_assignments.EnterRemoveViewer;

import java.util.concurrent.atomic.AtomicInteger;

public class EnterRemoveModel {
    public static <T> void remove(T x, TableView<T> table, ObservableList<T> data, AtomicInteger flag){
        flag.getAndDecrement();
        EnterRemoveViewer.remove(x, table);
        data.remove(x);
    }
    public static <T> void enter(T clubTypesSports, TableView<T> table, ObservableList<T> data, AtomicInteger flag){
        EnterRemoveViewer.enter(clubTypesSports, table);
        data.add(flag.getAndIncrement(), clubTypesSports);
    }
}
