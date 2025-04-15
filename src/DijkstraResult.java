import java.util.List;
import java.util.Map;

/**
 * stores the result from Dijkstra's algorithm
 */
public class DijkstraResult {
    private final List<City> shortestPath;
    private final Map<City, Double> distances;
    private final double totalDistance;

    /**
     * constructor for Dijkstra's results
     * @param shortestPath : the most efficient path calculated
     * @param distances : a map of cities and their distance from current
     * @param totalDistance : the total distance from current to start
     */
    public DijkstraResult(List<City> shortestPath, Map<City, Double> distances, double totalDistance) {
        this.shortestPath = shortestPath;
        this.distances = distances;
        this.totalDistance = totalDistance;
    }

    /**
     * gets most efficient path found
     * @return : list of cities in order of most efficient path found
     */
    public List<City> getPath() {
        return shortestPath;
    }

    /**
     * gets map of cities and their distance
     * @return : map of cities and their distance
     */
    public Map<City, Double> getDistances() {
        return distances;
    }

    /**
     * gets total distance
     * @return : double value of total distance
     */
    public double getTotalDistance() {
        return totalDistance;
    }
}
