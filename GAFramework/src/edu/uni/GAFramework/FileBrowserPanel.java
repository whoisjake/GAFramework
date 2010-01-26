//***********************************************
// file:   FileBrowserPanel.java
// author: Ryan Dixon
// date:   April 14, 2002
// notes:  File browser interface as a panel,
//         stackable for requesting multiple files
//	   from users.
//***********************************************

package edu.uni.GAFramework;

import edu.uni.GAFramework.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;


public class FileBrowserPanel extends JPanel
{
    private JLabel      browseTitle;
    private JTextField  filePath;
    private String      extension;

    public static void main(String[] args)
    {
        // Just a simple tester!
        JPanel myPanel = new FileBrowserPanel("hi.", ".java");
        JPanel myPanel2 = new FileBrowserPanel("bye.", ".*");
        
        JFrame frame = new JFrame("tester");
        
        frame.getContentPane().setLayout(new GridLayout(2,1,2,2));
        frame.getContentPane().add(myPanel);
        frame.getContentPane().add(myPanel2);
        frame.setVisible(true);
    }

    public FileBrowserPanel(String label, String extension)
    {
        super();
        
        this.extension = extension;
        
        // Configure Panel Layout
        setLayout(new GridLayout(2,1,2,2));
        setLayout(new GridBagLayout());

        // Initialize panel components
        browseTitle	   = new JLabel(label);
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
    }
    
    
    // Public accessor methods
    public String getFileName()
    {
        if (filePath.getText().equals("< no selection >"))
            return null;
            
            
        File file = new File(filePath.getText());
    
        return file.getName();
    }
    
    public String getFilePath()
    {
        if ( getFileName() == null )
            return null;
        
        return filePath.getText();
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
            
            if ( !extension.equals(".*") )
            {
                chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
                chooser.addChoosableFileFilter(new ExtensionFileFilter(extension));
            }
            
            int result = chooser.showOpenDialog(parentFrame);
            File selectedFile = chooser.getSelectedFile();
            
            if (result == JFileChooser.APPROVE_OPTION)
                path.setText( selectedFile.getPath() );
            else
                path.setText( "< no selection >" );
        }
    }


}
