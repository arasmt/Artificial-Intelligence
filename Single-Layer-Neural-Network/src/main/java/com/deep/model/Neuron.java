package com.deep.model;


public class Neuron {
	
	private int[] inputs;
	
	private double[] weights;
	
	private int output;
	
	private double threshold;
	
	private int target;
	
	private int net;
	
	private double learningRate;

	public Neuron(){
		
	}
	
	
	public Integer activate(){
		
		if(inputs.length == weights.length){
			int sum = 0;
			for (int i = 0; i < inputs.length; i++) {
				
				sum += inputs[i] * weights[i];
				
			}
			
			this.net = sum;
			
			if(sum >= threshold){
				this.output = 1;
				return output;
			}
			else{
				this.output = -1;
				return output;
			}
		}
		else{
			System.err.println("Inputs and weights size mismatch");
			return null;
		}
		
	}
	
	public double[] getWeightShiftVector(){
		
		double[] shift = new double[inputs.length];

			for (int i = 0; i < inputs.length; i++) {
				shift[i] = (inputs[i] * target * learningRate);
			}
			
		return shift;
	}
	
	public double[] getDeltaShiftVector(){
		double[] shift = new double[inputs.length];

		for (int i = 0; i < inputs.length; i++) {
			shift[i] = (inputs[i] * (target - output) * learningRate);
		}
		
	return shift;
	}
	
	public boolean isOutputAccurate(){
		
		if(output == target){
			return true;
		}
		return false;
	}
	
	public int[] getInputs() {
		return inputs;
	}


	public void setInputs(int[] inputs) {
		this.inputs = inputs;
	}


	public int getOutput() {
		return output;
	}

	public double[] getWeights() {
		return weights;
	}


	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public double getThreshold() {
		return threshold;
	}


	public void setThreshold(double threshold) {
		this.threshold = threshold;
	}


	public int getTarget() {
		return target;
	}


	public void setTarget(int target) {
		this.target = target;
	}


	public int getNet() {
		return net;
	}


	public double getLearningRate() {
		return learningRate;
	}


	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}
	
	
	
	
}
