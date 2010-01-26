//
//  GAStrategy.java
//  GAApplication
//
//  Implemented by Ryan Dixon on Sun March 10 2002.
//  ->	See original GAStrategy for previous comments/modifications
//  Update on Sat April 13 2002
//  ->  Update provides user interface for configuring variables
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Vector;
import java.io.*;
//import GAIndividual;
//import FitnessFunction;
//import SelectionFunction;
//import RouletteSelector;
//import TitledSliderPanel;

public class GAStrategy implements Runnable
{
    /* Added for Listener modifications
        Ryan Dixon March 10 2002 */
    private EventListenerList changeListeners;
    private Frame parentFrame;
    private Vector population;
    private int numIterations;
    private FitnessFunction function;
    private int maxSize;
    private int numReproduce;
    private double mutationRate;
    private SelectionFunction parentSelector;
    
    // General Constructor
    public GAStrategy (Frame frame, FitnessFunction f, GAIndividual foreFather) {
        parentFrame = frame;
        changeListeners = new EventListenerList();
        numIterations = 50;
        maxSize = 100;
        numReproduce = 25;
        mutationRate = 0.025;
        parentSelector = new RouletteSelector();
        function = f;
        population = new Vector();
        
        getUserParameterPreferences();
            
        try {
            
            if ( population.size() > maxSize )
                population.setSize(maxSize);
            else
            {
                int requiredIterations = maxSize - (population.size()-1);
                
                for (int i = 0; i < requiredIterations; i++)
                    population.addElement (new IndividualHolder(foreFather.randomInstance()));
            }
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
	// Report population according to 
        Vector mostRecentReproduced = new Vector();
        
	for (int i = 0; i < numReproduce; i++)
        {
            mostRecentReproduced.addElement( population.elementAt(i) );
        }
        
        return mostRecentReproduced;
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
        this.population = population;
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
    
    
    // Helper Methods
    private void getUserParameterPreferences()
    {
        final JDialog strategyFrame = new JDialog(parentFrame, "Adjust Strategy Variables", true);
        strategyFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        strategyFrame.getContentPane().setLayout(new GridBagLayout());
        strategyFrame.setResizable(false);
        
        final TitledSliderPanel iterationsPanel   	= 
            new TitledSliderPanel("Number of Iterations", JSlider.HORIZONTAL, 0, 100, numIterations, null);
        final TitledSliderPanel sizePanel        	=
            new TitledSliderPanel("Maximum Size", JSlider.HORIZONTAL, 0, 100, maxSize, null);
        final TitledSliderPanel reproducePanel    	=
            new TitledSliderPanel("Number of Reproductions", JSlider.HORIZONTAL, 0, 100, numReproduce, null);
        final TitledSliderPanel mutationRatePanel 	=
            new TitledSliderPanel("Mutation Rate Probability", JSlider.HORIZONTAL, 0.0, 1.0, mutationRate, 0.025, null);
        final FileBrowserPanel  initialPopulationPanel	= 
            new FileBrowserPanel("Select A 'Population' File", ".*");
        
        // Configure GridBagConstraints
        GridBagConstraints c		    = new GridBagConstraints();
        c.gridx		= GridBagConstraints.RELATIVE;
        c.gridy		= 0;
        c.gridwidth	= 1;
        c.gridheight	= 1;
        c.weightx	= .5;
        c.weighty	= 0.5;
        c.fill		= GridBagConstraints.HORIZONTAL;
        c.anchor	= GridBagConstraints.EAST;
        c.insets	= new Insets(0,0,0,1);
        
        // Add Components to frame and display frame
        strategyFrame.getContentPane().add(iterationsPanel, c);
        strategyFrame.getContentPane().add(sizePanel, c);
        
        c.gridy = 1;
        strategyFrame.getContentPane().add(reproducePanel, c);
        strategyFrame.getContentPane().add(mutationRatePanel, c);
        
        // Add execution button
        JButton executeButton = new JButton("Execute");
        executeButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                numIterations = (int)iterationsPanel.getValue();
                maxSize       = (int)sizePanel.getValue();
                numReproduce  = (int)reproducePanel.getValue();
                mutationRate  = mutationRatePanel.getValue();
                
                if ( initialPopulationPanel.getFilePath() != null )
                    population = getPopulationData( initialPopulationPanel.getFilePath() );
                
                strategyFrame.setVisible(false);
            }});
        
        c.gridy     = 2;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weighty   = 0.0;
        strategyFrame.getContentPane().add(initialPopulationPanel, c);
        
        c.gridy     = 3;
        c.anchor    = GridBagConstraints.SOUTH;
        strategyFrame.getContentPane().add(executeButton, c);

        // Set optimal size and then display dialog
        strategyFrame.pack();
        strategyFrame.setVisible(true);
    }
    
    private Vector getPopulationData( String filePath )
    {
        Vector initialPopulation = new Vector();
        
        try{
            FileInputStream fileStream = new FileInputStream(filePath); 
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
        
            while( fileStream.available() > 1 )
            {
                initialPopulation.addElement( objectStream.readObject() );
            }
            
            objectStream.close();
            fileStream.close();
            
        } catch(IOException e2) {System.err.println(e2);
                                System.exit(0);}
          catch(Exception evt) {System.err.println(evt);
                                System.exit(0);}
                                
        return initialPopulation;
    }
    
    
    // Private inner classes
    private class ConstantsInitializationFrame extends JFrame
    {
        JSlider iterationsSlider;
        JSlider maxSizeSlider;
        JSlider numReproductionsSlider;
        JSlider mutationRateSlider;
        
        public ConstantsInitializationFrame ()
        {
            // Total number of iterations to be performed
            iterationsSlider 	   = new JSlider(JSlider.HORIZONTAL, 0, 100,  50);
            iterationsSlider.setPaintTicks(true);
            iterationsSlider.setMajorTickSpacing(1);
            
            // Maximum population size
            maxSizeSlider    	   = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
            maxSizeSlider.setPaintTicks(true);
            maxSizeSlider.setMajorTickSpacing(25);
            maxSizeSlider.setMinorTickSpacing(1);
            
            // Maximum number of reproductions to be performed
            numReproductionsSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 25);
            numReproductionsSlider.setPaintTicks(true);
            numReproductionsSlider.setMajorTickSpacing(25);
            numReproductionsSlider.setMinorTickSpacing(1);
            
            // Frequency of inter-generation mutation
            mutationRateSlider     = new JSlider(JSlider.HORIZONTAL, 0, 40, 1);
            mutationRateSlider.setPaintTicks(true);
            mutationRateSlider.setMajorTickSpacing(8);
            mutationRateSlider.setMinorTickSpacing(1);
            
            // Construct and display the window frame
            this.setSize(450,450);
            this.getContentPane().setLayout(new GridLayout(2,2,1,1));
            
            
            this.add(iterationsSlider);
            this.add(maxSizeSlider);
            this.add(numReproductionsSlider);
            this.add(iterationsSlider);
        }
        
    }

}
