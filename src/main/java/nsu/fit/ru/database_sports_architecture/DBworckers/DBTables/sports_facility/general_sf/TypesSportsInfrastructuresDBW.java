package nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.sports_facility.general_sf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesSportsInfrastructures;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesTSINames;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;
import nsu.fit.ru.database_sports_architecture.models.sports_facility.general_sf.SportsFacilityInformationModel;
import oracle.jdbc.rowset.OracleSerialClob;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

public class TypesSportsInfrastructuresDBW {
    public static void dop_table(Statement statement, ObservableList<TypesTSINames> typesTSINames, TableView<TypesTSINames> dop_table){
        try(Statement statement1 = statement.getConnection().createStatement()){
            ResultSet resultSet = statement1.executeQuery("SELECT TTN_NAMES FROM TYPES_TSI_NAMES");
            while (resultSet.next()){
                TypesTSINames tsiNames = new TypesTSINames(resultSet.getString("TTN_NAMES"));
                typesTSINames.add(tsiNames);
            }
            dop_table.setItems(typesTSINames);
        }
        catch (SQLException e){
            throw new  RuntimeException(e);
        }
    }
    public static void TSI_NEW_TTN(TypesSportsInfrastructures typesSportsInfrastructures, Statement statement){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("UPDATE TYPES_SPORTS_INFRASTRUCTURES SET TSI_NAME = " + typesSportsInfrastructures.getTSI_NAME() + " WHERE TSI_ID = " + typesSportsInfrastructures.getTSI_ID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<TypesSportsInfrastructures> data, ResultSet rs, TableView<TypesSportsInfrastructures> table){
        ObservableList<TypesSportsInfrastructures> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table);
    }
    public static void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<TypesSportsInfrastructures> data, ResultSet rs, TableView<TypesSportsInfrastructures> table){
        ObservableList<TypesSportsInfrastructures> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table);
    }
    private static void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<TypesSportsInfrastructures> data, ObservableList<TypesSportsInfrastructures> newData, ResultSet rs, TableView<TypesSportsInfrastructures> table){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<TypesSportsInfrastructures> data, ObservableList<TypesSportsInfrastructures> newData) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            TypesSportsInfrastructures typesSportsInfrastructures = new TypesSportsInfrastructures(rs.getInt("TSI_ID"), rs.getString("TSI_NAME"), rs.getClob("TSI_DESCRIPTION"));
            data.add(typesSportsInfrastructures);
            newData.add(typesSportsInfrastructures);
            flag.getAndIncrement();
        }
    }
    public static <T> void remove(TypesSportsInfrastructures typesSportsInfrastructures, Statement statement, AtomicInteger flag, T t, TableView<T> table, ObservableList<T> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM TYPES_SPORTS_INFRASTRUCTURES WHERE TSI_ID = " + typesSportsInfrastructures.getTSI_ID());
            EnterRemoveModel.remove(t, table, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void enter(Statement statement, String type, String desc, AtomicInteger flag, TableView<TypesSportsInfrastructures> table,ObservableList<TypesSportsInfrastructures> data){

        try(Statement statement1 = statement.getConnection().createStatement()) {
            ResultSet rs1 = statement1.executeQuery("SELECT MAX(TSI_ID) as mx FROM TYPES_SPORTS_INFRASTRUCTURES");
            rs1.next();
            int NEW_TSI_ID = rs1.getInt("mx") + 1;
            rs1.close();
            Clob clob = null;
            try {
                InputStream inputStream = new ByteArrayInputStream(desc.getBytes());
                clob = new OracleSerialClob(desc.toCharArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
            TypesSportsInfrastructures clubTypesSports = new TypesSportsInfrastructures(NEW_TSI_ID, type, clob);
            if (desc == null){
                statement1.executeUpdate("INSERT INTO TYPES_SPORTS_INFRASTRUCTURES(TSI_ID, TSI_NAME, TSI_DESCRIPTION) values (" +
                        NEW_TSI_ID + ", '" + type + "', NULL)");
            }
            else {
                statement1.executeUpdate("INSERT INTO TYPES_SPORTS_INFRASTRUCTURES(TSI_ID, TSI_NAME, TSI_DESCRIPTION) values (" +
                        NEW_TSI_ID + ", '" + type + "', '" + desc + "')");
            }

            EnterRemoveModel.enter(clubTypesSports, table, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
