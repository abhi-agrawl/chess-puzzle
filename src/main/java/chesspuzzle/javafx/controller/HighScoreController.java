// CHECKSTYLE:OFF
package chesspuzzle.javafx.controller;

import chesspuzzle.results.GameResult;
import chesspuzzle.results.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class HighScoreController {


    private FXMLLoader fxmlLoader = new FXMLLoader();

    private GameResult gameResult = new GameResult();

    @FXML
    private TableView<Player> highScoreTable;

    @FXML
    private TableColumn<Player, String> player;

    @FXML
    private TableColumn<Player, Integer> steps;

    @FXML
    private TableColumn<Player, String> duration;

    @FXML
    private TableColumn<Player, String> created;

    @FXML
    private void initialize() {
        log.debug("Loading high scores...");
        List<Player> highScoreList = gameResult.readFromFile()
                .stream()
                .limit(10)
                .collect(Collectors.toList());

        player.setCellValueFactory(new PropertyValueFactory<>("player"));
        steps.setCellValueFactory(new PropertyValueFactory<>("steps"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        created.setCellValueFactory(new PropertyValueFactory<>("created"));

        ObservableList<Player> observableResult = FXCollections.observableArrayList(highScoreList);

        highScoreTable.setItems(observableResult);
    }

    public void handleRestartButton(ActionEvent actionEvent) throws IOException {
        log.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        log.info("Loading launch scene...");
        fxmlLoader.setLocation(getClass().getResource("/fxml/launch.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


}
