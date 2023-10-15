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
import javafx.scene.input.KeyCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Competition;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Winners;
import nsu.fit.ru.database_sports_architecture.DBTables.sports_facility.general_sf.TypesSportsInfrastructures;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Ranks;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition.MembersCompetitionDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition.WinnersDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.sports_facility.general_sf.TypesSportsInfrastructuresDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.DBworckers.sportsman.RanksDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.trainer.ProfileInfoTrainerDBW;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.competition.MembersCompetitionController;
import nsu.fit.ru.database_sports_architecture.controllers.competition.WinnersController;
import nsu.fit.ru.database_sports_architecture.controllers.special_assignments.GetterTextOrNullTextField;
import nsu.fit.ru.database_sports_architecture.controllers.trainer.ProfileInfoTrainerController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RanksController implements Initializable {
    Statement statement;
    ResultSet rs;
    String login;
    public RanksController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }

    @Getter
    @Setter
    static class RanksSportsmanSport{
        TypesSports typesSports;
        Sportsman sportsman;
        Ranks ranks;

        public RanksSportsmanSport(TypesSports typesSports, Sportsman sportsman, Ranks ranks) {
            this.typesSports = typesSports;
            this.sportsman = sportsman;
            this.ranks = ranks;
        }
    }
    @FXML
    TableView<RanksSportsmanSport> tableView;
    @FXML
    TableColumn<RanksSportsmanSport, String> R_PLACE;
    @FXML
    TableColumn<RanksSportsmanSport, String> R_NAME;
    @FXML
    TableColumn<RanksSportsmanSport, String> kind_of_sport;
    @FXML
    TableColumn<RanksSportsmanSport, String> R_DATE;
    @FXML
    TableColumn<RanksSportsmanSport, String> S_NAME;
    @FXML
    TableColumn<RanksSportsmanSport, String> S_SURNAME;
    @FXML
    TableColumn<RanksSportsmanSport, String> S_PATRONYMIC;
    @FXML
    TableColumn<RanksSportsmanSport, String> S_MAIL;
    @FXML
    TableColumn<RanksSportsmanSport, String> S_TEL;

    @FXML
    TableView<Sportsman> dop_table_S;
    @FXML
    TableColumn<Sportsman, String> dop_S_NAME;
    @FXML
    TableColumn<Sportsman, String> dop_S_SURNAME;
    @FXML
    TableColumn<Sportsman, String> dop_S_PATRONYMIC;
    @FXML
    TableColumn<Sportsman, String> dop_S_MAIL;
    @FXML
    TableColumn<Sportsman, String> dop_S_TEL;
    @FXML
    TableView<TypesSports> dop_table_TS;
    @FXML TableColumn<TypesSports, String> dop_Types_Sports;

    @FXML
    Button back;
    @FXML
    Button forward;
    @FXML
    TextField count;

    @FXML
    TextField enterR_Place;
    @FXML
    TextField enterR_NAME;
    @FXML
    TextField enterTypes_Sports;
    @FXML
    TextField enterR_Date;
    @FXML
    TextField enterS_TEL;
    @FXML
    TextField enterS_MAIL;




    @FXML
    Button enter;
    @FXML
    Button remove;
    @FXML
    Button menu;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtomicInteger count = new AtomicInteger();
        ObservableList<RanksSportsmanSport> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);

        R_PLACE.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getRanks().getRANK_SPORT())));
        R_PLACE.setCellFactory(TextFieldTableCell.forTableColumn());
        R_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRanks().getRANK_NAME()));
        R_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        R_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRanks().getRANK_DATE().toString()));
        R_DATE.setCellFactory(TextFieldTableCell.forTableColumn());

        kind_of_sport.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypesSports().getTS_NAME()));
        kind_of_sport.setCellFactory(TextFieldTableCell.forTableColumn());


        S_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_NAME()));
        S_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        S_SURNAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_SURNAME()));
        S_SURNAME.setCellFactory(TextFieldTableCell.forTableColumn());
        S_PATRONYMIC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_PATRONYMIC()));
        S_PATRONYMIC.setCellFactory(TextFieldTableCell.forTableColumn());
        S_MAIL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_MAIL()));
        S_MAIL.setCellFactory(TextFieldTableCell.forTableColumn());
        S_TEL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSportsman().getS_TEL()));
        S_TEL.setCellFactory(TextFieldTableCell.forTableColumn());





        dop_S_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_NAME()));
        dop_S_SURNAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_SURNAME()));
        dop_S_PATRONYMIC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_PATRONYMIC()));
        dop_S_MAIL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_MAIL()));
        dop_S_TEL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_TEL()));
        dop_S_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_SURNAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_PATRONYMIC.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_MAIL.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_TEL.setCellFactory(TextFieldTableCell.forTableColumn());

        dop_Types_Sports.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTS_NAME()));
        dop_Types_Sports.setCellFactory(TextFieldTableCell.forTableColumn());

        ObservableList<Sportsman> sportsmen = FXCollections.observableArrayList();
        ObservableList<TypesSports> typesSports = FXCollections.observableArrayList();

        RanksDBW.dop_tables(statement, sportsmen, dop_table_S, typesSports, dop_table_TS);



        AtomicReference<Sportsman> sportsmanAtomicReference = new AtomicReference<>();
        AtomicReference<TypesSports> typesSportsAtomicReference = new AtomicReference<>();

        dop_table_S.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<Sportsman> selectedRows = dop_table_S.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    sportsmanAtomicReference.set(selectedRows.get(0));
                    typesSportsAtomicReference.set(null);
                }
            }
            else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<Sportsman> selectedRows = dop_table_S.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    Sportsman sportsman1 = selectedRows.get(0);
                    enterS_MAIL.setText(sportsman1.getS_MAIL());
                    enterS_TEL.setText(sportsman1.getS_TEL());
                }
            }
        });
        dop_table_TS.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<TypesSports> selectedRows = dop_table_TS.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    typesSportsAtomicReference.set(selectedRows.get(0));
                    sportsmanAtomicReference.set(null);
                }
            }
            else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<TypesSports> selectedRows = dop_table_TS.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    TypesSports typesSports1 = selectedRows.get(0);
                    enterTypes_Sports.setText(typesSports1.getTS_NAME());
                    typesSportsAtomicReference.set(null);
                }
            }
        });
        tableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.V && event.isControlDown()) {
                ObservableList<RanksSportsmanSport> selectedItems = tableView.getSelectionModel().getSelectedItems();
                // Далее можем получить информацию о выделенных элементах
                RanksSportsmanSport ranksSportsmanSport = selectedItems.get(0);
                if(sportsmanAtomicReference.get() != null){
                    RanksDBW.newSportsman(ranksSportsmanSport.getRanks(), sportsmanAtomicReference.get(), statement);
                    ranksSportsmanSport.setSportsman(sportsmanAtomicReference.get());
                    tableView.refresh();
                }
                else if(typesSportsAtomicReference.get() != null){
                    RanksDBW.newTypesSports(ranksSportsmanSport.getRanks(), typesSportsAtomicReference.get(), statement);
                    ranksSportsmanSport.setTypesSports(typesSportsAtomicReference.get());
                    tableView.refresh();
                }
            }
        });
        forward.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            RanksDBW.forward(textFieldCount, count, flag, data, rs, tableView, RanksSportsmanSport::new);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            RanksDBW.back(textFieldCount, count, flag, data, rs, tableView, RanksSportsmanSport::new);
        });

        remove.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                RanksSportsmanSport ranksSportsmanSport = tableView.getItems().get(selectedIndex);
                RanksDBW.remove(ranksSportsmanSport.getRanks(), statement, flag, ranksSportsmanSport, tableView, data);
            }
        });
        enter.setOnAction(actionEvent -> {
            String R_PLACE = GetterTextOrNullTextField.getText(enterR_Place);
            String R_DATE = GetterTextOrNullTextField.getText(enterR_Date);
            String R_NAME = GetterTextOrNullTextField.getText(enterR_NAME);
            String S_TEL = GetterTextOrNullTextField.getText(enterS_TEL);
            String S_MAIL = GetterTextOrNullTextField.getText(enterS_MAIL);
            String types = GetterTextOrNullTextField.getText(enterTypes_Sports);
            RanksDBW.enter(statement, R_PLACE, R_DATE, R_NAME, S_TEL, S_MAIL, types, flag, tableView, data, RanksSportsmanSport::new);
        });
        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });
        R_NAME.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            RanksSportsmanSport ranksSportsmanSport = tableView.getItems().get(row);
            RanksDBW.new_NDP(ranksSportsmanSport.getRanks(), statement, newValue, ranksSportsmanSport.getRanks().getRANK_DATE().toString(), Integer.toString(ranksSportsmanSport.getRanks().getRANK_SPORT()), tableView);
        });
        R_DATE.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            RanksSportsmanSport ranksSportsmanSport = tableView.getItems().get(row);
            RanksDBW.new_NDP(ranksSportsmanSport.getRanks(), statement, ranksSportsmanSport.getRanks().getRANK_NAME(), newValue, Integer.toString(ranksSportsmanSport.getRanks().getRANK_SPORT()), tableView);
        });
        R_PLACE.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            RanksSportsmanSport ranksSportsmanSport = tableView.getItems().get(row);
            RanksDBW.new_NDP(ranksSportsmanSport.getRanks(), statement, ranksSportsmanSport.getRanks().getRANK_NAME(), ranksSportsmanSport.getRanks().getRANK_DATE().toString(), newValue, tableView);
        });
    }
}
