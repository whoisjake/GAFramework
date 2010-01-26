/*
  GAIndividual.java
  GAFramework
  Created by Ryan Dixon on Fri Feb 08 2002.
  Modified by Patrick Burke on Sat Mar 09, 2002.

  Key Instructions for creating FitnessFunctions:
      *The following methods MUST be created:
         mutate(double rate) 	//mutates itself
			directReproduce() 	//returns an asexually reproduced child
			reproduce(GAIndividual i)	//returns a sexually reproduced child
			toString()						//returns the representation of the individual
*/

   public interface GAIndividual
   {
      public void mutate(double rate);
      public GAIndividual randomInstance();
      public GAIndividual directReproduce();
      public GAIndividual reproduce( GAIndividual i );
      public String toString();
   }


