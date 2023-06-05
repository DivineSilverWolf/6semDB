module nsu.fit.ru.database_sports_architecture {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires ojdbc8;
    opens nsu.fit.ru.database_sports_architecture to javafx.fxml, lombok;
    exports nsu.fit.ru.database_sports_architecture;
    opens nsu.fit.ru.database_sports_architecture.DBTables.club_inf to lombok;
    opens nsu.fit.ru.database_sports_architecture.DBTables.competition to lombok;
    opens nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf to lombok;
    opens nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.tags_for_sf to lombok;
    opens nsu.fit.ru.database_sports_architecture.DBTables.sports_facility to lombok;
    opens nsu.fit.ru.database_sports_architecture.DBTables.types_sports to lombok;
    opens nsu.fit.ru.database_sports_architecture.DBTables.sportsman to lombok;
    opens nsu.fit.ru.database_sports_architecture.DBTables.trainer to lombok;
    exports nsu.fit.ru.database_sports_architecture.controllers;
    opens nsu.fit.ru.database_sports_architecture.controllers to javafx.fxml, lombok;

    opens nsu.fit.ru.database_sports_architecture.controllers.club_inf to javafx.fxml;
    opens nsu.fit.ru.database_sports_architecture.controllers.competition to javafx.fxml;
    opens nsu.fit.ru.database_sports_architecture.controllers.sportsman to javafx.fxml;
    opens nsu.fit.ru.database_sports_architecture.controllers.trainer to javafx.fxml;
    opens nsu.fit.ru.database_sports_architecture.controllers.sports_facility.general_sf to javafx.fxml;
    opens nsu.fit.ru.database_sports_architecture.controllers.types_sports to  javafx.fxml;
}
