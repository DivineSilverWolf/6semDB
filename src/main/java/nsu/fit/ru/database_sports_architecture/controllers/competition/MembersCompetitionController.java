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
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.MembersClubHistory;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Competition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.MembersCompetition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition.CompetitionDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition.MembersCompetitionDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.StarterApp;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class MembersCompetitionController implements Initializable {
    Statement statement;
    ResultSet rs;
    String login;
    public MembersCompetitionController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }

    @Getter
    @Setter
    private class MC_COM_CL_SP{
        MembersCompetition membersCompetition;
        Competition competition;
        Club club;
        Sportsman sportsman;

        public MC_COM_CL_SP(MembersCompetition membersCompetition, Competition competition, Club club, Sportsman sportsman) {
            this.membersCompetition = membersCompetition;
            this.competition = competition;
            this.club = club;
            this.sportsman = sportsman;
        }

    }
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
    TableView<MC_COM_CL_SP> tableView;
    @FXML
    TableView<SportsmansClubHistory> dop_MCH;
    @FXML
    TableView<Competition> dop_COM;

    @FXML
    TableColumn<MC_COM_CL_SP, String> COM_NAME;
    @FXML
    TableColumn<MC_COM_CL_SP, String> COM_START_DATE;
    @FXML
    TableColumn<MC_COM_CL_SP, String> COM_END_DATE;
    @FXML
    TableColumn<MC_COM_CL_SP, String> COM_START_REG_DATE;
    @FXML
    TableColumn<MC_COM_CL_SP, String> COM_END_REG_DATE;
    @FXML
    TableColumn<MC_COM_CL_SP, String> CL_NAME;
    @FXML
    TableColumn<MC_COM_CL_SP, String> CL_TEL;
    @FXML
    TableColumn<MC_COM_CL_SP, String> S_NAME;
    @FXML
    TableColumn<MC_COM_CL_SP, String> S_SURNAME;
    @FXML
    TableColumn<MC_COM_CL_SP, String> S_PATRONYMIC;
    @FXML
    TableColumn<MC_COM_CL_SP, String> S_MAIL;
    @FXML
    TableColumn<MC_COM_CL_SP, String> S_TEL;
    @FXML
    TableColumn<MC_COM_CL_SP, String>MC_REG_DATE;

    @FXML
    TableColumn<Competition, String> dop_COM_NAME;
    @FXML
    TableColumn<Competition, String> dop_COM_START_DATE;
    @FXML
    TableColumn<Competition, String> dop_COM_END_DATE;
    @FXML
    TableColumn<Competition, String> dop_COM_START_REG_DATE;
    @FXML
    TableColumn<Competition, String> dop_COM_END_REG_DATE;

    @FXML
    TableColumn<SportsmansClubHistory, String> dop_CL_NAME;
    @FXML
    TableColumn<SportsmansClubHistory, String> dop_CL_TEL;
    @FXML
    TableColumn<SportsmansClubHistory, String> dop_S_NAME;
    @FXML
    TableColumn<SportsmansClubHistory, String> dop_S_SURNAME;
    @FXML
    TableColumn<SportsmansClubHistory, String> dop_S_PATRONYMIC;
    @FXML
    TableColumn<SportsmansClubHistory, String> dop_S_MAIL;
    @FXML
    TableColumn<SportsmansClubHistory, String> dop_S_TEL;
    @FXML
    TableColumn<SportsmansClubHistory, String> dop_MCH_START_DATA;
    @FXML
    TableColumn<SportsmansClubHistory, String> dop_MCH_END_DATE;


    @FXML
    Button back;
    @FXML
    TextField count;
    @FXML
    Button forward;

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
    TextField enterCL_NAME;
    @FXML
    TextField enterCL_TEL;
    @FXML
    TextField enter_MC_REG_DATE;

    @FXML
    Button enter;
    @FXML
    Button remove;
    @FXML
    Button menu;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtomicInteger count = new AtomicInteger();
        ObservableList<MC_COM_CL_SP> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);

        dop_COM_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCOM_NAME()));
        dop_COM_NAME.setCellFactory(TextFieldTableCell.forTableColumn());

        dop_COM_START_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCOM_START_DATE().toString()));;
        dop_COM_START_DATE.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_COM_END_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCOM_END_DATE() == null ? "N/A" : cellData.getValue().getCOM_END_DATE().toString()));
        dop_COM_END_DATE.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_COM_START_REG_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCOM_START_REG_DATE().toString()));;
        dop_COM_START_REG_DATE.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_COM_END_REG_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCOM_END_REG_DATE().toString()));;
        dop_COM_END_REG_DATE.setCellFactory(TextFieldTableCell.forTableColumn());


        dop_CL_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClub().getCL_NAME()));
        dop_CL_TEL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClub().getCL_TEL()));
        dop_CL_NAME.setCellFactory(TextFieldTableCell.forTableColumn());;
        dop_CL_TEL.setCellFactory(TextFieldTableCell.forTableColumn());;

        dop_S_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_NAME()));
        dop_S_SURNAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_SURNAME()));
        dop_S_PATRONYMIC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_PATRONYMIC()));
        dop_S_MAIL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_MAIL()));
        dop_S_TEL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_TEL()));
        dop_S_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_SURNAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_PATRONYMIC.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_MAIL.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_TEL.setCellFactory(TextFieldTableCell.forTableColumn());

        dop_MCH_START_DATA.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMembersClubHistory().getMCH_START_DATE().toString()));
        dop_MCH_START_DATA.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_MCH_END_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMembersClubHistory().getMCH_END_DATE() == null ? "N/A" :
                 cellData.getValue().getMembersClubHistory().getMCH_END_DATE().toString()));
        dop_MCH_END_DATE.setCellFactory(TextFieldTableCell.forTableColumn());
        ObservableList<SportsmansClubHistory> sportsmansClubHistories = FXCollections.observableArrayList();
        ObservableList<Competition> competitions = FXCollections.observableArrayList();


        MembersCompetitionDBW.dop_tables(statement, competitions, sportsmansClubHistories, dop_COM, dop_MCH, SportsmansClubHistory::new);




        AtomicReference<Competition> competition = new AtomicReference<>();
        AtomicReference<SportsmansClubHistory> sportsmanClubHistory = new AtomicReference<>();

        dop_COM.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<Competition> selectedRows = dop_COM.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    competition.set(selectedRows.get(0));
                    sportsmanClubHistory.set(null);
                }
            }
            else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<Competition> selectedRows = dop_COM.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    Competition competition1 = selectedRows.get(0);
                    enterCOM_NAME.setText(competition1.getCOM_NAME());
                    enterCOM_START_DATE.setText(competition1.getCOM_START_DATE().toString());
                    enterCOM_END_DATE.setText(competition1.getCOM_END_DATE() == null ? "N/A" : competition1.getCOM_END_DATE().toString());
                    enterCOM_START_REG_DATE.setText(competition1.getCOM_START_REG_DATE().toString());
                    enterCOM_END_REG_DATE.setText(competition1.getCOM_END_REG_DATE().toString());
                }
            }
        });
        dop_MCH.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<SportsmansClubHistory> selectedRows = dop_MCH.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    sportsmanClubHistory.set(selectedRows.get(0));
                    competition.set(null);
                }
            }
            else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<SportsmansClubHistory> selectedRows = dop_MCH.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    SportsmansClubHistory sportsmansClubHistory = selectedRows.get(0);
                    enterCL_NAME.setText(sportsmansClubHistory.getClub().getCL_NAME());
                    enterCL_TEL.setText(sportsmansClubHistory.getClub().getCL_TEL());
                    enterS_MAIL.setText(sportsmansClubHistory.getSportsman().getS_MAIL());
                    enterS_TEL.setText(sportsmansClubHistory.getSportsman().getS_TEL());
                }
            }
        });





        COM_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_NAME()));
        COM_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        COM_START_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_START_DATE().toString()));
        COM_START_DATE.setCellFactory(TextFieldTableCell.forTableColumn());;
        COM_END_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_END_DATE() == null ? "N/A" : cellData.getValue().getCompetition().getCOM_END_DATE().toString()));
        COM_END_DATE.setCellFactory(TextFieldTableCell.forTableColumn());;
        COM_START_REG_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_START_REG_DATE().toString()));
        COM_START_REG_DATE.setCellFactory(TextFieldTableCell.forTableColumn());;
        COM_END_REG_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_END_REG_DATE().toString()));
        COM_END_REG_DATE.setCellFactory(TextFieldTableCell.forTableColumn());

        CL_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClub().getCL_NAME()));
        CL_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        CL_TEL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClub().getCL_TEL()));;
        CL_TEL.setCellFactory(TextFieldTableCell.forTableColumn());

        S_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_NAME()));
        S_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        S_SURNAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_SURNAME()));
        S_SURNAME.setCellFactory(TextFieldTableCell.forTableColumn());
        S_PATRONYMIC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_PATRONYMIC()));
        S_PATRONYMIC.setCellFactory(TextFieldTableCell.forTableColumn());
        S_TEL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_TEL()));
        S_TEL.setCellFactory(TextFieldTableCell.forTableColumn());
        S_MAIL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_MAIL()));
        S_MAIL.setCellFactory(TextFieldTableCell.forTableColumn());

        MC_REG_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMembersCompetition().getMC_REG_DATE().toString()));
        MC_REG_DATE.setCellFactory(TextFieldTableCell.forTableColumn());


        forward.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            MembersCompetitionDBW.forward(textFieldCount, count, flag, data, rs, tableView, MC_COM_CL_SP::new);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            MembersCompetitionDBW.back(textFieldCount, count, flag, data, rs, tableView, MC_COM_CL_SP::new);
        });

        COM_NAME.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            MC_COM_CL_SP mcComClSp = tableView.getItems().get(row);
            MembersCompetitionDBW.newCompetition(mcComClSp.getMembersCompetition(),mcComClSp.getCompetition(), competition.get() ,statement, tableView);
        });
        COM_START_DATE.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            MC_COM_CL_SP mcComClSp = tableView.getItems().get(row);
            MembersCompetitionDBW.newCompetition(mcComClSp.getMembersCompetition(),mcComClSp.getCompetition(), competition.get() ,statement, tableView);
        });
        COM_END_REG_DATE.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            MC_COM_CL_SP mcComClSp = tableView.getItems().get(row);
            MembersCompetitionDBW.newCompetition(mcComClSp.getMembersCompetition(),mcComClSp.getCompetition(), competition.get() ,statement, tableView);
        });
        COM_END_DATE.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            MC_COM_CL_SP mcComClSp = tableView.getItems().get(row);
            MembersCompetitionDBW.newCompetition(mcComClSp.getMembersCompetition(),mcComClSp.getCompetition(), competition.get() ,statement, tableView);
        });
        COM_START_REG_DATE.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            MC_COM_CL_SP mcComClSp = tableView.getItems().get(row);
            MembersCompetitionDBW.newCompetition(mcComClSp.getMembersCompetition(),mcComClSp.getCompetition(), competition.get() ,statement, tableView);
        });


        CL_NAME.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            MC_COM_CL_SP mcComClSp = tableView.getItems().get(row);
            MembersCompetitionDBW.newSportsmanClub(mcComClSp.getMembersCompetition(),mcComClSp.getClub(), sportsmanClubHistory.get().getClub(), mcComClSp.getSportsman(), sportsmanClubHistory.get().getSportsman(), statement, tableView);
        });
        CL_TEL.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            MC_COM_CL_SP mcComClSp = tableView.getItems().get(row);
            MembersCompetitionDBW.newSportsmanClub(mcComClSp.getMembersCompetition(),mcComClSp.getClub(), sportsmanClubHistory.get().getClub(), mcComClSp.getSportsman(), sportsmanClubHistory.get().getSportsman(), statement, tableView);
        });

        S_TEL.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            MC_COM_CL_SP mcComClSp = tableView.getItems().get(row);
            MembersCompetitionDBW.newSportsmanClub(mcComClSp.getMembersCompetition(),mcComClSp.getClub(), sportsmanClubHistory.get().getClub(), mcComClSp.getSportsman(), sportsmanClubHistory.get().getSportsman(), statement, tableView);
        });
        S_MAIL.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            MC_COM_CL_SP mcComClSp = tableView.getItems().get(row);
            MembersCompetitionDBW.newSportsmanClub(mcComClSp.getMembersCompetition(),mcComClSp.getClub(), sportsmanClubHistory.get().getClub(), mcComClSp.getSportsman(), sportsmanClubHistory.get().getSportsman(), statement, tableView);
        });

        MC_REG_DATE.setOnEditCommit(event -> {
            int row = event.getTablePosition().getRow();
            String newValue = event.getNewValue();
            MC_COM_CL_SP mcComClSp = tableView.getItems().get(row);
            MembersCompetitionDBW.newMC_REG_DATE(mcComClSp.getMembersCompetition(),newValue ,statement, tableView);
        });
        tableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.V && event.isControlDown()) {
                ObservableList<MC_COM_CL_SP> selectedItems = tableView.getSelectionModel().getSelectedItems();
                // Далее можем получить информацию о выделенных элементах
                MC_COM_CL_SP mcComClSp = selectedItems.get(0);
                if(sportsmanClubHistory.get() != null){
                    MembersCompetitionDBW.newSportsmanClub(mcComClSp.getMembersCompetition(),mcComClSp.getClub(), sportsmanClubHistory.get().getClub(), mcComClSp.getSportsman(), sportsmanClubHistory.get().getSportsman() ,statement, tableView);
                }
                if(competition.get() != null){
                    MembersCompetitionDBW.newCompetition(mcComClSp.getMembersCompetition(),mcComClSp.getCompetition(), competition.get() ,statement, tableView);

                }
            }
        });
        remove.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                MC_COM_CL_SP mcComClSp = tableView.getItems().get(selectedIndex);
                MembersCompetitionDBW.remove(mcComClSp.getMembersCompetition(), statement, flag, mcComClSp, tableView, data);
            }
        });
        enter.setOnAction(actionEvent -> {
            String COM_NAME = enterCOM_NAME.getText();
            String COM_START_DATE = enterCOM_START_DATE.getText();
            String COM_END_DATE = enterCOM_END_DATE.getText();
            String COM_START_REG_DATE = enterCOM_START_REG_DATE.getText();
            String COM_END_REG_DATE = enterCOM_END_REG_DATE.getText();
            String S_TEL = enterS_TEL.getText();
            String S_MAIL = enterS_MAIL.getText();
            String CL_NAME = enterCL_NAME.getText();
            String CL_TEL = enterCL_TEL.getText();
            String MC_REG_DATE = enter_MC_REG_DATE.getText();
            MembersCompetitionDBW.enter(statement, COM_NAME, COM_START_DATE, COM_END_DATE, COM_START_REG_DATE, COM_END_REG_DATE, S_TEL, S_MAIL, CL_NAME, CL_TEL, MC_REG_DATE, flag, tableView, data,MC_COM_CL_SP::new);
        });
        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });
    }
}
