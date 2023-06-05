package nsu.fit.ru.database_sports_architecture.DBTables.sportsman;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.sql.Clob;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ranks {
    Integer RANK_ID;
    Integer S_ID;
    Integer TS_ID;
    Integer RANK_SPORT;
    String RANK_NAME;
    Clob RANK_DESCRIPTION;
    Date RANK_DATE;
}
