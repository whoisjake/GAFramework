//
//  SixteenBitPopulation.java
//  GAFramework
//
//  Created by Ryan Dixon on Fri Feb 08 2002.
//

public class SixteenBitPopulation extends GAPopulation
{
    private GAIndividual nextIndividual;
    private int maxSize;
    
    public SixteenBitPopulation( Vector population )
    {
        super();
        setPopulation( population.elements() );
        nextIndividual = null;
    }
    
    // Enumeration Interface Compliance
    public boolean hasNext()
    {
        if ( getPopulation().hasNext() )
            return true;
        
        return false;
    }
    
    public Object nextElement() throws NoSuchElementException
    {
        if ( getPopulation().hasNext() )
            return getPopulation().nextElement();
        
        throw new NoSuchElementException();
    }
    
    // Burke & Good implementations
        public Individual select(int i)
    {
        return (Individual) population.elementAt(i);
    }

    public Individual selectRandom()
    {
        int seed = (int) (Math.random()*population.size());
        return (Individual) population.elementAt(seed);
    }

    public int populationSize()
    {
        return population.size();
    }

    public void reproduce()
    {
        for(int i=(population.size()/4); i < population.size() - 1; i++)
        {
            ((Individual)population.elementAt(i)).reproduce();
        }
    }

    public void printBest(int num)
    {
        System.out.println("Best" + num);
        for(int i=0; i<num; i++)
        {
            ((Individual) population.elementAt(i)).print();
        }
    }

    public void sort()
    {
        Individual best;
        Individual temp;
        int spot=0;
        
        for(int out=0; out<population.size(); out++)
        {
            best = (Individual) population.elementAt(out);
            spot = out;
            for(int in=out; in<population.size(); in++)
            {
                if(((Individual) population.elementAt(in)).fitness() > best.fitness())
                {
                        best=(Individual) population.elementAt(in);
                        spot=in;
                }
            }
            temp= (Individual) population.elementAt(out);
            population.removeElement (best);
            population.insertElementAt(best, out); 
        }
    }
}

