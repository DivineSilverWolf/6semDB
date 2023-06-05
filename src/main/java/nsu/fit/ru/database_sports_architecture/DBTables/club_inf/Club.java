package nsu.fit.ru.database_sports_architecture.DBTables.club_inf;

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
public class Club {
    Integer CL_ID;
    Integer TS_ID;
    String CL_NAME;
    Date CL_HAIR_DATE;
    String CL_FOUNDER;
    String CL_TEL;

}
