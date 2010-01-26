//***********************************************
// file:   IndividualHolder.java
// author: Pat Burke
// date:   April 2, 2002
// notes:  Wrapper class for individual.
//    purpse: provides functionality of Fitness as related to GAIndividuals
//***********************************************

package edu.uni.GAFramework;

import edu.uni.GAFramework.*;
import java.io.*;

/**
 * A wrapper class used for storage and universal inter-class parameter
 * passing of the <code>GAIndividual</code> object.
 * <br><p>This class is used to associate a fitness with a
 * <code>GAIndividual</code> object.
 *
 * @version 1.0
 * @author Pat Burke
 * @author Ryan Dixon
 * @author Jake Good
 * @author Jayapal Prabakaran
 */
public class IndividualHolder implements Serializable
{
    /** Fitness of the GAIndividual */
    private double fitness;
    /** <code>GAIndividual</code> object */
    private GAIndividual individual;

    /** Create a new IndividualHolder with an undefined fitness. */
    public IndividualHolder (GAIndividual i) {
        individual = i;
    }
    
    /** 
     * Associates a fitness with <code>GAIndividual</code> individual.
     * @parameter fit Associated <code>double</code> fitness for <code>individual</code>.
     * @see GAIndividual
     */
    public void setFitness(double fit) {
        fitness = fit;
    }
    
    /**
     * @return The assigned fitness for <code>individual</code>.
     * @see GAIndividual
     */
    public double getFitness () {
        return fitness;
    }
    
    /**
     * @return The <code>GAIndividual</code> object
     * @see GAIndividual
     */
    public GAIndividual individual() {
        return individual;
    }
    
    /**
     * @return A textual representation of the <code>GAIndividual</code> object
     * @see GAIndividual
     */
    public String toString()
    {
        return individual.toString();
    }
}

