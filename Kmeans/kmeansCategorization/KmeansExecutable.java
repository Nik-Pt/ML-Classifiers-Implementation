package kmeansCategorization;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class KmeansExecutable {
    public static void main(String[] args) {
        DataGenerator data = new DataGenerator();
        List bestDispersions = new ArrayList<Double>();
        data.writeToFile();

        for (int counter = 4; counter < 14; counter += 2){
            KMeans kmeans = new KMeans(counter);
            kmeans.loadDataFromFile("Data.txt");

            double bestDispersion = Double.MAX_VALUE;
            Data[] finalCenters = new Data[3];

            for (int i = 0; i < 20; i++){
                kmeans.kmeans();
                double dispersion = kmeans.calcTotalDispersion();

                if (bestDispersion > dispersion){
                    bestDispersion = dispersion;
                    finalCenters = kmeans.getCenters();
                }
            }
            bestDispersions.add(bestDispersion);
            System.err.println("Best total dispersion for M = "+ counter + ": " + bestDispersion + "\n");

            PrintWriter out = null;
            try {
                out = new PrintWriter(new FileOutputStream(counter +"Centers.txt"));
            } catch (FileNotFoundException e) {
                System.err.println("Error opening the file");
                System.exit(0);
            }
            for (int i = 0; i < counter; i++) {
                out.println(finalCenters[i].toString());
            }
            out.close();
        }

        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileOutputStream("DispersionResults.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("Error opening the file");
            System.exit(0);
        }
        for (int i = 0; i < bestDispersions.size(); i++) {
            out.println(bestDispersions.get(i));
        }
        out.close();
    }
}
