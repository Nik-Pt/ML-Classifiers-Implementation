package Classifier;

import java.util.Random;

public class Layer {

    private int level = 0;
    private String functionType;
    private boolean outputLayer = false;

    private double[] inputs;
    private int inputNumber;
	private double[] weights;
    private double bias;
    private double error;

    private double[] derivativeOfWeights;
    private double derivativeOfBias;

    private Random randomGenerator;

    public Layer(int inputNumber, int level, String functionType){
        this.level = level;
        this.functionType = functionType;
		this.inputNumber = inputNumber;
		this.weights = new double[inputNumber];
		this.bias = 0;
		this.inputs = new double[inputNumber];
		this.derivativeOfWeights = new double[inputNumber];
		this.derivativeOfBias = 0;
		this.error = 0;
		this.randomGenerator = new Random();

		randomizeWeights();
	}

    public Layer(int inputNumber, int level, String functionType, Boolean outputLayer){
        this.level = level;
        this.functionType = functionType;
        this.outputLayer = outputLayer;
		this.inputNumber = inputNumber;
		this.weights = new double[inputNumber];
		this.bias = 0;
		this.inputs= new double[inputNumber];
		this.derivativeOfWeights = new double[inputNumber];
		this.derivativeOfBias = 0;
		this.error = 0;
		this.randomGenerator = new Random();

		randomizeWeights();
	}

    //Randomization in range [-1,1]
	private void randomizeWeights(){
		for (int i = 0; i < inputNumber ; i ++){
			weights[i] = 2 * randomGenerator.nextDouble() - 1;
		}
		bias = 2 * randomGenerator.nextDouble() - 1;
	}

    public void calculateDerivativeOfWeights() {
		for(int j=0; j< inputNumber; j++){
			this.derivativeOfWeights[j] += error * inputs[j];
		}
		calculateDerivativeOfBias();
	}
	
	private void calculateDerivativeOfBias() {
		this.derivativeOfBias += this.error;
	}

    public void updateWeights(double learningRate) {	
		for (int i = 0; i < inputNumber; i++) {
			weights[i] = weights[i] + learningRate * this.derivativeOfWeights[i];
		}
		this.bias += learningRate * this.derivativeOfBias;
	}
	
	public void setInput(double[] input){
		this.inputs = input;
	}

    private double activationFunction(double x) {
		switch (functionType){
			case "relu":
				return Math.max(0, x);
			case "tanh":
				return Math.tanh(x);
			case "sigma":
				return 1/(1 + Math.exp(-x));
			default:
				return x;
		}
	}
	
	public double activationDerivative(double x) {
		double output = activationFunction(x);
		switch (functionType) {
			case "relu":
				return output > 0 ? 1 : 0;
			case "tanh":
				double tanh = Math.tanh(x);
				return 1 - tanh * tanh;
			case "sigma":
				double sigmoid = 1 / (1 + Math.exp(-x));
				return sigmoid * (1 - sigmoid);
			default:
				return 1; 
		}
	}
	
	public double dotProduct(){
		double sum = 0;
		for(int i=0; i< inputNumber; i++){
			sum += weights[i] * inputs[i];
		}
		sum += bias;
		return sum;
	}

	public double getWeight(int i) {
		return weights[i];
	}
	
	public double getOutput(){	
		return activationFunction(dotProduct());
	}
	
	public double getError() {
		return this.error;
	}
	
	public void setError(double error) {	
		this.error = error;
	}
	
	public void clearDerivatives() {
		for ( int i = 0; i < inputNumber; i++) {
			this.derivativeOfWeights[i] = 0;
		}
		this.derivativeOfBias = 0;
	}
}
