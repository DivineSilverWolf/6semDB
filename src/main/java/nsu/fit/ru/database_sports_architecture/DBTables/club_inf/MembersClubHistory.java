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
public class MembersClubHistory {
    Integer MCH_ID;
    Integer S_ID;
    Integer CL_ID;
    Date MCH_START_DATE;
    Date MCH_END_DATE;
}
