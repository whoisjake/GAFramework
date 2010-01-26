import java.util.Vector;
public class PatrickFitness implements FitnessFunction {
	public double evaluate (GAIndividual individual, Vector pop) {
		Evaluator subject = (Evaluator) individual;
		CheckersPlayer red = new CheckersPlayer(subject);
		int adversary = (int)(Math.random() * (pop.size()-1));
		Evaluator opponent = (Evaluator)((IndividualHolder) pop.elementAt(adversary)).individual();
		CheckersPlayer black = new CheckersPlayer (opponent);
		double redCount = 0.0;
		Checkers game;
		for(int i=1; i <= 8; i++)
		{
			if (i % 2 == 0){
				game = new Checkers(red, black);
			} else {
				game = new Checkers(black,red);
			}
			String winner;
			System.out.println("Game "+i);
			int result = game.playGame();
			if((result == Board.RED) && (1 % 2 == 0))
			{
				winner = "red";
				redCount = redCount + 1.0;
			}
			else if (result == Board.EMPTY)
			{
				winner = "draw";
				redCount = redCount + 0.5;
			}
			else
			{
				winner = "black";
			}

			System.out.println("Game "+i+"  winner: "+winner);
		}
		return redCount;
	}
}
