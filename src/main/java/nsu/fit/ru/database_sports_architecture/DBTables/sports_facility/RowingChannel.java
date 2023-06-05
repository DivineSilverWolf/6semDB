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
public class RowingChannel {
    Integer RC_ID;
    Integer RC_H_SIZE;
    Integer RC_W_SIZE;
    Integer RC_L_SIZE;
}
