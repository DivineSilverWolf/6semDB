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
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesSportsInfrastructures;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.club_inf.ClubDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition.WinnersDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.sports_facility.general_sf.SportsFacilityInformationDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.club_inf.ClubController;
import nsu.fit.ru.database_sports_architecture.controllers.competition.WinnersController;
import nsu.fit.ru.database_sports_architecture.controllers.special_assignments.GetterTextOrNullTextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SportsFacilityInformationController implements Initializable {
    Statement statement;
    ResultSet rs;
    String login;
    public SportsFacilityInformationController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }


    @Getter
    @Setter
    public static class Facility {
        private TypesSportsInfrastructures typesSportsInfrastructures;

        private SportsFacilityInformation sportsFacilityInformation;

        public Facility(TypesSportsInfrastructures typesSportsInfrastructures, SportsFacilityInformation sportsFacilityInformation) {
            this.typesSportsInfrastructures = typesSportsInfrastructures;
            this.sportsFacilityInformation = sportsFacilityInformation;
        }
    }

    @FXML
    TableView<Facility> tableView;
    @FXML
    TableColumn<Facility,String> types;
    @FXML
    TableColumn<Facility, String> name;
    @FXML
    TableColumn<Facility, String> address;

    @FXML
    TableView<TypesSportsInfrastructures> dop_table;
    @FXML
    TableColumn<TypesSportsInfrastructures, String> dop_types;

    @FXML
    Button back;
    @FXML
    Button forward;
    @FXML
    TextField count;

    @FXML
    TextField typesEnter;
    @FXML
    TextField nameEnter;
    @FXML
    TextField addressEnter;

    @FXML
    Button enter;
    @FXML
    Button remove;
    @FXML
    Button menu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtomicInteger count = new AtomicInteger();
        ObservableList<Facility> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);


        types.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypesSportsInfrastructures().getTSI_NAME()));
        types.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsFacilityInformation().getSFI_NAME()));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        address.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsFacilityInformation().getSFI_ADDR()));
        address.setCellFactory(TextFieldTableCell.forTableColumn());

        dop_types.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTSI_NAME()));
        dop_types.setCellFactory(TextFieldTableCell.forTableColumn());
        ObservableList<TypesSportsInfrastructures> typesSportsInfrastructures = FXCollections.observableArrayList();
        SportsFacilityInformationDBW.dop_table(statement,typesSportsInfrastructures,dop_table);
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
                    typesEnter.setText(typesSportsInfrastructures1.getTSI_NAME());
                }
            }
        });
        tableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.V && event.isControlDown()) {
                ObservableList<Facility> selectedItems = tableView.getSelectionModel().getSelectedItems();
                // Далее можем получить информацию о выделенных элементах
                Facility facility = selectedItems.get(0);
                if(typesSportsInfrastructuresAtomicReference.get() != null){
                    facility.setTypesSportsInfrastructures(typesSportsInfrastructuresAtomicReference.get());
                    facility.getSportsFacilityInformation().setTSI_ID(typesSportsInfrastructuresAtomicReference.get().getTSI_ID());
                    SportsFacilityInformationDBW.SFI_NEW_TSI(facility.getSportsFacilityInformation(), facility.getTypesSportsInfrastructures(), statement,
                            facility.getTypesSportsInfrastructures().getTSI_NAME());
                    tableView.refresh();
                }
            }
        });
        name.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            Facility facility = tableView.getItems().get(row);
            String newValue = event.getNewValue();
            SportsFacilityInformationDBW.newNA(facility.getSportsFacilityInformation(), newValue, facility.getSportsFacilityInformation().getSFI_ADDR(), statement, tableView);
        });
        address.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            Facility facility = tableView.getItems().get(row);
            String newValue = event.getNewValue();
            SportsFacilityInformationDBW.newNA(facility.getSportsFacilityInformation(), facility.getSportsFacilityInformation().getSFI_NAME(), newValue, statement, tableView);
        });

        forward.setOnAction(actionEvent ->
        {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            SportsFacilityInformationDBW.forward(textFieldCount, count, flag, data, rs, tableView, Facility::new);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            SportsFacilityInformationDBW.back(textFieldCount, count, flag, data, rs, tableView, Facility::new);
        });
        remove.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                Facility facility = tableView.getItems().get(selectedIndex);
                SportsFacilityInformationDBW.remove(facility.getSportsFacilityInformation(), statement, flag, facility, tableView, data);
            }

        });
        enter.setOnAction(actionEvent -> {
            String name = GetterTextOrNullTextField.getText(nameEnter);
            String addr = GetterTextOrNullTextField.getText(addressEnter);
            String types = GetterTextOrNullTextField.getText(typesEnter);
            SportsFacilityInformationDBW.enter(statement, name, addr, types, flag,tableView,data, Facility::new);
        });
        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });
    }
}
