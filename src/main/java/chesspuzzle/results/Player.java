package chesspuzzle.results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@NoArgsConstructor
@AllArgsConstructor
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Player implements Comparable<Player>{

    @XmlElement(name = "name")
    private String player;

    private Integer steps;

    private String duration;

    @XmlElement(name = "DateTime")
    private String created;

    @Override
    public int compareTo(Player o) {
        if(getSteps().equals(o.getSteps()))
            return getDuration().compareTo(o.getDuration());

        return Integer.compare(getSteps(), o.getSteps());
    }
}
