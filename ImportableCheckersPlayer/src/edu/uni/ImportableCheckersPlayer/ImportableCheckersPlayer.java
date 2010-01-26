package edu.uni.ImportableCheckersPlayer;
//***********************************************
import javax.swing.*;

public class ImportableCheckersPlayer
{
    public static void main( String [ ] args )
    {

        Engine.initializeCheckersEvaluator();
        Checkers game = new Checkers();
        game.show();
    }
}
