//
//  GAStrategy.java
//  GAFramework
//
//  Created by Ryan Dixon on Fri Feb 08 2002.
//  Modified by Patrick Burke on Fri Feb 15 2002.
/* Key Instructions for creating subclasses of GAStrategy:
      *The following methods MUST be overloaded:
         Constructor
         evaluate()
      *The following are default methods and can be overridden:
         run()
         evolve()
         reproduce()
*/
   import java.io.*;
    public abstract class GAStrategy
   {
      protected GAPopulation pop;
      protected int numIterations;
      protected GAGenerations generations;
    // General Constructor : must be decorated
       public GAStrategy (int iterations) {
         numIterations = iterations;
         generations = new GAGenerations();
      }
    // Running method of GAStrategy : can be overloaded to change defaults
       public void run() {
         System.out.println("Running the evolution strategy!");
         evaluate ();
			try {
               generations.savePopulation(pop);
            } 
                catch (IOException e) {
                  System.err.println("Caught IOException: " + e.getMessage());
         } 
         pop.printBest(5);
         evolve();
         System.out.println("Evolution complete: ");
         pop.printBest(15);
			try {
              	pop = generations.getPopulation(0);            
			} 
         catch (IOException e) {
				System.err.println("Caught IOException: " + e.getMessage());
         }
			catch (ClassNotFoundException e) {
				System.err.println("Caught ClassNotFoundException: " + e.getMessage());
         }   
		
			pop.printBest(15);
      }
    // Used by subclasses to initialize population
       protected void setPopulation (GAPopulation population) {
         pop = population;
      }
    // abstract method, special to subclasses.
    // Generally should include a call to the fitness function
       protected void evaluate () {}
    // Default method for evolve : can be overloaded to change defaults
       protected void evolve () {
         for (int i = 0; i < numIterations; i++) {
            reproduce();
            evaluate();
            try {
               generations.savePopulation(pop);
            } 
                catch (IOException e) {
                  System.err.println("Caught IOException: " + e.getMessage());
               }          
         }
      }
    // Default reproduction process : can be overloaded to change defaults
       protected void reproduce() {
         pop.crossover();
         pop.mutate(0.0008);
      }
   
    // General Information
       public String toString(){
         return "GAStrategy";}
   
   }

