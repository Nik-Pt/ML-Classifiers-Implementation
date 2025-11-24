package Classifier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class MLP {

    private Data[] trainingSet;
    private Data[] testSet;

    private Layer[] H1Neurons;
    private Layer[] H2Neurons;
    private Layer[] H3Neurons;
    private Layer[] outputLayerNeurons;

    public static final int d = 2;
    public static final int K = 4;

    public static int H1;
    public static int H2;
    public static int H3;

    public static double learningRate;
    public static String H1Function;
    public static String H2Function;
    public static String H3Function;
    public static String outputFunction;

    public static int batchSize;

    private double squaredErrorOfEpoch;
    private double errorOfLastEpoch;

    //==================== Purely for network testing ===================//
    private int correct;
    private int incorrect;
    private ArrayList<Data> correctList = new ArrayList<Data>();
    private ArrayList<Data> incorrectList = new ArrayList<Data>();
    private int C1Examples;
    private int C2Examples;
    private int C3Examples;
    private int C4Examples;
    //===================================================================//

    public MLP(String[] args){
        H1 = Integer.parseInt(args[0]);
        H2 = Integer.parseInt(args[1]);
        H3 = Integer.parseInt(args[2]);
        learningRate = Double.parseDouble(args[3]);
        H1Function = args[4];
        H2Function = args[5];
        H3Function = args[6];
        outputFunction = args[7];
        batchSize = Integer.parseInt(args[8]);
        trainingSet = new Data[4000];
        testSet = new Data[4000];
        this.squaredErrorOfEpoch = 0;
        this.errorOfLastEpoch = 0;

        builtTheNetwork();
    }

    public void builtTheNetwork(){
        H1Neurons = setupLayer(H1, d, 1, H1Function);
        H2Neurons = setupLayer(H2, H1, 2, H2Function);
        H3Neurons = setupLayer(H3, H2, 3, H3Function);
        outputLayerNeurons = setupLayer(K, H3, 4, outputFunction);
    }

    private Layer[] setupLayer(int neurons, int inputs, int level, String functionType){

        Layer[] layer = new Layer[neurons];
        for (int i = 0; i < neurons; i++) {
            layer[i] = new Layer(inputs, 1, functionType);
        }
        System.out.println("Layer level " + level + ": " + neurons + " neurons, " + inputs + " inputs.");

        return layer;
    }

    public void loadData(String trainingFile, String testFile){
        trainingSet = loadFile(trainingFile, true);
        testSet = loadFile(testFile, false);
    }

    public Data[] loadFile(String filename, boolean isTraining){
        Data[] dataSet = new Data[4000];

        Scanner inputReader = null;
        try {
            inputReader = new Scanner(new FileInputStream(filename));
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(0);
        }

        int i = 0;
        while (inputReader.hasNextLine()){
            String line = inputReader.nextLine();
            String[] values = line.split(",");
            dataSet[i] = new Data(Double.parseDouble(values[0]), Double.parseDouble(values[1]), values[2]);
            i++;
        }
        System.out.println((isTraining ? "Training" : "Test") + " set loaded successfully: " + dataSet.length + " records.");

        return dataSet;
    }

    public double[] forwardPass(double[] input){
        double[] currentOutput = input;
        double[] nextOutput;

        for (Layer[] layer : new Layer[][]{H1Neurons, H2Neurons, H3Neurons, outputLayerNeurons}){
            nextOutput = new double[layer.length];
            for (int i = 0; i < layer.length; i++) {
                layer[i].setInput(currentOutput);
                nextOutput[i] = layer[i].getOutput();
                /*System.err.println(layer.length);
                System.err.println(layer[i]);
                System.err.println("Layer output: " + Arrays.toString(nextOutput)); // Debugging output*/
            }
            //System.exit(0);
            currentOutput = nextOutput;
        }
        return currentOutput;
    }
	

    public void backPropagation(Data data){

        double[] input = data.toVectorNoBias();
        double[] output = forwardPass(input);
        double[] difference = calculateDifferenceWithExpected(output, data.getC());
        this.squaredErrorOfEpoch += calculateSquaredErrorOfExample(output, data.getC());

        for (int i = 0; i < K; i++){
            outputLayerNeurons[i].setError(difference[i] * outputLayerNeurons[i].activationDerivative(outputLayerNeurons[i].dotProduct()));
            outputLayerNeurons[i].calculateDerivativeOfWeights();
        }

        hiddenLayerBackPropHelper(outputLayerNeurons, H3Neurons, K, H3);
        hiddenLayerBackPropHelper(H3Neurons, H2Neurons, H3, H2);
        hiddenLayerBackPropHelper(H2Neurons, H1Neurons, H2, H1);
    }

    private void hiddenLayerBackPropHelper(Layer[] currentLayer, Layer[] nextLayer, int currentLayerNeurons, int nextLayerNeurons){
        for (int i = 0; i < nextLayerNeurons; i++) {
            double dotProductWeightsError = 0;
            for (int j = 0; j < currentLayerNeurons; j++) {
                double weight_ji = currentLayer[j].getWeight(i);
                double error_j = currentLayer[j].getError();
                dotProductWeightsError += weight_ji * error_j;
            }
            nextLayer[i].setError(dotProductWeightsError * nextLayer[i].activationDerivative(nextLayer[i].dotProduct()));
            nextLayer[i].calculateDerivativeOfWeights();
        }
    }

    public double[] calculateDifferenceWithExpected(double[] output, String category){
        double[] expectedOut = new double[K];
        double[] difference = new double[K];

        switch(category){
            case "C1":
                expectedOut = new double[] {0,0,0,1};
                break;
            case "C2":
                expectedOut = new double[] {0,0,1,0};
                break;
            case "C3":
                expectedOut = new double[] {0,1,0,0};
                break;
            case "C4":
                expectedOut = new double[] {1,0,0,0};
                break;
        }
        for(int i = 0; i < K; i++){
            difference[i] = output[i] - expectedOut[i];
        }
        return difference;
    }


    public double calculateSquaredErrorOfExample(double[] output, String category){
        double[] expectedOut = new double[K];

        switch(category){
            case "C1":
                expectedOut = new double[] {0,0,0,1};
                break;
            case "C2":
                expectedOut = new double[] {0,0,1,0};
                break;
            case "C3":
                expectedOut = new double[] {0,1,0,0};
                break;
            case "C4":
                expectedOut = new double[] {1,0,0,0};
                break;
        }
        //System.err.println("output: " + Arrays.toString(output)); // Debugging output*/
        //System.err.println("Category and expected: " + Arrays.toString(output) + "||||" + Arrays.toString(expectedOut)); // Debugging output*/
        double sum = 0;
        for(int i = 0; i < K; i++){
            sum += Math.pow(output[i] - expectedOut[i], 2);
        }
        double error = 0.5 * sum;

        //System.err.println("Error = " + error); // Debugging output*/
        return error;
    }

    public void updateWeights(){
        for(int i = 0; i < H1; i++){
            H1Neurons[i].updateWeights(learningRate);
        }
        for(int i = 0; i < H2; i++){
            H2Neurons[i].updateWeights(learningRate);
        }
        for (int i = 0; i < H3; i++){
            H3Neurons[i].updateWeights(learningRate);
        }
        for (int i = 0; i < K; i++){
            outputLayerNeurons[i].updateWeights(learningRate);
        }
    }

    public double calculateErrorBetweenEpochs(){
        double error = this.errorOfLastEpoch - this.squaredErrorOfEpoch;
        this.errorOfLastEpoch = this.squaredErrorOfEpoch;

        return error;
    }

    public void gradientDescentTraining(){
        int epoch = 0;
        while((epoch < 800 || (Math.abs(calculateErrorBetweenEpochs()) > 0.01)) && epoch < 300000){
            System.err.println("# of Epoch: " + epoch);

            this.squaredErrorOfEpoch = 0;
            int numberOfBatches = trainingSet.length / batchSize;
            for(int batch = 0; batch < numberOfBatches; batch++){
                clearAllDerivatives();
                for (int i = batch * batchSize; i < (batch + 1) * batchSize; i++){
                    backPropagation(trainingSet[i]);
                }
            }
            updateWeights();
            epoch++;
        }
    }

    public void clearAllDerivatives() {
		for(int i = 0; i < H1; i++) {
			H1Neurons[i].clearDerivatives();
		}
		for(int i = 0; i < H2; i++) {
			H2Neurons[i].clearDerivatives();
		}
		for(int i = 0; i < H3; i++) {
			H3Neurons[i].clearDerivatives();
		}
		for(int i = 0; i < K; i++) {
			outputLayerNeurons[i].clearDerivatives();
		}
	}

    public void networkTest(){
        for (int i = 0; i < testSet.length; i++){
            double[] input = testSet[i].toVectorNoBias();
            double[] output = forwardPass(input);

            compareResults(testSet[i], output);
        }
        //Plot p = new Plot(correctList,incorrectList);
		//p.setVisible(true);
    }

    public void compareResults(Data testData, double[] output){
        double max_value = -100000;
        int predictedOutput = 0;

        //System.err.println("Layer output: " + Arrays.toString(output)); // Debugging output

        for(int i = 0; i < K; i++){
            if(output[i] > max_value){
                max_value = output[i];
                predictedOutput= i;
            }
        }

        String predictedCategory = "C" + (predictedOutput + 1);
        
        if((testData.getC()).equals(predictedCategory)){
            switch(testData.getC()){
                case "C1":
                    this.C1Examples++;
                    break;
                case "C2":
                    this.C2Examples++;
                    break;
                case "C3":
                    this.C3Examples++;
                    break;
                case "C4":
                    this.C4Examples++;
                    break;
            }
            this.correct++;
            this.correctList.add(testData);
        }else{
            this.incorrect++;
            this.incorrectList.add(testData);
        }
    }

    public void printStats(){
        System.err.println("Size of the test data: " + testSet.length);
        System.err.println("Number of correct categorizations: " + this.correct);
        System.err.println("Number of wrong categorizations: " + this.incorrect);
        System.err.println("Percentage of correct predictions: " + String.format("%.5g", ((this.correct / (double)testSet.length) * 100)));
        System.err.println("Percentage of wrong predictions: " + String.format("%.5g", (100 - (this.correct / (double)testSet.length) * 100)));

    }


    public static void initiateNetwork(MLP network, String[] args){
        network = new MLP(args);
        network.loadData("Data.txt", "TestData.txt");
        network.gradientDescentTraining();
        network.networkTest();
        network.printStats();

        for(String i: args){
            System.out.print(i + " ");
        }
    }

    public static void main(String[] args){
        DataGenerator generator = new DataGenerator();
        generator.writeInFile();

        MLP network = null;
        MLP.initiateNetwork(network, args);
    }
}
