package nsu.fit.ru.database_sports_architecture.DBTables.competition;

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
public class MembersCompetition {
    Integer COM_ID;
    Integer S_ID;
    Integer CL_ID;
    Date MC_REG_DATE;
}
