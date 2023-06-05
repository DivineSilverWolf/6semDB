package nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.sports_facility.general_sf;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesSportsInfrastructures;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesTSINames;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.EnterRemoveModel;
import nsu.fit.ru.database_sports_architecture.models.special_assignments.ForwardBackModel;
import oracle.jdbc.rowset.OracleSerialClob;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

public class TypesTSINamesDBW {
    public static void forward(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<TypesTSINames> data, ResultSet rs, TableView<TypesTSINames> table){
        ObservableList<TypesTSINames> newData = FXCollections.observableArrayList();
        getDataTable(textFieldCount, count,flag, data,newData, rs, table);
    }
    public static void back(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<TypesTSINames> data, ResultSet rs, TableView<TypesTSINames> table){
        ObservableList<TypesTSINames> newData = FXCollections.observableArrayList();
        flag.getAndSet(flag.get() - textFieldCount.get() * 2);
        if(flag.get() < 0)
            flag.set(0);
        getDataTable(textFieldCount, count,flag,data,newData, rs, table);
    }
    private static void getDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ObservableList<TypesTSINames> data, ObservableList<TypesTSINames> newData, ResultSet rs, TableView<TypesTSINames> table){
        try{
            ForwardBackModel.forwardCountNotFlag(textFieldCount, count, flag, data, newData);
            newDataTable(textFieldCount, count, flag, rs, data, newData);
            ForwardBackModel.finishAddData(count, newData, table);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void newDataTable(AtomicInteger textFieldCount, AtomicInteger count, AtomicInteger flag, ResultSet rs, ObservableList<TypesTSINames> data, ObservableList<TypesTSINames> newData) throws SQLException {
        while (count.getAndIncrement() != textFieldCount.get() && rs.next()) {
            TypesTSINames typesSportsInfrastructures = new TypesTSINames(rs.getString("TTN_NAMES"));
            data.add(typesSportsInfrastructures);
            newData.add(typesSportsInfrastructures);
            flag.getAndIncrement();
        }
    }
    public static <T> void remove(TypesTSINames typesTSINames, Statement statement, AtomicInteger flag, T t, TableView<T> table, ObservableList<T> data){
        try(Statement statement1 = statement.getConnection().createStatement()) {
            statement1.executeUpdate("DELETE FROM TYPES_TSI_NAMES WHERE TTN_NAMES = '" + typesTSINames.getTTN_NAMES() + "'");
            EnterRemoveModel.remove(t, table, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> void enter(Statement statement, String type, AtomicInteger flag, TableView<TypesTSINames> table,ObservableList<TypesTSINames> data){

        try(Statement statement1 = statement.getConnection().createStatement()) {
            TypesTSINames tsiNames = new TypesTSINames(type);
            statement1.executeUpdate("INSERT INTO TYPES_TSI_NAMES(TTN_NAMES) values ('" + type + "')");
            EnterRemoveModel.enter(tsiNames, table, data, flag);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
