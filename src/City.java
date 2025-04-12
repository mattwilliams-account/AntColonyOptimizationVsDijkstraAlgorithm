/**
 * Defines coordinate of city and the distance from city to another
 */
public class City {
    public double x;
    public double y;

    /**
     * constructor for city object
     * @param x : x coordinate of city
     * @param y : y coordinate of city
     */
    public City(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * finds the distance to another city
     * @param c : target city to reach
     * @return : distance between current city and target
     */
    public double distanceTo(City c) {
        double dx = this.x - c.x;
        double dy = this.y - c.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

}
