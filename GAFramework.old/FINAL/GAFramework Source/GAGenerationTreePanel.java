//***********************************************
// file:   GAGenerationTreePanel.java
// author: Ryan Dixon
// date:   Feb 16, 2002
// notes:  Hierarchical view of generated objects obtained
//         using JTree in a scrollable view.
//***********************************************
// updated: Ryan Dixon
// date:    April 13, 2002
// notes:   
//  -> A MenuBar has been added for 'saving' functionality
//  -> Saving files has been incorporated into this revision
//  -> Serialization has been added to GAIndividual
//***********************************************

package edu.uni.GAFramework;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.JTree.*;
import java.util.*;
import java.io.*;


/**
 * A <code>GAGenerationTreePanel</code> object allows for a hierarchical (tree) view of
 * the <code>GAIndividual</code> class to be constructed. <br><br>
 * <code>GAGenerationTreePanel</code> is one interpretation of the Model-View-Controller
 * pattern to be used in conjunction with {@link GAApplication}.
 *
 * @version 1.0
 * @author Pat Burke
 * @author Ryan Dixon
 * @author Jake Good
 * @author Jayapal Prabakaran
 */
public class GAGenerationTreePanel extends JPanel
{
    /** The hierarchical implementation for <code>GAGenerationTreePanel</code>. */
    private JTree                   tree;
    /** Permits tree views of any size without losing the ability to view data. */
    private JScrollPane		    scrollPane;
    /** Current hierarchy parent node. */
    private int		            generationNumber;
    /** Object Monitor containing this view. */
    private GAObjectMonitor         gaMonitor;
    /** Adds the ability to insert nodes dynamically into the tree view. */
    private DefaultMutableTreeNode  generations;
    /** JTree-independent selection listener data. */
    private Vector		    selectedChildrenPaths;
    
    // Menubar items
    JMenuBar	menuBar;
    JMenu 	fileMenu;
    JMenuItem 	miOpen;
    JMenuItem 	miSave;
    JMenuItem 	miSaveAll;
    //JMenuItem miClose;
    //JMenuItem miNew;
    
    
    /**
     * Creates a new <code>GAGenerationTreePanel</code> JTree-based view.
     * The view is automatically wrapped in a scrollable pane.
    */
    public GAGenerationTreePanel(GAObjectMonitor monitor, String parentNodeTitle)
    {
        super();

        selectedChildrenPaths = new Vector();
        generationNumber = 1;
        generations      = new DefaultMutableTreeNode(parentNodeTitle);
        gaMonitor        = monitor;

        setLayout(new BorderLayout());
        
        tree = new JTree(generations);
        
        tree.setShowsRootHandles(true);
        tree.setRootVisible(true);
        tree.putClientProperty("JTree.lineStyle", "Angled");
        
        //Add Action Listeners
        MyTreeSelectionListener listener  =
            new MyTreeSelectionListener(monitor.getSelectionInfoPanel());
	MyTreeClickListener     mlistener = new MyTreeClickListener();

        tree.addTreeSelectionListener(listener);
	tree.addMouseListener(mlistener);

        // Add Scroll Plane
        scrollPane = new JScrollPane(tree);
        add(scrollPane, BorderLayout.CENTER);
        
        // Add Menu Bar Functionality
        createMenuBar();
        add(menuBar, BorderLayout.NORTH);
    }
    
    // Protected Methods
    
    /** 
     * Constructs a parent <code>DefaultMutableTreeNode</code>
     * on a per-generation basis.  Each generation is constructed in order
     * starting with Generation 1.
     * @parameter children The population to be added as this generation's children.
     */
    protected DefaultMutableTreeNode generationNodeBuilder( Vector children )
    {
        DefaultMutableTreeNode generationX =
            new DynamicUtilTreeNode( "Generation " + generationNumber, children );
            
        return generationX;
    }
    
    // Public Methods

    /**
     * Appends the next generation of children to the <code>JTree</code> view.
     * @parameter children The population to be added as this generation's children.
     * @see #generationNodeBuilder( Vector )
     */
    public void appendNextTreeGeneration( Vector generation )
    {
        DefaultMutableTreeNode nextGeneration =
            generationNodeBuilder( generation );
            
        generations.add( nextGeneration );
        
        // If Generations contains leaf nodes (generated objects)
        // Enabled Save All Menu Item
        if ( generations.getLeafCount() > 0 )
            miSaveAll.setEnabled(true);
        else
            miSaveAll.setEnabled(false);
        
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
    
    /** Returns the <code>JTree</code> in its current state. */
    public JTree getTree()
    {
        return tree;
    }
    
    // Event Listeners
    private class MyTreeSelectionListener implements TreeSelectionListener
    {
        private TreeSelectionInfoPanel selectionStatsPanel;
        
        public MyTreeSelectionListener( TreeSelectionInfoPanel jPanel )
        {
            super();
            selectionStatsPanel = jPanel;
            //selectedPaths       = new Vector();
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
                    selectedChildrenPaths.removeAllElements();
                    break;
                }
                
                if ( tmpNode.getAllowsChildren() )
                {
                    ArrayList tmpNodeChildren = new ArrayList();
                    
                    for (Enumeration e = tmpNode.children(); e.hasMoreElements() ; )
                    {
                        DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)e.nextElement();
                        TreePath treePath = new TreePath(childNode.getPath());

                        if ( event.isAddedPath(paths[i]) )
                        {
                            if ( !selectedChildrenPaths.contains(treePath) )
                                selectedChildrenPaths.addElement(treePath);
                        }
                        else
                            selectedChildrenPaths.removeElement(treePath);
                            
                    }
                    
                    //TreePath [] treePaths = new TreePath[tmpNodeChildren.size()];
                    
                    //if ( event.isAddedPath(paths[i]) )
                    //    tree.addSelectionPaths( (TreePath []) tmpNodeChildren.toArray(treePaths) );
                    //else
                    //    tree.removeSelectionPaths( (TreePath[]) tmpNodeChildren.toArray(treePaths) );
                    
                    // Collapse parent view -- commented for future use, DO NOT IMPLEMENT AS SHOWN
                    //tree.collapsePath( paths[i] );
                }
                else	// only a single node is being examined
                {
                    if ( event.isAddedPath(paths[i]) )
                    {
                        if ( !selectedChildrenPaths.contains(paths[i]) )
                            selectedChildrenPaths.addElement(paths[i]);
                    }
                    else
                        selectedChildrenPaths.removeElement(paths[i]);
                }
            }
            
