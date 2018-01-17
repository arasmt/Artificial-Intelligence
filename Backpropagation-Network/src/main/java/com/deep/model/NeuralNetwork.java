package com.deep.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.deep.util.Parameters;

public class NeuralNetwork {

	private int inputNeuronCount;
	private int outputNeuronCount;
	
	private double[][] inputs;
	private double[][] targets;
	
	private int hiddenLayers;
	private List<Integer> hiddenLayerNeuronCounts = new ArrayList<Integer>();
	
	private String activationFunction;
	private String weightCalculationProcedure;
	
	private double learningRate;
	private int epoch;
	
	private List<Layer> layers = new ArrayList<Layer>();
	
	private double lowestError = 9999;
	
	public NeuralNetwork(){
		
	}
	
	public NeuralNetwork(double[][] inputs, double[][] targets){
		
		this.inputs = inputs;
		this.targets = targets;
		
	}
	
	public NeuralNetwork build(){
		
		inputNeuronCount = inputs[0].length;
		outputNeuronCount = targets[0].length;
		
		initializeLayers();
		
		return this;
	}
	
	public void train(){
		
		double preError = 99999;
		String mark = "";
		
		for(int i=0; i<Parameters.EPOCH; i++){
			double error = backpropagate();
			
			if(preError < error) {
				mark = "*************************";
			}
			else {
				mark = "";
			}
			
			if(i%1000 == 0) {preError = error;}
			System.out.println("Epoch-"+(i+1)+" error = "+error+"  "+mark);
			//System.out.printf("Epoch-"+i+" error = %.12f", error);
			
			
			if(error < Parameters.ERROR_THRESHOLD) {
				break;
			}
		}
		
	}
	
	public double[] test(double[] input) {
		
		return passForward(input);
	}
	
	private void markBestWeights() {
		for (Layer layer : layers) {
			List<Neuron> neurons = layer.getNeurons();
			for (Neuron neuron : neurons) {
				List<Axon> axons = neuron.getForwardAxons();
				for (Axon axon : axons) {
					axon.markBest();
				}
			}
		}
	}
	
	private void transformToBestWeights(){
		for (Layer layer : layers) {
			List<Neuron> neurons = layer.getNeurons();
			for (Neuron neuron : neurons) {
				List<Axon> axons = neuron.getForwardAxons();
				for (Axon axon : axons) {
					axon.transformToBest();
				}
			}
		}
	}
	
	private void rollbackAll() {
		for (Layer layer : layers) {
			List<Neuron> neurons = layer.getNeurons();
			for (Neuron neuron : neurons) {
				List<Axon> axons = neuron.getForwardAxons();
				for (Axon axon : axons) {
					axon.rollback();
				}
			}
		}
	}
	
	private double backpropagate(){
		double sum = 0;
		for (int i = 0; i < inputs.length; i++) {
			passForward(inputs[i]);
			sum+= calculateError(targets[i]);
			passBackward(targets[i]);
			updateWeights();
		}
		return sum / inputs.length;
	}
	
	private double[] passForward(double[] input){
		
		List<Neuron> inputNeurons = getInputNeurons();
		
		for(int i=0; i<input.length; i++){
			inputNeurons.get(i).setOut(input[i]);
		}
		
		for (Layer layer : layers) {
			List<Neuron> neurons = layer.getNeurons();
			for (Neuron neuron : neurons) {
				neuron.feedForward();
			}
			
		}
		
		List<Neuron> outputNeurons = getOutputNeurons();
		double output[] = new double[outputNeurons.size()];
		for (int i=0; i< outputNeurons.size(); i++) {
			output[i] = outputNeurons.get(i).getOut();
		}
		return output;
	}
	
	private void passBackward(double[] target){
		List<Neuron> outputNeurons = getOutputNeurons();
		
		for(int i=0; i<target.length; i++){
			outputNeurons.get(i).setTarget(target[i]);
		}
		
		for(int i=layers.size()-1; i>=0; i--){
			List<Neuron> neurons = layers.get(i).getNeurons();
			for (Neuron neuron : neurons) {
				neuron.feedBackward();
			}
		}
		
	}
	
	private void updateWeights(){
		for (Layer layer : layers) {
			List<Neuron> neurons = layer.getNeurons();
			for (Neuron neuron : neurons) {
				List<Axon> axons = neuron.getForwardAxons();
				for (Axon axon : axons) {
					axon.update();
				}
			}
		}
	}
	
