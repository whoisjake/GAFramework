//***********************************************
// file:   BoardFitness.java
// author: Ryan Dixon
// date:   May 01, 2002
// notes:  Used to evaluate Tic-Tac-Toe players with
//          respect to a specific group of players.
//         Tournament style ratings.
//***********************************************

//#BoardFitness:#//
//#Rates TicTacToeIndividuals based on their ability#//
//#to beat other TicTacToeIndividuals.#//
//#Tournament style competetion is used.#//
import edu.uni.GAFramework.FitnessFunction;
import edu.uni.GAFramework.GAIndividual;
import java.util.Vector;

public class BoardFitness implements FitnessFunction
{
    public double evaluate(GAIndividual individual, Vector pop)
    {
        TicTacToeIndividual xPlayer 	= (TicTacToeIndividual) individual;
        
        System.out.println( individual );
        TicTacToeIndividual oPlayer 	= new TicTacToeIndividual();
        
        //(TicTacToeIndividual) ( (IndividualHolder)
        //                (pop.elementAt( (int)Math.random()*(pop.size()+1) )) ).individual();
                        
        TicTacToe 	    game;
        double 		    xWinCounter = 0.0;
        
        for(int i=1; i <= 10; i++)
        {
            String winner;
            //System.out.println("Game " + i);
            
            switch ( TicTacToe.playComputerOnlyGame(xPlayer, oPlayer, false) )
            {
                case TicTacToe.playerX:
                    winner = "X";
                    xWinCounter += 1.0;
                    break;
                case TicTacToe.playerO:
                    winner = "O";
                    break;
                default:
                    winner = "Cat";
                    xWinCounter += .5;
            }
    
            System.out.println("Game "+i+"  winner: "+winner + "\n");
        }
        
        return xWinCounter;
    }
}