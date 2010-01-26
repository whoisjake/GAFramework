//***********************************************
// file:   SelectionFunction.java
// author: Pat Burke
// date:   March 28, 2002
// notes:    Key Instructions for creating SelectionFunctions:
//         *The following method MUST be created:
//         selectParent(Vector pop)
//***********************************************

package edu.uni.GAFramework;

// Commented imports for java 1.4 compatibility
//import GAIndividual;
import edu.uni.GAFramework.*;
import java.util.Vector;

/**
 * An interface created for interaction with the GAStrategy strategy pattern design.
 *  
 * @see GAStrategy
 *
 * @version 1.0
 * @author Pat Burke
 * @author Ryan Dixon
 * @author Jake Good
 * @author Jayapal Prabakaran
 */
public interface SelectionFunction
{
    // this function chooses an individual given the group of individuals, "pop"
    /**
        * Chooses an individual given the group a population.
        * @parameter pop Population of <code>GAIndividual</code> objects to be used for selection.
        * @return <code>GAIndividual</code> object selected from the supplied population.
        * @see GAIndividual
        */
    public GAIndividual selectParent(Vector pop);
}