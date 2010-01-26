//
//  ImportableCheckersPlayer.java
//
//  Created by: Ryan Dixon  14 April 2002
//  Used as a driver for the updated Checkers games
//

import javax.swing.*;

public class ImportableCheckersPlayer
{
    public static void main( String [ ] args )
    {
        // Set Look And Feel Theme to 'Metal'
        try {
        UIManager.setLookAndFeel (
            "javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (java.lang.ClassNotFoundException e) {
        } catch (java.lang.InstantiationException e) {
        } catch (java.lang.IllegalAccessException e) {
        } catch (javax.swing.UnsupportedLookAndFeelException e) {}
        
        Engine.initializeCheckersEvaluator();
        Checkers game = new Checkers();
        game.show();
    }
}
