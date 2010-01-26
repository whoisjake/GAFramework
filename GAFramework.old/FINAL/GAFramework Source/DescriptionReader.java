//***********************************************
// file:   DescriptionReader.java
// author: Ryan Dixon
// date:   April 8, 2002
// notes:  A Simple DescriptionReader class that allows
//    for up to three lines of comments between the delimeters //# and #//
//***********************************************//

package edu.uni.GAFramework;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;


public class DescriptionReader
{	
    public static void main(String args[])
    {
        getDescription("testDesc.java");
    }
    
    public static String getDescription(String file)
    {
        String line;
        Vector description      = new Vector();
        String finalDescription = new String();
        
        try{
            FileReader     fileIn = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileIn);
            
            int index = 0;
            while( (line = reader.readLine()) != null )
            {
                int descriptionStart = line.indexOf("//#");
                int descriptionEnd   = 0;
                
                if ( descriptionStart != -1)
                {
                    descriptionStart += 3;
                    descriptionEnd    = line.indexOf("#//");
                    
                    description.addElement(line.substring(descriptionStart, descriptionEnd));
                    
                    System.out.println((String)description.elementAt(index));
                    index++;
                    
                    if ( index == 9 )
                        break;
                }
            }
        } catch (IOException e) {System.err.println(e);}
        
        if ( description.size() == 0 )
            finalDescription = "< No Description >";
        else
            for( int i = 0; i < description.size(); i++ )
                    finalDescription += (String)description.elementAt(i) + "\n";
        
        return finalDescription;
    }
}