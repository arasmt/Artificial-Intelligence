package com.deep.model;

import com.deep.util.Parameters;

public class Axon {

	private double weight;
	private double newWeight;
	private double oldWeight;
	private double bestWeight;
	
	private double lastChange;
	private double dbdLearningRate = Parameters.LEARNING_RATE;
	private double delta;

	private Neuron previous;
	private Neuron next;

	public void update() {
		oldWeight = weight;
		weight = newWeight;
	}
	
	public void rollback() {
		weight = oldWeight;
	}
	
	public void markBest() {
		bestWeight = weight;
	}
	
	public void transformToBest() {
		weight = bestWeight;
	}

	public void calculateNewWeight() {

		if (Parameters.WEIGHT_CALCULATION_PROCEDURE.equalsIgnoreCase("momentum")) {
			double change = Parameters.LEARNING_RATE * (next.derETotalOut() * next.derivactivate() * derNetW());
			newWeight = weight - change + (Parameters.MOMENTUM * lastChange);
			lastChange = change;
		} else if (Parameters.WEIGHT_CALCULATION_PROCEDURE.equalsIgnoreCase("deltabardelta")) {
			double change = dbdLearningRate * (next.derETotalOut() * next.derivactivate() * derNetW());
			double tempDelta = getDeltaBarDelta(change, lastChange);
			dbdLearningRate = getNewDbdLearningRate(tempDelta, change);
			newWeight = weight - change + (Parameters.MOMENTUM * lastChange);
			lastChange = change;
			delta = tempDelta;
		}
		else if(Parameters.WEIGHT_CALCULATION_PROCEDURE.equalsIgnoreCase("adaptive")) {
			
		}
	}

	private double getDeltaBarDelta(double change, double lastChange) {
		return ((1 - Parameters.DELTA_BETA) * change) + (Parameters.DELTA_BETA * delta);
	}

	private double getNewDbdLearningRate(double dbd, double change) {

		if ((dbd * change) > 0) {
			return dbdLearningRate + Parameters.DELTA_K;
		}
		else if ((dbd * change) < 0) {
			return dbdLearningRate * (1-Parameters.DELTA_GAMA);
		}
		else {
			return dbdLearningRate;
		}

	}

	private double derNetW() {
		return previous.getOut();
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getNewWeight() {
		return newWeight;
	}

	public void setNewWeight(double newWeight) {
		this.newWeight = newWeight;
	}

	public Neuron getPrevious() {
		return previous;
	}

	public void setPrevious(Neuron previous) {
		this.previous = previous;
	}

	public Neuron getNext() {
		return next;
	}

	public void setNext(Neuron next) {
		this.next = next;
	}

	public double getDbdLearningRate() {
		return dbdLearningRate;
	}

	public void setDbdLearningRate(double dbdLearningRate) {
		this.dbdLearningRate = dbdLearningRate;
	}

	public double getDelta() {
		return delta;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public double getOldWeight() {
		return oldWeight;
	}

	public void setOldWeight(double oldWeight) {
		this.oldWeight = oldWeight;
	}

	public double getBestWeight() {
		return bestWeight;
	}

	public void setBestWeight(double bestWeight) {
		this.bestWeight = bestWeight;
	}

}
