//
//  TreeSelectionInfoPanel.java
//  GAApplication
//
//  Created by Ryan Dixon on Thu Feb 21 2002.
//
//  Updated on Sun April 7 2002
//	Switched layout managers to more appropriate
//	GridLayout Manager.

import java.awt.Dimension;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class TreeSelectionInfoPanel extends JPanel
{   
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
        
        // Setup default (non-selection) Characteristics (border, layout, etc)
        setLayout(new GridLayout(4,1));
        setBorder(BorderFactory.createTitledBorder(this.getBorder(), "Selection Statistics",
                                                    TitledBorder.TOP, TitledBorder.CENTER));
        
        // Create Initial State Information
        childSelectionInfoLabel   = new JLabel();
        averageFitnessLabel 	  = new JLabel();
        bestFitnessLabel    	  = new JLabel();
        worstFitnessLabel   	  = new JLabel();
	setEmptySelection();
        
        add( childSelectionInfoLabel );
        add( averageFitnessLabel );
        add( bestFitnessLabel );
        add( worstFitnessLabel );
 
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
        //System.out.println( childSelectionInfoLabel.getText() + " " + averageFitnessLabel.getText() );
    }
    
    
}
