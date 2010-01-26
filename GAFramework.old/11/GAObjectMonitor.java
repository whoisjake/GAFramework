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
    
    public GAObjectMonitor(String title, GAStrategy model)
    {
        super(title);
        
        // Configure Frame and Constraints as a GridBagLayout
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx 	= 0;
        c.gridy 	= GridBagConstraints.RELATIVE;
        c.gridwidth	= 1;
        c.gridheight	= 1;
        c.weightx	= 1.0;
        c.weighty	= 1.0;
        c.fill		= GridBagConstraints.BOTH;
        c.anchor	= GridBagConstraints.NORTHWEST;
        c.insets	= new Insets(0,1,0,1);
        
        
        // Construct Individual Model and Selection Info Panel
        this.model = model;
        selectionInfoPanel = new TreeSelectionInfoPanel();
        
        // Construct infoPanel (JTree Panel View)
        if (model.getGenerationName() != null)
            infoPanel = new GAGenerationTreePanel(this, model.getGenerationName());
        else
            infoPanel = new GAGenerationTreePanel(this, "Generations");
        
        
        // Add InfoPanel and SelectionInfoPanel
        getContentPane().add(infoPanel, c);
        
        c.anchor	= GridBagConstraints.WEST;
        c.fill		= GridBagConstraints.HORIZONTAL;
        c.weighty	= 0;
        getContentPane().add(selectionInfoPanel, c);
        
        
        // Create Button For Thread Interation
        JButton threadControl = new JButton("Pause");
        c.anchor	= GridBagConstraints.SOUTH;
        getContentPane().add(threadControl, c);
        
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
        
        // Show Window
        setSize(WIDTH, HEIGHT);
        setVisible(true);

        // (Simple) Window Listeners
        addWindowListener(new WindowAdapter()
            { public void windowClosing(WindowEvent e) {System.exit(0);} });
        
    }
    
    public static void main(String args[])
    {
        //GAObjectMonitor objectBrowser = new GAObjectMonitor("GAObjectMonitor");
        
        //objectBrowser.setSize(WIDTH, HEIGHT);
        //objectBrowser.setVisible(true);
        
        // (Simple) Window Listeners
        //objectBrowser.addWindowListener(new WindowAdapter()
        //    { public void windowClosing(WindowEvent e) {System.exit(0);} });

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
