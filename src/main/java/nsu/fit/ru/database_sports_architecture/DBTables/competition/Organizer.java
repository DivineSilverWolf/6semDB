package nsu.fit.ru.database_sports_architecture.DBTables.competition;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Organizer {
    Integer ORG_ID;
    String ORG_NAME;
    String ORG_TEL;
    String ORG_S_MAIL;
}
