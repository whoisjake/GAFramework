//***********************************************
// file:   FitnessFunction.java
// author: Patrick Burke
// date:   March 9, 2002
// notes:    Key Instructions for creating FitnessFunctions:
//      *The following method MUST be created:
//         evaluate(GAIndividual individual, Vector pop)
//***********************************************

package edu.uni.GAFramework;

// Commented imports for java 1.4 compatibility
// import GAIndividual;
import edu.uni.GAFramework.*;
import java.util.Vector;


/**
* <code>FitnessFunction</code> provides a means of evaluating a <code>GAIndividual</code> object
* with respect to a supplied population of <code>GAIndividual</code> objects.
*
* @version 1.0
* @author Pat Burke
* @author Ryan Dixon
* @author Jake Good
* @author Jayapal Prabakaran
*/
public interface FitnessFunction
{
    /** 
     * Evaluates a <code>GAIndividual</code> given the group of individuals <code>pop</code>.
     * @parameter individual <code>GAIndividual</code> to be evaulated with respect to the population.
     * @parameter pop Vector of <code>GAIndividual</code> objects
     * @return <code>double</code> rating assigned to <code>individual</code> with respect to the population.
     * @see GAIndividual
     */
    public double evaluate(GAIndividual individual, Vector pop);
}
