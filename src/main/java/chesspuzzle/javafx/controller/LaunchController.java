// CHECKSTYLE:OFF
package chesspuzzle.javafx.controller;

import chesspuzzle.loadSave.LoadSave;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

@Data
@Slf4j
public class LaunchController {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    @FXML
    private TextField playerNameTextField;

    @FXML
    private Label errorLabel;

    public void startAction(ActionEvent actionEvent) throws IOException {
        if (playerNameTextField.getText().isEmpty()) {
            errorLabel.setText("Enter your name!");
        } else {
            fxmlLoader.setLocation(getClass().getResource("/fxml/game.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<GameController>getController().setPlayerName(playerNameTextField.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            log.info("The players name is set to {}, loading game scene", playerNameTextField.getText());
        }
    }

    public void loadAction(ActionEvent actionEvent) throws IOException {

        LoadSave loadSave = new LoadSave();
        String gameFile = loadSave.getGameFile();
        String playerFile = loadSave.getPlayerFile();

        if(!new File(gameFile).exists() || !new File(playerFile).exists())
            errorLabel.setText("No Saved File Found.");
        else{
            new PrintStream(loadSave.getLoadFile()).print(true);
            fxmlLoader.setLocation(getClass().getResource("/fxml/game.fxml"));
            Parent root = fxmlLoader.load();
            loadSave.loadData();
            playerNameTextField.setText(loadSave.getPlayerData().getPlayer());
            fxmlLoader.<GameController>getController().setPlayerName(playerNameTextField.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            log.info("The players name is set to {}, loading game scene with previous saved game.", playerNameTextField.getText());
        }
    }
}
