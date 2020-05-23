package chesspuzzle.javafx.controller;

import chesspuzzle.results.GameResult;
import chesspuzzle.state.ChessPuzzleState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class GameController {


    private FXMLLoader fxmlLoader = new FXMLLoader();

    private String playerName;
    private ChessPuzzleState gameState = new ChessPuzzleState();
    private IntegerProperty steps = new SimpleIntegerProperty();
    private Instant startTime;
    private List<Image> chessPieces;

    @FXML
    private Label messageLabel;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Label stepsLabel;

    @FXML
    private Label stopWatchLabel;

    private Timeline stopWatchTimeline;

    @FXML
    private Button resetButton;

    @FXML
    private Button giveUpButton;

    private BooleanProperty gameOver = new SimpleBooleanProperty();

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @FXML
    public void initialize() {
        chessPieces = List.of(
                new Image(getClass().getResource("/images/king.png").toExternalForm()),
                new Image(getClass().getResource("/images/bishop.png").toExternalForm()),
                new Image(getClass().getResource("/images/rook.png").toExternalForm())
        );
        stepsLabel.textProperty().bind(steps.asString());
        gameOver.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                addResult();
                stopWatchTimeline.stop();
            }
        });
        resetGame();
    }

    private void resetGame() {
        gameState = new ChessPuzzleState();
        steps.set(0);
        startTime = Instant.now();
        gameOver.setValue(false);
        displayGameState();
        createStopWatch();
        Platform.runLater(() -> messageLabel.setText("Good luck, " + playerName + "!"));
    }

    private void displayGameState() {
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 3; j++) {

                StackPane stackPane = (StackPane) gameGrid.getChildren().get(i * 3 + j);
                ImageView view = (ImageView) stackPane.getChildren().get(0);

                if(gameState.getCurrentState()[i][j].equals("King"))
                    view.setImage(chessPieces.get(0));

                if(gameState.getCurrentState()[i][j].contains("Bishop"))
                    view.setImage(chessPieces.get(1));

                if(gameState.getCurrentState()[i][j].contains("Rook"))
                    view.setImage(chessPieces.get(2));

                if(gameState.getCurrentState()[i][j].equals("-"))
                    view.setImage(null);
            }
    }

    public void handleClickOnChessPiece(MouseEvent mouseEvent) {
        int row = GridPane.getRowIndex((Node) mouseEvent.getSource());
        int col = GridPane.getColumnIndex((Node) mouseEvent.getSource());

        if (gameState.canMoveToEmptySpace(row, col)) {
            steps.set(steps.get() + 1);

            gameState.moveToEmptySpace(row, col);

            if (gameState.isGoalState()) {
                gameOver.setValue(true);
                messageLabel.setText("Congratulations, " + playerName + "!");
                resetButton.setDisable(true);
                giveUpButton.setText("Finish");
            }
        }
        displayGameState();
    }

    public void handleResetButton()  {
        stopWatchTimeline.stop();
        resetGame();
    }

    public void handleGiveUpButton(ActionEvent actionEvent) throws IOException {

        gameOver.setValue(true);
        fxmlLoader.setLocation(getClass().getResource("/fxml/highscores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void addResult() {

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);

        if(gameState.isGoalState())
             new GameResult().createGameResult(
                     playerName,
                     steps.get(),
                     DurationFormatUtils.
                             formatDuration(Duration.between(startTime, Instant.now()).toMillis(),"H:mm:ss"),
                     ZonedDateTime.now().format(formatter)
            );
    }

    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatchLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }
}
