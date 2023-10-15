package nsu.fit.ru.database_sports_architecture.DBTables.trainer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainerSportsmanHistory {
    Integer TSH_ID;
    Integer T_ID;
    Integer TS_ID;
    Integer S_ID;
    Date TSH_START_DATE;
    Date TSH_END_DATE;
}
