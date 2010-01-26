public class SpecialFunction implements FitnessFunction
{
	public void evaluate (Population pop)
	{
		for(int i=0; i < pop.populationSize(); i++)
			evaluate((SpecialIndividual) pop.select(i));
		pop.sort();
	}
	
	public void evaluate (SpecialIndividual subject)
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
