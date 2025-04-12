import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *driver for ant colony optimization
 */
public class ACODriver {
    private int numAnts;
    private int numIterations;
    private int alpha = 1; //
    private int beta = 5;
    private double evaporation = 0.5;
    private double Q = 100;
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

            for (int j = 0; j < numAnts; j++) {
                Ant ant = new Ant(cities);
                City start = cities.get(rand.nextInt(cities.size()));
                ant.visitCity(start);

            }
        }
    }
}