	private double calculateError(double[] target){
		List<Neuron> neurons = getOutputNeurons();
		
		double sum = 0;
		for (int i=0; i< neurons.size(); i++) {
			sum += Math.pow((target[i] - neurons.get(i).getOut()) , 2) / 2;
		}
		
		return sum;
	}

	
	private void initializeLayers(){
		Layer inputLayer = new Layer();
		for (int i = 0; i < inputNeuronCount; i++) {
			Neuron inputNeuron = new Neuron();
			inputLayer.getNeurons().add(inputNeuron);
		}
		
		layers.add(inputLayer);
		
		for (int i = 0; i < hiddenLayers; i++) {
			Layer hiddenLayer = new Layer();
			for (int j = 0; j < hiddenLayerNeuronCounts.get(i); j++) {				
				Neuron hiddenNeuron = new Neuron();
				hiddenLayer.getNeurons().add(hiddenNeuron);
			}
			layers.add(hiddenLayer);
		}
		
		Layer outputLayer = new Layer();
		for (int i = 0; i < outputNeuronCount; i++) {
			Neuron outputNeuron = new Neuron();
			outputLayer.getNeurons().add(outputNeuron);
		}
		layers.add(outputLayer);
		
		connectLayers(layers);
	}
	
	private void connectLayers(List<Layer> lays){

		Random rnd = new Random();
		
		for (int i = 0; i < lays.size()-1; i++) {
			Layer backLayer = lays.get(i);
			Layer nextLayer = lays.get(i+1);
			for (Neuron backNeuron : backLayer.getNeurons()) {
				for (Neuron nextNeuron : nextLayer.getNeurons()) {
					
					Axon axon = new Axon();
					axon.setWeight(rnd.nextDouble() * 2 - 1);
					axon.setPrevious(backNeuron);
					axon.setNext(nextNeuron);
					
					backNeuron.getForwardAxons().add(axon);
					nextNeuron.getBackwardAxons().add(axon);
					
				}
			}
						
			
			Neuron bias = new Neuron();
			bias.setOut(1);
			for (Neuron nextNeuron : nextLayer.getNeurons()) {
				
				Axon biasAxon = new Axon();
				biasAxon.setWeight(rnd.nextDouble() * 2 - 1);
				biasAxon.setPrevious(bias);
				biasAxon.setNext(nextNeuron);
				
				nextNeuron.getBackwardAxons().add(biasAxon);
				bias.getForwardAxons().add(biasAxon);
			}
			backLayer.getNeurons().add(bias);
			
		}
		
	}
	
	private List<Neuron> getInputNeurons(){
		return layers.get(0).getNeurons();
	}
	
	private List<Neuron> getOutputNeurons(){
		return layers.get(layers.size()-1).getNeurons();
	}
	 
	public int getInputNeuronCount() {
		return inputNeuronCount;
	}

	public void setInputNeuronCount(int inputNeuronCount) {
		this.inputNeuronCount = inputNeuronCount;
	}

	public int getOutputNeuronCount() {
		return outputNeuronCount;
	}

	public void setOutputNeuronCount(int outputNeuronCount) {
		this.outputNeuronCount = outputNeuronCount;
	}

	public int getHiddenLayers() {
		return hiddenLayers;
	}
	public NeuralNetwork setHiddenLayers(int hiddenLayers) {
		this.hiddenLayers = hiddenLayers;
		return this;
	}
	
	public String getActivationFunction() {
		return activationFunction;
	}
	public NeuralNetwork setActivationFunction(String activationFunction) {
		this.activationFunction = activationFunction;
		return this;
	}
	public String getWeightCalculationProcedure() {
		return weightCalculationProcedure;
	}
	public NeuralNetwork setWeightCalculationProcedure(String weightCalculationProcedure) {
		this.weightCalculationProcedure = weightCalculationProcedure;
		return this;
	}
	public double getLearningRate() {
		return learningRate;
	}
	public NeuralNetwork setLearningRate(double learningRate) {
		this.learningRate = learningRate;
		return this;
	}
	public int getEpoch() {
		return epoch;
	}
	public NeuralNetwork setEpoch(int epoch) {
		this.epoch = epoch;
		return this;
	}
	public List<Integer> getHiddenLayerNeuronCounts() {
		return hiddenLayerNeuronCounts;
	}
	public NeuralNetwork setHiddenLayerNeuronCounts(List<Integer> hiddenLayerNeuronCounts) {
		this.hiddenLayerNeuronCounts = hiddenLayerNeuronCounts;
		return this;
	}

	public double[][] getInputs() {
		return inputs;
	}

	public NeuralNetwork setInputs(double[][] inputs) {
		this.inputs = inputs;
		return this;
	}

	public double[][] getTargets() {
		return targets;
	}

	public NeuralNetwork setTargets(double[][] targets) {
		this.targets = targets;
		return this;
	}
	
	
	
}