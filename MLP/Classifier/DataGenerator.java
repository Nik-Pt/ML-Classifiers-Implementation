package Classifier;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;

public class DataGenerator {

    public Data[] trainingSet;
    public Data[] testSet;
    private Random randomGenerator;

    public DataGenerator() {
		trainingSet = new Data[4000];    
		testSet = new Data[4000];
		randomGenerator  = new Random();
		
		createtTrainingSet();
		createTestSet();
	}

    private void createtTrainingSet(){

        double r1;
        double r2;
        int C1_counter = 0;
        int C2_counter = 0;
        int C3_counter = 0;
        int C4_counter = 0;

        for (int i = 0; i < 4000; i++){
            r1 = 2 * randomGenerator.nextDouble() - 1;
            r2 = 2 * randomGenerator.nextDouble() - 1;

            if(inC1(r1, r2)){
                trainingSet[i] = new Data(r1, r2, "C1");
                C1_counter++;
            }
            else if(inC2(r1, r2)){
                trainingSet[i] = new Data(r1, r2, "C2");
                C2_counter++;
            }
            else{
                if ((r1 < 0 && r2 > 0) || (r1 > 0 && r2 < 0)) {
                    trainingSet[i] = new Data(r1, r2, "C3");
                    C3_counter++;
                }
                else if ((r1 > 0 && r2 > 0) || (r1 < 0 && r2 < 0)){
                    trainingSet[i] = new Data(r1, r2, "C4");
                    C4_counter++;
                }
            }
        }

        System.out.println("In C1:" + C1_counter);
	    System.out.println("In C2:" + C2_counter);
		System.out.println("In C3:" + C3_counter);
        System.out.println("In C4:" + C4_counter);
    }

    private void createTestSet(){

        double r1;
        double r2;

        for (int i = 0; i < 4000; i++){
            r1 = 2 * randomGenerator.nextDouble() - 1;
            r2 = 2 * randomGenerator.nextDouble() - 1;

            if(inC1(r1, r2)){
                testSet[i] = new Data(r1, r2, "C1");
            }
            else if(inC2(r1, r2)){
                testSet[i] = new Data(r1, r2, "C2");
            }
            else{
                if ((r1 < 0 && r2 > 0) || (r1 > 0 && r2 < 0)) {
                    testSet[i] = new Data(r1, r2, "C3");
                }
                else if ((r1 > 0 && r2 > 0) || (r1 < 0 && r2 < 0)){
                    testSet[i] = new Data(r1, r2, "C4");
                }
            }

        }
    }

    private boolean inC1(double x1, double x2) {
		if((Math.pow((x1-0.5),2) + Math.pow((x2-0.5),2) < 0.2) && x2 > 0.5) {
			return true;
		}
		if((Math.pow((x1+0.5),2) + Math.pow((x2+0.5),2) < 0.2) && x2 > -0.5) {
			return true;
		}
		if((Math.pow((x1-0.5),2) + Math.pow((x2+0.5),2) < 0.2) && x2 > -0.5) {
			return true;
		}
		if((Math.pow((x1+0.5),2) + Math.pow((x2-0.5),2) < 0.2) && x2 > 0.5) {
			return true;
		}
		return false;
	}

	private boolean inC2(double x1, double x2) {
		if((Math.pow((x1-0.5),2) + Math.pow((x2-0.5),2) < 0.2) && x2 < 0.5) {
			return true;
		}
		if((Math.pow((x1+0.5),2) + Math.pow((x2+0.5),2) < 0.2) && x2 < -0.5) {
			return true;
		}
		if((Math.pow((x1-0.5),2) + Math.pow((x2+0.5),2) < 0.2) && x2 < -0.5) {
			return true;
		}
        if((Math.pow((x1+0.5),2) + Math.pow((x2-0.5),2) < 0.2) && x2 < 0.5) {
			return true;
		}
		return false;
	}
    
    public void writeInFile(){

        PrintWriter out = null;
		try{
		    out = new PrintWriter(new FileOutputStream("Data.txt"));
		}catch(FileNotFoundException e){
            System.out.println("Error opening the file Data.txt");
            System.exit(0);
		}

		for(int i = 0; i < 4000; i++) {
			out.println(trainingSet[i].toString());
		}
        out.close();
		
        try{
		    out = new PrintWriter(new FileOutputStream("TestData.txt"));
		}catch(FileNotFoundException e){
            System.out.println("Error opening the file Data.txt");
            System.exit(0);
		}

		for(int i = 0; i < 4000; i++) {
			out.println(testSet[i].toString());
		}
		out.close();
    }

    public static void main(String[] args) {
        DataGenerator gen = new DataGenerator();
        gen.writeInFile();
    }

}
