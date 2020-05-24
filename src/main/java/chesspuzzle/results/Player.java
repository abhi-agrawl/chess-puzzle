package chesspuzzle.results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class representing the result of a game played by a
 * specific player.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "player")
public class Player implements Comparable<Player>{

    /**
     * The name of the player.
     */
    @XmlElement(name = "name")
    private String player;

    /**
     * The number of steps made by the player.
     */
    private Integer steps;

    /**
     * The duration of the game.
     */
    private String duration;

    /**
     * The timestamp when the result was saved.
     */
    @XmlElement(name = "DateTime")
    private String created;

    /**
     * Overrides the {@code compareTo} function to sort on the
     * basis of {@code steps} and {@code duration} .
     * @param o is a {@link Player} Object to compare with.
     * @return {@code 1} if {@code Player o} took less steps or more time,
     *         {@code 0} if {@code Player o} took same steps and time,
     *         {@code -1} if {@code Player o} took more steps or less time.
     */
    @Override
    public int compareTo(Player o) {
        if(getSteps().equals(o.getSteps()))
            return getDuration().compareTo(o.getDuration());

        return Integer.compare(getSteps(), o.getSteps());
    }
}
