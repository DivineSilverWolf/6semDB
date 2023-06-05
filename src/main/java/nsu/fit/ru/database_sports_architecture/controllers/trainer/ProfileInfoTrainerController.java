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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import nsu.fit.ru.database_sports_architecture.DBTables.competition.Organizer;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Ranks;
import nsu.fit.ru.database_sports_architecture.DBTables.sportsman.Sportsman;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.ProfileInfoTrainer;
import nsu.fit.ru.database_sports_architecture.DBTables.trainer.Trainer;
import nsu.fit.ru.database_sports_architecture.DBTables.types_sports.TypesSports;
import nsu.fit.ru.database_sports_architecture.DBworckers.DBTables.competition.OrganizerDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.special_assignments.MenuDSA;
import nsu.fit.ru.database_sports_architecture.DBworckers.sportsman.RanksDBW;
import nsu.fit.ru.database_sports_architecture.DBworckers.trainer.ProfileInfoTrainerDBW;
import nsu.fit.ru.database_sports_architecture.StarterApp;
import nsu.fit.ru.database_sports_architecture.controllers.special_assignments.GetterTextOrNullTextField;
import nsu.fit.ru.database_sports_architecture.controllers.sportsman.RanksController;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileInfoTrainerController implements Initializable {
    Statement statement;
    ResultSet rs;
    String login;
    public ProfileInfoTrainerController(Statement statement, ResultSet rs, String login) {
        this.statement = statement;
        this.rs = rs;
        this.login = login;
    }

    @Getter
    @Setter
    static class PIT_Trainer_Sports{
        TypesSports typesSports;
        Trainer trainer;
        ProfileInfoTrainer profileInfoTrainer;

        public PIT_Trainer_Sports(TypesSports typesSports, Trainer trainer, ProfileInfoTrainer profileInfoTrainer) {
            this.typesSports = typesSports;
            this.trainer = trainer;
            this.profileInfoTrainer = profileInfoTrainer;
        }
    }


    @FXML
    TableView<PIT_Trainer_Sports> tableView;
    @FXML
    TableColumn<PIT_Trainer_Sports, String> PIT_TRAINER_ACTIVE;
    @FXML
    TableColumn<PIT_Trainer_Sports, String> PIT_EXP_MONTHS;
    @FXML
    TableColumn<PIT_Trainer_Sports, String> kind_of_sport;
    @FXML
    TableColumn<PIT_Trainer_Sports, String> T_NAME;
    @FXML
    TableColumn<PIT_Trainer_Sports, String> T_SURNAME;
    @FXML
    TableColumn<PIT_Trainer_Sports, String> T_PATRONYMIC;
    @FXML
    TableColumn<PIT_Trainer_Sports, String> T_MAIL;
    @FXML
    TableColumn<PIT_Trainer_Sports, String> T_TEL;

    @FXML
    TableView<Trainer> dop_table_T;
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
    TableView<TypesSports> dop_table_TS;
    @FXML TableColumn<TypesSports, String> dop_Types_Sports;
    @FXML
    Button back;
    @FXML
    Button forward;
    @FXML
    TextField count;

    @FXML
    TextField enterPIT_TRAINER_ACTIVE;
    @FXML
    TextField enterPIT_EXP_MONTHS;
    @FXML
    TextField enterTypes_Sports;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtomicInteger count = new AtomicInteger();
        ObservableList<PIT_Trainer_Sports> data = FXCollections.observableArrayList();
        AtomicInteger flag = new AtomicInteger();

        AtomicInteger textFieldCount = new AtomicInteger(5);


        PIT_TRAINER_ACTIVE.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProfileInfoTrainer().getPIT_TRAINER_ACTIVE().equals("Y") ? "Активен" : "Неактивен"));
        PIT_TRAINER_ACTIVE.setCellFactory(TextFieldTableCell.forTableColumn());
        PIT_EXP_MONTHS.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getProfileInfoTrainer().getPIT_EXP_MONTHS())));
        PIT_EXP_MONTHS.setCellFactory(TextFieldTableCell.forTableColumn());

        kind_of_sport.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypesSports().getTS_NAME()));
        kind_of_sport.setCellFactory(TextFieldTableCell.forTableColumn());


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

        dop_T_NAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getT_NAME()));
        dop_T_SURNAME.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getT_SURNAME()));
        dop_T_PATRONYMIC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getT_PATRONYMIC()));
        dop_T_MAIL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getT_MAIL()));
        dop_T_TEL.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getT_TEL()));
        dop_T_NAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_T_SURNAME.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_T_PATRONYMIC.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_T_MAIL.setCellFactory(TextFieldTableCell.forTableColumn());
        dop_T_TEL.setCellFactory(TextFieldTableCell.forTableColumn());

        dop_Types_Sports.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTS_NAME()));
        dop_Types_Sports.setCellFactory(TextFieldTableCell.forTableColumn());


        ObservableList<Trainer> trainers = FXCollections.observableArrayList();
        ObservableList<TypesSports> typesSports = FXCollections.observableArrayList();

        ProfileInfoTrainerDBW.dop_tables(statement, trainers, dop_table_T, typesSports, dop_table_TS);


        AtomicReference<Trainer> trainerAtomicReference = new AtomicReference<>();
        AtomicReference<TypesSports> typesSportsAtomicReference = new AtomicReference<>();

        dop_table_T.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (event.getCode() == KeyCode.C && event.isControlDown()) {
                // Получаем выделенную строку
                ObservableList<Trainer> selectedRows = dop_table_T.getSelectionModel().getSelectedItems();
                if (!selectedRows.isEmpty()) {
                    // Делаем что-то с выделенной строкой
                    trainerAtomicReference.set(selectedRows.get(0));
                    typesSportsAtomicReference.set(null);
                }
            }
            else if (event.getCode() == KeyCode.SPACE) {
                ObservableList<Trainer> selectedRows = dop_table_T.getSelectionModel().getSelectedItems();
                if(!selectedRows.isEmpty()) {
                    Trainer trainer = selectedRows.get(0);
                    enterT_MAIL.setText(trainer.getT_MAIL());
                    enterT_TEL.setText(trainer.getT_TEL());
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
                    trainerAtomicReference.set(null);
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
                ObservableList<PIT_Trainer_Sports> selectedItems = tableView.getSelectionModel().getSelectedItems();
                // Далее можем получить информацию о выделенных элементах
                PIT_Trainer_Sports pitTrainerSports = selectedItems.get(0);
                if(trainerAtomicReference.get() != null){
                    ProfileInfoTrainerDBW.newTrainer(pitTrainerSports.getProfileInfoTrainer(), trainerAtomicReference.get(), statement);
                    pitTrainerSports.setTrainer(trainerAtomicReference.get());
                    tableView.refresh();
                }
                else if(typesSportsAtomicReference.get() != null){
                    ProfileInfoTrainerDBW.newTypesSports(pitTrainerSports.getProfileInfoTrainer(), typesSportsAtomicReference.get(), statement);
                    pitTrainerSports.setTypesSports(typesSportsAtomicReference.get());
                    tableView.refresh();
                }
            }
        });
        forward.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            ProfileInfoTrainerDBW.forward(textFieldCount, count, flag, data, rs, tableView, PIT_Trainer_Sports::new);
        });
        back.setOnAction(actionEvent -> {
            if(StarterApp.isDigit(this.count.getText()))
                textFieldCount.set(Integer.parseInt(this.count.getText()));
            ProfileInfoTrainerDBW.back(textFieldCount, count, flag, data, rs, tableView, PIT_Trainer_Sports::new);
        });
        remove.setOnAction(actionEvent -> {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            if (selectedIndex != -1) {
                PIT_Trainer_Sports ranksSportsmanSport = tableView.getItems().get(selectedIndex);
                ProfileInfoTrainerDBW.remove(ranksSportsmanSport.getProfileInfoTrainer(), statement, flag, ranksSportsmanSport, tableView, data);
            }
        });
        enter.setOnAction(actionEvent -> {
            String PIT_TRAINER_ACTIVE = GetterTextOrNullTextField.getText(enterPIT_TRAINER_ACTIVE).equals("Активен") ? "Y" : "N";
            String PIT_EXP_MONTHS = GetterTextOrNullTextField.getText(enterPIT_EXP_MONTHS);
            String T_TEL = GetterTextOrNullTextField.getText(enterT_TEL);
            String T_MAIL = GetterTextOrNullTextField.getText(enterT_MAIL);
            String types = GetterTextOrNullTextField.getText(enterTypes_Sports);
            ProfileInfoTrainerDBW.enter(statement, PIT_TRAINER_ACTIVE, PIT_EXP_MONTHS, T_TEL, T_MAIL, types, flag, tableView, data, PIT_Trainer_Sports::new);
        });
        menu.setOnAction(actionEvent -> {
            MenuDSA.menu(statement, menu.getScene(), login);
        });
        PIT_TRAINER_ACTIVE.setOnEditCommit(event -> {
            String newValue = event.getNewValue().equals("Активен") ? "Y" : "N";
            int row = event.getTablePosition().getRow();
            PIT_Trainer_Sports pitTrainerSports = tableView.getItems().get(row);
            ProfileInfoTrainerDBW.new_AE(pitTrainerSports.getProfileInfoTrainer(), statement, newValue, Integer.toString(pitTrainerSports.getProfileInfoTrainer().getPIT_EXP_MONTHS()), tableView);
        });
        PIT_EXP_MONTHS.setOnEditCommit(event -> {
            String newValue = event.getNewValue();
            int row = event.getTablePosition().getRow();
            PIT_Trainer_Sports pitTrainerSports = tableView.getItems().get(row);
            ProfileInfoTrainerDBW.new_AE(pitTrainerSports.getProfileInfoTrainer(), statement, pitTrainerSports.getProfileInfoTrainer().getPIT_TRAINER_ACTIVE(), newValue, tableView);
        });

    }
}
