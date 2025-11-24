package kmeansCategorization;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;

public class DataGenerator{

    private Data[] dataSet1;
    private Data[] dataSet2;
    private Data[] dataSet3;
    private Data[] dataSet4;
    private Data[] dataSet5;
    private Data[] dataSet6;
    private Data[] dataSet7;
    private Data[] dataSet8;
    private Data[] dataSet9;

    private Random random_generator;

    public DataGenerator(){
        random_generator = new Random();

        dataSet1 = new Data[100];
        dataSet2 = new Data[100];
        dataSet3 = new Data[100];
        dataSet4 = new Data[100];
        dataSet5 = new Data[100];
        dataSet6 = new Data[100];
        dataSet7 = new Data[100];
        dataSet8 = new Data[100];
        dataSet9 = new Data[200];
        createDataSets();
    }

    private void createDataSets(){
        double r1, r2;
        for (int i = 0; i < 100; i++) {
            r1 = random_generator.nextDouble();
            double x1 = -2*r1 + (-1.6)*(1-r1);

            r2 = random_generator.nextDouble();
            double x2 = (1.6)*r2 + 2*(1-r2);

            dataSet1[i] = new Data(x1, x2);
        }
        for (int i = 0; i < 100; i++) {
            r1 = random_generator.nextDouble();
            double x1 = (-1.2)*r1 + (-0.8)*(1-r1);

            r2 = random_generator.nextDouble();
            double x2 = (1.6)*r2 + 2*(1-r2);

            dataSet2[i] = new Data(x1, x2);
        }
        for (int i = 0; i < 100; i++) {
            r1 = random_generator.nextDouble();
            double x1 = (-0.4)*r1 + 0*(1-r1);

            r2 = random_generator.nextDouble();
            double x2 = (1.6)*r2 + 2*(1-r2);

            dataSet3[i] = new Data(x1, x2);
        }
        for (int i = 0; i < 100; i++) {
            r1 = random_generator.nextDouble();
            double x1 = (-1.8)*r1 + (-1.4)*(1-r1);

            r2 = random_generator.nextDouble();
            double x2 = 0.8*r2 + 1.2*(1-r2);

            dataSet4[i] = new Data(x1, x2);
        }
        for (int i = 0; i < 100; i++) {
            r1 = random_generator.nextDouble();
            double x1 = (-0.6)*r1 + (-0.2)*(1-r1);

            r2 = random_generator.nextDouble();
            double x2 = 0.8*r2 + 1.2*(1-r2);

            dataSet5[i] = new Data(x1, x2);
        }
        for (int i = 0; i < 100; i++) {
            r1 = random_generator.nextDouble();
            double x1 = (-2)*r1 + (-1.6)*(1-r1);

            r2 = random_generator.nextDouble();
            double x2 = 0*r2 + 0.4*(1-r2);

            dataSet6[i] = new Data(x1, x2);
        }
        for (int i = 0; i < 100; i++) {
            r1 = random_generator.nextDouble();
            double x1 = (-1.2)*r1 + (-0.8)*(1-r1);

            r2 = random_generator.nextDouble();
            double x2 = 0*r2 + 0.4*(1-r2);

            dataSet7[i] = new Data(x1, x2);
        }
        for (int i = 0; i < 100; i++) {
            r1 = random_generator.nextDouble();
            double x1 = (-0.4)*r1 + 0*(1-r1);

            r2 = random_generator.nextDouble();
            double x2 = 0*r2 + 0.4*(1-r2);

            dataSet8[i] = new Data(x1, x2);
        }
        for (int i = 0; i < 200; i++) {
            r1 = random_generator.nextDouble();
            double x1 = (-2)*r1 + 0*(1-r1);

            r2 = random_generator.nextDouble();
            double x2 = 0*r2 + 2*(1-r2);

            dataSet9[i] = new Data(x1, x2);
        }
    }

    public void writeToFile(){
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileOutputStream("Data.txt"));
        } catch (FileNotFoundException e) {
            System.err.println("Error opening the file");
            System.exit(0);
        }

        for (int i = 0; i < 100; i++) {
            out.println(dataSet1[i].toString());
        }
        for (int i = 0; i < 100; i++) {
            out.println(dataSet2[i].toString());
        }
        for (int i = 0; i < 100; i++) {
            out.println(dataSet3[i].toString());
        }
        for (int i = 0; i < 100; i++) {
            out.println(dataSet4[i].toString());
        }
        for (int i = 0; i < 100; i++) {
            out.println(dataSet5[i].toString());
        }
        for (int i = 0; i < 100; i++) {
            out.println(dataSet6[i].toString());
        }
        for (int i = 0; i < 100; i++) {
            out.println(dataSet7[i].toString());
        }
        for (int i = 0; i < 100; i++) {
            out.println(dataSet8[i].toString());
        }
        for (int i = 0; i < 200; i++) {
            out.println(dataSet9[i].toString());
        }
        out.close();
    }

/*    public static void main(String[] args) {
        DataGenerator dataGenerator = new DataGenerator();
        dataGenerator.createDataSets();
        dataGenerator.writeToFile();
    }*/

}