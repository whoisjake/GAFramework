//
//  TreeSelectionInfoPanel.java
//  GAApplication
//
//  Created by Ryan Dixon on Thu Feb 21 2002.
//

import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.*;

public class TreeSelectionInfoPanel extends JPanel
{
    // Private Static Variables
    private static int WIDTH  = 320;
    private static int HEIGHT = 20;
    
    // Private Final Variables
    private final String childInfoLabel =
        "Items Selected: ";
    private final String avgLabel  =
        "Average Total Fitness: ";
    private final String bestLabel =
        "Best Individual Fitness: ";
    private final String worstLabel =
        "Worst Individual Fitness: ";
        
    // Private Non-Static Variables
    private JLabel childSelectionInfoLabel;
    private JLabel averageFitnessLabel;
    private JLabel bestFitnessLabel;
    private JLabel worstFitnessLabel;
    
    public TreeSelectionInfoPanel()
    {
        super();
        
        // Setup default Component preferences
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setMaximumSize  (new Dimension(WIDTH, HEIGHT));
        setMinimumSize  (new Dimension(WIDTH, HEIGHT));
        
        // Setup default (non-selection) Characteristics (border, layout, etc)
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder(this.getBorder(), "Selection Statistics",
                                                    TitledBorder.TOP, TitledBorder.CENTER));
        
        
        // Create Initial State Information
        childSelectionInfoLabel   = new JLabel();
        averageFitnessLabel 	  = new JLabel();
        bestFitnessLabel    	  = new JLabel();
        worstFitnessLabel   	  = new JLabel();
	setEmptySelection();
        
        // Add Labels to layout using 'glue' as seperator
        add( Box.createGlue() );
        add( childSelectionInfoLabel );
        add( Box.createGlue() );
        add( averageFitnessLabel );
        add( Box.createGlue() );
        add( bestFitnessLabel );
        add( Box.createGlue() );
        add( worstFitnessLabel );
        add( Box.createGlue() );
 
    }
    
    // Public Methods
    public void setEmptySelection()
    {
        // < no items selected >
        childSelectionInfoLabel.setText ( childInfoLabel + "0" );
        averageFitnessLabel.setText( avgLabel + "--" );
        bestFitnessLabel.setText   ( bestLabel + "--" );
        worstFitnessLabel.setText  ( worstLabel + "--" );
    }
    
    public void setInfoLabel(int numberSelected)
    {	
        childSelectionInfoLabel.setText( childInfoLabel + numberSelected );
    }
    
    public void setSelectionStats( int childrenSelected,
                                   double avgFitness, double bestFitness,
                                   double worstFitness )
    {
        childSelectionInfoLabel.setText ( childInfoLabel + childrenSelected );
        averageFitnessLabel.setText( avgLabel + avgFitness );
        bestFitnessLabel.setText   ( bestLabel + bestFitness );
        worstFitnessLabel.setText  ( worstLabel + worstFitness );
    }
    
    
}
