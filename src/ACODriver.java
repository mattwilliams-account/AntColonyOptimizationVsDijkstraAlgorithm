import java.util.*;

/**
 *driver for ant colony optimization
 */
public class ACODriver {
    private int numAnts;
    private int numIterations;
    private int alpha = 1; //influence pheromones contributes toward ant behavior (larger = more exploitation for smallest global path)
    private int beta = 5; //influence distance contributes toward ant behavior (larger = more exploitation for closest city)
    private double evaporation = 0.5;
    private double Q = 100; //pheromone contribution amount
    private List<City> cities;
    private double[][] pheromones;

    /**
     * constructor for ACO driver
     * @param cities : list of cities in topography
     * @param numAnts : number of ants
     * @param numIterations : number of iterations
     */
    public ACODriver(List<City> cities, int numAnts, int numIterations) {
        this.cities = cities;
        this.numAnts = numAnts;
        this.numIterations = numIterations;
        pheromones = new double[cities.size()][cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            Arrays.fill(pheromones[i],1.0); //initializes every path between cities with the same pheromone level of 1.0
        }
    }

    public void solve() {
        Random rand = new Random();
        Ant best = null;

        for (int i = 0; i < numIterations; i++) {
            List<Ant> ants = new ArrayList<>();

            for (int j = 0; j < numAnts; j++) { //iterates for every ant
                Ant ant = new Ant(cities);
                City start = cities.get(rand.nextInt(cities.size())); // sets the starting city to a random city within list of cities
                ant.visitCity(start);

                while (!ant.notInfected.isEmpty()){ //iterates while there are still cities not infected
                    City next = selectNextCity(ant);
                    ant.visitCity(next);
                }
                ant.completePath(); //finalizes length of path travelled
                if (best == null || ant.length < best.length) { //keeps track of top performer
                    best = ant;
                }
                ants.add(ant);
            }
            evaporatePheromones();
            for (Ant ant : ants) {
                depostiPheromones(ant);
            }

            System.out.println("Iteration " + i + ": Best Length = " + best.length);
        }
    }

    private City selectNextCity(Ant ant) {
        City current = ant.path.get(ant.path.size() - 1); // finds location of ant
        double sum = 0;
        Map<City, Double> probabilities = new HashMap<>();

        for (City city : ant.notInfected) {
            double pheromone = pheromones[cities.indexOf(current)][cities.indexOf(city)];
            double distance = current.distanceTo(city);
            double value = Math.pow(pheromone, alpha) * Math.pow(1.0 / distance, beta);
            probabilities.put(city, value);
            sum += value;
        }

        double pick = Math.random() * sum;
        double total = 0;
        for (Map.Entry<City, Double> entry : probabilities.entrySet()) {
            total += entry.getValue();
            if (total >= pick) return entry.getKey();
        }
        return probabilities.keySet().iterator().next();
    }

    private void evaporatePheromones() {
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                pheromones[i][j] *= (1-evaporation);
            }
        }
    }

    private void depostiPheromones(Ant ant) {
        double contribution = Q / ant.length;
        for (int i = 0; i < ant.path.size() - 1; i++) {
            int from = cities.indexOf(ant.path.get(i));
            int to = cities.indexOf(ant.path.get(i + 1));
            pheromones[from][to] += contribution;
            pheromones[to][from] += contribution;
        }
    }
}

