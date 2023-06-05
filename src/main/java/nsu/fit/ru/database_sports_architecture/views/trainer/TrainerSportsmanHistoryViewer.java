package nsu.fit.ru.database_sports_architecture.views.trainer;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.Trainer;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;

public class TrainerSportsmanHistoryViewer {
    public static void dop_tables(ObservableList<Trainer> dop_trainer_data, ObservableList<Sportsman> dop_sportsman_data, ObservableList<TypesSports> dop_types_sports_data, TableView<Trainer> dop_trainer, TableView<Sportsman> dop_sportsman, TableView<TypesSports> dop_types_sports){
        dop_sportsman.setItems(dop_sportsman_data);
        dop_trainer.setItems(dop_trainer_data);
        dop_types_sports.setItems(dop_types_sports_data);
    }
}
