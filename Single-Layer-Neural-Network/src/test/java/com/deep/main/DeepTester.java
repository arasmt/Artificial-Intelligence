package com.deep.main;

import java.util.Arrays;
import java.util.List;

import com.deep.model.NeuralNetwork;

public class DeepTester {
	
	//Training inputs of A,B,C,D,E,J,K respectively
	static int[][] inputs = 
	//A
	{ { -1,-1,1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,1,1,1,1,1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,1,-1,1,1,1,-1,1,1,1 },
	{ -1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,1,1,1,1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,1,-1 },
	{ -1,-1,-1,1,-1,-1,-1, -1,-1,-1,1,-1,-1,-1, -1,-1,1,-1,1,-1,-1, -1,-1,1,-1,1,-1,-1, -1,1,-1,-1,-1,1,-1, -1,1,1,1,1,1,-1, 1,-1,-1,-1,-1,-1,1, 1,-1,-1,-1,-1,-1,1, 1,1,-1,-1,-1,1,1 },
	//B
	{ 1,1,1,1,1,1,-1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,1,1,1,1,1,-1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,1,1,1,1,1,1,-1 },
	{ 1,1,1,1,1,1,-1,1,-1,-1,-1,-1,-1,1,1,-1,-1,-1,-1,-1,1,1,-1,-1,-1,-1,-1,1,1,1,1,1,1,1,-1,1,-1,-1,-1,-1,-1,1,1,-1,-1,-1,-1,-1,1,1,-1,-1,-1,-1,-1,1,1,1,1,1,1,1,-1 },
	{ 1,1,1,1,1,1,-1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,1,1,1,1,1,-1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,1,1,1,1,1,1,-1 },
	//C
	{ -1,-1,1,1,1,1,1,-1,1,-1,-1,-1,-1,1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,1,-1,-1,1,1,1,1,-1 },
	{ -1,-1,1,1,1,-1,-1,-1,1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,-1,1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,1,-1,-1,-1,1,1,1,-1,-1 },
	{ -1,-1,1,1,1,-1,-1,-1,1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,-1,1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,1,-1,-1,-1,1,1,1,-1,-1 },
	//D
	{ 1,1,1,1,1,-1,-1, -1,1,-1,-1,-1,1,-1, -1,1,-1,-1,-1,-1,1, -1,1,-1,-1,-1,-1,1, -1,1,-1,-1,-1,-1,1, -1,1,-1,-1,-1,-1,1, -1,1,-1,-1,-1,-1,1, -1,1,-1,-1,-1,1,-1, 1,1,1,1,1,-1,-1 },
	{ 1,1,1,1,1,-1,-1, 1,-1,-1,-1,-1,1,-1, 1,-1,-1,-1,-1,-1,1, 1,-1,-1,-1,-1,-1,1, 1,-1,-1,-1,-1,-1,1, 1,-1,-1,-1,-1,-1,1, 1,-1,-1,-1,-1,-1,1,  1,-1,-1,-1,-1,1,-1, 1,1,1,1,1,-1,-1 },
	{ 1,1,1,1,1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,1,-1,1,1,1,1,1,-1,-1 },
	//E
	{ 1, 1,  1,  1,  1,  1,  1, -1, 1, -1, -1, -1, -1,  1, -1, 1, -1, -1, -1, -1, -1, -1, 1, -1,  1, -1, -1, -1, -1, 1,  1,  1, -1, -1, -1, -1, 1, -1,  1, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1,  1, 1, 1,  1,  1,  1,  1,  1 },
	{ 1,1,1,1,1,1,1, 1,-1,-1,-1,-1,-1,-1, 1,-1,-1,-1,-1,-1,-1, 1,-1,-1,-1,-1,-1,-1, 1,1,1,1,1,-1,-1, 1,-1,-1,-1,-1,-1,-1, 1,-1,-1,-1,-1,-1,-1, 1,-1,-1,-1,-1,-1,-1, 1,1,1,1,1,1,1 },
	{ 1,1,1,1,1,1,1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,1,-1,-1,-1,1,1,1,1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,1,1,1,1,1,1,1,1 },
	//J
	{ -1,-1,-1,1,1,1,1, -1,-1,-1,-1,-1,1,-1, -1,-1,-1,-1,-1,1,-1, -1,-1,-1,-1,-1,1,-1, -1,-1,-1,-1,-1,1,-1, -1,-1,-1,-1,-1,1,-1, -1,1,-1,-1,-1,1,-1, -1,1,-1,-1,-1,1,-1, -1,-1,1,1,1,-1,-1 },
	{ -1,-1,-1,-1,-1,1,-1, -1,-1,-1,-1,-1,1,-1, -1,-1,-1,-1,-1,1,-1, -1,-1,-1,-1,-1,1,-1, -1,-1,-1,-1,-1,1,-1, -1,-1,-1,-1,-1,1,-1, -1,1,-1,-1,-1,1,-1, -1,1,-1,-1,-1,1,-1, -1,-1,1,1,1,-1,-1 },
	{ -1,-1,-1,-1,1,1,1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,1,-1,-1,-1,1,1,1,-1,-1 },
	//K
	{ 1,1,1,-1,-1,1,1,-1,1,-1,-1,1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,1,-1,-1,-1,-1,-1,1,1,-1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,1,-1,-1,-1,1,-1,1,1,1,-1,-1,1,1 },
	{ 1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,1,-1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,1,-1 },
	{ 1,1,1,-1,-1,1,1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,1,-1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,1,-1,-1,-1,1,-1,1,1,1,-1,-1,1,1 }
	};
			
	
	//Target outputs of A,B,C,D,E,J,K respectively							  
	static int[][] targets = 
	//A
	{ { 1,-1,-1,-1,-1,-1,-1 }, { 1,-1,-1,-1,-1,-1,-1 } , { 1,-1,-1,-1,-1,-1,-1 } , 
	//B
	{ -1,1,-1,-1,-1,-1,-1 } , { -1,1,-1,-1,-1,-1,-1 } , { -1,1,-1,-1,-1,-1,-1 } ,
	//C
	{ -1,-1,1,-1,-1,-1,-1 } , { -1,-1,1,-1,-1,-1,-1 } , { -1,-1,1,-1,-1,-1,-1 } , 
	//D
	{ -1,-1,-1,1,-1,-1,-1 } , { -1,-1,-1,1,-1,-1,-1 } , { -1,-1,-1,1,-1,-1,-1 } , 
	//E
	{ -1,-1,-1,-1,1,-1,-1 } , { -1,-1,-1,-1,1,-1,-1 } , { -1,-1,-1,-1,1,-1,-1 } , 
	//J
	{ -1,-1,-1,-1,-1,1,-1 } , { -1,-1,-1,-1,-1,1,-1 } , { -1,-1,-1,-1,-1,1,-1 } , 
	//K
	{ -1,-1,-1,-1,-1,-1,1 } , { -1,-1,-1,-1,-1,-1,1 } , { -1,-1,-1,-1,-1,-1,1 }};

