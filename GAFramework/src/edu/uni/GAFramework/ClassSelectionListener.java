//***********************************************
// file:   ClassSelectionListener.java
// author: Ryan Dixon
// date:   April 9, 2002
// notes:  Alternative interface for non-dialog
//        window interaction... last restort!
//***********************************************

package edu.uni.GAFramework;

/**
 * An alternative interface provided for non-Dialog based
 * graphical interfaces.  This is not a thread safe method!
 * If a non-dialog setup must be used and thread compatibility is required,
 * all threads must be handled manually.
 * <br><br>
 * It is strongly recommended that this implementation is used as a last
 * resort only!
 *  
 * @version 1.0
 * @author Pat Burke
 * @author Ryan Dixon
 * @author Jake Good
 * @author Jayapal Prabakaran
 */
public interface ClassSelectionListener
{
    /**
     * Invoked once an action has been performed in order to notify
     * non-Dialog-based classes about changes.
     * @parameter classOne <code>String</code> representation for the first file selected.
     * @parameter classTwo <code>String</code> representation for the second file selected.
     */
    public void initializeClassSelection(String classOne, String classTwo);
}
