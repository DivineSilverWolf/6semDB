package nsu.fit.ru.database_sports_architecture.models.sports_facility.general_sf;

import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesSportsInfrastructures;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;

public class SportsFacilityInformationModel {
    public static void SFI_NEW_TSI(SportsFacilityInformation sportsFacilityInformation, TypesSportsInfrastructures typesSportsInfrastructures, String newValue, int NEW_TSI_ID){
        sportsFacilityInformation.setTSI_ID(NEW_TSI_ID);
        typesSportsInfrastructures.setTSI_ID(NEW_TSI_ID);
        typesSportsInfrastructures.setTSI_NAME(newValue);
    }
    public static void newNA(SportsFacilityInformation sportsFacilityInformation, String sfiName, String sfiAddr){
        sportsFacilityInformation.setSFI_NAME(sfiName);
        sportsFacilityInformation.setSFI_ADDR(sfiAddr);
    }
}
