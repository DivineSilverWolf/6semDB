package nsu.fit.ru.database_sports_architecture.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import nsu.fit.ru.database_sports_architecture.DBworckers.MenuBDW;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuController implements Initializable {
    String loginName;
    Statement statement;

    @FXML
    Button club;
    @FXML
    Button clubHistory;
    @FXML
    Button competition;
    @FXML
    Button membersCompetition;
    @FXML
    Button organizer;
    @FXML
    Button winners;
    @FXML
    Button sportsFacilityInformation;
    @FXML
    Button typesSportsInfrastructures;
    @FXML
    Button typesTSINames;
    @FXML
    Button sportsmans;
    @FXML
    Button ranks;
    @FXML
    Button trainers;
    @FXML
    Button profileInfoTrainer;
    @FXML
    Button trainerSportsmanHistory;
    @FXML
    Button typeSports;
    @FXML
    Button disconnect; // отправка в меню
    @FXML
    Button requests; // отправка на страницу запросов

    @FXML
    Text login;

    public MenuController(String login, Statement statement) {
        this.loginName = login;
        this.statement = statement;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login.setText(loginName);

        club.setOnAction(actionEvent -> {
            try {
                MenuBDW.club(statement,club.getScene(), login.getText());
            } catch (SQLException | IOException | RuntimeException e) {
                e.printStackTrace();
            }
        });
        clubHistory.setOnAction(actionEvent -> {
            try {
                MenuBDW.clubHistory(statement,club.getScene(), login.getText());
            } catch (SQLException | IOException | RuntimeException e) {
                e.printStackTrace();
            }
        });

        competition.setOnAction( actionEvent -> {
            try {
                    MenuBDW.competition(statement, club.getScene(), login.getText());
                } catch (SQLException | IOException | RuntimeException e) {
                    e.printStackTrace();
                }
            }
        );
        membersCompetition.setOnAction(actionEvent -> {
            try {
                MenuBDW.members_competition(statement, club.getScene(), login.getText());
            } catch (SQLException | IOException | RuntimeException e) {
                e.printStackTrace();
            }
        });
        organizer.setOnAction(actionEvent -> {
            try {
                MenuBDW.organizer(statement, club.getScene(), login.getText());
            } catch (SQLException | IOException | RuntimeException e) {
                e.printStackTrace();
            }
        });
        winners.setOnAction(actionEvent -> {
            try {
                MenuBDW.winners(statement, club.getScene(), login.getText());
            } catch (SQLException | IOException | RuntimeException e) {
                e.printStackTrace();
            }
        });
        sportsFacilityInformation.setOnAction(actionEvent -> {
            try {
                MenuBDW.sportsFacilityInformation(statement, club.getScene(), login.getText());
            } catch (SQLException | IOException | RuntimeException e) {
                e.printStackTrace();
            }
        });
        typesSportsInfrastructures.setOnAction(actionEvent -> {
            try {
                MenuBDW.typesSportsInfrastructures(statement, club.getScene(), login.getText());
            } catch (SQLException | IOException | RuntimeException e) {
                e.printStackTrace();
            }
        });
        typesTSINames.setOnAction(actionEvent -> {
            try {
                MenuBDW.typesTSINames(statement, club.getScene(), login.getText());
            } catch (SQLException | IOException | RuntimeException e) {
                e.printStackTrace();
            }
        });
        sportsmans.setOnAction(actionEvent -> {
            try {
                MenuBDW.sportsmans(statement, club.getScene(), login.getText());
            } catch (SQLException | IOException | RuntimeException e) {
                e.printStackTrace();
            }
        });
        ranks.setOnAction(actionEvent -> {
            try {
                MenuBDW.ranks(statement, club.getScene(), login.getText());
            } catch (SQLException | IOException | RuntimeException e) {
                e.printStackTrace();
            }
        });
        trainers.setOnAction(actionEvent -> {
            try {
                MenuBDW.trainer(statement, club.getScene(), login.getText());
            } catch (SQLException | IOException | RuntimeException e) {
                e.printStackTrace();
            }
        });
        profileInfoTrainer.setOnAction(actionEvent -> {
            try {
                MenuBDW.profileInfoTrainer(statement, club.getScene(), login.getText());
            } catch (SQLException | IOException | RuntimeException e) {
                e.printStackTrace();
            }
        });
        trainerSportsmanHistory.setOnAction(actionEvent -> {
            try {
                MenuBDW.trainerSportsmanHistory(statement, club.getScene(), login.getText());
            } catch (SQLException | IOException | RuntimeException e) {
                e.printStackTrace();
            }
        });
        typeSports.setOnAction(actionEvent -> {
            try {
                MenuBDW.typeSports(statement, club.getScene(), login.getText());
            } catch (SQLException | IOException | RuntimeException e) {
                e.printStackTrace();
            }
        });


        disconnect.setOnAction(actionEvent -> {
            try {
                MenuBDW.disconnect(statement, disconnect.getScene());
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        requests.setOnAction(actionEvent -> System.out.println(18));
    }
}
