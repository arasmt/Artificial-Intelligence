package com.deep.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import com.deep.model.NeuralNetwork;
import com.deep.util.DatasetAnalyzer;
import com.deep.util.Parameters;

public class NetworkTester {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in).useLocale(Locale.US);

		System.out.println("Select dataset: [Type i for iris dataset] , [Type s for seeds dataset]");
		String datasetDecision = sc.nextLine();

		DatasetAnalyzer analyzer = null;
		if (datasetDecision.equalsIgnoreCase("s")) {
			analyzer = new DatasetAnalyzer("data/seeds_dataset.txt", "\t");
			Parameters.LEARNING_RATE = 0.05;
		} else {
			analyzer = new DatasetAnalyzer("data/iris.data.txt", ",");
			Parameters.LEARNING_RATE = 0.2;
		}

		boolean customization = false;
		System.out.println("Default parameters are:");
		System.out.println("Learning rate = "+Parameters.LEARNING_RATE+"\nEpoch = "+Parameters.EPOCH+"\nActivation function = "+Parameters.ACTIVATION
				+"\nWeight Calculation Procedure = "+Parameters.WEIGHT_CALCULATION_PROCEDURE+"\nMomentum = "+Parameters.MOMENTUM
				+"\nError Threshold = "+Parameters.ERROR_THRESHOLD+"\nDataset Pieces = "+Parameters.DATASET_PIECES);
		System.out.println();
		System.out.println("Do you want to customize parameters? [Type y to customize parameters] , [Type n to use default parameters]");
		
		if(sc.nextLine().equalsIgnoreCase("y")){
			customization = true;
		}
		
		if(customization){			
			askActivation();
			askWeightCalculationProcedure();
			
			System.out.println("Enter learning rate:");
			Parameters.LEARNING_RATE = sc.nextDouble();
		}

		System.out.println("Enter hidden layer count:(Max 2 layer)");
		int hiddenLayers = sc.nextInt();
		if (hiddenLayers > 2) {
			hiddenLayers = 2;
		}

		List<Integer> hiddenLayerNeuronCounts = new ArrayList<Integer>();
		for (int i = 0; i < hiddenLayers; i++) {
			System.out.println("Enter Neuron Count of Hidden Layer-" + (i + 1));
			int count = sc.nextInt();
			hiddenLayerNeuronCounts.add(count);
		}

		if(customization){			
			System.out.println("Enter Epoch count:");
			Parameters.EPOCH = sc.nextInt();
			
			System.out.println("Enter error threshold: Default is 0.00005");
			Parameters.ERROR_THRESHOLD = sc.nextDouble();
		}

		double inputs[][] = analyzer.getInputs();
		double targets[][] = analyzer.getTargets();

		shuffleArray(inputs , targets);
		
		int pieces = Parameters.DATASET_PIECES;
		
		double trainingInputs[][] = new double[((pieces-1) * inputs.length / pieces)][inputs[0].length];
		double trainingTargets[][] = new double[(pieces-1) * targets.length / pieces][targets[0].length];
		
		double testingInputs[][] = new double[inputs.length / pieces][inputs[0].length];
		double testingTargets[][] = new double[targets.length / pieces][targets[0].length];
		
		for (int i = 0; i < inputs.length/pieces; i++) {
			testingInputs[i] = inputs[i];
		}
		for (int i = 0; i < targets.length/pieces; i++) {
			testingTargets[i] = targets[i];
		}
		
		for (int i = 0; i < (pieces-1)*inputs.length/pieces; i++) {
			trainingInputs[i] = inputs[i+inputs.length/pieces];
		}
		for (int i = 0; i < (pieces-1)*targets.length/pieces; i++) {
			trainingTargets[i] = targets[i+targets.length/pieces];
		}
		
		

		NeuralNetwork network = new NeuralNetwork().setInputs(trainingInputs).setTargets(trainingTargets).setHiddenLayers(hiddenLayers)
				.setHiddenLayerNeuronCounts(hiddenLayerNeuronCounts).build();
		
		long t1 = System.currentTimeMillis();
		network.train();
		long t2 = System.currentTimeMillis();

		double sum = 0;
		for (int i = 0; i < testingInputs.length; i++) {
			System.out.println("Input = " + Arrays.toString(testingInputs[i]));
			System.out.println("Target = " + Arrays.toString(testingTargets[i]));
			
			double[] testOutput = network.test(testingInputs[i]);
			System.out.println("Output = " + Arrays.toString(testOutput));
			System.out.println("----------------------------------------------------------------");
			
			sum+= getAccuracy(testingTargets[i], testOutput);
		}

		sum = sum / testingInputs.length;
		System.out.println("Training time = "+(t2-t1)+" milliseconds");
		System.out.println("Final Accuracy Over Test Data = %"+sum*100);
		
	}
	
	private static double getAccuracy(double[] testingTarget , double[] testOutput ) {
		double sum = 0;
		for (int i = 0; i < testOutput.length; i++) {
			 sum += Math.pow(testingTarget[i] - testOutput[i], 2)/2; 
		}
		
		return 1-sum;
	}

	private static void shuffleArray(double[][] inputs, double[][] targets) {
		Random rnd = ThreadLocalRandom.current();
		for (int i = inputs.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);

			double[] a = inputs[index];
			inputs[index] = inputs[i];
			inputs[i] = a;
			
			double[] b = targets[index];
			targets[index] = targets[i];
			targets[i] = b;
			
		}
	}

	private static void askActivation() {
		Scanner sc = new Scanner(System.in).useLocale(Locale.US);

		System.out.println("Enter activation function: [Type s for sigmoid] , [Type t for tanh] , [Type r for relu]");
		String activationDecision = sc.nextLine();
		if (activationDecision.equalsIgnoreCase("t")) {
			Parameters.ACTIVATION = "tanh";
		} else if (activationDecision.equalsIgnoreCase("r")) {
			Parameters.ACTIVATION = "relu";
		} else {
			Parameters.ACTIVATION = "sigmoid";
		}

	}

	private static void askWeightCalculationProcedure() {
		Scanner sc = new Scanner(System.in).useLocale(Locale.US);

		System.out.println(
				"Enter weight calculation procedure: [Type m for momentum] , [Type d for delta-bar-delta] , [Type a for adaptive]");
		String weightProcedureDecision = sc.nextLine();
		if (weightProcedureDecision.equalsIgnoreCase("d")) {
			Parameters.WEIGHT_CALCULATION_PROCEDURE = "deltabardelta";
			askDBDParameters();
		} else if (weightProcedureDecision.equalsIgnoreCase("a")) {
			// Parameters.ACTIVATION = "adaptive";
			Parameters.WEIGHT_CALCULATION_PROCEDURE = "momentum";
		} else {
			Parameters.WEIGHT_CALCULATION_PROCEDURE = "momentum";
			System.out.println("Enter momentum value:");
			Parameters.MOMENTUM = sc.nextDouble();
		}

	}

	private static void askDBDParameters() {
		Scanner sc = new Scanner(System.in).useLocale(Locale.US);

		System.out.println("Delta-Bar-Delta Parameters:");
		System.out.println("Enter K Value");
		Parameters.DELTA_K = sc.nextDouble();

		System.out.println("Enter Gama Value");
		Parameters.DELTA_GAMA = sc.nextDouble();

		System.out.println("Enter Beta Value");
		Parameters.DELTA_BETA = sc.nextDouble();

	}

}
