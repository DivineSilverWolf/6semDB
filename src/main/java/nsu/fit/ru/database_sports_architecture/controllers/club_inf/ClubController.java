package nsu.fit.ru.database_sports_architecture.controllers.club_inf;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.Setter;
import lombok.Getter;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.club_inf.ClubDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.club_inf.MembersClubHistoryDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.StarterApp;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClubController implements Initializable {

    @Getter
    @Setter
    public static class ClubTypesSports {
        private Club club;

        private TypesSports typesSports;

        public ClubTypesSports(Club club, TypesSports typesSports) {
            this.club = club;
            this.typesSports = typesSports;
        }
    }
    Statement statement;
    ResultSet rs;
    String login;

    @FXML
    TableView<TypesSports> dop_table;
    @FXML
    TableColumn<TypesSports, String> dop_ts;
    @FXML
    TableView<ClubTypesSports> table;
    @FXML
    TableColumn<ClubTypesSports, String> clubName;
    @FXML
    TableColumn<ClubTypesSports, String> founder;
    @FXML
    TableColumn<ClubTypesSports, String> tel;
    @FXML
    TableColumn<ClubTypesSports, String> date;
    @FXML
    TableColumn<ClubTypesSports, String> typesSport;

    @FXML
    Button back;
    @FXML
    Button forward;
    @FXML
    TextField count;

    @FXML
    TextField clubEnter;
    @FXML
    TextField founderEnter;
    @FXML
    TextField telEnter;
    @FXML
    TextField dateEnter;
    @FXML
    TextField typesSportEnter;

    @FXML
    Button enter;
    @FXML
    Button remove;

    @FXML
    Button menu;

    public ClubController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtomicInteger count = new AtomicInteger();
        ObservableList<ClubTypesSports> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);

        clubName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClub().getCL_NAME()));
        clubName.setCellFactory(TextFieldTableCell.forTableColumn());
        founder.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClub().getCL_FOUNDER()));
        founder.setCellFactory(TextFieldTableCell.forTableColumn());
        tel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClub().getCL_TEL()));
        tel.setCellFactory(TextFieldTableCell.forTableColumn());
        date.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClub().getCL_HAIR_DATE().toString()));
        date.setCellFactory(TextFieldTableCell.forTableColumn());
        typesSport.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypesSports().getTS_NAME()));
        typesSport.setCellFactory(TextFieldTableCell.forTableColumn());

        dop_ts.setCellValueFactory(cellDate -> new SimpleStringProperty(cellDate.getValue().getTS_NAME()));
        dop_ts.setCellFactory(TextFieldTableCell.forTableColumn());
        ObservableList<TypesSports> typesSportsList = FXCollections.observableArrayList();
        ClubDBW.dop_table(statement,typesSportsList,dop_table);
        AtomicReference<TypesSports> typesSports = new AtomicReference<>();
        dop_table.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<TypesSports> selectedRows = dop_table.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    typesSports.set(selectedRows.get(0));
                }
            } else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<TypesSports> selectedRows = dop_table.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    TypesSports typesSports1 = selectedRows.get(0);
                    typesSportEnter.setText(typesSports1.getTS_NAME());
                }
            }
        });
        table.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.V && event.isControlDown()) {
                ObservableList<ClubTypesSports> selectedItems = table.getSelectionModel().getSelectedItems();
                // Далее можем получить информацию о выделенных элементах
                ClubTypesSports clubTypesSports = selectedItems.get(0);
                if(typesSports.get() != null){
                    clubTypesSports.setTypesSports(typesSports.get());
                    clubTypesSports.getTypesSports().setTS_ID(typesSports.get().getTS_ID());
                    ClubDBW.typesSport(clubTypesSports.getClub(), clubTypesSports.getTypesSports(), statement,
                            clubTypesSports.getTypesSports().getTS_NAME());
                    table.refresh();
                }
            }
        });

        forward.setOnAction(actionEvent ->
        {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            ClubDBW.forward(textFieldCount, count, flag, data, rs, table, ClubTypesSports::new);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            ClubDBW.back(textFieldCount, count, flag, data, rs, table, ClubTypesSports::new);
        });

        founder.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            ClubTypesSports clubTypesSports = table.getItems().get(row);
            ClubDBW.founder(clubTypesSports.getClub(), statement, newValue);
        });
        tel.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            ClubTypesSports clubTypesSports = table.getItems().get(row);
            ClubDBW.tel(clubTypesSports.getClub(), statement, newValue);
        });
        date.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            ClubTypesSports clubTypesSports = table.getItems().get(row);
            ClubDBW.date(clubTypesSports.getClub(), statement, newValue);


        });
        clubName.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            ClubTypesSports clubTypesSports = table.getItems().get(row);
            ClubDBW.clubName(clubTypesSports.getClub(), statement, newValue);
        });
        typesSport.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            ClubTypesSports clubTypesSports = table.getItems().get(row);
            ClubDBW.typesSport(clubTypesSports.getClub(), clubTypesSports.getTypesSports(), statement, newValue);
        });

        remove.setOnAction(actionEvent -> {
            int selectedIndex = table.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                ClubTypesSports clubTypesSports = table.getItems().get(selectedIndex);
                ClubDBW.remove(clubTypesSports.getClub(), statement, flag, clubTypesSports, table, data);
            }

        });
        enter.setOnAction(actionEvent -> {
            String typesSportsName = typesSportEnter.getText();
            String clubName = clubEnter.getText();
            String founderName = founderEnter.getText();
            String tel = telEnter.getText();
            String date = dateEnter.getText();
            ClubDBW.enter(statement, clubName, founderName, tel, date, typesSportsName, flag,table,data,ClubTypesSports::new);
        });
        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });

    }
}
