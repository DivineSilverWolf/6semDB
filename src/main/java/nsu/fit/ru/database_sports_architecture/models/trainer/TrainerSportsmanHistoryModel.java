package nsu.fit.ru.database_sports_architecture.models.trainer;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.MembersClubHistory;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.Trainer;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.TrainerSportsmanHistory;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.views.trainer.TrainerSportsmanHistoryViewer;

import java.sql.Date;
import java.time.LocalDate;

public class TrainerSportsmanHistoryModel {
    public static void dop_tables(ObservableList<Trainer> dop_trainer_data, ObservableList<Sportsman> dop_sportsman_data, ObservableList<TypesSports> dop_types_sports_data, TableView<Trainer> dop_trainer, TableView<Sportsman> dop_sportsman, TableView<TypesSports> dop_types_sports){
        TrainerSportsmanHistoryViewer.dop_tables(dop_trainer_data,dop_sportsman_data,dop_types_sports_data,dop_trainer,dop_sportsman,dop_types_sports);
    }
    public static void start_date(TrainerSportsmanHistory trainerSportsmanHistory, String newValue){
        trainerSportsmanHistory.setTSH_START_DATE(Date.valueOf(LocalDate.parse(newValue)));
    }
    public static void end_date(TrainerSportsmanHistory trainerSportsmanHistory, String newValue){
        if (newValue != null)
            trainerSportsmanHistory.setTSH_END_DATE(Date.valueOf(LocalDate.parse(newValue)));
        else
            trainerSportsmanHistory.setTSH_END_DATE(null);
    }
}
