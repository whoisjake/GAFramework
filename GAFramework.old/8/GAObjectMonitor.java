//
//  GAObjectMonitor.java
//  
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

public class GAObjectMonitor extends JFrame
{
    // Static Variables
    private static int WIDTH  = 320;
    private static int HEIGHT = 700;
    
    // Non-static Variables
    private GAGenerationTreePanel   infoPanel;
    private GAStrategy  	    model;
    private TreeSelectionInfoPanel  selectionInfoPanel;
    
    public GAObjectMonitor(String title)
    {
        super(title);
        
        // Create Text Info area for highlighted objects
        selectionInfoPanel = new TreeSelectionInfoPanel();
        getContentPane().add(selectionInfoPanel,BorderLayout.CENTER);
        
        // Construct model
        model = new GAStrategy(new CheckersFitness(),new CheckersEvaluator());
        
        // Construct infoPanel (JTree Panel View)
        if (model.getGenerationName() != null)
            infoPanel = new GAGenerationTreePanel(this, model.getGenerationName());
        else
            infoPanel = new GAGenerationTreePanel(this, "Generations");
        
        // Create Frame Layout, add two Panels
        getContentPane().add(infoPanel,BorderLayout.NORTH);
        
        // Create Button For Thread Interation
        JButton threadControl = new JButton("Pause");
        getContentPane().add(threadControl,BorderLayout.SOUTH);
        
        // Start the object model thread
        Thread modelThread = new Thread(model);
        modelThread.start();
        
        // Add ThreadListener to button
        ThreadControlListener tcl =
            new ThreadControlListener(threadControl,modelThread);
            
        threadControl.addActionListener( tcl );
        
        // Hook up the infoPanel View up to the Model
        IndObjectToJTreeAdapter MV = new IndObjectToJTreeAdapter(model,infoPanel);
        model.addChangeListener(MV);
    }
    
    public static void main(String args[])
    {
        GAObjectMonitor objectBrowser = new GAObjectMonitor("GAObjectMonitor");
        
        objectBrowser.setSize(WIDTH, HEIGHT);
        objectBrowser.setVisible(true);
        
        // (Simple) Window Listeners
        objectBrowser.addWindowListener(new WindowAdapter()
            { public void windowClosing(WindowEvent e) {System.exit(0);} });

    }
    
    // Public Methods
    public double getFitness( Object node )
    {
        return model.getFitness( (IndividualHolder) node );
    }
    
    // Public Accessors
    public TreeSelectionInfoPanel getSelectionInfoPanel()
    {
        return selectionInfoPanel;
    }

    // Thread Control
    private class ThreadControlListener implements ActionListener
    {
        private JButton button;
        private Thread  thread;
        
        public ThreadControlListener( JButton jb, Thread t )
        {
            super();
            button = jb;
            thread = t;
        }
        
        public void actionPerformed(ActionEvent e)
        {
            if ( button.getText() == "Pause" )
            {
                button.setText("Resume");
                thread.suspend();
            }
            else
            {
                button.setText("Pause");
                thread.resume();
            }
        }
    }
      
    // Model Adapters
    
    private class IndObjectToJTreeAdapter implements ChangeListener
    {
        private GAStrategy		model;
        private GAGenerationTreePanel	panel;
        
        public IndObjectToJTreeAdapter(GAStrategy m, GAGenerationTreePanel p)
        {
            super();
            model = m;
            panel = p;
        }
        
        public void stateChanged(ChangeEvent e)
        {
            //System.out.print("---> stateChanged reached!");
            //System.out.println("  " + model.getMostRecentGeneration() );
            panel.appendNextTreeGeneration(model.getMostRecentGeneration());
        }
    }
  
    private class SelectionStats
    {
        private Object[] selectedItems;
        
        public SelectionStats()
        {
            super();
        }
        
    }
}

