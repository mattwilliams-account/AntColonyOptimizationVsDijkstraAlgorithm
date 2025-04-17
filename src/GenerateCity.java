import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateCity {
    public static void main(String[] args) throws IOException {
        final int numCities = Integer.parseInt(args[0]);
        final int citySize = Integer.parseInt(args[1]);

        Random rand = new Random();

        BufferedWriter writer = new BufferedWriter(new FileWriter("Cities.csv"));

        for (int i = 0; i < numCities; i++) {
            writer.append(Integer.toString(rand.nextInt(0, citySize))).append(",");
            writer.append(Integer.toString(rand.nextInt(0, citySize))).append("\r\n");
        }
        writer.close();
    }
}
