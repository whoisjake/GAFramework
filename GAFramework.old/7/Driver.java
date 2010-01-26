public class Driver
{
	public static void main( String [ ] args )
	{
		CheckersPlayer black = new CheckersPlayer(new DefaultEvaluator());
		CheckersPlayer red = new CheckersPlayer(new DefaultEvaluator());
		Checkers game = new Checkers(black,red);
		int redCount = 0;
		int blackCount = 0;

		for(int i=1; i <= 10; i++)
		{
			String winner;
			System.out.println("Game "+i);
			if(game.playGame() == Board.BLACK)
			{
				winner = "black";
				blackCount++;
			}
			else
			{
				winner = "red";
				redCount++;
			}

			System.out.println("Game "+i+"  winner: "+winner);
		}
		System.out.println("");
		System.out.println("Red   Won: "+redCount+" games");
		System.out.println("Black Won: "+blackCount+" games"); 
	}
}