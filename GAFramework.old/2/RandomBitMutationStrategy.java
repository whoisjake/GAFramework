//
//  RandomBitMutationStrategy.java
//  GAFramework
//
//  Created by Ryan Dixon on Fri Feb 08 2002.
//

public class RandomBitMutationStrategy implements GAStrategy
{
    public RandomBitMutationStrategy()
    {
        super();
    }
    
    public void run()
    {
        System.out.println("Running the evolution strategy!");
//        pop.reproduce ();
//        ffunction.evaluate (pop);
//        pop.printBest(5);
    }
    
    public String toString()
    {
        return "RandomBitMutationStrategy";
    }
    
}

