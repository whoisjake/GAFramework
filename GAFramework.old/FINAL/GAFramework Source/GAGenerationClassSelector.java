//***********************************************
// file:   GAGenerationClassSelector.java
// author: Ryan Dixon
// date:   April 7, 2002
// notes:  Interface for allowing users to select
//         specific GA generation files.
//***********************************************

package edu.uni.GAFramework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GAGenerationClassSelector extends JDialog
{
    ClassSelectionListener selectionListener;
    JavaFileBrowserPanel   individualBrowserPanel;
    JavaFileBrowserPanel   fitnessBrowserPanel;
    JButton 		   executeButton;
    
    public GAGenerationClassSelector(Frame parentFrame)
    {
        this(parentFrame, "GAGeneration Class Selector", null);
    }
    
    public GAGenerationClassSelector(Frame parentFrame, String label)
    {
        this(parentFrame, label, null);
    }
    
    public GAGenerationClassSelector(ClassSelectionListener selectionListener)
    {
        this(new Frame(), "GAGeneration Class Selector", selectionListener);
    }
    
    public GAGenerationClassSelector(Frame parentFrame, ClassSelectionListener selectionListener)
    {
        this(parentFrame, "GAGeneration Class Selector", selectionListener);
    }
    
    public GAGenerationClassSelector(String label)
    {
        this(new Frame(), label, null);
    }
    
    public GAGenerationClassSelector(Frame parentFrame, String label, ClassSelectionListener selectionListener)
    {
        super(parentFrame, label, true);
        
        //setSize(450,325);
        setResizable(false);
        this.getContentPane().setLayout(new GridBagLayout());
        
        // Configure GridBagContraints
        GridBagConstraints c = new GridBagConstraints();
        c.gridx		= 0;
        c.gridy 	= GridBagConstraints.RELATIVE;
        c.gridwidth 	= 1;
        c.gridheight 	= 1;
        c.weightx 	= 1.0;
        c.weighty	= 0.4;
        c.anchor	= GridBagConstraints.NORTHWEST;
        c.fill 		= GridBagConstraints.HORIZONTAL;
        c.insets 	= new Insets(1,1,1,1);
        
        // Construct Browser Panels
        individualBrowserPanel = new JavaFileBrowserPanel("Select a 'Fitness Function' Class");
        fitnessBrowserPanel    = new JavaFileBrowserPanel("Select an 'Individual' Class");
        
        this.getContentPane().add(individualBrowserPanel, c);
        this.getContentPane().add(fitnessBrowserPanel, c);
        
        // Add execution button
        executeButton = new JButton("Execute");
        ActionListener executeListener =
            new ExecuteButtonListener();
        executeButton.addActionListener( executeListener );

        c.weighty = .2;
        c.anchor  = GridBagConstraints.SOUTH;
        this.getContentPane().add(executeButton, c);
        
        // Show Window
        pack();
        setVisible(true);
        
        // Window Action Listner
        this.addWindowListener( new CloseWindowListener() );
        
        // Add Listener
        this.selectionListener = selectionListener;
    }
    
    // Helper Methods
    public String[] getSelection()
    {
        // Remove dependence on ClassSelectionListener interface!
        return new String[] { individualBrowserPanel.getClassName(),
                               fitnessBrowserPanel.getClassName() };
    }
    
    public void fireFileSelection()
    {
        if (selectionListener != null)
            selectionListener.initializeClassSelection(
                individualBrowserPanel.getClassName(),
                fitnessBrowserPanel.getClassName() );
    }

    // Private Inner Classes
    
    // Action Listeners
    private class CloseWindowListener extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            System.out.println("File Selection Process Aborted!");
            System.exit(0);
        }
    }

    private class ExecuteButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            GAGenerationClassSelector.this.dispose();
            fireFileSelection();
        }
    }
    
    public static void main(String args[]) {
        // Set Look And Feel Theme to 'Metal'
        try {
        UIManager.setLookAndFeel (
            "javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (java.lang.ClassNotFoundException e) {
        } catch (java.lang.InstantiationException e) {
        } catch (java.lang.IllegalAccessException e) {
        } catch (javax.swing.UnsupportedLookAndFeelException e) {}
        
        // Create a new FinalFileBrowser Object
        new GAGenerationClassSelector("Class Initialization");
    }

}