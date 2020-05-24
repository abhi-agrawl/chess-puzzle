package chesspuzzle.loadSave;

import chesspuzzle.results.Player;
import chesspuzzle.state.ChessPuzzleState;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import util.jaxb.JAXBHelper;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Class for loading and saving the
 * current game state to a file using
 * {@link JAXBHelper}.
 */
@Slf4j
@Data
public class LoadSave {

    /**
     * Represents {@link Player} class object.
     */
    private Player playerData;

    /**
     * Represents {@link ChessPuzzleState} class object.
     */
    private ChessPuzzleState chessPuzzleState;

    /**
     * Gets the home directory of the user to save data.
     */
    private final String folderLoc = System.getProperty("user.home") + File.separator + ".chess-puzzle/savedGame";

    /**
     * Stores location to save player's data.
     */
    private final String playerFile = folderLoc + "/playerData.xml";

    /**
     * Stores location to save current game state.
     */
    private final String gameFile = folderLoc + "/gameData.xml";

    /**
     * Stores location to save load file which saves {@code boolean}
     * value.
     */
    private final String loadFile = folderLoc + "/load.txt";

    /**
     * Reads the data from XML file using {@link JAXBHelper}.
     */
    public void loadData(){

        try {
            this.playerData = JAXBHelper.fromXML(Player.class, new FileInputStream(playerFile));
            this.chessPuzzleState = JAXBHelper.fromXML(ChessPuzzleState.class, new FileInputStream(gameFile));
        } catch (FileNotFoundException | JAXBException e) {
            log.error(e.toString());
        }
    }

    /**
     * Writes the data to the XML using {@link JAXBHelper}.
     * @param player represents {@link Player} class object.
     * @param chessPuzzleState represents {@link ChessPuzzleState} class object.
     */
    public void saveData(Player player, ChessPuzzleState chessPuzzleState){

            if(!new File(folderLoc).exists()) {
                new File(folderLoc).mkdirs();
                log.debug("Creating file dir {}", folderLoc);
            }

            try{
                JAXBHelper.toXML(player, new FileOutputStream(playerFile));
                JAXBHelper.toXML(chessPuzzleState, new FileOutputStream(gameFile));
            } catch (FileNotFoundException | JAXBException e) {
                log.error(e.toString());
            }
    }
}
