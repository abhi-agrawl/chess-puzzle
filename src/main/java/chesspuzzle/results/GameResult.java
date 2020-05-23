package chesspuzzle.results;

import util.jaxb.JAXBHelper;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GameResult {

    private PlayersList playersList = new PlayersList();

    public List<Player> readFromFile(){

        try {
            this.playersList = JAXBHelper.fromXML(PlayersList.class, new FileInputStream("topPlayerList.xml"));
        }catch (FileNotFoundException ex){
            this.playersList.setPlayerList(new ArrayList<>());
        }catch (JAXBException ex){
            ex.printStackTrace();
        }
        return this.playersList.getPlayerList();
    }

    public void createGameResult(String player, int steps, String duration, String created) {

        List<Player> players = readFromFile();

        players.add(new Player(player, steps, duration, created));
        Collections.sort(players);

        this.playersList.setPlayerList(players);

        try {
            JAXBHelper.toXML(this.playersList, new FileOutputStream("topPlayerList.xml"));
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
