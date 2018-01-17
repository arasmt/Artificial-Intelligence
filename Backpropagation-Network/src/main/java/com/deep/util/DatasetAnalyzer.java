package com.deep.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;

public class DatasetAnalyzer {

	private String filename;
	private String splitter;
	
	private String[][] dataset;
	private double[][] inputs;
	private double[][] targets;
	
	private HashMap<String, Integer> classes = new HashMap<String, Integer>();
	
	public DatasetAnalyzer(String filename, String splitter){
		this.filename = filename;
		this.splitter = splitter;
		preprocess();
		initialize();
	}

	private void initialize(){
		
		dataset = readDataset(filename+".out");
		
		inputs = new double[dataset.length][dataset[0].length-1];
		
		for (int i = 0; i < dataset.length; i++) {
			for (int j = 0; j < dataset[i].length-1; j++) {
				inputs[i][j] = Double.parseDouble(dataset[i][j]);
			}
		}
		
		int classCount = getClassNumbers(dataset);
		targets = new double[dataset.length][classCount];
		
		for (int i = 0; i < dataset.length; i++) {
			String className = dataset[i][dataset[i].length-1];
			
			targets[i][classes.get(className)] = 1;
			
		}
		//bipolarize(targets);
	}
	
	private void preprocess(){
		
		FileReader fr = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fr = new FileReader(filename); 
			BufferedReader br = new BufferedReader(fr); 
			fw = new FileWriter(filename+".out"); 
			bw = new BufferedWriter(fw);
			
			String line;
			
			int counter = 0;

			while((line = br.readLine()) != null)
			{ 
			    line = line.trim(); // remove leading and trailing whitespace
			    if (!line.isEmpty()) // don't write out blank lines
			    {
			    	if(counter > 0){
			    		bw.newLine();
			    	}
			    	
			    	if(line.contains(splitter+splitter)) {
			    		line = line.replace(splitter+splitter, splitter);
			    	}
			    	
			        bw.write(line, 0, line.length());
			        counter++;
			    }
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		finally{
			if(fr != null){
				try {
					fr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(bw != null){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(fw != null){
				try {
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private String[][] readDataset(String fn){
		
		String data[][] = null;
		
		try (BufferedReader br = new BufferedReader(new FileReader(fn))) {

			String sCurrentLine;
			int totalLines = getLineNumbers();
			int line = 0;

			while ((sCurrentLine = br.readLine()) != null) {
				
				String[] arr = sCurrentLine.split(splitter);
				
				if(data == null){
					data = new String[totalLines][arr.length];
				}
				
				for(int i=0; i<arr.length; i++){
					data[line][i] = arr[i];
				}
				line++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}
	
	private int getLineNumbers(){
		
		int lineNumbers = -1;
		
		FileReader reader = null;
		LineNumberReader lReader = null;
		
		try {
			reader = new FileReader(filename+".out");
			lReader = new LineNumberReader(reader);
			
			while (lReader.skip(Long.MAX_VALUE) > 0)
			   {
			      // Loop just in case the file is > Long.MAX_VALUE or skip() decides to not read the entire file
			   }
			
			lineNumbers = lReader.getLineNumber();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				lReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return lineNumbers+1;
	}
	
	private int getClassNumbers(String[][] ds){
		
		int counter = 0;
		
		for (int i = 0; i < ds.length; i++) {
			if(!classes.containsKey(ds[i][ds[i].length-1])){
				classes.put(ds[i][ds[i].length-1], counter);
				counter++;
			}
		}
		
		return classes.size();
	}
	
	private void bipolarize(double[][] arr){
		
		for (int i=0; i<arr.length; i++) {
			for(int j=0; j<arr[i].length; j++) {
				if(arr[i][j] == 0) {
					arr[i][j] = -1;
				}
			}
		}
		
	}

	public double[][] getInputs() {
		return inputs;
	}

	public void setInputs(double[][] inputs) {
		this.inputs = inputs;
	}

	public double[][] getTargets() {
		return targets;
	}

	public void setTargets(double[][] targets) {
		this.targets = targets;
	}
	
	
	
}
