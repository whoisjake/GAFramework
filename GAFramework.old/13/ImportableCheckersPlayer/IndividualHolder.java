/*
  IndividualHolder.java
  GAFramework
  Created by Patrick Burke on Sat Mar 09, 2002.

  Wrapper Class for individual:
    Purpose: Provides functionality of Fitness as related to GAIndividuals

*/

import java.io.*;

   public class IndividualHolder implements Serializable
   {
      private double fitness;
      private GAIndividual individual;
   
      public IndividualHolder (GAIndividual i) {
         individual = i;
      }
      public void setFitness(double fit) {
         fitness = fit;
      }
      public double getFitness () {
         return fitness;
      }
      public GAIndividual individual() {
         return individual;
      }
      
      public String toString()
      {
        return individual.toString();
      }
   }

