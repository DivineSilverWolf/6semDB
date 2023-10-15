package nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.sports_facility.general_sf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesSportsInfrastructures;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.controllers.sports_facility.general_sf.SportsFacilityInformationController;
import nsu.fit.ru.database_sports_architecture.models.club_inf.ClubModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;
import nsu.fit.ru.database_sports_architecture.models.sports_facility.general_sf.SportsFacilityInformationModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public class SportsFacilityInformationDBW {
    public static <T> void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, BiFunction<TypesSportsInfrastructures,SportsFacilityInformation, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table, constructor);
    }
    public static <T> void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, BiFunction<TypesSportsInfrastructures,SportsFacilityInformation, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table, constructor);
    }
    private static <T> void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ObservableList<T> newData, ResultSet rs, TableView<T> table, BiFunction<TypesSportsInfrastructures,SportsFacilityInformation, T> constructor){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData, constructor);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static <T> void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<T> data, ObservableList<T> newData, BiFunction<TypesSportsInfrastructures,SportsFacilityInformation, T> constructor) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            SportsFacilityInformation sportsFacilityInformation = new SportsFacilityInformation(rs.getInt("SFI_ID"),rs.getInt("TSI_ID"),rs.getString("SFI_ADDR"),rs.getString("SFI_NAME"));
            TypesSportsInfrastructures typesSportsInfrastructures = new TypesSportsInfrastructures(rs.getInt("TSI_ID"),rs.getString("TSI_NAME"), null);
            T clubTypesSports = constructor.apply(typesSportsInfrastructures, sportsFacilityInformation);
            data.add(clubTypesSports);
            newData.add(clubTypesSports);
            flag.getAndIncrement();
        }
    }
    public static void dop_table(Statement statement, ObservableList<TypesSportsInfrastructures> typesSportsInfrastructures, TableView<TypesSportsInfrastructures> dop_table){
        try(Statement statement1 = statement.getConnection().createStatement()){
            ResultSet resultSet = statement1.executeQuery("SELECT TSI_ID, TSI_NAME FROM TYPES_SPORTS_INFRASTRUCTURES");
            while (resultSet.next()){
                TypesSportsInfrastructures typesSportsInfrastructura = new TypesSportsInfrastructures(resultSet.getInt("TSI_ID"),resultSet.getString("TSI_NAME"), null);
                typesSportsInfrastructures.add(typesSportsInfrastructura);
            }
            dop_table.setItems(typesSportsInfrastructures);
        }
        catch (SQLException e){
            throw new  RuntimeException(e);
        }
    }
    public static void SFI_NEW_TSI(SportsFacilityInformation sportsFacilityInformation, TypesSportsInfrastructures typesSportsInfrastructures, Statement statement, String newValue){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet rs1 = statement1.executeQuery("SELECT TSI_ID FROM TYPES_SPORTS_INFRASTRUCTURES ts WHERE ts.TSI_NAME = '" + newValue + "'");
            rs1.next();
            int NEW_TSI_ID = rs1.getInt("TSI_ID");
            statement1.executeUpdate("UPDATE SPORTS_FACILITY_INFORMATION SET TSI_ID = " + NEW_TSI_ID + " WHERE SFI_ID = " + sportsFacilityInformation.getSFI_ID());
            rs1.close();
            SportsFacilityInformationModel.SFI_NEW_TSI(sportsFacilityInformation, typesSportsInfrastructures, newValue, NEW_TSI_ID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> void newNA(SportsFacilityInformation sportsFacilityInformation, String sfiName, String sfiAddr, Statement statement, TableView<T> tableView) {
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE SPORTS_FACILITY_INFORMATION SET SFI_NAME = '" + sfiName + "', SFI_ADDR = '" + sfiAddr + "' WHERE SFI_ID = " + sportsFacilityInformation.getSFI_ID());
            SportsFacilityInformationModel.newNA(sportsFacilityInformation, sfiName, sfiAddr);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void remove(SportsFacilityInformation sportsFacilityInformation, Statement statement, AtomicInteger flag, T t, TableView<T> table, ObservableList<T> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM SPORTS_FACILITY_INFORMATION WHERE SFI_ID = " + sportsFacilityInformation.getSFI_ID());
            EnterRemoveModel.remove(t, table, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void enter(Statement statement, String name, String addr, String types, AtomicInteger flag, TableView<T> table,ObservableList<T> data,
                                 BiFunction<TypesSportsInfrastructures, SportsFacilityInformation, T> constructor){

        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet rs1 = statement1.executeQuery("SELECT TSI_ID FROM TYPES_SPORTS_INFRASTRUCTURES ts WHERE ts.TSI_NAME = '" + types + "'");
            rs1.next();
            int NEW_TSI_ID = rs1.getInt("TSI_ID");
            rs1 = statement1.executeQuery("SELECT MAX(SFI_ID) as mx FROM SPORTS_FACILITY_INFORMATION");
            rs1.next();
            int NEW_SFI_ID = rs1.getInt("mx") + 1;
            rs1.close();
            TypesSportsInfrastructures typesSportsInfrastructures = new TypesSportsInfrastructures(NEW_TSI_ID, types, null);
            SportsFacilityInformation sportsFacilityInformation = new SportsFacilityInformation(NEW_SFI_ID, NEW_TSI_ID, addr, name);
            T clubTypesSports = constructor.apply(typesSportsInfrastructures, sportsFacilityInformation);

            statement1.executeUpdate("INSERT INTO SPORTS_FACILITY_INFORMATION(SFI_ID, TSI_ID, SFI_ADDR, SFI_NAME) values (" +
                    NEW_SFI_ID + ", " + NEW_TSI_ID + ", '" + addr + "', '" + name + "')");


            EnterRemoveModel.enter(clubTypesSports, table, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
