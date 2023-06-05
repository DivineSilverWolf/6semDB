package nsu.fit.ru.database_sports_architecture.DBTables.sportsman;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sportsman {
    Integer S_ID;
    String S_NAME;
    String S_SURNAME;
    String S_PATRONYMIC;
    String S_TEL;
    String S_MAIL;
}
