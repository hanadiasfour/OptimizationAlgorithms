package optimizationAlgorithms;

import java.util.Random;

/*
 * 	This class implements the simulated annealing algorithm for optimizing the seating arrangement.
 *  This algorithm starts with a random initial arrangement and then explores neighboring solutions.
 *  It accepts worse arrangements with a probability depending on a decreasing "temperature".
 *  The temperature gradually decreases, which allows escaping from local optima for a better solution.
 * */

public class SimulatedAnnealing {

	private Random rand = new Random();// for selecting random indexes
	private DislikeTable matrix = Main.matrix;// holds utility functions and data

	/*
	 * THis method takes in a temperature which is used to indicate the rate for
	 * accepting worse arrangements, the cooling rate decreases the temperature and
	 * hence decreases the amount of worse cases being accepted. The number of
	 * iterations given are the number of times to select neighboring arrangements
	 * to be selected
	 *
	 */
	public int[] simulatedAnnealing(double initialTemperature, double coolingRate, int numIterations) {
		int[] current = matrix.generateRandomArrangement();// initial state
		double currentCost = matrix.calculateCost(current);// cost of the initial arrangement

		int[] bestArr = current.clone();// assuming the best is the initial state
		double bestCost = currentCost;

		double temp = initialTemperature;// will start to decrease by the cooling rate

		for (int i = 0; i < numIterations; i++) {// repeat by number of iterations
			int[] neighbor = getNeighbor(current);// state to move to next
			double neighborCost = matrix.calculateCost(neighbor);

			// accept the neighboring state only if the next state is :
			// 1) better than the current
			// 2) worse but the random number is less than the equation ^E/temp
			if (neighborCost < currentCost || rand.nextDouble() < Math.exp((currentCost - neighborCost) / temp)) {
				// assigning the current as the neighboring state
				current = neighbor;
				currentCost = neighborCost;

				if (currentCost < bestCost) {// check if it is the ultimate best arrangement found
					bestArr = current;
					bestCost = currentCost;
				}
			}

			temp *= coolingRate;// decrease worse case acceptance rate
		}

		return bestArr;// return best found arrangement
	}

	// returns a new state by switching two individuals in the given arrangement
	public int[] getNeighbor(int[] arrangement) {
		int[] neighbor = arrangement.clone();
		int a, b;// indexes to switch

		do {
			a = rand.nextInt(arrangement.length);
			b = rand.nextInt(arrangement.length);
		} while (a == b);// making sure indexes never equal

		// swap individuals
		int temp = neighbor[a];
		neighbor[a] = neighbor[b];
		neighbor[b] = temp;
		return neighbor;
	}

}
