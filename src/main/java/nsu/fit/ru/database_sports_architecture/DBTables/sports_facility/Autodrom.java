package nsu.fit.ru.database_sports_architecture.DBTables.sports_facility;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Autodrom {
    Integer A_ID;
    Integer A_CAPASITY;
}