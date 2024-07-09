package optimizationAlgorithms;

/*
 * This class implements the hill climbing algorithm for optimizing the seating arrangement. 
 * this algorithm starts with a random initial arrangement and generates neighboring solutions by finding the combinations of swapping pairs of people.
 * it keeps track of the best arrangement with the lowest conflict found so far until lowest cost was found and we can't restart.
 * the random restarts is to escape local minimum and explore different parts of the solution space.
 * 
 * */

public class HillClimbing {

	private DislikeTable matrix = Main.matrix;// holds utility functions and data

	public int[] hillClimbing(int numRestarts) {
		int[] bestArr = null;// to hold the best encountered arrangement
		double bestCost = Double.MAX_VALUE;

		// loop until all of the restarts were completed
		for (int restart = 0; restart < numRestarts; restart++) {
			int[] currentArr = matrix.generateRandomArrangement();// initial state
			double currentCost = matrix.calculateCost(currentArr);// initial cost

			// loop until lest costly neighbor is not less than current
			while (true) {
				int[][] neighbors = getNeighbors(currentArr);/// generate all neighboring arrangements to the current
				int[] next = currentArr;// potential next arrangement state
				double nextCost = currentCost;

				// finding the neighbor with the least cost
				for (int[] neighbor : neighbors) {
					double neighborCost = matrix.calculateCost(neighbor);
					if (neighborCost < nextCost) {
						next = neighbor;
						nextCost = neighborCost;
					}
				}

				// if the least cost found neighbor is better than the currently held
				// arrangement then assign it as the current
				if (nextCost < currentCost) {
					currentArr = next;
					currentCost = nextCost;
				} else {
					break;
					/*
					 * cost not decreased from the current state so no use of continuing this loop
					 * (reached minimum) and stop this loop and restart
					 */
				}
			}

			// tracking the best arrangement detected in every loop restart
			if (currentCost < bestCost) {
				bestArr = currentArr;
				bestCost = currentCost;
			}
		}

		return bestArr;// returning the best arrangement with the least costs
	}

	// generates all of the possible neighboring arrangements from the given current
	// state, neighboring states are the states created by swapping two individuals
	public int[][] getNeighbors(int[] arrangement) {

		// The number of combinations of n elements taken 2 at a time
		int combinationsSize = arrangement.length * (arrangement.length - 1) / 2;
		int[][] neighbors = new int[combinationsSize][];// initializing to store neighbors
		int index = 0;// track neighbor index

		// generating all of combinations when swapping 2 individuals
		for (int i = 0; i < arrangement.length; i++) {
			for (int j = i + 1; j < arrangement.length; j++) {

				// copy the current arrangement
				int[] neighbor = arrangement.clone();

				// swap individuals at i and j
				int temp = neighbor[i];
				neighbor[i] = neighbor[j];
				neighbor[j] = temp;

				// save this neighbor
				neighbors[index++] = neighbor;
			}
		}
		return neighbors;
	}

}
