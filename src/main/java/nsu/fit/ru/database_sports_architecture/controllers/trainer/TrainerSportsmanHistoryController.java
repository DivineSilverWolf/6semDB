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
import javafx.scene.input.KeyCode;
import lombok.Getter;
import lombok.Setter;
import nsu.fit.ru.database_sports_architecture.DBTables.club_inf.Club;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.ProfileInfoTrainer;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.Trainer;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.TrainerSportsmanHistory;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.club_inf.MembersClubHistoryDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.DBworckers.trainer.TrainerSportsmanHistoryDBW;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.club_inf.MembersClubHistoryController;
import nsu.fit.ru.database_sports_architecture.controllers.special_assignments.GetterTextOrNullTextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class TrainerSportsmanHistoryController implements Initializable {

    Statement statement;
    ResultSet rs;
    String login;
    public TrainerSportsmanHistoryController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }
    @Getter
    @Setter
    static class TS_T_S_TSH{
        TypesSports typesSports;
        Trainer trainer;
        Sportsman sportsman;
        TrainerSportsmanHistory trainerSportsmanHistory;

        public TS_T_S_TSH(TypesSports typesSports, Trainer trainer, Sportsman sportsman, TrainerSportsmanHistory trainerSportsmanHistory) {
            this.typesSports = typesSports;
            this.trainer = trainer;
            this.sportsman = sportsman;
            this.trainerSportsmanHistory = trainerSportsmanHistory;
        }
    }
    @FXML
    TableView<TS_T_S_TSH> tableView;
    @FXML
    TableColumn<TS_T_S_TSH, String> typesName;
    @FXML
    TableColumn<TS_T_S_TSH, String> START_DATE;
    @FXML
    TableColumn<TS_T_S_TSH, String> END_DATE;
    @FXML
    TableColumn<TS_T_S_TSH, String> T_NAME;
    @FXML
    TableColumn<TS_T_S_TSH, String> T_SURNAME;
    @FXML
    TableColumn<TS_T_S_TSH, String> T_PATRONYMIC;
    @FXML
    TableColumn<TS_T_S_TSH, String> T_MAIL;
    @FXML
    TableColumn<TS_T_S_TSH, String> T_TEL;
    @FXML
    TableColumn<TS_T_S_TSH, String> S_NAME;
    @FXML
    TableColumn<TS_T_S_TSH, String> S_SURNAME;
    @FXML
    TableColumn<TS_T_S_TSH, String> S_PATRONYMIC;
    @FXML
    TableColumn<TS_T_S_TSH, String> S_MAIL;
    @FXML
    TableColumn<TS_T_S_TSH, String> S_TEL;

    @FXML
    TableView<Trainer> dop_TrainerTable;
    @FXML
    TableColumn<Trainer, String> dop_T_NAME;
    @FXML
    TableColumn<Trainer, String> dop_T_SURNAME;
    @FXML
    TableColumn<Trainer, String> dop_T_PATRONYMIC;
    @FXML
    TableColumn<Trainer, String> dop_T_MAIL;
    @FXML
    TableColumn<Trainer, String> dop_T_TEL;

    @FXML
    TableView<Sportsman> dop_SportsmanTable;
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
    TableView<TypesSports> dop_TypesSports;
    @FXML
    TableColumn<TypesSports, String> dop_TS_NAME;


    @FXML
    TextField enterSTART_DATE;
    @FXML
    TextField enterEND_DATE;
    @FXML
    TextField enterTypesSports;
    @FXML
    TextField enterS_TEL;
    @FXML
    TextField enterS_MAIL;

    @FXML
    TextField enterT_TEL;
    @FXML
    TextField enterT_MAIL;


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
    @FXML
    Button droppedOut;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtomicInteger count = new AtomicInteger();
        ObservableList<TS_T_S_TSH> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);

        typesName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypesSports().getTS_NAME()));
        typesName.setCellFactory(TextFieldTableCell.forTableColumn());

        START_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrainerSportsmanHistory().getTSH_START_DATE().toString()));
        START_DATE.setCellFactory(TextFieldTableCell.forTableColumn());;
        END_DATE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrainerSportsmanHistory().getTSH_END_DATE() == null ? "N/A" : cellData.getValue().getTrainerSportsmanHistory().getTSH_END_DATE().toString()));
        END_DATE.setCellFactory(TextFieldTableCell.forTableColumn());;

        T_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrainer().getT_NAME()));
        T_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        T_SURNAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrainer().getT_SURNAME()));
        T_SURNAME.setCellFactory(TextFieldTableCell.forTableColumn());
        T_PATRONYMIC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrainer().getT_PATRONYMIC()));
        T_PATRONYMIC.setCellFactory(TextFieldTableCell.forTableColumn());
        T_MAIL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrainer().getT_MAIL()));
        T_MAIL.setCellFactory(TextFieldTableCell.forTableColumn());
        T_TEL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTrainer().getT_TEL()));
        T_TEL.setCellFactory(TextFieldTableCell.forTableColumn());

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

        dop_T_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getT_NAME()));
        dop_T_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_T_SURNAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getT_SURNAME()));
        dop_T_SURNAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_T_PATRONYMIC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getT_PATRONYMIC()));
        dop_T_PATRONYMIC.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_T_MAIL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getT_MAIL()));
        dop_T_MAIL.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_T_TEL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getT_TEL()));
        dop_T_TEL.setCellFactory(TextFieldTableCell.forTableColumn());

        dop_S_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_NAME()));
        dop_S_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_SURNAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_SURNAME()));
        dop_S_SURNAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_PATRONYMIC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_PATRONYMIC()));
        dop_S_PATRONYMIC.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_MAIL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_MAIL()));
        dop_S_MAIL.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_S_TEL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getS_TEL()));
        dop_S_TEL.setCellFactory(TextFieldTableCell.forTableColumn());

        dop_TS_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTS_NAME()));
        dop_TS_NAME.setCellFactory(TextFieldTableCell.forTableColumn());


        ObservableList<Trainer> dop_trainer_data = FXCollections.observableArrayList();
        ObservableList<Sportsman> dop_sportsman_data = FXCollections.observableArrayList();
        ObservableList<TypesSports> dop_types_sports_data = FXCollections.observableArrayList();
        TrainerSportsmanHistoryDBW.dop_tables(statement,dop_trainer_data,dop_sportsman_data, dop_types_sports_data, dop_TrainerTable,dop_SportsmanTable, dop_TypesSports);
        AtomicReference<Trainer> trainer = new AtomicReference<>();
        AtomicReference<Sportsman> sportsman = new AtomicReference<>();
        AtomicReference<TypesSports> types_sports = new AtomicReference<>();
        trainer.set(null);
        sportsman.set(null);
        types_sports.set(null);

        dop_TrainerTable.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<Trainer> selectedRows = dop_TrainerTable.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    trainer.set(selectedRows.get(0));
                    sportsman.set(null);
                    types_sports.set(null);
                }
            } else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<Trainer> selectedRows = dop_TrainerTable.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    Trainer trainer1 = selectedRows.get(0);
                    enterT_MAIL.setText(trainer1.getT_MAIL());
                    enterT_TEL.setText(trainer1.getT_TEL());
                }
            }
        });
        dop_SportsmanTable.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<Sportsman> selectedRows = dop_SportsmanTable.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    sportsman.set( selectedRows.get(0));
                    trainer.set(null);
                    types_sports.set(null);
                }
            }
            else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<Sportsman> selectedRows = dop_SportsmanTable.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    Sportsman sportsman1 = selectedRows.get(0);
                    enterS_MAIL.setText(sportsman1.getS_MAIL());
                    enterS_TEL.setText(sportsman1.getS_TEL());
                }
            }
        });
        dop_TypesSports.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<TypesSports> selectedRows = dop_TypesSports.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    sportsman.set(null);
                    trainer.set(null);
                    types_sports.set(selectedRows.get(0));
                }
            }
            else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<TypesSports> selectedRows = dop_TypesSports.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    TypesSports typesSports = selectedRows.get(0);
                    enterTypesSports.setText(typesSports.getTS_NAME());
                }
            }
        });

        tableView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.V && event.isControlDown()) {
                ObservableList<TS_T_S_TSH> selectedItems = tableView.getSelectionModel().getSelectedItems();
                // Далее можем получить информацию о выделенных элементах
                TS_T_S_TSH tsTSTsh = selectedItems.get(0);
                if(trainer.get() != null){
                    tsTSTsh.setTrainer(trainer.get());
                    tsTSTsh.getTrainerSportsmanHistory().setT_ID(trainer.get().getT_ID());
                    TrainerSportsmanHistoryDBW.anotherTrainer(tsTSTsh.getTrainerSportsmanHistory(), trainer.get(), statement, tableView);
                }
                if(sportsman.get() != null){
                    tsTSTsh.setSportsman(sportsman.get());
                    tsTSTsh.getTrainerSportsmanHistory().setS_ID(sportsman.get().getS_ID());
                    TrainerSportsmanHistoryDBW.anotherSportsman(tsTSTsh.getTrainerSportsmanHistory(),sportsman.get(),statement, tableView);
                }
                if(types_sports.get() != null){
                    tsTSTsh.setTypesSports(types_sports.get());
                    tsTSTsh.getTrainerSportsmanHistory().setTS_ID(types_sports.get().getTS_ID());
                    TrainerSportsmanHistoryDBW.anotherTypesSports(tsTSTsh.getTrainerSportsmanHistory(),types_sports.get(),statement, tableView);
                }
            }
        });
        droppedOut.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                TS_T_S_TSH tsTSTsh = tableView.getItems().get(selectedIndex);
                TrainerSportsmanHistoryDBW.droppedOut(tsTSTsh.getTrainerSportsmanHistory(), statement, tableView);
            }
        });

        forward.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            TrainerSportsmanHistoryDBW.forward(textFieldCount, count, flag, data, rs, tableView, TS_T_S_TSH::new);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            TrainerSportsmanHistoryDBW.back(textFieldCount, count, flag, data, rs, tableView, TS_T_S_TSH::new);
        });

        START_DATE.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            TS_T_S_TSH tsTSTsh = tableView.getItems().get(row);
            TrainerSportsmanHistoryDBW.start_date(tsTSTsh.getTrainerSportsmanHistory(), statement, newValue);
        });
        END_DATE.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            TS_T_S_TSH tsTSTsh = tableView.getItems().get(row);
            TrainerSportsmanHistoryDBW.end_date(tsTSTsh.getTrainerSportsmanHistory(), statement, newValue, tableView);
        });

        remove.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                TS_T_S_TSH tsTSTsh = tableView.getItems().get(selectedIndex);
                TrainerSportsmanHistoryDBW.remove(tsTSTsh.getTrainerSportsmanHistory(), statement, flag, tsTSTsh, tableView, data);
            }
        });
        enter.setOnAction(actionEvent -> {
            String sTel = GetterTextOrNullTextField.getText(enterS_TEL);
            String sMail = GetterTextOrNullTextField.getText(enterS_MAIL);
            String tTel = GetterTextOrNullTextField.getText(enterT_TEL);
            String tMail = GetterTextOrNullTextField.getText(enterT_MAIL);
            String sportName = GetterTextOrNullTextField.getText(enterTypesSports);
            String start_date = GetterTextOrNullTextField.getText(enterSTART_DATE);
            String end_date = GetterTextOrNullTextField.getText(enterEND_DATE);
            TrainerSportsmanHistoryDBW.enter(statement, sTel, sMail, tTel, tMail, sportName, start_date,end_date, flag, tableView, data, TS_T_S_TSH::new);
        });
        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });
    }
}
