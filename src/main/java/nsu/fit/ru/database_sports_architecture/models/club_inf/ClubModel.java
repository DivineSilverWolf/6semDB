package nsu.fit.ru.database_sports_architecture.models.club_inf;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.views.club_inf.ClubViewer;

import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class ClubModel {
    public static void founder(Club club, String newValue){
        club.setCL_FOUNDER(newValue);
    }
    public static void tel(Club club, String newValue){
        club.setCL_TEL(newValue);
    }
    public static void date(Club club, String newValue){
        club.setCL_HAIR_DATE(Date.valueOf(LocalDate.parse(newValue)));
    }
    public static void clubName(Club club, String newValue){
        club.setCL_NAME(newValue);
    }
    public static void typesSport(TypesSports typesSports, Club club, String newValue, int NEW_TS_ID){
        club.setTS_ID(NEW_TS_ID);
        typesSports.setTS_ID(NEW_TS_ID);
        typesSports.setTS_NAME(newValue);
    }

}
