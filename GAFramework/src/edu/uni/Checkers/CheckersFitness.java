package edu.uni.Checkers;
//***********************************************
// file:   CheckersFitness.java
// author: Patrick Burke
// date:   March 30, 2002
// notes:  Created for Checkers fitness evaluation
//         process 
//***********************************************


//#CheckersFitness:#//
//#Rates Checkers players based on games won#//
//#during tournament conditions.#//

// Commented imports for java 1.4 compatibility
//import CheckersPlayer;
//import Checkers;
//import CheckersEvaluator;
import edu.uni.GAFramework.FitnessFunction;
import edu.uni.GAFramework.GAIndividual;
import java.util.Vector;


public class CheckersFitness implements FitnessFunction {
	public double evaluate (GAIndividual individual, Vector pop) {
		CheckersEvaluator subject = (CheckersEvaluator) individual;
		CheckersPlayer red = new CheckersPlayer(subject);
		Checkers game = new Checkers(red);
		double redCount = 0.0;

		for(int i=1; i <= 4; i++)
		{
			String winner;
			System.out.println("Game "+i);
			if(game.playGame() == Board.RED)
			{
				winner = "red";
				redCount = redCount + 1.0;
			}
			else
			{
				winner = "black";
				if (game.getBoard().redPieces()>0)
					redCount = redCount + 0.5;
			}

			System.out.println("Game "+i+"  winner: "+winner);
		}
		return redCount;
	}
}
