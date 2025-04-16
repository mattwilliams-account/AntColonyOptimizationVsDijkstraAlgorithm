import java.io.*;
import java.util.*;

public class TestData {
    public static void main(String[] args) throws IOException {
        final int numAnts = Integer.parseInt(args[0]);
        final int numIterations = Integer.parseInt(args[1]);
        boolean dijkstra = false;
        if (args.length == 3) {
            dijkstra = args[2] != null && args[2].equals("-d");
        }
        Random rand = new Random();

        // Ant Colony Optimization

        if (!dijkstra) {

            BufferedWriter acoOutput = new BufferedWriter(new FileWriter("ACOOutput.csv"));
            acoOutput.write("NumCities,NumAnts,NumIterations,TimeMs\r\n");
            acoOutput.flush();

            BufferedReader acoInput = new BufferedReader(new FileReader("Cities.csv"));

            String line;
            while ((line = acoInput.readLine()) != null) {
                List<City> cities = new ArrayList<>();
                for (int j = 0; j < 10000; j++) {
                    cities.add(new City(rand.nextInt(0, 10000), rand.nextInt(0, 10000)));
                }
                ACODriver driver = new ACODriver(cities, numAnts, numIterations);
                List<Long> results = driver.solve();

                acoOutput.append(String.valueOf(1)).append(",");

                Long bestAnt = Long.MAX_VALUE;
                int bestAntIndex = -1;
                Long bestAntTime = null;

                for (int j = 0; j < results.size(); j++) {
                    Long getJ = results.get(j);
                    if (j % 2 == 1) {
                        if (getJ < bestAnt) {
                            bestAnt = getJ;
                            bestAntIndex = (j - 1) / 2;
                        }
                    } else {
                        bestAntTime = getJ;
                    }
                }

                acoOutput.append(String.valueOf(bestAntIndex)).append(",");
                acoOutput.append(String.valueOf(bestAnt)).append(",");
                acoOutput.append(String.valueOf(bestAntTime)).append("");
                acoOutput.append("\r\n");

            }
            acoOutput.close();
        } else {

            // Dijkstra's Algorithm

            BufferedWriter dijOutput = new BufferedWriter(new FileWriter("DijOutput.csv"));
            dijOutput.write("NumCities,TotalDistance,TimeMs\r\n");
            dijOutput.flush();

            for (int i = 2; i < 10000 + 1; i++) {
                List<City> cities = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    cities.add(new City(rand.nextInt(0, 10000), rand.nextInt(0, 10000)));
                }

                City start = cities.getFirst();
                City end = cities.get(rand.nextInt(1, cities.size()));

                long startTime;
                long endTime;

                double totalDistance = -1;
                long timeMs = -1;

                dijOutput.append(String.valueOf(i)).append(",");

                for (int j = 0; j < numIterations; j++) {
                    startTime = System.currentTimeMillis();
                    DijkstraResult dijkstraResults = DijkstraDriver.result(cities, start, end);
                    endTime = System.currentTimeMillis();
                    totalDistance = Math.round(dijkstraResults.getTotalDistance());
                    timeMs = endTime - startTime;
                }

                dijOutput.append(Double.toString(totalDistance)).append(",");
                dijOutput.append(Long.toString(timeMs)).append("\r\n");
            }
            dijOutput.close();
        }
    }
}
