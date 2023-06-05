package nsu.fit.ru.database_sports_architecture.DBTables.types_sports;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.sql.Clob;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypesSports {
    Integer TS_ID;
    Integer TSI_ID;
    String TS_NAME;
    Clob TS_DESCRIPTION;
}
