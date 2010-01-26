//***********************************************
// file:   GAApplication.java
// author: Ryan Dixon
// date:   Feb 16, 2002
// notes:  An adaptable GUI permitting user interaction
//         in the Evolution process through file and parameter
//         selections.  View aspect of the MVC
//***********************************************

package edu.uni.GAFramework;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.JTree.*;
import java.util.*;
import java.lang.System;
import java.io.*;
import java.lang.reflect.*;


/**
 * A <code>GAApplication</code> object gives a Graphical User Interface to
 * the {@link edu.uni.GAFramework.GAStrategy} framework.  <br><br>
 * <code>GAApplication</code> is to be viewed with respect to the Model-View-Controller
 * pattern.  <code>GAApplication</code> provides a way through which tools can be added
 * to enchance the usability and view-ability of the {@link edu.uni.GAFramework.GAStrategy} framework.
 * <br><br><code>GAApplication</code> can be instantiated
 * via the command-line interface <code>java GAApplication</code> or
 * <code>java GAApplication FitnessFunction GAIndividual</code>
 *
 * @version 1.0
 * @author Pat Burke
 * @author Ryan Dixon
 * @author Jake Good
 * @author Jayapal Prabakaran
 */
public class GAApplication extends JFrame
{
    /** The <code>GAStrategy</code> based model for this pattern. */
    private GAStrategy	    model;
    /** The <code>GAObjectMonitor</code> based view for this pattern. */
    private GAObjectMonitor infoPanel;
    
    /**
     * Creates a new <code>GAApplication</code>.
     * Prompts the user (graphically) to select the appropriate <code>FitnessFunction</code>
     * and <code>GAIndividual</code> classes prior to execution.
     * @see FitnessFunction
     * @see GAApplication
     * @see GAApplication#GAApplication(String, String)
     */
    public GAApplication()
    {
        super();
        GAGenerationClassSelector selector = new GAGenerationClassSelector(this, "Select Appropriate Classes");

        String fitnessClass = selector.getSelection()[0];
        String individualClass = selector.getSelection()[1];
        initializeObjectMonitor(fitnessClass, individualClass);
    }
    
    
    /**
     * Creates a new <code>GAApplication</code> based on the two String class names
     * passed as parameters.
     * @param fitnessClass The <code>FitnessFunction</code> used to evaluate the specified individual.
     * @param individualClass The <code>GAIndividual</code> used for regeneration.
     * @see FitnessFunction
     * @see GAIndividual
     * @see GAApplication#GAApplication()
     */
    public GAApplication(String fitnessClass, String individualClass)
    {
        super();
        initializeObjectMonitor(fitnessClass, individualClass);
    }
    
    public static void main(String[] args)
    {
        // Set Look And Feel Theme to 'Metal'
        try {
        UIManager.setLookAndFeel (
            "javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (java.lang.ClassNotFoundException e) {
        } catch (java.lang.InstantiationException e) {
        } catch (java.lang.IllegalAccessException e) {
        } catch (javax.swing.UnsupportedLookAndFeelException e) {}
        
        if ( args.length == 2 )
            new GAApplication(args[0], args[1]);
        else
            new GAApplication();
            
        
        // Future Addition  --- Output Window for simple output logging
        
        // Redirect Standard Output Stream
        //System.setOut(new PrintStream(new PipedOutputStream()));
        
        //JFrame myOutWindow = new JFrame("System Out Piped");
        //JTextPane myOutPane = new JTextPane();
        //JScrollPane sp = new JScrollPane(myOutPane);
        
        //myOutWindow.getContentPane().add(myOutPane);
        //myOutWindow.setLocation(320,0);
        //myOutWindow.setSize(500,400);
        //myOutWindow.addWindowListener(new WindowAdapter()
        //    { public void windowClosing(WindowEvent e) {System.exit(0);} });
        //myOutWindow.setVisible(true);
    }
    
    protected void initializeObjectMonitor(String fitness, String individual)
    {
        try{
            Class fitnessClass    = Class.forName(fitness);
            Class individualClass = Class.forName(individual);
    
            model     = new GAStrategy(this, (FitnessFunction) fitnessClass.newInstance(), 
                                             (GAIndividual) individualClass.newInstance());
            infoPanel = new GAObjectMonitor("GAObjectMonitor", model);
        }
        catch (Exception evt){
            System.err.print("Error with command line parameters!\n\t");
            System.err.println(evt);
            System.exit(0);}
    }

}
