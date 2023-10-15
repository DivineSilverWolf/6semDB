package nsu.fit.ru.database_sports_architecture.models.competition;

import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.views.special_assignments.RefreshTableViewer;

public class OrganizerModel {
    public static void org_NTM(Organizer organizer, String newName, String newTel, String newMail, TableView<Organizer> tableView){
        organizer.setORG_NAME(newName);
        organizer.setORG_TEL(newTel);
        organizer.setORG_S_MAIL(newMail);
        RefreshTableViewer.refresh(tableView);
    }
}
