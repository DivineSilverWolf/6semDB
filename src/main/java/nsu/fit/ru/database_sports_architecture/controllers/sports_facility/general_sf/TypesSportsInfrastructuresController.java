package nsu.fit.ru.database_sports_architecture.controllers.sports_facility.general_sf;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesSportsInfrastructures;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesTSINames;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.sports_facility.general_sf.SportsFacilityInformationDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.sports_facility.general_sf.TypesSportsInfrastructuresDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.sports_facility.general_sf.TypesTSINamesDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.special_assignments.GetterTextOrNullTextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypesSportsInfrastructuresController implements Initializable  {

    Statement statement;
    ResultSet rs;
    String login;
    public TypesSportsInfrastructuresController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }
    @FXML
    TableView<TypesSportsInfrastructures> tableView;
    @FXML
    TableColumn<TypesSportsInfrastructures, String> types;
    @FXML
    TableColumn<TypesSportsInfrastructures, String> description;

    @FXML
    TableView<TypesTSINames> dop_table;
    @FXML
    TableColumn<TypesTSINames, String> dop_types;

    @FXML
    TextField typesEnter;
    @FXML
    TextField descriptionEnter;

    @FXML
    Button back;
    @FXML
    Button forward;
    @FXML
    TextField count;
    @FXML
    Button enter;
    @FXML
    Button remove;
    @FXML
    Button menu;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtomicInteger count = new AtomicInteger();
        ObservableList<TypesSportsInfrastructures> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);
        types.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTSI_NAME()));
        types.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(cellData.getValue().getTSI_DESCRIPTION() != null ?  cellData.getValue().getTSI_DESCRIPTION().getSubString(1, (int) cellData.getValue().getTSI_DESCRIPTION().length()) : "N/A");
            } catch (SQLException e) {
                return new SimpleStringProperty("ERROR");
            }
        });
        description.setCellFactory(TextFieldTableCell.forTableColumn());


        dop_types.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTTN_NAMES()));
        dop_types.setCellFactory(TextFieldTableCell.forTableColumn());
        ObservableList<TypesTSINames> typesSportsInfrastructures = FXCollections.observableArrayList();
        TypesSportsInfrastructuresDBW.dop_table(statement,typesSportsInfrastructures,dop_table);
        AtomicReference<TypesTSINames> typesTSINamesAtomicReference = new AtomicReference<>();

        dop_table.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<TypesTSINames> selectedRows = dop_table.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    typesTSINamesAtomicReference.set(selectedRows.get(0));
                }
            } else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<TypesTSINames> selectedRows = dop_table.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    TypesTSINames typesTSINames = selectedRows.get(0);
                    typesEnter.setText(typesTSINames.getTTN_NAMES());
                }
            }
        });

        tableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.V && event.isControlDown()) {
                ObservableList<TypesSportsInfrastructures> selectedItems = tableView.getSelectionModel().getSelectedItems();
                // Далее можем получить информацию о выделенных элементах
                TypesSportsInfrastructures typesSportsInfrastructures1 = selectedItems.get(0);
                if(typesTSINamesAtomicReference.get() != null){
                    TypesSportsInfrastructuresDBW.TSI_NEW_TTN(typesSportsInfrastructures1, statement);
                    typesSportsInfrastructures1.setTSI_NAME(typesTSINamesAtomicReference.get().getTTN_NAMES());
                    tableView.refresh();
                }
            }
        });
        forward.setOnAction(actionEvent ->
        {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            TypesSportsInfrastructuresDBW.forward(textFieldCount, count, flag, data, rs, tableView);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            TypesSportsInfrastructuresDBW.back(textFieldCount, count, flag, data, rs, tableView);
        });
        remove.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                TypesSportsInfrastructures typesSportsInfrastructures1 = tableView.getItems().get(selectedIndex);
                TypesSportsInfrastructuresDBW.remove(typesSportsInfrastructures1, statement, flag, typesSportsInfrastructures1, tableView, data);
            }

        });
        enter.setOnAction(actionEvent -> {
            String type = GetterTextOrNullTextField.getText(typesEnter);
            String desc = GetterTextOrNullTextField.getText(descriptionEnter);
            TypesSportsInfrastructuresDBW.enter(statement, type, desc, flag,tableView,data);
        });
        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });

    }
}
