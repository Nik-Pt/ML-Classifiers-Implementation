package kmeansCategorization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class KMeans{

    private int M;
    private Data[] exampleDataSet;
    private Data[] groupCenters;
    private ArrayList[] groups;

    public KMeans(int M){
        this.M = M;
        exampleDataSet = new Data[1000];
    }

    public void loadDataFromFile(String filename){
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream(filename));
        } catch (FileNotFoundException e) {
            System.out.println("File "+ filename + "was not found");
			System.exit(0);
        }

        int i = 0;
        while(input.hasNextLine()){
            String[] line = input.nextLine().split(",");
            exampleDataSet[i] = new Data(Double.parseDouble(line[0]), Double.parseDouble(line[1]));
            i++;
        }
        input.close();
    }
    
    public void initializeCenters(){
        groups = new ArrayList[M];
        for (int i = 0; i < M; i++){
            groups[i] = new ArrayList<Data>();
        }

        groupCenters = new Data[M];
        Random random = new Random();
        
        for (int i = 0; i < M; i++){
            int randomPoint = random.nextInt(1000);
            groupCenters[i] = new Data(exampleDataSet[randomPoint].getX1(), exampleDataSet[randomPoint].getX2());
        }
    }

    public boolean updateCenters(){
        double x1 = 0;
        double x2 = 0;
        boolean exit_flag = true;
        
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < groups[i].size(); j++){
                x1 += ((Data)groups[i].get(j)).getX1();
                x2 += ((Data)groups[i].get(j)).getX2();
            }

            if (!groups[i].isEmpty()){
                x1 = x1 / groups[i].size();
                x2 = x2 / groups[i].size();

                if (groupCenters[i].getX1() != x1 || groupCenters[i].getX2() != x2){
                    groupCenters[i].setX1(x1);
                    groupCenters[i].setX2(x2);
                    exit_flag = false;
                }
            }  
        }
        return exit_flag;
    }

    public void calcEuclideanDistance(Data data){
        double keep_distance = Double.MAX_VALUE;
        int group_index = 0;
        double distance;

        for (int i = 0; i < M; i++) {
            distance = Math.sqrt(Math.pow(Math.abs(data.getX1() - groupCenters[i].getX1()), 2) + Math.pow((data.getX2() - groupCenters[i].getX2()), 2));
            if (distance < keep_distance) {
                keep_distance = distance;
                group_index = i;
            }
        }

        if (!groups[group_index].contains(data)){
            for (int i = 0; i < M; i++) {
                if (groups[i].contains(data)){
                    groups[i].remove(data);
                }
            }
            groups[group_index].add(data);
        }

    }

    public double calcTotalDispersion(){
        double dispersion = 0;

        for (int i = 0; i < M; i++){
            for (int j = 0; j < groups[i].size(); j++){
                dispersion += Math.pow(Math.abs(((Data)groups[i].get(j)).getX1() - groupCenters[i].getX1()), 2) + Math.pow(Math.abs(((Data)groups[i].get(j)).getX2() - groupCenters[i].getX2()), 2);
            }
        }

        System.err.println("Total dispersion: "+ dispersion);
        return dispersion;
    }

    public void kmeans(){
        boolean flag = true;
        boolean minEuclideanDistanceReached;
        int counter = 0;

        initializeCenters();

        while (flag){
            for (int i = 0; i < exampleDataSet.length; i++){
                calcEuclideanDistance(exampleDataSet[i]);
            }
            minEuclideanDistanceReached = updateCenters();
            
            if (minEuclideanDistanceReached){
                flag = false;
            }
            else{
                if (counter > 1000){
                    flag = false;
                }
                counter++;
            }
        }
    }

    public Data[] getCenters(){
        return groupCenters;
    }
}