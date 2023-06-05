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
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesSportsInfrastructures;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesTSINames;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.sports_facility.general_sf.TypesSportsInfrastructuresDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.sports_facility.general_sf.TypesTSINamesDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.special_assignments.GetterTextOrNullTextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class TypesTSINamesController implements Initializable {
    Statement statement;
    ResultSet rs;
    String login;

    public TypesTSINamesController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }

    @FXML
    TableView<TypesTSINames> tableView;
    @FXML
    TableColumn<TypesTSINames, String> types;

    @FXML
    TextField typesEnter;


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
        ObservableList<TypesTSINames> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);

        types.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTTN_NAMES()));
        types.setCellFactory(TextFieldTableCell.forTableColumn());

        forward.setOnAction(actionEvent ->
        {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            TypesTSINamesDBW.forward(textFieldCount, count, flag, data, rs, tableView);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            TypesTSINamesDBW.back(textFieldCount, count, flag, data, rs, tableView);
        });
        remove.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                TypesTSINames typesTSINames = tableView.getItems().get(selectedIndex);
                TypesTSINamesDBW.remove(typesTSINames, statement, flag, typesTSINames, tableView, data);
            }

        });
        enter.setOnAction(actionEvent -> {
            String type = GetterTextOrNullTextField.getText(typesEnter);
            TypesTSINamesDBW.enter(statement, type, flag,tableView,data);
        });
        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });
    }
}
