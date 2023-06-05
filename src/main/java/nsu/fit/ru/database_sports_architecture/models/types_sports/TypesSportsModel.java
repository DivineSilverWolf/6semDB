package nsu.fit.ru.database_sports_architecture.models.types_sports;

import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesSportsInfrastructures;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;

public class TypesSportsModel {
    public static void TS_NEW_TSI(TypesSports typesSports, TypesSportsInfrastructures typesSportsInfrastructures, String newValue, int NEW_TSI_ID){
        typesSports.setTSI_ID(NEW_TSI_ID);
        typesSportsInfrastructures.setTSI_ID(NEW_TSI_ID);
        typesSportsInfrastructures.setTSI_NAME(newValue);
    }
    public static void newTS(TypesSports typesSports, String tsName){
        typesSports.setTS_NAME(tsName);
    }
}
