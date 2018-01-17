package com.deep.model;

import java.util.ArrayList;
import java.util.List;

import com.deep.util.Parameters;

public class Neuron {

	private List<Axon> forwardAxons = new ArrayList<Axon>();
	private List<Axon> backwardAxons = new ArrayList<Axon>();
	
	
	private double netIn;
	private double out;
	private double target;
	
	
	
	private double activate(double netIn){
		
		if(Parameters.ACTIVATION.equalsIgnoreCase("sigmoid")){			
			return 1/(1+Math.exp(-netIn));
		}
		else if(Parameters.ACTIVATION.equalsIgnoreCase("tanh")) {
			return (2/(1+Math.exp(-2*netIn)))-1;
		}
		else if(Parameters.ACTIVATION.equalsIgnoreCase("relu")) {
			if(netIn < 0)
				return 0;
			else if(netIn >= 0)
				return netIn;
		}
		
		return 1/(1+Math.exp(-netIn));
	}
	
	
	public void feedForward(){
		
		if(backwardAxons != null && !backwardAxons.isEmpty()){
			double sum = 0;
			
			for (Axon axon : backwardAxons) {
				sum += axon.getPrevious().getOut() * axon.getWeight();
			}
			
			netIn = sum;
			
			out = activate(netIn);	
		}
		
	}
	
	public void feedBackward(){
		
		if(backwardAxons != null && !backwardAxons.isEmpty()){
			
			for (Axon axon : backwardAxons) {
				axon.calculateNewWeight();
			}
			
		}
		
	}
	
	public double derETotalOut(){
		
		
		if(forwardAxons.isEmpty()){ // Output layer
			return (out-target);
		}
		
		double sum = 0;
		for (Axon axon : forwardAxons) {
			sum += axon.getNext().derETotalOut() * axon.getNext().derivactivate() * axon.getWeight();
		}
		
		return sum;
	}
	
	public double derivactivate(){
		
		if(Parameters.ACTIVATION.equalsIgnoreCase("sigmoid")){			
			return out * (1 - out);
		}
		else if(Parameters.ACTIVATION.equalsIgnoreCase("tanh")) {
			return 1- Math.pow(out, 2);
		}
		else if(Parameters.ACTIVATION.equalsIgnoreCase("relu")) {
			if(netIn < 0)
				return 0;
			else if(netIn >= 0)
				return 1;
		}
		
		return 0;
	}


	public List<Axon> getForwardAxons() {
		return forwardAxons;
	}


	public void setForwardAxons(List<Axon> forwardAxons) {
		this.forwardAxons = forwardAxons;
	}


	public List<Axon> getBackwardAxons() {
		return backwardAxons;
	}


	public void setBackwardAxons(List<Axon> backwardAxons) {
		this.backwardAxons = backwardAxons;
	}


	public double getNetIn() {
		return netIn;
	}


	public void setNetIn(double netIn) {
		this.netIn = netIn;
	}


	public double getOut() {
		return out;
	}


	public void setOut(double out) {
		this.out = out;
	}

	public double getTarget() {
		return target;
	}


	public void setTarget(double target) {
		this.target = target;
	}
	
	
	
}
