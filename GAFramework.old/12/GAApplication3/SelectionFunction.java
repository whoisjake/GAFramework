/*
  SelectionFunction.java
  GAFramework

  Created by Patrick Burke on Sat Mar 9, 2002.
  Key Instructions for creating SelectionFunctions:
      *The following method MUST be created:
         selectParent(Vector pop)
*/
import java.util.Vector;
public interface SelectionFunction
{
	// this function chooses an individual given the group of individuals, "pop"
	public GAIndividual selectParent(Vector pop);
}