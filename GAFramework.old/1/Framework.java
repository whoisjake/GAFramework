public class Framework {
	private FitnessFunction ffunction;
	private Population pop;
	public Framework (Population p, FitnessFunction fit) {
		pop = p;
		ffunction = fit;
		ffunction.evaluate (pop);
	}

	public void evolve () {
		pop.reproduce ();
		ffunction.evaluate (pop);
		pop.printBest(5);
	}

	public Population population () {
		return pop;
	}
}	
