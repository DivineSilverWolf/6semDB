package nsu.fit.ru.database_sports_architecture.models.sportsman;

import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Ranks;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.ProfileInfoTrainer;
import nsu.fit.ru.database_sports_architecture.views.special_assignments.RefreshTableViewer;

import java.sql.Date;
import java.time.LocalDate;

public class RanksModel {
    public static <T> void new_NDP(Ranks ranks, String r_name, String r_date, String r_place, TableView<T> tableView){
        ranks.setRANK_NAME(r_name);
        ranks.setRANK_DATE(Date.valueOf(LocalDate.parse(r_date)));
        ranks.setRANK_SPORT(Integer.parseInt(r_place));
        RefreshTableViewer.refresh(tableView);
    }
}
