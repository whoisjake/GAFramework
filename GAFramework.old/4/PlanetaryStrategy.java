//  file: PlanetaryStrategy.java
//
//  Created by Ryan Dixon on Fri Feb 08 2002
//  edited by Jake Good on Sat Feb 16 2002
//  notes: changed nameing convention.. everything else stayed

import java.util.Vector;

public class PlanetaryStrategy extends GAStrategy
{
    public static void main (String [] args) {
        PlanetaryStrategy theStrategy = 
                         new PlanetaryStrategy();
        theStrategy.run();
    }
    private PlanetaryFitnessFunction ffunction;

    public PlanetaryStrategy() {
        super(25);
        Vector initPopulation = new Vector();
        for (int i = 0; i<35; i++) {
            initPopulation.addElement(new PlanetarySystem());
        }
        setPopulation (new GAPopulation(initPopulation));
        ffunction = new PlanetaryFitnessFunction();
    }
    protected void reproduce () {
        pop.crossover ();
        pop.mutate (.1);
    }

    protected void evaluate () {
        ffunction.evaluate (pop);
    }

    public String toString() {
        return "PlanetaryStrategy";
    }
    
}

