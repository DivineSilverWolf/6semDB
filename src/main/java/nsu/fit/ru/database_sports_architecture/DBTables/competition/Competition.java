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
public class Competition {
    Integer COM_ID;
    Integer SFI_ID;
    Integer TS_ID;
    Integer ORG_ID;
    String COM_NAME;
    Date COM_START_DATE;
    Date COM_END_DATE;
    Date COM_END_REG_DATE;
    Date COM_START_REG_DATE;
}
