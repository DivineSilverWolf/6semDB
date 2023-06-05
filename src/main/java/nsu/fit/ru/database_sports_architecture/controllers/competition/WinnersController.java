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
import javafx.scene.input.KeyCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Competition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.MembersCompetition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Winners;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition.MembersCompetitionDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition.WinnersDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.models.competition.WinnersModel;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class WinnersController implements Initializable {

    Statement statement;
    ResultSet rs;
    String login;
    public WinnersController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }


    @Getter
    @Setter
    public static class MembersCompetitionWinners {
        private Sportsman sportsman;
        private Competition competition;
        private Winners winners;

        public MembersCompetitionWinners(Sportsman sportsman, Competition competition, Winners winners) {
            this.sportsman = sportsman;
            this.competition = competition;
            this.winners = winners;
        }
    }
    @Getter
    @Setter
    public static class Members_Competition {
        private Sportsman sportsman;
        private Competition competition;
        private MembersCompetition membersCompetition;

        public Members_Competition(Sportsman sportsman, Competition competition, MembersCompetition membersCompetition) {
            this.sportsman = sportsman;
            this.competition = competition;
            this.membersCompetition = membersCompetition;
        }
    }


    @FXML
    TableView<MembersCompetitionWinners>  tableView;
    @FXML
    TableView<Members_Competition> dop_MC;


    @FXML
    TableColumn<MembersCompetitionWinners, String> W_PLACE;
    @FXML
    TableColumn<MembersCompetitionWinners, String> W_DATE;
    @FXML
    TableColumn<MembersCompetitionWinners, String> COM_NAME;
    @FXML
    TableColumn<MembersCompetitionWinners, String> COM_START_DATE;
    @FXML
    TableColumn<MembersCompetitionWinners, String> COM_END_DATE;
    @FXML
    TableColumn<MembersCompetitionWinners, String> COM_START_REG_DATE;
    @FXML
    TableColumn<MembersCompetitionWinners, String> COM_END_REG_DATE;
    @FXML
    TableColumn<MembersCompetitionWinners, String> S_NAME;
    @FXML
    TableColumn<MembersCompetitionWinners, String> S_SURNAME;
    @FXML
    TableColumn<MembersCompetitionWinners, String> S_PATRONYMIC;
    @FXML
    TableColumn<MembersCompetitionWinners, String> S_MAIL;
    @FXML
    TableColumn<MembersCompetitionWinners, String> S_TEL;


    @FXML
    TableColumn<Members_Competition, String> dop_S_NAME;
    @FXML
    TableColumn<Members_Competition, String> dop_S_SURNAME;
    @FXML
    TableColumn<Members_Competition, String> dop_S_PATRONYMIC;
    @FXML
    TableColumn<Members_Competition, String> dop_S_MAIL;
    @FXML
    TableColumn<Members_Competition, String> dop_S_TEL;
    @FXML
    TableColumn<Members_Competition, String> dop_COM_NAME;
    @FXML
    TableColumn<Members_Competition, String> dop_COM_START_DATE;
    @FXML
    TableColumn<Members_Competition, String> dop_COM_END_DATE;
    @FXML
    TableColumn<Members_Competition, String> dop_COM_START_REG_DATE;
    @FXML
    TableColumn<Members_Competition, String> dop_COM_END_REG_DATE;

    @FXML
    Button back;
    @FXML
    Button forward;
    @FXML
    TextField count;

    @FXML
    TextField enterCOM_NAME;
    @FXML
    TextField enterCOM_START_DATE;
    @FXML
    TextField enterCOM_END_DATE;
    @FXML
    TextField enterCOM_START_REG_DATE;
    @FXML
    TextField enterCOM_END_REG_DATE;
    @FXML
    TextField enterS_TEL;
    @FXML
    TextField enterS_MAIL;
    @FXML
    TextField enterW_Place;
    @FXML
    TextField enterW_Date;
    @FXML
    Button enter;
    @FXML
    Button remove;
    @FXML
    Button menu;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtomicInteger count = new AtomicInteger();
        ObservableList<MembersCompetitionWinners> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);

        dop_COM_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_NAME()));
        dop_COM_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_COM_START_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_START_DATE().toString()));;
        dop_COM_START_DATE.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_COM_END_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_END_DATE() == null ? "N/A" : cellData.getValue().getCompetition().getCOM_END_DATE().toString()));
        dop_COM_END_DATE.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_COM_START_REG_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_START_REG_DATE().toString()));;
        dop_COM_START_REG_DATE.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_COM_END_REG_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_END_REG_DATE().toString()));;
        dop_COM_END_REG_DATE.setCellFactory(TextFieldTableCell.forTableColumn());

        dop_S_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_NAME()));;
        dop_S_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_SURNAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_SURNAME()));;
        dop_S_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_PATRONYMIC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_PATRONYMIC()));;
        dop_S_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_MAIL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_MAIL()));;
        dop_S_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_TEL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_TEL()));;
        dop_S_NAME.setCellFactory(TextFieldTableCell.forTableColumn());

        ObservableList<Members_Competition> sportsmansClubHistories = FXCollections.observableArrayList();

        WinnersDBW.dop_tables(statement, sportsmansClubHistories, dop_MC, Members_Competition::new);



        AtomicReference<Members_Competition> members_competitionAtomicReference = new AtomicReference<>();

        dop_MC.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<Members_Competition> selectedRows = dop_MC.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    members_competitionAtomicReference.set(selectedRows.get(0));
                }
            }
            else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<Members_Competition> selectedRows = dop_MC.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    Members_Competition members_competition = selectedRows.get(0);
                    enterCOM_NAME.setText(members_competition.getCompetition().getCOM_NAME());
                    enterCOM_START_DATE.setText(members_competition.getCompetition().getCOM_START_DATE().toString());
                    enterCOM_END_DATE.setText(members_competition.getCompetition().getCOM_END_DATE() == null ? "N/A" : members_competition.getCompetition().getCOM_END_DATE().toString());
                    enterCOM_START_REG_DATE.setText(members_competition.getCompetition().getCOM_START_REG_DATE().toString());
                    enterCOM_END_REG_DATE.setText(members_competition.getCompetition().getCOM_END_REG_DATE().toString());
                    enterS_MAIL.setText(members_competition.getSportsman().getS_MAIL());
                    enterS_TEL.setText(members_competition.getSportsman().getS_TEL());
                }
            }
        });


        W_PLACE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWinners().getW_PLACE().toString()));;
        W_PLACE.setCellFactory(TextFieldTableCell.forTableColumn());
        W_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWinners().getW_DATE().toString()));;
        W_DATE.setCellFactory(TextFieldTableCell.forTableColumn());
        S_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_NAME()));;
        S_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        S_SURNAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_SURNAME()));;
        S_SURNAME.setCellFactory(TextFieldTableCell.forTableColumn());
        S_PATRONYMIC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_PATRONYMIC()));;
        S_PATRONYMIC.setCellFactory(TextFieldTableCell.forTableColumn());
        S_MAIL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_MAIL()));;
        S_MAIL.setCellFactory(TextFieldTableCell.forTableColumn());
        S_TEL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_TEL()));;
        S_TEL.setCellFactory(TextFieldTableCell.forTableColumn());
        COM_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_NAME()));
        COM_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        COM_START_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_START_DATE().toString()));;
        COM_START_DATE.setCellFactory(TextFieldTableCell.forTableColumn());
        COM_END_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_END_DATE() == null ? "N/A" : cellData.getValue().getCompetition().getCOM_END_DATE().toString()));
        COM_END_DATE.setCellFactory(TextFieldTableCell.forTableColumn());
        COM_START_REG_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_START_REG_DATE().toString()));;
        COM_START_REG_DATE.setCellFactory(TextFieldTableCell.forTableColumn());
        COM_END_REG_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_END_REG_DATE().toString()));;
        COM_END_REG_DATE.setCellFactory(TextFieldTableCell.forTableColumn());

        tableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.V && event.isControlDown()) {
                ObservableList<MembersCompetitionWinners> selectedItems = tableView.getSelectionModel().getSelectedItems();
                // Далее можем получить информацию о выделенных элементах
                MembersCompetitionWinners membersCompetitionWinners = selectedItems.get(0);
                if(members_competitionAtomicReference.get() != null){
                    WinnersDBW.change_entry(membersCompetitionWinners.getWinners(),membersCompetitionWinners.getCompetition(), members_competitionAtomicReference.get().getCompetition(), membersCompetitionWinners.getSportsman(), members_competitionAtomicReference.get().getSportsman() ,statement, tableView);
                }
            }
        });
        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });
        forward.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            WinnersDBW.forward(textFieldCount, count, flag, data, rs, tableView, MembersCompetitionWinners::new);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            WinnersDBW.back(textFieldCount, count, flag, data, rs, tableView, MembersCompetitionWinners::new);
        });
        W_PLACE.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            MembersCompetitionWinners membersCompetitionWinners = tableView.getItems().get(row);
            if(StarterApp.isDigit(event.getNewValue())) {
                int place = Integer.parseInt(event.getNewValue());
                WinnersDBW.new_W_PLACE(membersCompetitionWinners.getWinners(), place, statement, tableView);
            }
        });
        W_DATE.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            MembersCompetitionWinners membersCompetitionWinners = tableView.getItems().get(row);
            String date = event.getNewValue();
            WinnersDBW.new_W_DATE(membersCompetitionWinners.getWinners(), date, statement, tableView);
        });
        enter.setOnAction(actionEvent -> {
            String W_PLACE_STR = enterW_Place.getText();
            if(!StarterApp.isDigit(W_PLACE_STR)) {
                System.out.println("ds");
                return;
            }
            int W_PLACE = Integer.parseInt(W_PLACE_STR);
            String COM_NAME = enterCOM_NAME.getText();
            String COM_START_DATE = enterCOM_START_DATE.getText();
            String COM_END_DATE = enterCOM_END_DATE.getText();
            String COM_START_REG_DATE = enterCOM_START_REG_DATE.getText();
            String COM_END_REG_DATE = enterCOM_END_REG_DATE.getText();
            String S_TEL = enterS_TEL.getText();
            String S_MAIL = enterS_MAIL.getText();
            String W_DATE = enterW_Date.getText();
            WinnersDBW.enter(statement,COM_NAME,COM_START_DATE,COM_END_DATE,COM_START_REG_DATE,COM_END_REG_DATE,S_TEL,S_MAIL,W_DATE,W_PLACE,flag,tableView,data,MembersCompetitionWinners::new);
        });

        remove.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                MembersCompetitionWinners membersCompetitionWinners = tableView.getItems().get(selectedIndex);
                WinnersDBW.remove(membersCompetitionWinners.getWinners(), statement, flag, membersCompetitionWinners, tableView, data);
            }
        });

    }
}
