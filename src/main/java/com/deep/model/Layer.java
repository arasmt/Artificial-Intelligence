package com.deep.model;

import java.util.ArrayList;
import java.util.List;

public class Layer {

	private List<Neuron> neurons = new ArrayList<Neuron>();

	public List<Neuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}

}
