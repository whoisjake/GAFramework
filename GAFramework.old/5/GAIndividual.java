//
//  GAIndividual.java
//  GAFramework
//
//  Created by Ryan Dixon on Fri Feb 08 2002.
//

public interface GAIndividual
{
    public void mutate(double rate);
    public GAIndividual directReproduce();
    public GAIndividual reproduce( GAIndividual i );
    
    public int getFitness();
    public void setFitness ( int fitness );
    public void setRoulette (int start, int finish);
    public boolean hasRouletteNumber (int winner);
    public String toString();
}

