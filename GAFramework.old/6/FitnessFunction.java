import java.util.Vector;

public interface FitnessFunction
{
	public double evaluate( GAIndividual individual, Vector pop);
}