import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Main {
    private static Scanner in =new Scanner(System.in);
    private static long timerStart;
    private static long timerEnd;
    public static void main(String[] args) {
        int numCities;
        char choice;
        Random rand = new Random();
        System.out.println("Select an Algorithm: (D)ijkstra or (A)nt Colony Optimization");
        choice = in.next().charAt(0);
        List<City> cities = new ArrayList<>();
        System.out.println("Enter the number of Cities (0 or negative for default): ");
        numCities = in.nextInt();
        if (numCities <= 0) numCities = 10;
        for (int i = 0; i < numCities; i++) {
            cities.add(new City(rand.nextInt(0, 10000), rand.nextInt(0, 10000)));
        }
        if (choice == 'A' || choice == 'a') {
            int numAnts;
            int numIterations;
            System.out.println("Enter number of ants (0 or negative for default): ");
            numAnts = in.nextInt();
            if (numAnts <= 0) numAnts = 10;
            System.out.println("Enter number of iterations (0 or negative for default): ");
            numIterations = in.nextInt();
            if (numIterations <= 0) numIterations = 100;
            ACODriver aco = new ACODriver(cities, numAnts, numIterations );
            System.out.println("Ant Colony Optimization Results: ");
            List<Long> acoResults = aco.solve();
            List<Long> acoLengths = new ArrayList<>();
            List<Long> acoTimes = new ArrayList<>();
            for (Long acoResult : acoResults) {
                if (acoResults.indexOf(acoResult) % 2 == 0){ //splits results into lengths and times
                    acoTimes.add(acoResult); //times are stored in even indexes of acoResults
                }
                else acoLengths.add(acoResult); //lengths are stored in odd indexes of acoResults
            }
            long acoAvgLength = average(acoLengths);
            long acoAvgTime = average(acoTimes);
            System.out.println(acoLengths);
            System.out.println("Average best length: " +acoAvgLength);
            System.out.println("Average time per iteration (milli seconds): " +acoAvgTime);

        } else if (choice == 'D' || choice == 'd') {
            City start = cities.get(0); //assigns first city to first city in given list
            City end = cities.get(rand.nextInt(1,cities.size())); //assigns end city to random city that is not the start
            int numIterations;
            System.out.println("Enter number of iterations (0 or negative for default): ");
            numIterations = in.nextInt();
            if (numIterations <= 0) numIterations = 10; // 10 iterations by default
            List<Long> results = new ArrayList<>();
            List<Long> times = new ArrayList<>();
            System.out.println("Dijkstra Algorithm Results: ");
            for (int i = 0; i < numIterations; i++) {
                timerStart = System.currentTimeMillis(); //start timer dijkstra's
                DijkstraResult dijkstraResults = DijkstraDriver.result(cities,start,end); //results are stored with path, distance table, and total distance
                timerEnd = System.currentTimeMillis(); //end timer dijkstra's
                double length = dijkstraResults.getTotalDistance();
                System.out.println("[Dijkstra Algorithm] iteration: "+i + " Shortest path length: " +length);
                results.add((long) length);
                times.add( timerEnd - timerStart );
            }
            long avgDijkstraResults = average(results);
            long avgDijkstraTime = average(times);

            System.out.println("Average length: " +avgDijkstraResults );
            System.out.println("Average time (milli seconds): " +avgDijkstraTime);
            // lengths per iteration are stored in results
            // times per iteration are stored in times
        }
        else System.out.println("[ERROR] Please choose either 'A' or 'D': "); choice =in.next().charAt(0);
    }

    /**
     * computes average from list of values
     * @param values : list of long values being averaged
     * @return : long value of computed average
     */
    private static long average(List<Long> values){
        long toReturn= 0;
        for (int i = 0; i < values.size(); i++) {
            toReturn += values.get(i);
        }
        return toReturn/values.size();
    }
}
