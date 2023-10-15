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
public class ProfileInfoTrainer {
    Integer T_ID;
    Integer TS_ID;
    Integer PIT_EXP_MONTHS;
    String PIT_TRAINER_ACTIVE;
}
