//
//  GAPopulation.java
//  GAFramework
//
//  Created by Ryan Dixon on Fri Feb 08 2002.
//

import java.util.*;

public abstract class GAPopulation implements Enumeration
{
    private Enumeration  individuals;
    
    public GAPopulation()
    {
        super();
        individuals    = null; 
        nextIndividual = null;  // null if next is not known!
    }
    
    // Enumeration Interface Compliance
    public boolean hasNext()
    {
        return false;
    }
    
    public Object nextElement() throws NoSuchElementException
    {
        if ( hasNext() )
            return null;
        else
            throw new NoSuchElementException();
    }
    
    // Protected Accessor Methods
    public Enumeration getPopulation()
    {
        return individuals;
    }
    
    public void setPopulation( Enumeration population )
    {
        individuals = population;
    }
    
}

