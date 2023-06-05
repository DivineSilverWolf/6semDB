package nsu.fit.ru.database_sports_architecture.controllers.types_sports;

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
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesSportsInfrastructures;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.sports_facility.general_sf.SportsFacilityInformationDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.DBworckers.types_sports.TypesSportsBDW;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.special_assignments.GetterTextOrNullTextField;
import nsu.fit.ru.database_sports_architecture.controllers.sports_facility.general_sf.SportsFacilityInformationController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@FieldDefaults(level = AccessLevel.PRIVATE)

public class TypesSportsController implements Initializable {

    Statement statement;
    ResultSet rs;
    String login;
    public TypesSportsController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }
    @Getter
    @Setter
    public static class TS_TSI {
        private TypesSportsInfrastructures typesSportsInfrastructures;
        private TypesSports typesSports;

        public TS_TSI(TypesSportsInfrastructures typesSportsInfrastructures, TypesSports typesSports) {
            this.typesSportsInfrastructures = typesSportsInfrastructures;
            this.typesSports = typesSports;
        }
    }
    @FXML
    TableView<TypesSportsInfrastructures> dop_table;
    @FXML
    TableColumn<TypesSportsInfrastructures, String> dop_types;
    @FXML
    TableView<TS_TSI> tableView;
    @FXML
    TableColumn<TS_TSI,String> typesSports;
    @FXML
    TableColumn<TS_TSI, String> typeInf;

    @FXML
    Button back;
    @FXML
    Button forward;
    @FXML
    TextField count;

    @FXML
    TextField typesSportsEnter;
    @FXML
    TextField typeInfEnter;
    @FXML
    Button enter;
    @FXML
    Button remove;
    @FXML
    Button menu;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtomicInteger count = new AtomicInteger();
        ObservableList<TS_TSI> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);

        typesSports.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypesSports().getTS_NAME()));
        typesSports.setCellFactory(TextFieldTableCell.forTableColumn());
        typeInf.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypesSportsInfrastructures().getTSI_NAME()));
        typeInf.setCellFactory(TextFieldTableCell.forTableColumn());

        dop_types.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTSI_NAME()));
        dop_types.setCellFactory(TextFieldTableCell.forTableColumn());


        ObservableList<TypesSportsInfrastructures> typesSportsInfrastructures = FXCollections.observableArrayList();
        TypesSportsBDW.dop_table(statement,typesSportsInfrastructures,dop_table);
        AtomicReference<TypesSportsInfrastructures> typesSportsInfrastructuresAtomicReference = new AtomicReference<>();

        dop_table.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<TypesSportsInfrastructures> selectedRows = dop_table.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    typesSportsInfrastructuresAtomicReference.set(selectedRows.get(0));
                }
            } else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<TypesSportsInfrastructures> selectedRows = dop_table.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    TypesSportsInfrastructures typesSportsInfrastructures1 = selectedRows.get(0);
                    typeInfEnter.setText(typesSportsInfrastructures1.getTSI_NAME());
                }
            }
        });
        tableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.V && event.isControlDown()) {
                ObservableList<TS_TSI> selectedItems = tableView.getSelectionModel().getSelectedItems();
                // Далее можем получить информацию о выделенных элементах
                TS_TSI tsTsi = selectedItems.get(0);
                if(typesSportsInfrastructuresAtomicReference.get() != null){
                    tsTsi.setTypesSportsInfrastructures(typesSportsInfrastructuresAtomicReference.get());
                    tsTsi.getTypesSports().setTSI_ID(typesSportsInfrastructuresAtomicReference.get().getTSI_ID());
                    TypesSportsBDW.TS_NEW_TSI(tsTsi.getTypesSports(), tsTsi.getTypesSportsInfrastructures(), statement,
                            tsTsi.getTypesSportsInfrastructures().getTSI_NAME());
                    tableView.refresh();
                }
            }
        });
        typesSports.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            TS_TSI tsTsi = tableView.getItems().get(row);
            String newValue = event.getNewValue();
            TypesSportsBDW.newTS(tsTsi.getTypesSports(), newValue, statement, tableView);
        });
        forward.setOnAction(actionEvent ->
        {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            TypesSportsBDW.forward(textFieldCount, count, flag, data, rs, tableView, TS_TSI::new);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            TypesSportsBDW.back(textFieldCount, count, flag, data, rs, tableView, TS_TSI::new);
        });
        remove.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                TS_TSI tsTsi = tableView.getItems().get(selectedIndex);
                TypesSportsBDW.remove(tsTsi.getTypesSports(), statement, flag, tsTsi, tableView, data);
            }

        });
        enter.setOnAction(actionEvent -> {
            String typesSports = GetterTextOrNullTextField.getText(typesSportsEnter);
            String typeInf = GetterTextOrNullTextField.getText(typeInfEnter);
            TypesSportsBDW.enter(statement, typesSports, typeInf, flag,tableView,data, TS_TSI::new);
        });
        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });
    }
}
