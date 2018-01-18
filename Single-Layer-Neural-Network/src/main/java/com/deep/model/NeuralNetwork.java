package com.deep.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeuralNetwork {

	private int[][] inputs;
	private int[][] targets;
	
	private double learningRate = 1;
	
	private int epoch = 1;
	
	private String rule;
	
	private List<Neuron> neurons = new ArrayList<Neuron>();
	
	public NeuralNetwork(String rule){
		this.rule = rule;
	}
	
	public void train(){
		
		int sampleCount = inputs.length;
		int inputSize = inputs[0].length;
		int outputSize = targets[0].length;

		for (int k = 0; k < outputSize; k++) { //For every class (every neuron in output layer)
		
			Neuron n = new Neuron();
			double[] weights = new double[inputSize];
			
			System.out.println("Neuron-"+(k+1)+" learning");
			
			for (int i = 0; i < inputSize; i++) {
				weights[i] = 0; // Weights are initialized as 0
			}
			
			n.setWeights(weights);
			n.setThreshold(0.2);
			n.setLearningRate(this.learningRate);
			
			for (int e = 0; e < epoch; e++) { //For every epoch
			
				for (int i = 0; i < sampleCount; i++) { //For every sample
					int[] row = inputs[i];
					
					if(row != null){
						n.setInputs(row);
						n.setTarget(targets[i][k]);
						n.activate();
						
						//Hebb Learning Rule
						if(this.rule.equalsIgnoreCase("hebb")){
							
							double[] weightShiftVector = n.getWeightShiftVector();
							
							//System.out.println("Weight = "+Arrays.toString(weights));
							
							for (int w = 0; w < weightShiftVector.length; w++) {
								
								weights[w] = weights[w] + weightShiftVector[w];
								
							}
						}
						
						//Perceptron Learning Rule
						else if(this.rule.equalsIgnoreCase("perceptron")){
							if(!n.isOutputAccurate()){
								double[] weightShiftVector = n.getWeightShiftVector();
								
								//System.out.println("Weight = "+Arrays.toString(weights));
								
								for (int w = 0; w < weightShiftVector.length; w++) {
									
									weights[w] = weights[w] + weightShiftVector[w];
									
								}
							}
						}
						
						//Delta Learning Rule
						else if(this.rule.equalsIgnoreCase("delta")){
							if(!n.isOutputAccurate()){
								double[] weightShiftVector = n.getDeltaShiftVector();
																
								for (int w = 0; w < weightShiftVector.length; w++) {
									
									weights[w] = weights[w] + weightShiftVector[w];
									
								}
							}
						}
						 
						
						n.activate();
						n.setWeights(weights);
					}
				}
			
			}
			
			neurons.add(n);
			System.out.println("Final weights = "+Arrays.toString(weights));
		}
		
		
		System.out.println("Network trained successfully \n");
	}
	
	public int[] test(int[] input){
		
		int[] nets = new int[neurons.size()];
		
		for (int i=0; i<neurons.size(); i++) {
			Neuron n = neurons.get(i);
			n.setInputs(input);
			n.activate();
			nets[i] = neurons.get(i).getNet();
		}
		
		return nets;
	}

	public int[][] getInputs() {
		return inputs;
	}

	public void setInputs(int[][] inputs) {
		this.inputs = inputs;
	}

	public int[][] getTargets() {
		return targets;
	}

	public void setTargets(int[][] targets) {
		this.targets = targets;
	}

	public String getRule() {
		return rule;
	}

	public List<Neuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}

	public int getEpoch() {
		return epoch;
	}

	public void setEpoch(int epoch) {
		if(this.rule.equalsIgnoreCase("hebb")){
			this.epoch = 1;
		}
		else{			
			this.epoch = epoch;
		}
	}

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

}
