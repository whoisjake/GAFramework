//***********************************************
// file:   GAIndividual.java
// author: Ryan Dixon
// date:   Feb 8, 2002
// notes:  Interface for individuals belonging to
//         the GAStrategy Framework
//***********************************************
// updated: Pat Burke
// date: March 9, 2002
// notes: Key Instructions for creating FitnessFunctions:
//      *The following methods MUST be created:
//         mutate(double rate)       //mutates itself
//	   directReproduce() 	     //returns an asexually reproduced child
//	   reproduce(GAIndividual i)	//returns a sexually reproduced child
//	   toString()			//returns the representation of the individual
//***********************************************
// modified: PJ
// date:     March 11, 2002
// notes:    Altered for use with "Product Example"
//   !no longer reflected in this code example
//***********************************************
// updated: Pat Burke
// date: April 2, 2002
// notes: Tranferred fitness to the wrapper file.
//***********************************************

package edu.uni.GAFramework;

/**
 * <code>GAIndividual</code> is one of two key components of the GAApplication Framework.
 * <code>GAIndividual</code> is an interface that provides a protocol for
 * multiple methods of reproduction including mutation, direct reproduction, and asexual reproduction.
 * <br><br>
 * @see FitnessFunction
 *
 * @version 1.0
 * @author Pat Burke
 * @author Ryan Dixon
 * @author Jake Good
 * @author Jayapal Prabakaran
 */
public interface GAIndividual
{
    /** 
     * Method responsible for the mutation of the <code>GAIndiviudal</code> object.
     * @parameter rate Probability of the mutation occurring.
     * @see #directReproduce()
     * @see #reproduce( GAIndividual )
     */
    public void mutate(double rate);
    /**
     * Create a random instantiation of a <code>GAIndividual</code>.
     * @return A random instantiation of a <code>GAIndividual</code>.
     */
    public GAIndividual randomInstance();
    /**
     * Method responsible for asexual reproduction.
     * @return A new <code>GAIndividual</code> altered (in an undefined manner) from the parent.
     * @see #mutate( double )
     * @see #directReproduce()
     */
    public GAIndividual directReproduce();
    /**
     * Method responsible for reproducing with another <code>GAIndividual</code>.
     * @return A new <code>GAIndividual</code> with traits inherited from multiple parents.
     * @see #mutate( double )
     * @see #reproduce( GAIndividual )
     */
    public GAIndividual reproduce( GAIndividual i );
    /**
     * A method describing the features of the individual.
     * @return A concise description of the features of the individual.
     */
    public String toString();
}