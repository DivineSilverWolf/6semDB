package nsu.fit.ru.database_sports_architecture.controllers.club_inf;

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
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.MembersClubHistory;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.club_inf.MembersClubHistoryDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.special_assignments.GetterTextOrNullTextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MembersClubHistoryController implements Initializable {

    @Getter
    @Setter
    public static class SportsmansClubHistory {
        private Club club;
        private Sportsman sportsman;
        private MembersClubHistory membersClubHistory;

        public SportsmansClubHistory(Club club, Sportsman sportsman, MembersClubHistory membersClubHistory) {
            this.club = club;
            this.sportsman = sportsman;
            this.membersClubHistory = membersClubHistory;
        }
    }
    @FXML
    TableView<Sportsman> dop_sportsman;
    @FXML
    TableColumn<Sportsman, String> dop_name;
    @FXML
    TableColumn<Sportsman, String> dop_surname;
    @FXML
    TableColumn<Sportsman, String> dop_patronymic;
    @FXML
    TableColumn<Sportsman, String> dop_sTel;
    @FXML
    TableColumn<Sportsman, String> dop_sMail;
    @FXML
    TableView<Club> dop_club;
    @FXML
    TableColumn<Club, String> dop_cTel;
    @FXML
    TableColumn<Club, String> dop_clubName;



    @FXML
    TableView<SportsmansClubHistory> tableView;
    @FXML
    TableColumn<SportsmansClubHistory, String> name;
    @FXML
    TableColumn<SportsmansClubHistory, String> surname;
    @FXML
    TableColumn<SportsmansClubHistory, String> patronymic;
    @FXML
    TableColumn<SportsmansClubHistory, String> clubName;
    @FXML
    TableColumn<SportsmansClubHistory, String> start_date;
    @FXML
    TableColumn<SportsmansClubHistory, String> end_date;
    @FXML
    TableColumn<SportsmansClubHistory, String> sMail;
    @FXML
    TableColumn<SportsmansClubHistory, String> sTel;
    @FXML
    TableColumn<SportsmansClubHistory, String> cTel;
    @FXML
    Button back;
    @FXML
    Button forward;
    @FXML
    TextField count;
    @FXML
    Button droppedOut;

    @FXML
    TextField enterSTel;
    @FXML
    TextField enterSMail;
    @FXML
    TextField enterClub;
    @FXML
    TextField enterCTel;
    @FXML
    TextField start_date_enter;
    @FXML
    TextField end_date_enter;

    @FXML
    Button enter;
    @FXML
    Button remove;
    @FXML
    Button menu;

    Statement statement;
    ResultSet rs;
    String login;
    public MembersClubHistoryController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtomicInteger count = new AtomicInteger();
        ObservableList<SportsmansClubHistory> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);

        dop_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_NAME()));
        dop_name.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_surname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_SURNAME()));
        dop_surname.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_patronymic.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_PATRONYMIC()));
        dop_patronymic.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_sMail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_MAIL()));
        dop_sMail.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_sTel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_TEL()));
        dop_sTel.setCellFactory(TextFieldTableCell.forTableColumn());

        dop_clubName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCL_NAME()));
        dop_clubName.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_cTel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCL_TEL()));
        dop_cTel.setCellFactory(TextFieldTableCell.forTableColumn());

        ObservableList<Club> dop_club_data = FXCollections.observableArrayList();
        ObservableList<Sportsman> dop_sportsman_data = FXCollections.observableArrayList();
        MembersClubHistoryDBW.dop_tables(statement,dop_club_data,dop_sportsman_data,dop_sportsman,dop_club);
        AtomicReference<Club> club = new AtomicReference<>();
        AtomicReference<Sportsman> sportsman = new AtomicReference<>();
        club.set(null);
        sportsman.set(null);
        dop_club.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<Club> selectedRows = dop_club.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                     club.set(selectedRows.get(0));
                     sportsman.set(null);
                }
            } else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<Club> selectedRows = dop_club.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    Club club1 = selectedRows.get(0);
                    enterClub.setText(club1.getCL_NAME());
                    enterCTel.setText(club1.getCL_TEL());
                }
            }
        });
        dop_sportsman.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<Sportsman> selectedRows = dop_sportsman.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    sportsman.set( selectedRows.get(0));
                    club.set(null);
                }
            }
            else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<Sportsman> selectedRows = dop_sportsman.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    Sportsman sportsman1 = selectedRows.get(0);
                    enterSMail.setText(sportsman1.getS_MAIL());
                    enterSTel.setText(sportsman1.getS_TEL());
                }
            }
        });



        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_NAME()));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        surname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_SURNAME()));
        surname.setCellFactory(TextFieldTableCell.forTableColumn());
        patronymic.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().
                getS_PATRONYMIC() != null ? cellData.getValue().getSportsman().getS_PATRONYMIC() : "N/A"));
        patronymic.setCellFactory(TextFieldTableCell.forTableColumn());
        clubName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClub().getCL_NAME()));
        clubName.setCellFactory(TextFieldTableCell.forTableColumn());

        start_date.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().membersClubHistory.getMCH_START_DATE().toString()));
        start_date.setCellFactory(TextFieldTableCell.forTableColumn());
        end_date.setCellValueFactory(cellData ->  new SimpleStringProperty(
                        cellData.getValue().getMembersClubHistory().getMCH_END_DATE() != null ?
                                cellData.getValue().getMembersClubHistory().getMCH_END_DATE().toString() :
                                "N/A"));
        end_date.setCellFactory(TextFieldTableCell.forTableColumn());

        sMail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().sportsman.getS_MAIL()));
        sMail.setCellFactory(TextFieldTableCell.forTableColumn());
        sTel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().sportsman.getS_TEL()));
        sTel.setCellFactory(TextFieldTableCell.forTableColumn());
        cTel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().club.getCL_TEL()));
        cTel.setCellFactory(TextFieldTableCell.forTableColumn());

        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });

        forward.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            MembersClubHistoryDBW.forward(textFieldCount, count, flag, data, rs, tableView, SportsmansClubHistory::new);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            MembersClubHistoryDBW.back(textFieldCount, count, flag, data, rs, tableView, SportsmansClubHistory::new);
        });
        clubName.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            SportsmansClubHistory sportsmansClubHistory = tableView.getItems().get(row);
            MembersClubHistoryDBW.clubName(sportsmansClubHistory.getMembersClubHistory(), sportsmansClubHistory.getClub(), statement, newValue, tableView);
        });
        cTel.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            SportsmansClubHistory sportsmansClubHistory = tableView.getItems().get(row);
            MembersClubHistoryDBW.clubTel(sportsmansClubHistory.getMembersClubHistory(), sportsmansClubHistory.getClub(), statement, newValue, tableView);
        });
        start_date.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            SportsmansClubHistory clubTypesSports = tableView.getItems().get(row);
            MembersClubHistoryDBW.start_date(clubTypesSports.getMembersClubHistory(), statement, newValue);
        });
        end_date.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            SportsmansClubHistory clubTypesSports = tableView.getItems().get(row);
            MembersClubHistoryDBW.end_date(clubTypesSports.getMembersClubHistory(), statement, newValue, tableView);
        });

        sMail.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            SportsmansClubHistory clubTypesSports = tableView.getItems().get(row);
            MembersClubHistoryDBW.anotherSportsman(clubTypesSports.getMembersClubHistory(),clubTypesSports.getSportsman(),statement,newValue,null, tableView);
        });
        sTel.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            SportsmansClubHistory clubTypesSports = tableView.getItems().get(row);
            MembersClubHistoryDBW.anotherSportsman(clubTypesSports.getMembersClubHistory(),clubTypesSports.getSportsman(),statement,null, newValue, tableView);
        });
        droppedOut.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                SportsmansClubHistory sportsmansClubHistory = tableView.getItems().get(selectedIndex);
                MembersClubHistoryDBW.droppedOut(sportsmansClubHistory.getMembersClubHistory(), statement, tableView);
            }
        });
        remove.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                SportsmansClubHistory sportsmansClubHistory = tableView.getItems().get(selectedIndex);
                MembersClubHistoryDBW.remove(sportsmansClubHistory.getMembersClubHistory(), statement, flag, sportsmansClubHistory, tableView, data);
            }
        });
        enter.setOnAction(actionEvent -> {
            String sTel = GetterTextOrNullTextField.getText(enterSTel);
            String sMail = GetterTextOrNullTextField.getText(enterSMail);
            String cTel = GetterTextOrNullTextField.getText(enterCTel);
            String clName = GetterTextOrNullTextField.getText(enterClub);
            String start_date = GetterTextOrNullTextField.getText(start_date_enter);
            String end_date = GetterTextOrNullTextField.getText(end_date_enter);
            MembersClubHistoryDBW.enter(statement, sTel, sMail, cTel, clName, start_date,end_date, flag, tableView, data,SportsmansClubHistory::new);
        });

        tableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.V && event.isControlDown()) {
                ObservableList<SportsmansClubHistory> selectedItems = tableView.getSelectionModel().getSelectedItems();
                // Далее можем получить информацию о выделенных элементах
                SportsmansClubHistory sportsmansClubHistory = selectedItems.get(0);
                if(club.get() != null){
                    sportsmansClubHistory.setClub(club.get());
                    sportsmansClubHistory.getMembersClubHistory().setCL_ID(club.get().getCL_ID());
                    MembersClubHistoryDBW.clubTel(sportsmansClubHistory.getMembersClubHistory(), sportsmansClubHistory.getClub(), statement, sportsmansClubHistory.getClub().getCL_TEL(), tableView);
                }
                if(sportsman.get() != null){
                    sportsmansClubHistory.setSportsman(sportsman.get());
                    sportsmansClubHistory.getMembersClubHistory().setS_ID(sportsman.get().getS_ID());
                    MembersClubHistoryDBW.anotherSportsman(sportsmansClubHistory.getMembersClubHistory(),sportsmansClubHistory.getSportsman(),statement,null, sportsmansClubHistory.getSportsman().getS_TEL(), tableView);
                }
            }
        });
    }

}
