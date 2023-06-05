package nsu.fit.ru.database_sports_architecture.models.sportsman;

import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.views.special_assignments.RefreshTableViewer;

public class SportsmanModel {
    public static void s_NSPTM(Sportsman sportsman, String newName, String newSurname, String newPatronymic, String newTel, String newMail, TableView<Sportsman> tableView){
        sportsman.setS_NAME(newName);
        sportsman.setS_SURNAME(newSurname);
        sportsman.setS_PATRONYMIC(newPatronymic);
        sportsman.setS_TEL(newTel);
        sportsman.setS_MAIL(newMail);
        RefreshTableViewer.refresh(tableView);
    }
}
