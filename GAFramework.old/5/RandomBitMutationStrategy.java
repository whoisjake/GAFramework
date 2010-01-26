//
//  RandomBitMutationStrategy.java
//  GAFramework
//
//  Created by Ryan Dixon on Fri Feb 08 2002.
//

import java.util.Vector;

public class RandomBitMutationStrategy extends GAStrategy
{
    public static void main (String [] args) {
        RandomBitMutationStrategy theStrategy = 
                         new RandomBitMutationStrategy();
        theStrategy.run();
    }
    private SpecialFunction ffunction;

    public RandomBitMutationStrategy() {
        super(10);
        Vector initPopulation = new Vector();
        for (int i = 0; i<500; i++) {
            initPopulation.addElement(new SpecialIndividual());
        }
        setPopulation (new GAPopulation(initPopulation));
        ffunction = new SpecialFunction();
    }
    protected void reproduce () {
        pop.crossover ();
        pop.mutate (.0008);
    }

    protected void evaluate () {
        ffunction.evaluate (pop);
    }

    public String toString() {
        return "RandomBitMutationStrategy";
    }
    
}

