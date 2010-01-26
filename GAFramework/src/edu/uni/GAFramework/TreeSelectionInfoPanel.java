//***********************************************
// file:   TreeSelectionInfoPanel.java
// author: Ryan Dixon
// date:   Feb 21, 2002
// notes:  Provides concise selection information
//         for a GAIndividual's fitness
//***********************************************
// updated: Ryan Dixon
// date     April 7, 2002
// notes:   Switched layout managers to more
//          appropriate GridLayout Manager
//***********************************************

package edu.uni.GAFramework;

import java.awt.Dimension;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


/**
 * 
 *
 * @version 1.0
 * @author Pat Burke
 * @author Ryan Dixon
 * @author Jake Good
 * @author Jayapal Prabakaran
 */
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
