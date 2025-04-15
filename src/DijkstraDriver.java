import java.util.*;

/**
 * handles logic for Dijkstra's algorithm
 */
public class DijkstraDriver {

    public static DijkstraResult result(List<City> cities, City start, City end) {
        Map <City, Double> distances = new HashMap<>();
        Map<City, City> previous = new HashMap<>();
        Set<City> infected = new HashSet<>();
        PriorityQueue<CityDistance> pq = new PriorityQueue<>(Comparator.comparing(cd -> cd.distance)); //add cities with the smallest distance to front of queue

        for (City city : cities) {
            distances.put(city, Double.POSITIVE_INFINITY); //initialize distance to other cities as infinity
        }
        distances.put(start, 0.0);
        pq.add(new CityDistance(start, 0));

        while (!pq.isEmpty()) {
            CityDistance current = pq.poll();
            City currentCity = current.city;
            if (infected.contains(current.city)) continue; //ignore iteration if city is infected
            infected.add(current.city); // infect city if not infected
            if (currentCity.equals(end)) break; //terminate if current city is the end
            for (City target: cities){
                if (target.equals(currentCity) || infected.contains(target)) continue; //skip iteration if target is current or infected
                double TotDistance = distances.get(currentCity) + currentCity.distanceTo(target);
                if (TotDistance < distances.get(target)) { // if path is shorter update
                    distances.put(target, TotDistance);
                    previous.put(target, currentCity);
                    pq.add(new CityDistance(target, TotDistance));
                }
            }
        }
        List<City> shortestPath = new ArrayList<>();
        if (!previous.containsKey(end) && !end.equals(start)) {
            return new DijkstraResult(shortestPath, distances, Double.POSITIVE_INFINITY); //returns infinite distance if unreachable
        }
        if (end.equals(start)) {
            shortestPath.add(start);
            return new DijkstraResult(shortestPath, distances, 0.0); //returns 0 distance if end is start
        }
        for (City current = end; current != null; current = previous.get(current)) {
            shortestPath.add(current);
            if (current.equals(start)) break; //terminate when start is reached
        }
        Collections.reverse(shortestPath); //puts list in start to finish order
        return new DijkstraResult(shortestPath, distances, distances.get(end));
    }

}

