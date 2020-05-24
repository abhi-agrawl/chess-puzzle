package chesspuzzle.results;

import lombok.extern.slf4j.Slf4j;
import util.jaxb.JAXBHelper;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for reading and writing the
 * Game Result to a file using {@link JAXBHelper}.
 */
@Slf4j
public class GameResult {

    /**
     * {@link PlayersList} object creating a list to store
     * all player's data.
     */
    private PlayersList playersList = new PlayersList();

    /**
     * Gets the home directory of the user to save data.
     */
    private final String folderCreate = System.getProperty("user.home") + File.separator + ".chess-puzzle";

    /**
     * Appends fileName to {@code folderCreate}.
     */
    private final String fileName = folderCreate + "/topPlayerList.xml";

    /**
     * Reads the data from XML file using {@link JAXBHelper}.
     * If there is no file then .chess-puzzle dir is created
     * and sets the {@code playersList}  to a new Array.
     * @return {@code List} of player's Data of {@link Player} class.
     */
    public List<Player> readFromFile(){

        try {
            this.playersList = JAXBHelper.fromXML(PlayersList.class, new FileInputStream(fileName));
        }catch (FileNotFoundException ex){
            log.error("File ({}) Not Found Exception.", fileName);
            new File(folderCreate).mkdir();
            this.playersList.setPlayerList(new ArrayList<>());
        }catch (JAXBException ex){
            log.error("JAXB Exception. Error message: \n{}", ex.toString());
        }
        return this.playersList.getPlayerList();
    }

    /**
     * Writes the data of the new player to the XML file.
     * @param player name of the player
     * @param steps number of steps made by the player
     * @param duration duration of the game
     * @param created timestamp when the result was saved
     */
    public void createGameResult(String player, int steps, String duration, String created) {

        List<Player> players = readFromFile();

        players.add(new Player(player, steps, duration, created));
        Collections.sort(players);

        this.playersList.setPlayerList(players);

        try {
            JAXBHelper.toXML(this.playersList, new FileOutputStream(fileName));
        } catch (JAXBException | FileNotFoundException e) {
            log.error(e.toString());
        }
    }
}
