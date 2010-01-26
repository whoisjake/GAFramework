/*
  FitnessFunction.java
  GAFramework

  Created by Patrick Burke on Sat Mar 9, 2002.
  Key Instructions for creating FitnessFunctions:
      *The following method MUST be created:
         evaluate(GAIndividual individual, Vector pop)
*/
   import java.util.Vector;
   public interface FitnessFunction
   {
   // this function evaluates an individual given the group of individuals, "pop"
   // "pop" must be supplied, but the fitness function may choose to ignore it.
      public double evaluate(GAIndividual individual, Vector pop);
   }
