// File : GAPopulation.java
// Created by Patrick Burke
// Last modified 2/15/02 by Patrick Burke
/* This class needs not to be altered, or subclassed when implementing
   the Genetic Framework  */

import java.util.Vector;
import java.lang.*;
import java.io.*;

public class GAPopulation implements Serializable {

    private int maxSize;  // maximum size of the population
    private Vector individuals; // all of the individuals of the population
    // General constructor accepts an initial population as input
    public GAPopulation(Vector initPopulation) {
        maxSize = initPopulation.size();
        individuals = initPopulation;
    }
    // Select method for pulling individuals out of the population
    public GAIndividual select(int i) {
        return (GAIndividual) individuals.elementAt(i);
    }
    // Select method for pulling individuals out at random
    public GAIndividual selectRandom() {
        int seed = (int) (Math.random()*individuals.size());
        return (GAIndividual) individuals.elementAt(seed);
    }
    // accessor method for the size of the population
    public int populationSize() {
        return individuals.size();
    }
    /* crossover directive that uses roulette parent selection
            this is only one type of crossover
            .... other methods to be decided later
    */
    public void crossover () {
        int sumSoFar = 0;
        for (int i = 0;i < individuals.size();i++){
            ((GAIndividual)individuals.elementAt(i)).setRoulette(sumSoFar,
                                         sumSoFar += ((GAIndividual)individuals.elementAt(i)).getFitness());
        }
        for (int i = 0; i < individuals.size()/4; i ++) {
            individuals.addElement(findParent(((int)(Math.random()*(individuals.size()-1)))).reproduce
                                    (findParent((int)(Math.random()*(individuals.size()-1)))));
        }
    }
    // this method locates the parent using the integer value given
    private GAIndividual findParent (int value) {
        for (int i = 0; i < individuals.size();i++) {
            if (((GAIndividual)individuals.elementAt(i)).hasRouletteNumber(value))
                return (GAIndividual)individuals.elementAt(i);
        }
        return (GAIndividual)individuals.elementAt(0);
    }
    // this method KILLS off the excess individuals in the population
    public void trim () {
       individuals.setSize(maxSize);
    }
    // mutation directive that accepts the mutation rate as input
    // this method passes the message on to the individuals of the population
    public void mutate (double rate) {
        for(int i=0; i < individuals.size(); i++) {
            ((GAIndividual)individuals.elementAt(i)).mutate(rate);
        }
    }
    // prints the best <num> members of the population, according to
    // position in the population
    public void printBest(int num) {
        System.out.println("Best" + num);
        for(int i=0; i<num; i++) {
            System.out.println(((GAIndividual) individuals.elementAt(i)).toString());
        }
    }
    // sorts the individuals of the population according to fitness
    public void sort() {
        GAIndividual best;
        GAIndividual temp;
        int spot=0;
        for(int out=0; out<individuals.size(); out++){
            best = (GAIndividual) individuals.elementAt(out);
            spot = out;
            for(int in=out; in<individuals.size(); in++) {
                if(((GAIndividual) individuals.elementAt(in)).getFitness() > best.getFitness()) {
                    best=(GAIndividual) individuals.elementAt(in);
                    spot=in;
                }
            }
            temp= (GAIndividual) individuals.elementAt(out);
            individuals.removeElement (best);
            individuals.insertElementAt(best, out); 
        }
    }

}
