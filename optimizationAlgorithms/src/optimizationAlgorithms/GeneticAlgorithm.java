package optimizationAlgorithms;

import java.util.Random;

/*
 * 
 * This class implements the genetic algorithm for optimizing the seating arrangement based on the dislike matrix.
 * The algorithm iterates through multiple generations, selecting the fittest arrangement with the least amount of conflict
 * then creates offsprings and applies mutations.
 * 
 * */

public class GeneticAlgorithm {
	private Random rand = new Random();// for selecting random indexes
	private DislikeTable matrix = Main.matrix;// holds utility functions and data

	/*
	 * this method takes in the population size, number of generations to produce,
	 * and the mutation rate which a mutation will be done on a child state. The
	 * method returns the fittest arrangement which was produced in the last
	 * generation. The result may not be the optimal solution, but it is close to
	 * it.
	 * 
	 */
	public int[] geneticAlgorithm(int populationSize, int numGenerations, double mutationRate) {

		// initializing the population with random arrangements
		int[][] population = initializePopulation(populationSize);

		// keep looping until the specifies number of generation were generated
		for (int generation = 0; generation < numGenerations; generation++) {

			int[][] selectedElite = selection(population);// select the top 60% elite arrangements with the least
															// conflicts
			int[][] newGen = new int[populationSize][10];// container for upcoming generations of arrangements

			// loop the population to generate the net generation
			for (int i = 0; i < populationSize; i += 2) {

				// selecting a random two parent arrangements from the top 60% of the population
				int[] parent1 = selectedElite[rand.nextInt(selectedElite.length)];
				int[] parent2 = selectedElite[rand.nextInt(selectedElite.length)];

				int point = rand.nextInt(parent1.length - 2) + 1;// index point for crossover [1,2,3...(size-1)]

				// generating the children resulted from the crossover of the selected parents
				int[] child1 = crossover(parent1, parent2, point);
				int[] child2 = crossover(parent2, parent1, point);

				// applying mutation to children
				mutate(child1, mutationRate);
				mutate(child2, mutationRate);

				// adding children to the new generation container
				newGen[i] = child1;
				newGen[i + 1] = child2;
			}

			population = newGen;// setting the current population to the new generation
		}

		// to hold the resulted best arrangement (least conflict)
		int[] bestArrangement = null;
		double bestCost = -1;// least cost (impossible)

		// loop the end population to find the best discovered arrangement (best cost)
		for (int[] arrangement : population) {
			double fitness = matrix.calculateFitness(arrangement);

			if (fitness > bestCost) {// found a better cost
				bestArrangement = arrangement;
				bestCost = fitness;
			}
		}

		return bestArrangement;// return best found arrangement
	}

	// this method fills the population with random seating arrangements
	public int[][] initializePopulation(int size) {
		int[][] population = new int[size][10];
		for (int i = 0; i < size; i++)
			population[i] = matrix.generateRandomArrangement();

		return population;
	}

	// selects the top 60% elite of the population given, which are the arrangements
	// with the best cost (least conflict)
	public int[][] selection(int[][] population) {

		// finding the cost of each arrangement in the given population
		double[] fitness = new double[population.length];
		for (int i = 0; i < population.length; i++)
			fitness[i] = matrix.calculateFitness(population[i]);

		int size = (int) (population.length * 0.6);// the number of selected arrangements
		int[][] elites = new int[size][];// to hold the best of the best

		// selecting the top 60% of the population
		for (int i = 0; i < size; i++) {// just the selected size
			int max = 0;// hold index

			for (int j = 1; j < population.length; j++) {// comparing with the whole pop
				if (fitness[j] > fitness[max])
					max = j;
			}

			// saving the individual with the highest fitness function cost
			elites[i] = population[max];

			// preventing this arrangement to be selected again next round
			fitness[max] = -1;// setting fitness to a very low number
		}

		return elites; // Return the selected population
	}

	// crossover the two parent arrangements to produce the new one
	public int[] crossover(int[] parent1, int[] parent2, int point) {
		int size = parent1.length;// size of arrangement
		int[] child = new int[size];// produced child
		boolean[] seated = new boolean[size];// keep track of seen people in arrangement

		// copy first part from parent1 to child
		for (int i = 0; i <= point; i++) {
			child[i] = parent1[i];
			seated[child[i]] = true;// mark as taken
		}

		// fill remaining part in the same order it appeared in the parent
		int p2Index = 0;
		for (int i = point + 1; i < size; i++) {
			// until an unassigned person appears
			while (seated[parent2[p2Index]])
				p2Index++;

			child[i] = parent2[p2Index];// add to child arrangement
			seated[child[i]] = true;// set as seated
		}

		return child;// return resulted arrangement
	}

	// applies a mutation by switching two individuals from the arrangement given
	public void mutate(int[] arrangement, double rate) {

		if (rand.nextDouble() <= rate) {// only mutate within the rate
			int a, b;// indexes to mutate(switch)

			do {
				a = rand.nextInt(arrangement.length);
				b = rand.nextInt(arrangement.length);
			} while (a == b);// making sure indexex never equal

			int temp = arrangement[a];
			arrangement[a] = arrangement[b];
			arrangement[b] = temp;
		}
	}

}
