import java.util.*;

/**
 *driver handles logic for ant colony optimization
 */
public class ACODriver {
    private int numAnts;
    private int numIterations;
    private int alpha = 1; //influence pheromones contributes toward ant behavior (larger = more exploitation for smallest global path)
    private int beta = 5; //influence distance contributes toward ant behavior (larger = more exploitation for closest city)
    private double evaporation = 0.5;
    private double Q = 100; //pheromone contribution scale
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

    /**
     * main logic used by algorithm to determine best path
     */
    public List<Long> solve() {
        Random rand = new Random();
        Ant best = null;
        List<Long> results = new ArrayList<>();
        long startTime;
        long endTime;
        for (int i = 0; i < numIterations; i++) {
            startTime = System.currentTimeMillis();
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
                depositPheromones(ant);
            }
            endTime = System.currentTimeMillis();
            results.add(endTime - startTime); //results store time for each iteration in even indexes
            results.add((long) best.length); // results store length for each iteration in odd indexes
            System.out.println("[ACO] Iteration " + i + ": Best Length = " + best.length);
        }
        return results;
    }

    /**
     * function to select the next city to travel to
     * @param ant : ant that is making the decision
     * @return : city that ant should visit next
     */
    private City selectNextCity(Ant ant) {
        City current = ant.path.get(ant.path.size() - 1); // finds location of ant
        double sum = 0;
        Map<City, Double> probabilities = new HashMap<>();

        for (City city : ant.notInfected) { //iterate to determine likeliness of ant choosing city based on parameters
            double pheromone = pheromones[cities.indexOf(current)][cities.indexOf(city)]; //pheromone at current city
            double distance = current.distanceTo(city); //distance from current city to other
            double value = Math.pow(pheromone, alpha) * Math.pow(1.0 / distance, beta); // probability value giving houses with higher pheromone levels and less distance higher chance of selection
            probabilities.put(city, Double.valueOf(value)); //relates cities with their probability value of being chosen
            sum += value;
        }

        double pick = Math.random() * sum; //random number between 0 and total weight
        double total = 0;
        for (Map.Entry<City, Double> entry : probabilities.entrySet()) { //iterate through every city and sum their probability values
            total += entry.getValue();
            if (total >= pick) return entry.getKey(); 
        }
        return probabilities.keySet().iterator().next(); //if the pick fails returns first city in list
    }

    /**
     * evaporates pheromones by evaporation rate over entire map
     */
    private void evaporatePheromones() {
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                pheromones[i][j] *= (1-evaporation);
            }
        }
    }

    /**
     * determines contribution amount and deposits pheromones at city
     * @param ant : ant making deposit
     */
    private void depositPheromones(Ant ant) {
        double contribution = Q / ant.length; //solutions with shorter paths leave higher amounts of pheromones
        for (int i = 0; i < ant.path.size() - 1; i++) {
            int from = cities.indexOf(ant.path.get(i));
            int to = cities.indexOf(ant.path.get(i + 1));
            pheromones[from][to] += contribution;
            pheromones[to][from] += contribution;
        }
    }
}

