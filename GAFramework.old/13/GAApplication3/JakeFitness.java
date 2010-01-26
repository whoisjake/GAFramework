import java.util.Vector;
public class JakeFitness implements FitnessFunction {
	public double evaluate (GAIndividual individual, Vector pop) {
		CheckersEvaluator subject = (CheckersEvaluator) individual;
		CheckersPlayer red = new CheckersPlayer(subject);
		double redCount = 0.0;
		for(int k=0; k < (pop.size()/3); k++)
		{
				
				CheckersEvaluator opponent = (CheckersEvaluator )((IndividualHolder) pop.elementAt(k)).individual();
				
				CheckersPlayer black = new CheckersPlayer (opponent);
				Checkers game = new Checkers(red,black);
				String winner;
				System.out.print("Game vs. Player #"+k);
				if(game.playGame() == Board.RED)
				{
					winner = "red";
					redCount = redCount + (pop.size()/3) - k;
				}
				else
				{
					winner = "black";
					if (game.getBoard().redPieces()>0)
						redCount = redCount + 0.5;
				}

				System.out.println("\twinner: "+winner);
				
		}
		return redCount;
	}
}