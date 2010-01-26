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

public class GAApplication extends JFrame
{
    public GAApplication()
    {
        super();
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

        GAObjectMonitor infoPanel = new GAObjectMonitor("GAObjectMonitor");
        
        infoPanel.addWindowListener(new WindowAdapter()
            { public void windowClosing(WindowEvent e) {System.exit(0);} });
    
        infoPanel.setSize(300, 700);
        infoPanel.setVisible(true);
        
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

}

