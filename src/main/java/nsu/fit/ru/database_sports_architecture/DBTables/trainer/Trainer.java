package nsu.fit.ru.database_sports_architecture.DBTables.trainer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Trainer {
    Integer T_ID;
    String T_NAME;
    String T_SURNAME;
    String T_PATRONYMIC;
    String T_TEL;
    String T_MAIL;
}
