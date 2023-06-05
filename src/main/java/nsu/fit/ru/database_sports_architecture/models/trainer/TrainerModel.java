package nsu.fit.ru.database_sports_architecture.models.trainer;

import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.Trainer;
import nsu.fit.ru.database_sports_architecture.views.special_assignments.RefreshTableViewer;

public class TrainerModel {
    public static void t_NSPTM(Trainer trainer, String newName, String newSurname, String newPatronymic, String newTel, String newMail, TableView<Trainer> tableView){
        trainer.setT_NAME(newName);
        trainer.setT_SURNAME(newSurname);
        trainer.setT_PATRONYMIC(newPatronymic);
        trainer.setT_TEL(newTel);
        trainer.setT_MAIL(newMail);
        RefreshTableViewer.refresh(tableView);
    }
}
