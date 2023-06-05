package nsu.fit.ru.database_sports_architecture.controllers.sportsman;

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
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition.OrganizerDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.DBworckers.sportsman.SportsmanDBW;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.special_assignments.GetterTextOrNullTextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class SportsmanController implements Initializable {
    Statement statement;
    ResultSet rs;
    String login;
    public SportsmanController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }

    @FXML
    TableView<Sportsman> tableView;
    @FXML
    TableColumn<Sportsman, String> name;
    @FXML
    TableColumn<Sportsman, String> surname;
    @FXML
    TableColumn<Sportsman, String> patronymic;
    @FXML
    TableColumn<Sportsman, String> tel;
    @FXML
    TableColumn<Sportsman, String> mail;

    @FXML
    TextField nameEnter;
    @FXML
    TextField surnameEnter;
    @FXML
    TextField patronymicEnter;
    @FXML
    TextField telEnter;
    @FXML
    TextField mailEnter;

    @FXML
    Button enter;
    @FXML
    Button remove;
    @FXML
    Button menu;

    @FXML
    Button back;
    @FXML
    TextField count;
    @FXML
    Button forward;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtomicInteger count = new AtomicInteger();
        ObservableList<Sportsman> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);

        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_NAME()));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        surname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_SURNAME()));
        surname.setCellFactory(TextFieldTableCell.forTableColumn());
        patronymic.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getS_PATRONYMIC() != null ? cellData.getValue().getS_PATRONYMIC(): "N/A"));
        patronymic.setCellFactory(TextFieldTableCell.forTableColumn());
        mail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_MAIL()));
        mail.setCellFactory(TextFieldTableCell.forTableColumn());
        tel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_TEL()));
        tel.setCellFactory(TextFieldTableCell.forTableColumn());


        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });

        forward.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            SportsmanDBW.forward(textFieldCount, count, flag, data, rs, tableView);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            SportsmanDBW.back(textFieldCount, count, flag, data, rs, tableView);
        });

        name.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            Sportsman sportsman = tableView.getItems().get(row);
            SportsmanDBW.s_NSPTM(sportsman, statement, newValue, sportsman.getS_SURNAME(), sportsman.getS_PATRONYMIC(), sportsman.getS_TEL(), sportsman.getS_MAIL(), tableView);
        });
        surname.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            Sportsman sportsman = tableView.getItems().get(row);
            SportsmanDBW.s_NSPTM(sportsman, statement, sportsman.getS_NAME(), newValue, sportsman.getS_PATRONYMIC(), sportsman.getS_TEL(), sportsman.getS_MAIL(), tableView);
        });
        patronymic.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            Sportsman sportsman = tableView.getItems().get(row);
            SportsmanDBW.s_NSPTM(sportsman, statement, sportsman.getS_NAME(), sportsman.getS_SURNAME(), newValue, sportsman.getS_TEL(), sportsman.getS_MAIL(), tableView);
        });
        tel.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            Sportsman sportsman = tableView.getItems().get(row);
            SportsmanDBW.s_NSPTM(sportsman, statement, sportsman.getS_NAME(), sportsman.getS_SURNAME(), sportsman.getS_PATRONYMIC(), newValue, sportsman.getS_MAIL(), tableView);
        });
        mail.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            Sportsman sportsman = tableView.getItems().get(row);
            SportsmanDBW.s_NSPTM(sportsman, statement, sportsman.getS_NAME(), sportsman.getS_SURNAME(), sportsman.getS_PATRONYMIC(), sportsman.getS_TEL(), newValue, tableView);
        });

        remove.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                Sportsman sportsman = tableView.getItems().get(selectedIndex);
                SportsmanDBW.remove(statement, flag, sportsman, tableView, data);
            }
        });
        enter.setOnAction(actionEvent -> {
            String name = GetterTextOrNullTextField.getText(nameEnter);
            String surname = GetterTextOrNullTextField.getText(surnameEnter);
            String patronymic = GetterTextOrNullTextField.getText(patronymicEnter);
            String tel = GetterTextOrNullTextField.getText(telEnter);
            String mail = GetterTextOrNullTextField.getText(mailEnter);
            SportsmanDBW.enter(statement, name, surname, patronymic, tel, mail, flag, tableView, data);
        });
    }
}
