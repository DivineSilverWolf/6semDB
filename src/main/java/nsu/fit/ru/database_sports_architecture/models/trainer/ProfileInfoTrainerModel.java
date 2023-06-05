package nsu.fit.ru.database_sports_architecture.models.trainer;

import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.ProfileInfoTrainer;
import nsu.fit.ru.database_sports_architecture.views.special_assignments.RefreshTableViewer;

public class ProfileInfoTrainerModel {
    public static <T> void new_AE(ProfileInfoTrainer profileInfoTrainer, String active, String exp, TableView<T> tableView){
        profileInfoTrainer.setPIT_TRAINER_ACTIVE(active);
        profileInfoTrainer.setPIT_EXP_MONTHS(Integer.parseInt(exp));
        RefreshTableViewer.refresh(tableView);
    }
}
