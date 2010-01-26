import java.util.Vector;
import java.lang.*;

public class RouletteSelector implements SelectionFunction
{
	private double totalFitness;
	private void initialize (Vector pop)
	{
		totalFitness = 0.0;
		for (int i=0; i < pop.size() ; i++)
		{
			totalFitness += ((IndividualHolder) pop.elementAt(i)).getFitness();
		}

	}

	public GAIndividual selectParent(Vector pop)
	{
		initialize (pop);
		double sumSoFar = 0.0;
		double goal = Math.random() * totalFitness;
		
		for (int i = 0; i < pop.size();i++)
		{
			if ((sumSoFar += ((IndividualHolder)pop.elementAt(i)).getFitness()) > goal)
			{
				return ((IndividualHolder)pop.elementAt(i)).individual();
			}
		}
		return ((IndividualHolder)pop.elementAt(0)).individual();
		
	}
}
