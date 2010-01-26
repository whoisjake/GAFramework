package edu.uni.Checkers;
//***********************************************
// file:   DefaultEvaluator.java
// author: Jake Good
// date:   March 30, 2002
// notes:  the default evaluator
//***********************************************


public class DefaultEvaluator implements Evaluator
{
	private int CHECKER = 100;    //one checker worth 100  
	private int POS=1;            //one position along the -j worth 1
	private int KING=200;         //a king's worth
	private int EDGE=10;          // effect of king being on the edge
	private int RANDOM_WEIGHT=10; // weight factor
	private int color;
	
	public DefaultEvaluator()
	{
	}

	public void setColor(int color)
	{
		this.color=color;
	}

	public int opposit(int piece)
	{
		int result = 0;
		if(piece == Board.RED)
			result = Board.BLACK;
		else if (piece == Board.RKING)
			result = Board.BKING;
		else if (piece == Board.BLACK)
			result = Board.RED;
		else if (piece == Board.BKING)
			result = Board.RKING;
		return result;
	}

	private int yourKing()
	{
		if(color==Board.BLACK)
			return Board.BKING;
		else
			return Board.RKING;
	}

	//**************************************************
	// taken from Engine.java
	// (C)opyright 1997 Victor Huang and Sung Ha Huh.
	// changed calls to match our implementation
	//**************************************************
	public int evaluate(Board position)
	{
		int score=0;

    	for (int i=0; i<8; i++)
		{
      	for (int j=0; j<8; j++)
			{
				if (position.getPiece(i,j) == opposit(color))
	      	{
					score-=CHECKER;
					score-=POS*j*j;
	      	}
	    		else if (position.getPiece(i,j) == opposit(yourKing()))
				{
	      		score-=KING;
		  			if (i==0 || i==7)
			  			score += EDGE;
		  			if (j==0 || j==7)
			  			score += EDGE;
				}
				else if (position.getPiece(i,j) == yourKing())
				{
	      		score+=KING;
		  			if (i==0 || i==7)
			  			score -= EDGE;
		  			if (j==0 || j==7)
			  			score -= EDGE;
	    		}
	    		else if (position.getPiece(i,j) == color)
	    		{
					score+=CHECKER;
					score+=POS*(7-j)*(7-j); 
				}
			}
		}
		score += (int)(Math.random() * RANDOM_WEIGHT);
   	return score;
	}
}

