//***********************************************
// file:   Evaluator.java
// author: Jake Good
// date:   March 30, 2002
// notes:  Evaluation interface for checkers
//***********************************************
public interface Evaluator
{
	// static evaluation function for Checkers
	public int evaluate (Board position);
	public void setColor (int color);
}

