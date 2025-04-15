/**
 * Stores the value of the distance one city is from current city
 */
public class CityDistance {
    City city;
    double distance;

    /**
     * constructor for City Distance
     * @param city : the city being looked at
     * @param distance : distance from current
     */
    public CityDistance(City city, double distance) {
        this.city = city;
        this.distance = distance;
    }
}
