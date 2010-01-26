//  file: PlanetaryFitnessFunction.java
//
//  Created by Patrick Burke on Fri Feb 15 2002
//  edited by Jake Good on Sat Feb 16 2002
//
//  notes: changed to fit Planetary Motion problem
//         as defined in sessions 19 and 20 of the
//         Eugene Wallingfords AI course


public class PlanetaryFitnessFunction implements FitnessFunction
{
	private double [] A = { .72, 1.0, 1.52, 5.2, 9.53, 19.1 };
	private double [] actualP = { .61, 1.0, 1.84, 11.9, 29.4, 83.5 };
 	
	public void evaluate (GAPopulation pop)
	{
		for(int i=0; i < pop.populationSize(); i++)
			evaluate((PlanetarySystem) pop.select(i));
		pop.sort();
		pop.trim();
	}
	
	private void evaluate (PlanetarySystem subject)
	{

		MathEvaluator testCase = new MathEvaluator(subject.toString());;
		double totalError=0;
		
		for(int a=0; a<6; a++)
		{
			testCase.addVariable("A", A[a]);
			totalError -= Math.abs(actualP[a] - (testCase.getValue()).doubleValue());
		}

		subject.setFitness(totalError);

	}

}
