package nsu.fit.ru.database_sports_architecture.controllers.trainer;

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
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.Trainer;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.DBworckers.sportsman.SportsmanDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.trainer.TrainerDBW;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.special_assignments.GetterTextOrNullTextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainerController implements Initializable {
    Statement statement;
    ResultSet rs;
    String login;
    public TrainerController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }

    @FXML
    TableView<Trainer> tableView;
    @FXML
    TableColumn<Trainer, String> name;
    @FXML
    TableColumn<Trainer, String> surname;
    @FXML
    TableColumn<Trainer, String> patronymic;
    @FXML
    TableColumn<Trainer, String> tel;
    @FXML
    TableColumn<Trainer, String> mail;

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
        ObservableList<Trainer> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getT_NAME()));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        surname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getT_SURNAME()));
        surname.setCellFactory(TextFieldTableCell.forTableColumn());
        patronymic.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getT_PATRONYMIC() != null ? cellData.getValue().getT_PATRONYMIC(): "N/A"));
        patronymic.setCellFactory(TextFieldTableCell.forTableColumn());
        mail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getT_MAIL()));
        mail.setCellFactory(TextFieldTableCell.forTableColumn());
        tel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getT_TEL()));
        tel.setCellFactory(TextFieldTableCell.forTableColumn());

        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });

        forward.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
           TrainerDBW.forward(textFieldCount, count, flag, data, rs, tableView);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
           TrainerDBW.back(textFieldCount, count, flag, data, rs, tableView);
        });

        name.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
           Trainer trainer = tableView.getItems().get(row);
           TrainerDBW.t_NSPTM(trainer, statement, newValue, trainer.getT_SURNAME(), trainer.getT_PATRONYMIC(), trainer.getT_TEL(), trainer.getT_MAIL(), tableView);
        });
        surname.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
           Trainer trainer = tableView.getItems().get(row);
           TrainerDBW.t_NSPTM(trainer, statement, trainer.getT_NAME(), newValue, trainer.getT_PATRONYMIC(), trainer.getT_TEL(), trainer.getT_MAIL(), tableView);
        });
        patronymic.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
           Trainer trainer = tableView.getItems().get(row);
           TrainerDBW.t_NSPTM(trainer, statement, trainer.getT_NAME(), trainer.getT_SURNAME(), newValue, trainer.getT_TEL(), trainer.getT_MAIL(), tableView);
        });
        tel.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
           Trainer trainer = tableView.getItems().get(row);
           TrainerDBW.t_NSPTM(trainer, statement, trainer.getT_NAME(), trainer.getT_SURNAME(), trainer.getT_PATRONYMIC(), newValue, trainer.getT_MAIL(), tableView);
        });
        mail.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
           Trainer trainer = tableView.getItems().get(row);
           TrainerDBW.t_NSPTM(trainer, statement, trainer.getT_NAME(), trainer.getT_SURNAME(), trainer.getT_PATRONYMIC(), trainer.getT_TEL(), newValue, tableView);
        });

        remove.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
               Trainer trainer = tableView.getItems().get(selectedIndex);
               TrainerDBW.remove(statement, flag, trainer, tableView, data);
            }
        });
        enter.setOnAction(actionEvent -> {
            String name = GetterTextOrNullTextField.getText(nameEnter);
            String surname = GetterTextOrNullTextField.getText(surnameEnter);
            String patronymic = GetterTextOrNullTextField.getText(patronymicEnter);
            String tel = GetterTextOrNullTextField.getText(telEnter);
            String mail = GetterTextOrNullTextField.getText(mailEnter);
           TrainerDBW.enter(statement, name, surname, patronymic, tel, mail, flag, tableView, data);
        });
    }
}
