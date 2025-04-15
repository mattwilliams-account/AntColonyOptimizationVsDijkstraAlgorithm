import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * defines behavior of ant
 */
public class Ant {
    public List<City> path = new ArrayList<>(); //path that virus follows
    public Set<City> notInfected = new HashSet<>(); //set of cities not infected
    public long length = 0; //distance virus has travelled

    /**
     * constructor for Ant
     * @param cities : list of cities in topography
     */
    public Ant(List<City> cities) {
        path.addAll(cities);
    }

    /**
     * visits a city
     * @param c : target city to visit
     */
    public void visitCity(City c) {
        if (!path.isEmpty()) { //if not a new path
            length += path.get(path.size() - 1).distanceTo(c);
        }
        path.add(c); //add city to path
        notInfected.remove(c); //remove city from uninfected list
    }

    /**
     * completes the path finalizing length
     */
    public void completePath() {
        length += path.get(path.size() - 1).distanceTo(path.get(0));
    }
}
