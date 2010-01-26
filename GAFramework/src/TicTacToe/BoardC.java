//***********************************************
// file:   Board.java
// author: Ryan Dixon
// date:   May 01, 2002
// notes:  Adapted from C code.
//         Reference: http://www.pune-csharp.com/news/articles/titactoe/
//	   -Board analysis and GAIndividual compatiblity added
//***********************************************

import java.util.*;
import java.lang.Math;

public class BoardC
{
    int[][] Board = new int[3][3];
    
    public BoardC () 
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                Board[i][j] = Convert(i,j);
    }
    
    private int Convert (int i, int j)
    {
        return(i*3 + j + 1);
    }
    
    
    public void PrintBoard ()
    {
        System.out.println("+---+---+---+");
        for (int i = 0; i < 3; i++)
        {
            System.out.print("|");
            for (int j = 0; j < 3; j++)
            {
                switch(Board[i][j])
                { 
                    case -1:
                        System.out.print(" X ");
                        break;
                    case -2:
                        System.out.print(" O ");
                        break;
                    default:
                        System.out.print(" " + Board[i][j] + " ");
                        break;
                }
    
                System.out.print("|");
            }
            
            System.out.println("");
            System.out.println("+---+---+---+");
        }
    }
    
    private void CopyBoard (BoardC curbrd)
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
            {
                this.Board[i][j] = curbrd.Board[i][j];
            }
    }
    
    
    //The CopyBoard method is used extensively during the search. The top level search function is ComputerMove():
    public int ComputerMove ()
    { 
        long bestMove;
    
        // depth of 4 is enough to play optimal game
        bestMove = MiniMax(this, 4, 1); // board, depth, player
        
        bestMove = bestMove % 10;
        return (int) bestMove;
    }

	public int ComputerMove( TicTacToeIndividual evaluator )
    {
		long bestMove;
		
		bestMove = MiniMax(this, 4, 1, evaluator);

		bestMove = bestMove % 10;
		return (int) bestMove;
	}
    
    // ComputerMove() simply calls MiniMax() and then returns the best move. 
    // MiniMax() is the longest and most complicated method in this program:
    public long MiniMax (BoardC curbrd, int depth, int player)
    {
        ArrayList legal = new ArrayList();
        boolean verbose = false;
        legal = curbrd.LegalMoves();
        
        long bestVal = -99999999999999999L*player;
        
        // return if there is a winner
        int result = curbrd.CheckWinner();
            
        if (result != 0) // there is a winner
        {
            if (player == 1)
                return 1;
            if (player == -1)
                return 10000;
        }
        
        // return if we've searched to depth
        if (depth == 0) return (curbrd.EvalBoard());
        
        // return if there are no more moves
        if (legal.size() == 0) 
        {
            return (curbrd.EvalBoard());
        }
        
        for (int i = 0; i < legal.size(); i++)
        {
            BoardC trybrd = new BoardC();
            trybrd.CopyBoard(curbrd);
            int move = ((Integer)legal.get(i)).intValue();
            
            if (verbose)
            {
                System.out.println("Depth: " + depth + "Examining move: " + move);
            }
            
            if (player == 1)
            {
                trybrd.MakeMove(move, -2);
            }
            else if (player == -1)
            {
                trybrd.MakeMove(move, -1);
            }
            else System.out.println("Failure in MiniMax");
            
            long curVal = MiniMax(trybrd, depth-1, -player);
            
            if (player == 1)
            {
                if (bestVal <= ((curVal*10)+move))
                bestVal = curVal*10+move;
            }
            
            if (player == -1)
                if (bestVal >= ((curVal*10)+move))
                    bestVal = curVal*10+move;
            
            if (verbose)
            {
                System.out.println("Depth: " + depth + " Player: " + player + " Move: " + move + " curVal: " + curVal + " bestVal: " + bestVal);
            }
        }
        
        // System.out.println("Depth: " + depth + "Player:" + player + " bestval: " + bestVal);
        return bestVal;
    }
    
    // Adapted MiniMax
    public long MiniMax (BoardC curbrd, int depth, int player, TicTacToeIndividual ind)
    {
        ArrayList legal = new ArrayList();
        boolean verbose = false;
        legal = curbrd.LegalMoves();
        
        long bestVal = -99999999999999999L*player;
        
        // return if there is a winner
        int result = curbrd.CheckWinner();
            
        if (result != 0) // there is a winner
        {
            if (player == 1)
                return 1;
            if (player == -1)
                return 10000;
        }
        
        // return if we've searched to depth
        if (depth == 0)
			return (ind.evaluate(curbrd));
        
        // return if there are no more moves
        if (legal.size() == 0) 
            return (ind.evaluate(curbrd));
        
        for (int i = 0; i < legal.size(); i++)
        {
            BoardC trybrd = new BoardC();
            trybrd.CopyBoard(curbrd);
            int move = ((Integer)legal.get(i)).intValue();
            
            if (verbose)
            {
                System.out.println("Depth: " + depth + "Examining move: " + move);
            }
            
            if (player == 1)
            {
                trybrd.MakeMove(move, -2);
            }
            else if (player == -1)
            {
                trybrd.MakeMove(move, -1);
            }
            else System.out.println("Failure in MiniMax");
            
            long curVal = MiniMax(trybrd, depth-1, -player, ind);
            
            if (player == 1)
            {
                if (bestVal <= ((curVal*10)+move))
                bestVal = curVal*10+move;
            }
            
            if (player == -1)
                if (bestVal >= ((curVal*10)+move))
                    bestVal = curVal*10+move;
            
            if (verbose)
            {
                System.out.println("Depth: " + depth + " Player: " + player + " Move: " + move + " curVal: " + curVal + " bestVal: " + bestVal);
            }
        }
        
        // System.out.println("Depth: " + depth + "Player:" + player + " bestval: " + bestVal);
        return bestVal;
    }
    
    private ArrayList LegalMoves()
    {
        ArrayList legal = new ArrayList();
    
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (Board[i][j] == Convert(i,j))
                    legal.add(new Integer(Convert(i,j)));
    
        return legal;
    }
    
    public void MakeMove (int move, int player)
    { 
        move--;
        int i = move/3;
        int j = move % 3;
        
        //System.out.println(i + ", " + j);
        
        Board[i][j] = player;
    }
    
    
    // 0 = no winner; -1/-2 if win
    public int CheckWinner ()
    {
    
        // Check rows
        for (int i = 0; i<3; i++)
            if ((Board[i][0] == Board[i][1]) && (Board[i][1] == Board[i][2]))
                return Board[i][0];
        
        // Check columns
        for (int j = 0; j<3; j++)
            if ((Board[0][j] == Board[1][j]) && (Board[1][j] == Board[2][j]))
                return Board[0][j];
        
        // Check diagonals
        if ((Board[0][0] == Board[1][1]) && (Board[1][1] == Board[2][2]))
            return Board[0][0];
        if ((Board[0][2] == Board[1][1]) && (Board[1][1] == Board[2][0]))
            return Board[0][2];
        
        return 0;
    }
    
    private int EvalBoard ()
    {
        int score = 0;
        
        // center is worth 4
        score += 4 * centerPiece( -2 );
        
        // corners are worth 3
        score += 3 * cornerPieces( -2 );
        
        // everything else worth 2
        score += 2 * nonCornerCenterPieces( -2 );

        
        return score;
    }
    
    // Evauluation Helper Methods
    public int centerPiece(int player)
    {
        if (Board[1][1] == player)
            return 1;
        
        return 0;
    }
    
    public int cornerPieces(int player)
    {
        int total = 0;
        
        if (Board[0][0] == player)
            ++total;
        if (Board[0][2] == player) 
            ++total;
        if (Board[2][0] == player) 
            ++total;
        if (Board[2][2] == player) 
            ++total;
            
        return total;
    }
    
    public int nonCornerCenterPieces(int player)
    {
        int total = 0;
        
        if (Board[0][1] == player)
            ++total;
        if (Board[1][0] == player)
            ++total;
        if (Board[2][1] == player)
            ++total;
        if (Board[1][2] == player)
            ++total;
        
        return total;
    }

    public int twoInCorner(int player)
    {
        int total = 0;
        
        total = (int) cornerPieces(player)/2;
        
        return total;
    }
}