            // If selections exist, enabled "Save Selected" menu item
            if ( selectedChildrenPaths.size() > 0 )
                miSave.setEnabled(true);
            else
                miSave.setEnabled(false);
                
            // Calculate selection information statistics
            for (int j = 0; j < selectedChildrenPaths.size(); j++)
            {
                double fitness = 0;
                
                node (DefaultMutableTreeNode)
			((TreePath)selectedChildrenPaths.elementAt(j)).getLastPathComponent();
                

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
                avgFitness   = avgFitness / (double) selectedChildrenPaths.size();
                
            // Panel may not exist!  If null, ignore
            if ( selectionStatsPanel != null )
            {
                if ( bestFitness != -1 )
                    selectionStatsPanel.setSelectionStats( selectedChildrenPaths.size(), avgFitness,
                                                            bestFitness, worstFitness);
                else
                    selectionStatsPanel.setEmptySelection();
            }
        }
    }

    // Private helper methods
    private void createMenuBar()
    {
        menuBar  = new JMenuBar();
        fileMenu = new JMenu("File");
        
        miOpen = new JMenuItem ("Open...");
        miOpen.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.META_MASK));
        fileMenu.add(miOpen).setEnabled(false);
        miOpen.addActionListener(new MenuActionListener());
		
        miSave = new JMenuItem ("Save Selected...");
        miSave.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.META_MASK));
        fileMenu.add(miSave).setEnabled(false);
        miSave.addActionListener(new MenuActionListener());
		
        miSaveAll = new JMenuItem ("Save All...");
        fileMenu.add(miSaveAll).setEnabled(false);
        miSaveAll.addActionListener(new MenuActionListener());
		
        menuBar.add(fileMenu);
    }

    // Menu Driven functions
    private void doOpen()
    {
        // No Functionality yet!
    }
    
    private ObjectOutputStream getObjectOutputStream()
    {
        File f = new File(".");
        String loadDirectory = f.getAbsolutePath();
        
        JFileChooser chooser = new JFileChooser(loadDirectory);
        chooser.setDialogTitle("Save Generation File As...");
        chooser.setMultiSelectionEnabled(false);
        
        int result = chooser.showSaveDialog(this);
        File selectedFile = chooser.getSelectedFile();
        
        if ( result == JFileChooser.APPROVE_OPTION )
        {
            try{
                FileOutputStream fileStream = new FileOutputStream(selectedFile.getPath());
                ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
                
                return objectStream;
            } catch(IOException e) {System.err.println(e);}
        }
        
        return null;
    }
    
    private void doSave()
    {
        ObjectOutputStream objectStream = getObjectOutputStream();
        
        if ( objectStream != null )
        {        
            try{  
                System.out.println("Saving " + selectedChildrenPaths.size() + " Selected Generations...");
                
                for (int i=0; i < selectedChildrenPaths.size(); i++)
                {
                    // Get the userObject at the supplied path
                    Object selectedPath = ((TreePath)selectedChildrenPaths.elementAt(i)).getLastPathComponent();
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)selectedPath;

                    objectStream.writeObject( selectedNode.getUserObject() );
                }

                objectStream.close();
                System.out.println("Save completed successfully.");
            } catch(IOException e) {System.err.println(e);}
        }
        else
        {
            System.out.println("Save Selected Files has been aborted!");
        }
    }
    
    private void doSaveAll()
    {
        ObjectOutputStream objectStream = getObjectOutputStream();
        
        if ( objectStream != null )
        {
            try{
                System.out.println("Saving All " + generations.getLeafCount() + " Generations...");
                
                for (Enumeration e = generations.depthFirstEnumeration(); e.hasMoreElements(); )
                {
                    DefaultMutableTreeNode tmpNode = (DefaultMutableTreeNode) e.nextElement();
                    
                    if ( tmpNode.isLeaf() )
                        objectStream.writeObject( tmpNode.getUserObject() );
                }
            
                objectStream.close();    
                System.out.println("Save completed successfully.");
            } catch(IOException e) {System.err.println(e);}
        }
        else
        {
            System.out.println("Save All Files has been aborted!");
        }
    }
    

    // Private Inner Classes
    private class MenuActionListener  implements ActionListener
    {
        // ActionListener interface (for menus)
        public void actionPerformed(ActionEvent newEvent)
        {
            if (newEvent.getActionCommand().equals(miOpen.getActionCommand())) doOpen();
            else if (newEvent.getActionCommand().equals(miSave.getActionCommand())) doSave();
            else if (newEvent.getActionCommand().equals(miSaveAll.getActionCommand())) doSaveAll();
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
