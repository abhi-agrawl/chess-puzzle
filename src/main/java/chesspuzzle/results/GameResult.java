package chesspuzzle.results;

import lombok.extern.slf4j.Slf4j;
import util.jaxb.JAXBHelper;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class GameResult {

    private PlayersList playersList = new PlayersList();

    private String fileName = "topPlayerList.xml";

    public List<Player> readFromFile(){

        try {
            this.playersList = JAXBHelper.fromXML(PlayersList.class, new FileInputStream(fileName));
        }catch (FileNotFoundException ex){
            log.error("File ({}) Not Found Exception.", fileName);
            this.playersList.setPlayerList(new ArrayList<>());
        }catch (JAXBException ex){
            log.error("JAXB Exception. Error message: \n{}", ex.toString());
        }
        return this.playersList.getPlayerList();
    }

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
