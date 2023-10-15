package nsu.fit.ru.database_sports_architecture.DBworckers.types_sports;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesSportsInfrastructures;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;
import nsu.fit.ru.database_sports_architecture.models.sports_facility.general_sf.SportsFacilityInformationModel;
import nsu.fit.ru.database_sports_architecture.models.types_sports.TypesSportsModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public class TypesSportsBDW {
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
    public static void TS_NEW_TSI(TypesSports typesSports, TypesSportsInfrastructures typesSportsInfrastructures, Statement statement, String newValue){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet rs1 = statement1.executeQuery("SELECT TSI_ID FROM TYPES_SPORTS_INFRASTRUCTURES ts WHERE ts.TSI_NAME = '" + newValue + "'");
            rs1.next();
            int NEW_TSI_ID = rs1.getInt("TSI_ID");
            statement1.executeUpdate("UPDATE TYPES_SPORTS SET TSI_ID = " + NEW_TSI_ID + " WHERE TS_ID = " + typesSports.getTS_ID());
            rs1.close();
            TypesSportsModel.TS_NEW_TSI(typesSports, typesSportsInfrastructures, newValue, NEW_TSI_ID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void newTS(TypesSports typesSports, String tsName, Statement statement, TableView<T> tableView) {
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE TYPES_SPORTS SET TS_NAME = '" + tsName + "' WHERE TS_ID = " + typesSports.getTS_ID());
            TypesSportsModel.newTS(typesSports, tsName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, BiFunction<TypesSportsInfrastructures,TypesSports, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table, constructor);
    }
    public static <T> void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ResultSet rs, TableView<T> table, BiFunction<TypesSportsInfrastructures,TypesSports, T> constructor){
        ObservableList<T> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table, constructor);
    }
    private static <T> void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<T> data, ObservableList<T> newData, ResultSet rs, TableView<T> table, BiFunction<TypesSportsInfrastructures,TypesSports, T> constructor){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData, constructor);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static <T> void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<T> data, ObservableList<T> newData, BiFunction<TypesSportsInfrastructures,TypesSports, T> constructor) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            TypesSports typesSports = new TypesSports(rs.getInt("TS_ID"), rs.getInt("TSI_ID"), rs.getString("TS_NAME"), null);
            TypesSportsInfrastructures typesSportsInfrastructures = new TypesSportsInfrastructures(rs.getInt("TSI_ID"),rs.getString("TSI_NAME"), null);
            T clubTypesSports = constructor.apply(typesSportsInfrastructures, typesSports);
            data.add(clubTypesSports);
            newData.add(clubTypesSports);
            flag.getAndIncrement();
        }
    }
    public static <T> void remove(TypesSports typesSports, Statement statement, AtomicInteger flag, T t, TableView<T> table, ObservableList<T> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM TYPES_SPORTS WHERE TS_ID = " + typesSports.getTS_ID());
            EnterRemoveModel.remove(t, table, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void enter(Statement statement, String typesSports, String typeInf, AtomicInteger flag, TableView<T> table,ObservableList<T> data,
                                 BiFunction<TypesSportsInfrastructures, TypesSports, T> constructor){

        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet rs1 = statement1.executeQuery("SELECT TSI_ID FROM TYPES_SPORTS_INFRASTRUCTURES ts WHERE ts.TSI_NAME = '" + typeInf + "'");
            rs1.next();
            int NEW_TSI_ID = rs1.getInt("TSI_ID");
            rs1 = statement1.executeQuery("SELECT MAX(TS_ID) as mx FROM TYPES_SPORTS");
            rs1.next();
            int NEW_TS_ID = rs1.getInt("mx") + 1;
            rs1.close();
            TypesSportsInfrastructures typesSportsInfrastructures = new TypesSportsInfrastructures(NEW_TSI_ID, typeInf, null);
            TypesSports typesSports1 = new TypesSports(NEW_TS_ID, NEW_TSI_ID, typesSports, null);
            T t = constructor.apply(typesSportsInfrastructures, typesSports1);

            statement1.executeUpdate("INSERT INTO TYPES_SPORTS(TS_ID, TSI_ID, TS_NAME) values (" +
                    NEW_TS_ID + ", " + NEW_TSI_ID + ", '" + typesSports + "')");


            EnterRemoveModel.enter(t, table, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
