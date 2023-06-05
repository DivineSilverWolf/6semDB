package nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.tags_for_sf;

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
public class MaterialsSportsGround {
    String MSG_NAME_ID;
    Clob DESCRIPTION;
}
