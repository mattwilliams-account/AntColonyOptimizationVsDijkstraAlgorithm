import java.util.*;

/**
 * handles logic for Dijkstra's algorithm
 */
public class DijkstraDriver {

    public static Map<City, Double> dijkstra(List<City> cities, City start, City end) {
        Map<City, Double> distances = new HashMap<>();
        Map<City, City> previous = new HashMap<>();
        Set<City> infected = new HashSet<>();
        PriorityQueue<CityDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(cd -> cd.distance)); // adds cities to queue based on their distance putting shortest at the front

        for (City city : cities) {
            distances.put(city, Double.POSITIVE_INFINITY); //stores initial distance value of each city to infinity
        }
        distances.put(start, 0.0);
        pq.add(new CityDistance(start, 0.0));
        while (!pq.isEmpty()) {
            CityDistance current = pq.poll();
            City currentCity = current.city;
            if (infected.contains(currentCity)) continue; //if city is already infected then skip that iteration
            infected.add(currentCity);
            if (currentCity == end) break;
            for (City next : cities) {
                if (next == currentCity || infected.contains(next)) continue; // if the next city being viewed is the current city or already infected skip iteration
                double totDistance = distances.get(currentCity) + currentCity.distanceTo(next) ; //gets total distance between current and next city being targeted
                if (totDistance < distances.get(next)){ // if total distance of current city being targeted is less than previous target add as new shortest path
                    distances.put(next, totDistance);
                    previous.put(next, currentCity);
                    pq.add(new CityDistance(next, totDistance));
                }
            }
        }
        return distances;
    }

    /**
     * Driver handles usability for Dijkstra's algorithm
     * @param cities : topography of cities
     * @param start : starting city
     * @param end : target city trying to be reached
     * @return : list of cities in order of shortest distance from start to finish
     */
    public static List<City> result(List<City> cities, City start, City end){
        Map<City, Double> distances = new HashMap<>();
        Map<City, City> previous = new HashMap<>();
        Set<City> infected = new HashSet<>();
        PriorityQueue<CityDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(d -> d.distance)); //puts city with the shortest distance at front of queue

        for (City city : cities) {
            distances.put(city, Double.POSITIVE_INFINITY); //initial distance of unknown cities to infinity
        }
        distances.put(start, 0.0);
        pq.add(new CityDistance(start, 0.0));

        while (!pq.isEmpty()) {
            CityDistance current = pq.poll();
            City currentCity = current.city;
            if (infected.contains(currentCity)) continue; //ignore if already infected
            infected.add(currentCity); //infect if not infected
            if (currentCity == end) break;
            for (City next : cities) {
                if (next == currentCity || infected.contains(next)) continue; //if target is current or infected skip
                double totDistance = distances.get(currentCity) + currentCity.distanceTo(next);
                if (totDistance < distances.get(next)){
                    distances.put(next, totDistance);
                    previous.put(next, currentCity);
                    pq.add(new CityDistance(next, totDistance));
                }
            }
        }
        List<City> shortestPath = new ArrayList<>();
        for (City current = end; end != null; current = previous.get(current)) { //iterates shortest path from end to beginning
            shortestPath.add(current);
        }
        Collections.reverse(shortestPath);
        return shortestPath;
    }

}

