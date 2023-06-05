package nsu.fit.ru.database_sports_architecture.controllers.competition;

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
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.club_inf.ClubDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition.CompetitionDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition.MembersCompetitionDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition.OrganizerDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.club_inf.ClubController;
import nsu.fit.ru.database_sports_architecture.controllers.special_assignments.GetterTextOrNullTextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrganizerController implements Initializable {
    Statement statement;
    ResultSet rs;
    String login;
    public OrganizerController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }
    @FXML
    TableView<Organizer> tableView;
    @FXML
    TableColumn<Organizer, String> name;
    @FXML
    TableColumn<Organizer, String> tel;
    @FXML
    TableColumn<Organizer, String> mail;

    @FXML
    TextField nameEnter;
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
        ObservableList<Organizer> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);

        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getORG_NAME()));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        mail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getORG_S_MAIL()));
        mail.setCellFactory(TextFieldTableCell.forTableColumn());
        tel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getORG_TEL()));
        tel.setCellFactory(TextFieldTableCell.forTableColumn());

        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });

        forward.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            OrganizerDBW.forward(textFieldCount, count, flag, data, rs, tableView);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            OrganizerDBW.back(textFieldCount, count, flag, data, rs, tableView);
        });

        name.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            Organizer organizer = tableView.getItems().get(row);
            OrganizerDBW.org_NTM(organizer, statement, newValue, organizer.getORG_TEL(), organizer.getORG_S_MAIL(), tableView);
        });
        tel.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            Organizer organizer = tableView.getItems().get(row);
            OrganizerDBW.org_NTM(organizer, statement, organizer.getORG_NAME(), newValue, organizer.getORG_S_MAIL(), tableView);
        });
        mail.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            Organizer organizer = tableView.getItems().get(row);
            OrganizerDBW.org_NTM(organizer, statement, organizer.getORG_NAME(), organizer.getORG_TEL() , newValue, tableView);
        });

        remove.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                Organizer organizer = tableView.getItems().get(selectedIndex);
                OrganizerDBW.remove(statement, flag, organizer, tableView, data);
            }
        });
        enter.setOnAction(actionEvent -> {
            String name = GetterTextOrNullTextField.getText(nameEnter);
            String tel = GetterTextOrNullTextField.getText(telEnter);
            String mail = GetterTextOrNullTextField.getText(mailEnter);
            OrganizerDBW.enter(statement, name, tel, mail, flag, tableView, data);
        });
    }
}
