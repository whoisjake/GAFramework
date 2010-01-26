//***********************************************
// file:   JavaFileBrowserPanel.java
// author: Ryan Dixon
// date:   Feb 16, 2002
// notes:  A tool for allowing users to browse
//         and select input files.
//***********************************************
//  Created By: Ryan Dixon  Sun 07 April 2002
//  Updated By: Ryan Dixon  Mon 08 April 2002
//  Updated By: Ryan Dixon  Tue 10 April 2002
// notes: Minor interface updates and enhancements
//***********************************************

package edu.uni.GAFramework;

import edu.uni.GAFramework.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;


public class JavaFileBrowserPanel extends JPanel
{
    private JPanel      fileSelectionPanel;    
    private JLabel      browseTitle;
    private JTextArea   textDescription;
    private JTextField  filePath;

    public static void main(String[] args)
    {
        // Just a simple tester!
        JPanel myPanel = new JavaFileBrowserPanel("hi.");
        JPanel myPanel2 = new JavaFileBrowserPanel("bye.");
        
        JFrame frame = new JFrame("tester");
        
        frame.getContentPane().setLayout(new GridLayout(2,1,2,2));
        frame.getContentPane().add(myPanel);
        frame.getContentPane().add(myPanel2);
        frame.setVisible(true);
    }

    public JavaFileBrowserPanel(String label)
    {
        super();
        
        // Configure Panel Layout
        setLayout(new GridLayout(2,1,2,2));
        setLayout(new GridBagLayout());
        
        // Initialize panel components
        fileSelectionPanel = new JPanel();
        browseTitle	   = new JLabel(label);
        textDescription    = new JTextArea("No file has been selected.", 3, 35);
        filePath	   = new JTextField("< no selection >",30);

        // configure individual components
        filePath.setEditable(false);
        
        // Construct complete panel
        constructBrowserPanel();
    }

    // Constructor helper methods
    protected void constructBrowserPanel()
    {
        JButton     	   browseButton     = new JButton("Browse...");
        JPanel		   descriptionPanel = new JPanel();
        GridBagConstraints c		    = new GridBagConstraints();

        // Configure GridBagConstraints
        c.gridx		= GridBagConstraints.RELATIVE;
        c.gridy		= 0;
        c.gridwidth	= 1;
        c.gridheight	= 1;
        c.weightx	= 1;
        //c.weighty	= 0.5;
        c.fill		= GridBagConstraints.HORIZONTAL;
        c.anchor	= GridBagConstraints.EAST;
        c.insets	= new Insets(0,1,0,1);


        // Attach action listeners
        ActionListener fileBrowserListener = 
            new BrowseButtonListener(this, filePath);
        browseButton.addActionListener( fileBrowserListener );
        
        
        // Construct browser panel interface
        setBorder(BorderFactory.createTitledBorder(
                                BorderFactory.createBevelBorder(1),
                                browseTitle.getText(), TitledBorder.LEFT, 
                                TitledBorder.TOP));
                                
        // Add file path to Panel
        add(filePath,c);
        
        // Add Browse Button to Panel
        c.fill    = GridBagConstraints.NONE;
        c.anchor  = GridBagConstraints.WEST;
        c.weightx = 0;
        add(browseButton,c);


        // Construct description panel
        // Set preferred size arbitrarily low so it will not grow!
        descriptionPanel.setLayout(new BorderLayout());
        descriptionPanel.setBorder(BorderFactory.createTitledBorder(descriptionPanel.getBorder(), "Description",
                                    TitledBorder.LEFT, TitledBorder.TOP));
        textDescription.setOpaque(false);
        textDescription.setPreferredSize(new Dimension(0,0));
        textDescription.setEditable(false);
        textDescription.setLineWrap(true);
        textDescription.setWrapStyleWord(true);
        descriptionPanel.add(textDescription);

        // Add description panel to Panel
        c.gridx 	= 0;
        c.gridy 	= 1;
        c.gridwidth 	= 2;
        c.weightx	= 1;
        c.fill		= GridBagConstraints.HORIZONTAL;
        c.anchor	= GridBagConstraints.EAST;
        c.insets        = new Insets(1,1,1,1);
        add(descriptionPanel,c);
    }
    
    
    // Public accessor methods
    public String getFileName()
    {
        if (filePath.getText().equals("< no selection >"))
            return null;
            
            
        File file = new File(filePath.getText());
    
        return file.getName();
    }
    
    public String getClassName()
    {
        String fullFileName = getFileName();
        
        // String ".java" suffix and return String
        if ( fullFileName == null )
            return null;
        
        return fullFileName.substring(0,(fullFileName.length()-5));
    }
    
    // Private Inner Classes
    private class BrowseButtonListener implements ActionListener
    {
        JTextField path;
        JPanel     parentFrame;
        
        public BrowseButtonListener(JPanel parent, JTextField textField)
        {
            super();
            
            parentFrame = parent;
            path        = textField;
        }
        
        public void actionPerformed(ActionEvent e)
        {
            browseFiles();
        }
    
        public void browseFiles()
        {
            File f = new File(".");
            String loadDirectory = f.getAbsolutePath();
            
            JFileChooser chooser = new JFileChooser(loadDirectory);
            chooser.setDialogTitle("File Browser");
            chooser.setMultiSelectionEnabled(false);
            
            chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
            chooser.addChoosableFileFilter(new JavaFileFilter());
            
            
            int result = chooser.showOpenDialog(parentFrame);
            File selectedFile = chooser.getSelectedFile();
            System.out.println("Return value from showOpenDialog is " + result);
            
            if (selectedFile != null)
            {
                System.out.println("Chosen file is " + selectedFile.getPath());
                path.setText( selectedFile.getPath() );
                
                // Set Text Description of Selected File (if available)
                textDescription.setText( DescriptionReader.getDescription(selectedFile.getPath()) );
            }
            else
            {
                System.out.println("No file was selected");
                path.setText( "< no selection >" );
                
                // Set Text Description to Empty
                textDescription.setText( "No file has been selected." );
            }
        }
    }

    private class JavaFileFilter extends FileFilter
    {        
        public JavaFileFilter()
        {
            super();
        }
        
        public boolean accept(File f)
        {
            if (f.isDirectory())
                return true;
            
            String name = f.getName();
            int length = name.length();
            
            return name.endsWith(".java");
        }
        
        public String getDescription()
        {
            return "Java Files (*.java)";
        }
    }
}
