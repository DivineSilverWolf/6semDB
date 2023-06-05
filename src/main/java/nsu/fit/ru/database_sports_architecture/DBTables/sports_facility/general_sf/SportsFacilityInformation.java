package nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SportsFacilityInformation {
    Integer SFI_ID;
    Integer TSI_ID;
    String SFI_ADDR;
    String SFI_NAME;
}
