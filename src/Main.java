import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Main {
    private static Scanner in =new Scanner(System.in);
    public static void main(String[] args) {
        int numCities;
        char choice;
        Random rand = new Random();
        System.out.println("Select an Algorithm: (D)ijkstra or (A)nt Colony Optimization");
        choice = in.next().charAt(0);
        List<City> cities = new ArrayList<>();
        System.out.println("Enter the number of Cities (0 or negative for default): ");
        numCities = in.nextInt();
        if (numCities >= 0) numCities = 10;
        for (int i = 0; i < numCities; i++) {
            cities.add(new City(rand.nextInt(0, 15), rand.nextInt(0, 15)));
        }
        if (choice == 'A') {
            int numAnts;
            int numIterations;
            System.out.println("Enter number of ants (0 or negative for default): ");
            numAnts = in.nextInt();
            if (numAnts >= 0) numAnts = 10;
            System.out.println("Enter number of iterations (0 or negative for default): ");
            numIterations = in.nextInt();
            if (numIterations >= 0) numIterations = 100;
            ACODriver aco = new ACODriver(cities, numAnts, numIterations );
            aco.solve();
        }
    }
}