	static int[] correctInputFont1A = inputs[0];
	static int[] correctInputFont2A = inputs[1];
	static int[] correctInputFont3A = inputs[2];
	
	static int[] correctInputFont1B = inputs[3];
	static int[] correctInputFont2B = inputs[4];
	static int[] correctInputFont3B = inputs[5];

	static int[] correctInputFont1C = inputs[6];	
	static int[] correctInputFont2C = inputs[7];	
	static int[] correctInputFont3C = inputs[8];	

	static int[] correctInputFont1D = inputs[9];
	static int[] correctInputFont2D = inputs[10];
	static int[] correctInputFont3D = inputs[11];
	
	static int[] correctInputFont1E = inputs[12];
	static int[] correctInputFont2E = inputs[13];
	static int[] correctInputFont3E = inputs[14];
	
	static int[] correctInputFont1J = inputs[15];
	static int[] correctInputFont2J = inputs[16];
	static int[] correctInputFont3J = inputs[17];
	
	static int[] correctInputFont1K = inputs[18];
	static int[] correctInputFont2K = inputs[19];
	static int[] correctInputFont3K = inputs[20];
	
	
	static int[] corruptedInputFont1A5 = { 1,-1,1,1,-1,-1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,1,1,1,1,1,-1,-1,1,-1,-1,-1,1,-1,1,1,-1,-1,-1,1,-1,1,1,1,-1,1,1,-1 };
	static int[] corruptedInputFont3B4 = { 1,1,1,1,-1,1,-1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,1,1,-1,1,1,1,1,1,-1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,-1,1,-1,1,-1,-1,1,-1,1,-1,1,-1,-1,-1,-1,1,1,1,1,1,1,-1,-1 };
	static int[] corruptedInputFont2C8 = { -1,1,1,1,1,-1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,-1,-1,1,-1,1,1,-1,-1,-1,-1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,-1,-1,-1,-1,1,-1,1,-1,-1,-1,1,1,1,-1,-1,-1,1,-1,-1,-1,-1,1,1,1,-1 };	
	static int[] corruptedInputFont2D3 = { 1,-1,1,1,1,-1,-1, 1,-1,-1,-1,-1,1,-1, 1,-1,-1,-1,-1,-1,1, 1,-1,-1,-1,-1,-1,1, 1,-1,-1,-1,-1,-1,1, 1,-1,-1,-1,-1,-1,1, 1,-1,1,-1,-1,-1,1,  1,-1,-1,-1,-1,1,-1, 1,1,1,-1,1,-1,-1 };
	static int[] corruptedInputFont3E25 = { -1,-1,-1,1,1,-1,1,1,-1,-1,1,-1,-1,-1,-1,1,1,-1,1,1,-1,1,-1,-1,1,1,1,-1,1,-1,-1,1,-1,-1,-1,1,-1,-1,1,-1,-1,1,-1,1,-1,-1,1,-1,-1,-1,-1,-1,-1,-1,1,1,1,1,1,1,1,1,1 };
	static int[] corruptedInputFont1J40 = { 1,1,1,-1,-1,-1,-1, -1,1,1,-1,1,-1,1, 1,-1,1,1,1,1,-1, -1,-1,1,1,-1,-1,1, 1,-1,-1,1,1,-1,-1, -1,1,1,-1,-1,-1,-1, 1,-1,-1,-1,1,-1,1, 1,-1,1,1,-1,-1,-1, 1,1,1,1,-1,-1,-1 };
	static int[] corruptedInputFont1K15 = { 1,1,-1,-1,-1,1,1,-1,1,1,-1,1,-1,-1,1,1,-1,1,1,-1,-1,-1,-1,1,-1,-1,1,-1,-1,1,1,1,-1,-1,-1,-1,-1,-1,1,1,-1,-1,-1,1,-1,1,1,-1,-1,-1,1,-1,-1,-1,-1,-1,1,-1,1,1,1,1,-1 };
	
	
	public static void main(String[] args) {

		System.out.println("--------------------------------------------------------------------------------------------------------------");
		System.out.println("Hebb Algorithm \n");
		start("hebb");
		System.out.println("--------------------------------------------------------------------------------------------------------------");

		System.out.println("--------------------------------------------------------------------------------------------------------------");
		System.out.println("Perceptron Algorithm \n");
		start("perceptron");
		System.out.println("--------------------------------------------------------------------------------------------------------------");
		
		System.out.println("--------------------------------------------------------------------------------------------------------------");
		System.out.println("Delta Algorithm \n");
		start("delta");
		System.out.println("--------------------------------------------------------------------------------------------------------------");
			
	}

	
	public static void start(String learningRule){
		
		NeuralNetwork network = new NeuralNetwork(learningRule);
		network.setEpoch(10);
		network.setInputs(inputs);
		network.setTargets(targets);
		network.setLearningRate(1);
		
		network.train();
		
		
		int[] testNets = null;

		testNets = network.test(correctInputFont1A);
		System.out.println("Test nets of Font1_A = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font1_A == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont2A);
		System.out.println("Test nets of Font2_A = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font2_A == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont3A);
		System.out.println("Test nets of Font3_A = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font3_A == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont1B);
		System.out.println("Test nets of Font1_B = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font1_B == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont2B);
		System.out.println("Test nets of Font2_B = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font2_B == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont3B);
		System.out.println("Test nets of Font3_B = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font3_B == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont1C);
		System.out.println("Test nets of Font1_C = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font1_C == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont2C);
		System.out.println("Test nets of Font2_C = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font2_C == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont3C);
		System.out.println("Test nets of Font3_C = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font3_C == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont1D);
		System.out.println("Test nets of Font1_D = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font1_D == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont2D);
		System.out.println("Test nets of Font2_D = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font2_D == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont3D);
		System.out.println("Test nets of Font3_D = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font3_D == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont1E);
		System.out.println("Test nets of Font1_E = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font1_E == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont2E);
		System.out.println("Test nets of Font2_E = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font2_E == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont3E);
		System.out.println("Test nets of Font3_E = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font3_E == "+getMatchedResult(testNets));
		System.out.println("\n");
			
		testNets = network.test(correctInputFont1J);
		System.out.println("Test nets of Font1_J = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font1_J == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont2J);
		System.out.println("Test nets of Font2_J = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font2_J == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont3J);
		System.out.println("Test nets of Font3_J = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font3_J == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(correctInputFont1K);
		System.out.println("Test nets of Font1_K = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font1_K == "+getMatchedResult(testNets));
		System.out.println("\n\n");
		
		testNets = network.test(correctInputFont2K);
		System.out.println("Test nets of Font2_K = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font2_K == "+getMatchedResult(testNets));
		System.out.println("\n\n");
		
		testNets = network.test(correctInputFont3K);
		System.out.println("Test nets of Font3_K = " + Arrays.toString(testNets));
		System.out.println("Matching of Correct Font3_K == "+getMatchedResult(testNets));
		System.out.println("\n\n");
		
		
		testNets = network.test(corruptedInputFont1A5);
		System.out.println("Test nets of 5 pixel Corrupted Font1_A = " + Arrays.toString(testNets));
		System.out.println("Matching of 5 pixel Corrupted Font1_A == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(corruptedInputFont3B4);
		System.out.println("Test nets of 4 pixel Corrupted Font3_B = " + Arrays.toString(testNets));
		System.out.println("Matching of 4 pixel Corrupted Font3_B == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(corruptedInputFont2C8);
		System.out.println("Test nets of 8 pixel Corrupted Font2_C = " + Arrays.toString(testNets));
		System.out.println("Matching of 8 pixel Corrupted Font2_C == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(corruptedInputFont2D3);
		System.out.println("Test nets of 3 pixel Corrupted Font2_D = " + Arrays.toString(testNets));
		System.out.println("Matching of 3 pixel Corrupted Font2_D == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(corruptedInputFont3E25);
		System.out.println("Test nets of 25 pixel Corrupted Font3_E = " + Arrays.toString(testNets));
		System.out.println("Matching of 25 pixel Corrupted Font3_E == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(corruptedInputFont1J40);
		System.out.println("Test nets of 40 pixel Corrupted Font1_J = " + Arrays.toString(testNets));
		System.out.println("Matching of 40 pixel Corrupted Font1_J == "+getMatchedResult(testNets));
		System.out.println("\n");
		
		testNets = network.test(corruptedInputFont1K15);
		System.out.println("Test nets of 15 pixel Corrupted Font1_K = " + Arrays.toString(testNets));
		System.out.println("Matching of 15 pixel Corrupted Font1_K == "+getMatchedResult(testNets));
		System.out.println("\n");
		
	}
	
	private static String getMatchedResult(int[] nets){
		
		List<String> matchings = Arrays.asList("A", "B", "C", "D", "E", "J", "K");

		int maxIndex = getMaxIndex(nets);
		
		return matchings.get(maxIndex);
	}

	private static int getMaxIndex(int[] nets){
		
		if(nets.length > 0){			
			int max = nets[0];
			int maxIndex = -1;
			
			for(int i=0; i<nets.length; i++){
				
				if(nets[i] >= max){
					max = nets[i];
					maxIndex = i;
				}
			}
			return maxIndex;
		}
		else{
			return -1;
		}
		
	}
	
}
