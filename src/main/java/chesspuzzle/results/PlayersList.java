package chesspuzzle.results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Class for creating the list of
 * {@link Player} object.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlRootElement(name = "ranking")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayersList {

    /**
     * List of {@link Player} object.
     */
    @XmlElement(name = "player")
    private List<Player> playerList;

}
