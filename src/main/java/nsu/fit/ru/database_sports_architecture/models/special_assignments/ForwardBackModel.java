package nsu.fit.ru.database_sports_architecture.models.special_assignments;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.views.special_assignments.ForwardBackViewer;

import java.util.concurrent.atomic.AtomicInteger;

public class ForwardBackModel {
    public static <T> void  forwardCountNotFlag(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ObservableList<T> newData){
        if (flag.get() != data.size()) {
            while (count.getAndIncrement() != textFieldCount.get() && flag.get() != data.size()) {
                newData.add(data.get(flag.getAndIncrement()));
            }
            count.getAndDecrement();
        }
    }
    public static <T> void finishAddData(AtomicInteger count,ObservableList<T> newData, TableView<T> table){
        count.set(0);
        ForwardBackViewer.viewTable(newData, table);
    }
}
