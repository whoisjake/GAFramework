import java.util.*;

public class Driver {
	public static void main (String [] args) {
		Framework theFramework = new Framework (
				new Population (initialize (50)),
				new SpecialFunction ());
		theFramework.evolve();
		for (int i = 0; i<100; i++) {
			theFramework.evolve();
		}
		theFramework.population().printBest(50);
	}
	private static Vector initialize (int num) {
		Vector v = new Vector ();
		for (int i=0; i < num; i++)
			v.addElement(new SpecialIndividual());
		return v;
	}
}
