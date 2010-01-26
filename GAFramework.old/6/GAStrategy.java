//
//  GAStrategy.java
//  GAApplication
//
//  Implemented by Ryan Dixon on Sun March 10 2002.
//  ->	See original GAStrategy for previous comments/modifications

import javax.swing.*;
import javax.swing.event.*;
import java.util.Vector;

public class GAStrategy implements Runnable
{
    /* Added for Listener modifications
        Ryan Dixon March 10 2002 */
    private EventListenerList changeListeners;

    private Vector population;
    private int numIterations;
    private FitnessFunction function;
    private int maxSize;
    private int numReproduce;
    private double mutationRate;
    private SelectionFunction parentSelector;
    
    
    // General Constructor
    public GAStrategy (FitnessFunction f, GAIndividual foreFather) {
        changeListeners = new EventListenerList();
        numIterations = 50;
        maxSize = 100;
        numReproduce = 25;
        mutationRate = 0.025;
        parentSelector = new RouletteSelector();
        function = f;
        population = new Vector();
        try {
        for (int i = 1; i <= maxSize; i++)
            population.addElement (new IndividualHolder(foreFather.randomInstance()));
        } 
        catch (Exception e){
            System.out.println("can't create new individual");}
    }
    
    // Running method of GAStrategy
    public void run() {
        System.out.println("Running the evolution strategy!");
        evaluate ();
        printBest(5);
        
        /* Important Thread control starts here!
           Evolve method has been removed!
           Ryan Dixon March 10 2002 */
        for (int i = 0; i < numIterations; i++)
        {
            reproduce();
            fireChange();
            evaluate();
            
            // Needed for proper thread execution!
            // improper data recording can occur if set too low!
            try{
                Thread.sleep(25);
            } catch (java.lang.InterruptedException e) {};
        }

        System.out.println("Evolution complete: ");
        printBest(5);
    }
    
    /* Description used for naming the generations
        UI enhancement, should be overloaded with a descriptive
        title for the generations
        Ryan Dixon March 10 2002 */
    public String getGenerationName()
    {
        return "Generations";
    }

    /* Added for Listener support, listeners will call this method
        to obtain most recent version of the population
        Ryan Dixon March 10 2002 */
    public synchronized Vector getMostRecentGeneration()
    {
        Vector top25MostRecent = new Vector();
        for (int i = 0; i < 25; i++)
        {
            top25MostRecent.addElement( population.elementAt(i) );
        }
        
        return top25MostRecent;
    }
    
    public double getFitness( IndividualHolder ind )
    {
        return ind.getFitness();
    }
    /* Modifications necessary for implementing Listeners
        no need to overload any of the following methods:
        addChangeListener, removeChangeListener, and fireChange
        Ryan Dixon March 10 2002 */

    // Listener notification support
    public void addChangeListener(ChangeListener x)
    {
        changeListeners.add (ChangeListener.class, x);
    }
    
    public void removeChangeListener(ChangeListener x)
    {
        changeListeners.remove (ChangeListener.class, x);
    }
    
    protected void fireChange()
    {
        // Create the event:
        ChangeEvent c = new ChangeEvent(this);
        
        // Get the listener list
        Object[] listeners = changeListeners.getListenerList();
        
        // Process the listeners last to first
        // List is in pairs, Class and instance
        for (int i = listeners.length-2; i >= 0; i -= 2)
        {
            if (listeners[i] == ChangeListener.class)
            {
                ChangeListener cl = (ChangeListener)listeners[i+1];
                cl.stateChanged(c);
            }
        }
    }


    // Used by subclasses to initialize population
    protected void setPopulation (Vector population) {
        population = population;
    }


    // includes a call to the fitness function with population as a context
    protected void evaluate () {
        for (int i=0;i<population.size();i++) {
        ((IndividualHolder)population.elementAt(i)).setFitness( function.evaluate
                                (((IndividualHolder)population.elementAt(i)).individual(),population));
        }
        sort();
        trim();
    }

    // Default reproduction process : can be overloaded to change defaults
    protected void reproduce() {
        for (int i=0;i<numReproduce;i++) {
        population.addElement(new IndividualHolder(
                                                parentSelector.selectParent(population).reproduce
                                                    (parentSelector.selectParent(population))));
        ((IndividualHolder)population.elementAt
                    (population.size()-1)).individual().mutate(mutationRate);
        }         
    
    }

    private void printBest(int num) {
        System.out.println("Best" + num);
        for(int i=0; i<num; i++) {
        System.out.println(((IndividualHolder)population.elementAt(i)).individual().toString());
        }
    }

    private void sort() {
        IndividualHolder best;
        IndividualHolder temp;
        int spot=0;
        for(int out=0; out<population.size(); out++){
        best = (IndividualHolder) population.elementAt(out);
        spot = out;
        for(int in=out; in<population.size(); in++) {
            if(((IndividualHolder) population.elementAt(in)).getFitness() > best.getFitness()) {
                best=(IndividualHolder) population.elementAt(in);
                spot=in;
            }
        }
        temp= (IndividualHolder) population.elementAt(out);
        population.removeElement (best);
        population.insertElementAt(best, out); 
        }
    }

    public void trim () {
        population.setSize(maxSize);
        //population.setSize(population.size()-1);
    }

    // General Information
    public String toString(){
        return "GAStrategy";}

}
