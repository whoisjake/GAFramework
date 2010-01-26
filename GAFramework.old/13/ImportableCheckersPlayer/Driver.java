import java.awt.event.*;

public class Driver
{
	public static void main( String [ ] args )
	{
		Checkers game = new Checkers();
		game.show();
		
		game.addWindowListener(new WindowAdapter()
	            { public void windowClosing(WindowEvent e) {System.exit(0);} });

	}
}
