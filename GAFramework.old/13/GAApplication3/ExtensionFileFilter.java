//
//  ExtensionFileFilter.java
//  ImportableCheckersPlayer
//
//  Created by Ryan Dixon on Mon Apr 15 2002.
//
//  Allows for simple JFileChooser Filtering based
//   on String extension passed to the constructor.

import java.io.*;
import javax.swing.filechooser.FileFilter;

public class ExtensionFileFilter extends FileFilter
{
    String extension;
    
    public ExtensionFileFilter(String extension)
    {
        super();
        
        this.extension = extension;
    }
    
    public boolean accept(File f)
    {
        if (f.isDirectory())
            return true;
        
        String name = f.getName();
        int length = name.length();
        
        return name.endsWith(extension);
    }
    
    public String getDescription()
    {
        return "'*" + extension + "' file type";
    }
}