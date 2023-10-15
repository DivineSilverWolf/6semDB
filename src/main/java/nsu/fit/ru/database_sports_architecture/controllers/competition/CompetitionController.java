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
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Competition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.SportsFacilityInformation;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.club_inf.MembersClubHistoryDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition.CompetitionDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.club_inf.MembersClubHistoryController;
import nsu.fit.ru.database_sports_architecture.models.competition.CompetitionModel;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompetitionController implements Initializable {
    Statement statement;
    ResultSet rs;
    String login;
    public CompetitionController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }
    @Getter @Setter
    private class COM_SFI_ORG_TS{
        Competition competition;
        SportsFacilityInformation sportsFacilityInformation;
        Organizer organizer;
        TypesSports typesSports;

        public COM_SFI_ORG_TS(Competition competition, SportsFacilityInformation sportsFacilityInformation, Organizer organizer, TypesSports typesSports) {
            this.competition = competition;
            this.sportsFacilityInformation = sportsFacilityInformation;
            this.organizer = organizer;
            this.typesSports = typesSports;
        }
    }



    @FXML
    TableView<COM_SFI_ORG_TS> tableView;
    @FXML
    TableView<SportsFacilityInformation> dop_SFI;
    @FXML
    TableView<TypesSports> dop_types_sports;
    @FXML
    TableView<Organizer> dop_org;

    @FXML
    TableColumn<COM_SFI_ORG_TS, String> COM_NAME;
    @FXML
    TableColumn<COM_SFI_ORG_TS, String> TS_NAME;
    @FXML
    TableColumn<COM_SFI_ORG_TS, String> COM_START_DATE;
    @FXML
    TableColumn<COM_SFI_ORG_TS, String> COM_END_DATE;
    @FXML
    TableColumn<COM_SFI_ORG_TS, String> COM_START_REG_DATE;
    @FXML
    TableColumn<COM_SFI_ORG_TS, String> COM_END_REG_DATE;
    @FXML
    TableColumn<COM_SFI_ORG_TS, String> ORG_NAME;
    @FXML
    TableColumn<COM_SFI_ORG_TS, String> ORG_TEL;
    @FXML
    TableColumn<COM_SFI_ORG_TS, String> ORG_S_MAIL;
    @FXML
    TableColumn<COM_SFI_ORG_TS, String> SFI_ADDR;
    @FXML
    TableColumn<COM_SFI_ORG_TS, String> SFI_NAME;


    @FXML
    TableColumn<SportsFacilityInformation, String> dop_SFI_ADDR;
    @FXML
    TableColumn<SportsFacilityInformation, String> dop_SFI_NAME;

    @FXML
    TableColumn<TypesSports, String> dop_TS_NAME;


    @FXML
    TableColumn<Organizer, String> dop_ORG_NAME;
    @FXML
    TableColumn<Organizer, String> dop_ORG_TEL;
    @FXML
    TableColumn<Organizer, String> dop_ORG_S_MAIL;

    @FXML
    Button back;
    @FXML
    Button forward;
    @FXML
    TextField count;

    @FXML
    Button droppedOut;

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
    TextField enterTS_NAME;

    @FXML
    TextField enterORG_TEL;
    @FXML
    TextField enterORG_S_MAIL;

    @FXML
    TextField enterSFI_ADDR;
    @FXML
    TextField enterSFI_NAME;

    @FXML
    Button enter;
    @FXML
    Button remove;
    @FXML
    Button menu;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtomicInteger count = new AtomicInteger();
        ObservableList<COM_SFI_ORG_TS> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);


        dop_ORG_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getORG_NAME()));
        dop_ORG_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_ORG_S_MAIL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getORG_S_MAIL()));
        dop_ORG_S_MAIL.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_ORG_TEL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getORG_TEL()));
        dop_ORG_TEL.setCellFactory(TextFieldTableCell.forTableColumn());


        dop_TS_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTS_NAME()));
        dop_TS_NAME.setCellFactory(TextFieldTableCell.forTableColumn());


        dop_SFI_ADDR.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSFI_ADDR()));
        dop_SFI_ADDR.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_SFI_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSFI_NAME()));
        dop_SFI_NAME.setCellFactory(TextFieldTableCell.forTableColumn());

        ObservableList<SportsFacilityInformation> sportsFacilityInformationA = FXCollections.observableArrayList();
        ObservableList<TypesSports> typesSportsA = FXCollections.observableArrayList();
        ObservableList<Organizer> organizerA = FXCollections.observableArrayList();


        CompetitionDBW.dop_tables(statement, typesSportsA, sportsFacilityInformationA, organizerA, dop_types_sports, dop_SFI, dop_org);




        AtomicReference<SportsFacilityInformation> sportsFacilityInformation = new AtomicReference<>();
        AtomicReference<TypesSports> typesSports = new AtomicReference<>();
        AtomicReference<Organizer> organizer = new AtomicReference<>();
        dop_org.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<Organizer> selectedRows = dop_org.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    organizer.set(selectedRows.get(0));
                    typesSports.set(null);
                    sportsFacilityInformation.set(null);
                }
            }
            else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<Organizer> selectedRows = dop_org.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    Organizer organizer1 = selectedRows.get(0);
                    enterORG_S_MAIL.setText(organizer1.getORG_S_MAIL());
                    enterORG_TEL.setText(organizer1.getORG_TEL());
                }
            }
        });
        dop_types_sports.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<TypesSports> selectedRows = dop_types_sports.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    organizer.set(null);
                    typesSports.set(selectedRows.get(0));
                    sportsFacilityInformation.set(null);
                }
            }
            else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<TypesSports> selectedRows = dop_types_sports.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    TypesSports typesSports1 = selectedRows.get(0);
                    enterTS_NAME.setText(typesSports1.getTS_NAME());
                }
            }
        });
        dop_SFI.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<SportsFacilityInformation> selectedRows = dop_SFI.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    organizer.set(null);
                    typesSports.set(null);
                    sportsFacilityInformation.set(selectedRows.get(0));
                }
            }
            else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<SportsFacilityInformation> selectedRows = dop_SFI.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    SportsFacilityInformation sportsFacilityInformation1 = selectedRows.get(0);
                    enterSFI_ADDR.setText(sportsFacilityInformation1.getSFI_ADDR());
                    enterSFI_NAME.setText(sportsFacilityInformation1.getSFI_NAME());
                }
            }
        });

        COM_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_NAME()));
        COM_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        COM_START_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_START_DATE().toString()));
        COM_START_DATE.setCellFactory(TextFieldTableCell.forTableColumn());
        COM_END_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_END_DATE() != null ?
                cellData.getValue().getCompetition().getCOM_END_DATE().toString() : "N/A"));
        COM_END_DATE.setCellFactory(TextFieldTableCell.forTableColumn());
        COM_START_REG_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_START_REG_DATE().toString()));
        COM_START_REG_DATE.setCellFactory(TextFieldTableCell.forTableColumn());
        COM_END_REG_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompetition().getCOM_END_REG_DATE().toString()));
        COM_END_REG_DATE.setCellFactory(TextFieldTableCell.forTableColumn());

        TS_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypesSports().getTS_NAME()));
        TS_NAME.setCellFactory(TextFieldTableCell.forTableColumn());

        ORG_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrganizer().getORG_NAME()));
        ORG_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        ORG_TEL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrganizer().getORG_TEL()));
        ORG_TEL.setCellFactory(TextFieldTableCell.forTableColumn());
        ORG_S_MAIL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrganizer().getORG_S_MAIL()));
        ORG_S_MAIL.setCellFactory(TextFieldTableCell.forTableColumn());

        SFI_ADDR.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsFacilityInformation().getSFI_ADDR()));
        SFI_ADDR.setCellFactory(TextFieldTableCell.forTableColumn());
        SFI_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsFacilityInformation().getSFI_NAME()));
        SFI_NAME.setCellFactory(TextFieldTableCell.forTableColumn());

        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });

        forward.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            CompetitionDBW.forward(textFieldCount, count, flag, data, rs, tableView, COM_SFI_ORG_TS::new);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            CompetitionDBW.back(textFieldCount, count, flag, data, rs, tableView, COM_SFI_ORG_TS::new);
        });


        COM_NAME.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            COM_SFI_ORG_TS comSfiOrgTs = tableView.getItems().get(row);
            CompetitionDBW.clubName(comSfiOrgTs.getCompetition(), statement, newValue, tableView);
        });
        COM_START_DATE.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            COM_SFI_ORG_TS comSfiOrgTs = tableView.getItems().get(row);
            CompetitionDBW.newStartDate(comSfiOrgTs.getCompetition(), statement, newValue, tableView);
        });
        COM_END_DATE.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            COM_SFI_ORG_TS comSfiOrgTs = tableView.getItems().get(row);
            CompetitionDBW.newEndDate(comSfiOrgTs.getCompetition(), statement, newValue, tableView);
        });
        COM_START_REG_DATE.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            COM_SFI_ORG_TS comSfiOrgTs = tableView.getItems().get(row);
            CompetitionDBW.newStartRegDate(comSfiOrgTs.getCompetition(), statement, newValue, tableView);
        });
        COM_END_REG_DATE.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            COM_SFI_ORG_TS comSfiOrgTs = tableView.getItems().get(row);
            CompetitionDBW.newEndRegDate(comSfiOrgTs.getCompetition(), statement, newValue, tableView);
        });

        tableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.V && event.isControlDown()) {
                ObservableList<COM_SFI_ORG_TS> selectedItems = tableView.getSelectionModel().getSelectedItems();
                // Далее можем получить информацию о выделенных элементах
                COM_SFI_ORG_TS comSfiOrgTs = selectedItems.get(0);
                if(organizer.get() != null){
                    CompetitionDBW.newOrg(comSfiOrgTs.getCompetition(), comSfiOrgTs.getOrganizer(), organizer.get(), statement, tableView);
                }
                if(sportsFacilityInformation.get() != null){
                    CompetitionDBW.newSFI(comSfiOrgTs.getCompetition(),comSfiOrgTs.getSportsFacilityInformation(),
                            sportsFacilityInformation.get(), statement, tableView);
                }
                if(typesSports.get() != null){
                    comSfiOrgTs.setTypesSports(typesSports.get());
                    comSfiOrgTs.getCompetition().setTS_ID(typesSports.get().getTS_ID());
                    CompetitionDBW.newTS(comSfiOrgTs.getCompetition(),comSfiOrgTs.getTypesSports(),statement,comSfiOrgTs.getTypesSports().getTS_NAME(), tableView);
                }
            }
        });
        remove.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                COM_SFI_ORG_TS comSfiOrgTs = tableView.getItems().get(selectedIndex);
                CompetitionDBW.remove(comSfiOrgTs.competition, statement, flag, comSfiOrgTs, tableView, data);
            }
        });
        enter.setOnAction(actionEvent -> {
            String COM_NAME = enterCOM_NAME.getText();
            String COM_START_DATE = enterCOM_START_DATE.getText();
            String COM_END_DATE = enterCOM_END_DATE.getText();
            String COM_START_REG_DATE = enterCOM_START_REG_DATE.getText();
            String COM_END_REG_DATE = enterCOM_END_REG_DATE.getText();
            String TS_NAME = enterTS_NAME.getText();
            String ORG_TEL = enterORG_TEL.getText();
            String ORG_S_MAIL = enterORG_S_MAIL.getText();
            String SFI_ADDR = enterSFI_ADDR.getText();
            String SFI_NAME = enterSFI_NAME.getText();

            CompetitionDBW.enter(statement, COM_NAME, COM_START_DATE, COM_END_DATE, COM_START_REG_DATE, COM_END_REG_DATE, TS_NAME, ORG_TEL, ORG_S_MAIL, SFI_ADDR, SFI_NAME, flag, tableView, data, COM_SFI_ORG_TS::new);

        });
        droppedOut.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                COM_SFI_ORG_TS comSfiOrgTs = tableView.getItems().get(selectedIndex);
                CompetitionDBW.droppedOut(comSfiOrgTs.competition, statement, tableView);
            }
        });

    }
}
