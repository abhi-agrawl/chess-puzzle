package chesspuzzle.results;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Player implements Comparable<Player>{

    @XmlElement(name = "name")
    private String player;

    private Integer steps;

    private String duration;

    @XmlElement(name = "DateTime")
    private String created;

    public Player(String player, int steps, String duration, String created) {
        this.player = player;
        this.steps = steps;
        this.duration = duration;
        this.created = created;
    }

    public Player() {
    }

    @Override
    public int compareTo(Player o) {
        if(getSteps().equals(o.getSteps()))
            return getDuration().compareTo(o.getDuration());

        return Integer.compare(getSteps(), o.getSteps());
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
