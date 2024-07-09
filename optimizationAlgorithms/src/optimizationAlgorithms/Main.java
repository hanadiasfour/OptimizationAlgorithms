package optimizationAlgorithms;

import java.util.Arrays;

public class Main {

	static DislikeTable matrix = new DislikeTable();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// initializing optimizing algorithms classes
		GeneticAlgorithm genetic = new GeneticAlgorithm();
		SimulatedAnnealing simulated = new SimulatedAnnealing();
		HillClimbing hill = new HillClimbing();
		
				
		//////////////////
		// * <Genetic> *//
		//////////////////
		int[] bestSeatingG = genetic.geneticAlgorithm(100, 1000, 0.1);
		double bestCostG = matrix.calculateCost(bestSeatingG);
		
		//print results
		System.out.println("-----------------------------------------------------");

		System.out.println(
				"Genetic Algorithm Best Seating: \n" + Arrays.toString(matrix.getArrangmentOfNames(bestSeatingG)));
		System.out.println("Total cost: " + bestCostG + "\n\n");
		System.out.println("-----------------------------------------------------");

		
		
		//////////////////////////////
		// * <Simulated Annealing> *//
		//////////////////////////////
		int[] bestSeatingS = simulated.simulatedAnnealing(1000, 0.99, 10000);
		double bestCostS = matrix.calculateCost(bestSeatingS);
		
		//print results
		System.out.println(
				"Simulated Annealing Best Seating: \n" + Arrays.toString(matrix.getArrangmentOfNames(bestSeatingS)));
		System.out.println("Total cost: " + bestCostS + "\n\n");
		System.out.println("-----------------------------------------------------");

				
		////////////////////////
		// * <Hill Climbing> *//
		///////////////////////
		int[] bestSeatingH = hill.hillClimbing(100);
		double bestCostH = matrix.calculateCost(bestSeatingH);
		
		//print results
		System.out
				.println("Hill Climbing Best Seating: \n" + Arrays.toString(matrix.getArrangmentOfNames(bestSeatingH)));
		System.out.println("Total cost: " + bestCostH + "\n\n");
		System.out.println("-----------------------------------------------------");



	}

}
