
public class SpecialFunction implements FitnessFunction
{
	public void evaluate (GAPopulation pop)
	{
		for(int i=0; i < pop.populationSize(); i++)
			evaluate((SpecialIndividual) pop.select(i));
		pop.sort();
		pop.trim();
	}
	
	private void evaluate (SpecialIndividual subject)
	{
		
		int sum=0;
		int [] bitString = subject.body();
		
		for(int bit=0; bit < 16; bit++)
		{
			sum+=bitString[bit];
		}
		
		subject.setFitness(sum);
	}
}
