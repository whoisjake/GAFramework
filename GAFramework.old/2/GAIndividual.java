//
//  GAIndividual.java
//  GAFramework
//
//  Created by Ryan Dixon on Fri Feb 08 2002.
//

public interface GAIndividual
{
    public GAIndividual mutate();
    public GAIndividual directReproduce();
    public GAIndividual reproduce( GAIndividual i );
    
    public int getFitness();
    public void setFitness ( int fitness );
    
    public String toString();
}

