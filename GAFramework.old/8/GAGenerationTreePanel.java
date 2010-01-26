//
//  GAGenerationTreePanel.java
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

public class GAGenerationTreePanel extends JPanel
{
    private JTree                   tree;
    private JScrollPane		    scrollPane;
    private int		            generationNumber;
    private GAObjectMonitor         gaMonitor;
    private DefaultMutableTreeNode  generations;
    
    
    public GAGenerationTreePanel(GAObjectMonitor monitor, String parentNodeTitle)
    {
        super();
        
        generationNumber = 1;
        generations      = new DefaultMutableTreeNode(parentNodeTitle);
        gaMonitor        = monitor;

        setLayout(new BorderLayout());
        
        tree = new JTree(generations);
        
        tree.setShowsRootHandles(true);
        tree.setRootVisible(true);
        tree.setVisibleRowCount(25);
        tree.putClientProperty("JTree.lineStyle", "Angled");
        
        //Add Action Listeners
        MyTreeSelectionListener listener  =
            new MyTreeSelectionListener(monitor.getSelectionInfoPanel());
	MyTreeClickListener     mlistener = new MyTreeClickListener();

        tree.addTreeSelectionListener(listener);
	tree.addMouseListener(mlistener);

        // Add Scroll Plane
        scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }
    
    // Protected Methods
    protected DefaultMutableTreeNode generationNodeBuilder( Vector children )
    {
        DefaultMutableTreeNode generationX =
            new DynamicUtilTreeNode( "Generation " + generationNumber, children );
            
        return generationX;
    }
    
    // Public Methods
    public void appendNextTreeGeneration( Vector generation )
    {
        DefaultMutableTreeNode nextGeneration =
            generationNodeBuilder( generation );
            
        generations.add( nextGeneration );
        
        // Update JTree View
        
        // affected nodes needing updating
        int[] nodeRangeToUpdate = { generations.getIndex( nextGeneration ) };
        ((DefaultTreeModel)tree.getModel()).nodesWereInserted( generations, nodeRangeToUpdate );

        // Expand Parent after first child node is displayed
        if ( generationNumber == 1 )
            tree.expandRow(0);
            
        ++generationNumber;
    }
    
    // Public Accessors
    public JTree getTree()
    {
        return tree;
    }

    // Event Listeners
    private class MyTreeSelectionListener implements TreeSelectionListener
    {
        private TreeSelectionInfoPanel selectionStatsPanel;
        private Vector		       selectedPaths;
        
        public MyTreeSelectionListener( TreeSelectionInfoPanel jPanel )
        {
            super();
            selectionStatsPanel = jPanel;
            selectedPaths       = new Vector();
        }
        
        public void valueChanged (TreeSelectionEvent event)
        {
            //DefaultMutableTreeNode node = new DefaultMutableTreeNode();
            DefaultMutableTreeNode node;
            TreePath paths[] = event.getPaths();
            
            // Statistical variables
            Object bestNode;
            Object worstNode;
            double avgFitness   =  0;
            double bestFitness  = -1;
            double worstFitness = -1;
            
            // Update Selection Panel to reflect current selections
            for (int i = 0; i < paths.length; i++)
            {
                // If a parent node is selected, select all children nodes
                DefaultMutableTreeNode tmpNode = (DefaultMutableTreeNode)paths[i].getLastPathComponent();
                
                // If Root is selected, clear all selections
                if ( tmpNode.isRoot() )
                {
                    tree.clearSelection();
                    selectedPaths.removeAllElements();
                    break;
                }
                
                if ( tmpNode.getAllowsChildren() )
                {
                    for (Enumeration e = tmpNode.children() ; e.hasMoreElements() ;)
                    {
                        DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)e.nextElement();
                        TreePath treePath = new TreePath(childNode.getPath());
                    
                        if ( event.isAddedPath(paths[i]) )
                            tree.addSelectionPath( treePath );
                        else
                            tree.removeSelectionPath( treePath );
                    }
                    
                    // If the parent parent has been de-selected, collapse it's view
                    if ( !event.isAddedPath(paths[i]) )
                        tree.collapsePath( paths[i] );
                }
                else
                {
                    if ( !event.isAddedPath(paths[i]) )
                        selectedPaths.remove(paths[i]);
                    else
                        if ( !selectedPaths.contains(paths[i]) )
                            selectedPaths.addElement( paths[i] );
                }

            }
            
            // Calculate selection information statistics
            for (int j = 0; j < selectedPaths.size(); j++)
            {
                double fitness = 0;
                
                node = ((DefaultMutableTreeNode)((TreePath)selectedPaths.elementAt(j)).getLastPathComponent());
                
                fitness = gaMonitor.getFitness( node.getUserObject() );
                
                avgFitness += fitness;
                
                if ( fitness > bestFitness || bestFitness == -1)
                {
                    bestFitness = fitness;
                    bestNode    = node;
                }
                
                if ( fitness < worstFitness || worstFitness == -1)
                {
                    worstFitness = fitness;
                    worstNode    = node;
                }
            }
            
            // Finialize Statistics
            if ( bestFitness != -1 )
                avgFitness   = avgFitness / (double) selectedPaths.size();
                
            // Panel may not exist!  If null, ignore
            if ( selectionStatsPanel != null )
            {
                if ( bestFitness != -1 )
                    selectionStatsPanel.setSelectionStats( selectedPaths.size(), avgFitness,
                                                            bestFitness, worstFitness);
                else
                    selectionStatsPanel.setEmptySelection();
            }
        }
    }

    private class MyTreeClickListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            int selRow       = tree.getRowForLocation(e.getX(), e.getY());
            TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());

            if(e.getClickCount() == 2)
            {
                myDoubleClick( selRow, selPath );
            }
        }

        private void mySingleClick(int row, TreePath path)
        {
            // Method unused in this version
            System.out.println("Single Click Performed!");
        }

        private void myDoubleClick(int row, TreePath path)
        {
            if ( path != null )
            {
                JFrame infoFrame = new JFrame("Object Info for: " + path);
                JLabel objectLabel;			
                JLabel parentPathLabel;
    
                // Display Selected Object Name;
                objectLabel = new JLabel("Object: " + path.getLastPathComponent().toString() );
                objectLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
                // Display specific object information
                parentPathLabel = new JLabel("Parent Path: " + path.getParentPath() );
                parentPathLabel.setHorizontalAlignment(SwingConstants.CENTER);
    
                // Construct infoFrame
                infoFrame.getContentPane().add(objectLabel,BorderLayout.CENTER);
                infoFrame.getContentPane().add(parentPathLabel,BorderLayout.SOUTH);
                infoFrame.pack();
                infoFrame.setVisible(true);
            }
        }
    }
    
}

