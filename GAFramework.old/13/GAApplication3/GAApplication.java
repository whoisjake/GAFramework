//
//  GAApplication.java
//  GAApplication
//
//  Created by Ryan Dixon on Sat Feb 16 2002.
//

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

public class GAApplication extends JFrame
{
    GAStrategy	    model;
    GAObjectMonitor infoPanel;
    
    public GAApplication()
    {
        super();
        GAGenerationClassSelector selector = new GAGenerationClassSelector(this, "Select Appropriate Classes");

        String fitnessClass = selector.getSelection()[0];
        String individualClass = selector.getSelection()[1];
        initializeObjectMonitor(fitnessClass, individualClass);
    }
    
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
            
        
        // Redirect Standard Output Stream
        //System.setOut(new PrintStream(new PipedOutputStream()));
        
        JFrame myOutWindow = new JFrame("System Out Piped");
        JTextPane myOutPane = new JTextPane();
        JScrollPane sp = new JScrollPane(myOutPane);
        
        myOutWindow.getContentPane().add(myOutPane);
        myOutWindow.setLocation(320,0);
        myOutWindow.setSize(500,400);
        myOutWindow.addWindowListener(new WindowAdapter()
            { public void windowClosing(WindowEvent e) {System.exit(0);} });
        //myOutWindow.setVisible(true);
    }
    
    protected void initializeObjectMonitor(String fitness, String individual)
    {
        try{
            Class fitnessClass    = Class.forName(fitness);
            Class individualClass = Class.forName(individual);
    
            model     = new GAStrategy(this, (FitnessFunction) fitnessClass.newInstance(), (GAIndividual) individualClass.newInstance());
            infoPanel = new GAObjectMonitor("GAObjectMonitor", model);
        }
        catch (Exception evt){
            System.err.print("Error with command line parameters!\n\t");
            System.err.println(evt);
            System.exit(0);}
    }

}
