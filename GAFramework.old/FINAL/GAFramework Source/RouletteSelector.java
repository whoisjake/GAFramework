//***********************************************
// file:   RouletteSelector.java
// author: Pat Burke
// date:   March 28, 2002
// notes:    Key Instructions for creating SelectionFunctions:
//         *The following method MUST be created:
//         selectParent(Vector pop)
//***********************************************

package edu.uni.GAFramework;
    
    // Commented imports for java 1.4 compatibility
    //import GAIndividual;
    import java.util.Vector;
    import java.lang.*;

/**
 * A supplied <code>SelectionFunction</code> that bases parent selection on the
 * principles of Roulette selection.  A heavier probability of selection is given to
 * the most fit individuals.
 * <code>RouletteSelector</code> is part of the strategy pattern design of <code>GAStrategy</code>.
 *
 * @version 1.0
 * @author Pat Burke
 * @author Ryan Dixon
 * @author Jake Good
 * @author Jayapal Prabakaran
 */
public class RouletteSelector implements SelectionFunction
{
    /** Total fitness for all individuals included in the population. */
    private double totalFitness;
    
    /**
     * Initializes the popluation for selection.
     * @parameter pop A <code>Vector</code> of <code>GAIndividual</code> objects.
     * @see GAIndividual
     */
    private void initialize (Vector pop) {  
        totalFitness = 0.0;
        for (int i = 0;i < pop.size();i++){
        totalFitness += ((IndividualHolder)pop.elementAt(i)).getFitness();
        }
    }
    
    /** 
     * Chooses an individual given the group of <code>GAIndividual</code> objects.
     * @parameter pop <code>Vector</code> of <code>GAIndividual</code> objects.
     * @return A randomly selected <code>GAIndividual</code> from the supplied population.
     * @see GAIndividual
     */
    public GAIndividual selectParent(Vector pop)
    {
        initialize (pop);
        double sumSoFar = 0.0;
        double goal = Math.random()+totalFitness;
    
        for (int i = 0; i < pop.size();i++)
        {
            if ((sumSoFar += ((IndividualHolder)pop.elementAt(i)).getFitness()) > goal)
                return ((IndividualHolder)pop.elementAt(i)).individual();
        }
        
        return ((IndividualHolder)pop.elementAt(0)).individual();
    }
}