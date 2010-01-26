//***********************************************
// file:   TicTacToe.java
// author: Ryan Dixon
// date:   May 01, 2002
// notes:  Adapted from C code.
//         Reference: http://www.pune-csharp.com/news/articles/titactoe/
//***********************************************

import java.io.*;

public class TicTacToe 
{
    public static final int playerX = -1;
    public static final int playerO = -2;
    
    public static void main(String[] args)
	{
		playHumanVsComputerGame();
	}

    public static int playComputerOnlyGame(TicTacToeIndividual x, TicTacToeIndividual o, boolean verbose)
    {
		  // For now the 'o' individual is disregarded, can be used for tournament playoffs eventually.

        int move = -1;
        int numMoves = 0;
        int winner   = 0;
        
        // Configure player assignments
        x.setPlayer( playerX );
        o.setPlayer( playerO );
        
        BoardC brd = new BoardC();
        
        while(winner == 0)
        {
            
            // First Player's (computer x) move
            move = brd.ComputerMove(x);
            
            brd.MakeMove(move, -1);
            
            if (verbose)
            {
                System.out.println("Computer X Move: " + move);
                brd.PrintBoard();
            }
            
            if ((winner = brd.CheckWinner()) != 0) break;
            if (++numMoves == 5) break;
            
            // Second Player's (computer o) move
            move = brd.ComputerMove();
            brd.MakeMove(move, playerO);
        
            
            if (verbose)
            {
                System.out.println("Computer O Move: " + move);
                brd.PrintBoard();
            }
            
            winner = brd.CheckWinner();
        }
    
        //reportWinner(brd.CheckWinner());
        
        return brd.CheckWinner();
    }
        
    
    public static int playHumanVsComputerGame()
    {
        int move = -1;
        int computermove = -1;
        int numMoves = 0;
        
        int winner = 0;
        
        BoardC brd = new BoardC();
        brd.PrintBoard();
        
        while(winner == 0) 
        {
            System.out.println("What is your move?");
            DataInputStream dataIn = new DataInputStream(System.in);
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataIn));
            
            try{
                move = Integer.parseInt(reader.readLine());
            }catch(IOException e) { System.out.println("error reading data!"); System.exit(0); }
            
            brd.MakeMove(move, -1);

            if ((winner = brd.CheckWinner()) != 0) break;
            if (++numMoves == 5) break;
            
            //computermove = brd.ComputerMove();//new TicTacToeIndividual());
            
            TicTacToeIndividual individual = new TicTacToeIndividual( new int[] {8,7,5,7}, playerO );
            computermove = brd.ComputerMove(individual);         
            brd.MakeMove(computermove, -2);
        
            System.out.println("My move is: " + computermove);
            brd.PrintBoard();
            
            winner = brd.CheckWinner();
        }
        
        reportWinner(brd.CheckWinner());
        
        return brd.CheckWinner();
    }
    
    // Private helper-methods
    
    private static void reportWinner(int winner)
    {
        switch (winner) 
        {
            case playerX:
                System.out.println("X Wins!");
                break;
            case playerO:
                System.out.println("O Wins!");
                break;
            default:
                System.out.println("Draw!");
                break;
        }
    }
}