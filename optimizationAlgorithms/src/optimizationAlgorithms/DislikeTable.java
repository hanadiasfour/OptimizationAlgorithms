package optimizationAlgorithms;

import java.util.Random;

public class DislikeTable {

	private static double[][] dislikeMatrix = { { 0, 0.68, 0.55, 0.30, 0.82, 0.48, 0.33, 0.10, 0.76, 0.43 },
			{ 0.68, 0, 0.90, 0.11, 0.76, 0.20, 0.55, 0.17, 0.62, 0.99 },
			{ 0.55, 0.90, 0, 0.70, 0.63, 0.96, 0.51, 0.90, 0.88, 0.64 },
			{ 0.30, 0.11, 0.70, 0, 0.91, 0.86, 0.78, 0.99, 0.53, 0.92 },
			{ 0.82, 0.76, 0.63, 0.91, 0, 0.43, 0.88, 0.53, 0.42, 0.75 },
			{ 0.48, 0.20, 0.96, 0.86, 0.43, 0, 0.63, 0.97, 0.37, 0.26 },
			{ 0.33, 0.55, 0.51, 0.78, 0.88, 0.63, 0, 0.92, 0.87, 0.81 },
			{ 0.10, 0.17, 0.90, 0.99, 0.53, 0.97, 0.92, 0, 0.81, 0.78 },
			{ 0.76, 0.62, 0.88, 0.53, 0.42, 0.37, 0.87, 0.81, 0, 0.45 },
			{ 0.43, 0.99, 0.64, 0.92, 0.75, 0.26, 0.81, 0.78, 0.45, 0 } };
	private static String[] names = { "Ahmad", "Salem", "Ayman", "Hani", "Kamal", "Samir", "Hakam", "Fuad", "Ibrahim",
			"Khalid" };
	private Random rand = new Random();

	// retrieve the dislike value between two people
	public double getDislike(int p1, int p2) {
		return dislikeMatrix[p1][p2];
	}

	public String getNameById(int id) {
		return names[id];

	}

	// number of people being seated
	public int getNumPeople() {
		return dislikeMatrix.length;
	}

	
	//returns the arrangement as a array of names in the same order of the indexes given
	public String[] getArrangmentOfNames(int[] numArr) {
		String[] nameArr = new String[getNumPeople()];//initialize

		for (int i = 0; i < nameArr.length; i++) //assigning name
			nameArr[i] = names[numArr[i]];

		return nameArr;
	}


	//generates a random seating arrangement for initial states
	public int[] generateRandomArrangement() {
		int[] arr = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };//contains all indexes
		// loop the sequence arr to shuffle it
		for (int i = arr.length - 1; i > 0; i--) {
			int index = rand.nextInt(i + 1);//random index ahead
			//swap current with the random index
			int temp = arr[index];
			arr[index] = arr[i];
			arr[i] = temp;
		}
		return arr;//return shuffled arrangement
	}
	
	//method that calculated the fitness of the arrangement instead of the cost
	//the higher the fitness, the smaller the conflict, the better the arrangement
	//used for the genetic algorithm
	public double calculateFitness(int[] arrangement) {
		double sum = 0;
		int numPeople = getNumPeople();
		for (int i = 0; i < numPeople; i++) {
			int personA = arrangement[i];
			int personB = arrangement[(i + 1) % numPeople];
			sum += (1- dislikeMatrix[personA][personB]) + (1-dislikeMatrix[personB][personA]);
		}
		return sum;
	}
	
	//calculates the cost of the seating arrangement given
	// the higher the cost, the higher the conflict, and the worse the arrangement is
	//used for hill climbing and simulated annealing
	public double calculateCost(int[] arrangement) {
		double totalCost = 0;
		int numPeople = arrangement.length;
		for (int i = 0; i < numPeople; i++) {
			int personA = arrangement[i];
			int personB = arrangement[(i + 1) % numPeople];
			totalCost += dislikeMatrix[personA][personB] + dislikeMatrix[personB][personA];
		}
		return totalCost;
	}

}
