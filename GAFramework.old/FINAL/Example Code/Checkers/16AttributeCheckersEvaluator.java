//***********************************************
// file:   16AttributeCheckersEvaluator.java
// author: Patrick Burke
// date:   March 30, 2002
// notes:  Used for evaluation of checkers minimax
//***********************************************
// updated: Ryan Dixon
// date:    April 06, 2002
// notes:   Added Serialization capability for use with
//          GAObjectMonitor.  Default serialized version
//          checking is used.
//**********************************************
// updated: PJ
// date:    May 02, 2002
// notes:   Analyzes 16 board attributes.
//**********************************************

//#16AttributeCheckersEvaluator:#//
//#Analyzes sixteen board aspects to determine#//
//#the fitness of static positions.#//

import java.io.*;

public class CheckersEvaluator implements GAIndividual, Evaluator, Serializable {
	private int [] attributes;
	private int max;
	public CheckersEvaluator () {
		attributes = new int [16];
		for (int i = 0; i < attributes.length; i++) {
			
			attributes [i] = (int)((Math.random()*16 +1)-10);
		}
	}

	public CheckersEvaluator(int[] values)
	{
		attributes = values;
	}

	public CheckersEvaluator (int [] mother, int [] father) {
		int crossoverPoint = (int)(Math.random()*16);
		attributes = new int [mother.length];
		System.arraycopy(mother,0,attributes,0,crossoverPoint);
		System.arraycopy(father,crossoverPoint,attributes,crossoverPoint,
															attributes.length-crossoverPoint);
	}
	public GAIndividual directReproduce(){return this;}
	public GAIndividual reproduce( GAIndividual i ){
		return new CheckersEvaluator(body(),((CheckersEvaluator)i).body());
	}
	public void mutate(double rate) {
		if (Math.random() <= rate) {
			int child [] = attributes;
			int bitToChange = (int)(Math.random() * child.length);
			attributes [bitToChange] = (int)(Math.random()*16 + 1);
		}
	}
	public GAIndividual randomInstance() {
		return new CheckersEvaluator();
	}
	public String toString() {
		String s = "";
		s = (s + "Red: " + attributes[0] + ", Black: " + attributes[1]);
		s = (s +  ", Red Kings: " + attributes[2] + ", Black Kings: " + attributes[3]);
		s = (s +  ", Red Home: " + attributes[4] + ", Black Home: " + attributes[5]);
		s = (s +  ", Red In Corner: " + attributes[6] + ", Black In corner: " + attributes[7]);
		s = (s +  ", Red King In Center: " + attributes[8] + ", Black King In Center: " + attributes[9]);
		s = (s +  ", Only Red: " + attributes[10] + ", Only Black: " + attributes[11]);
		s = (s +  ", Potential Red King: " + attributes[12] + ", Potential Black King: " + attributes[13]);
		s = (s +  ", Red Can Capture: " + attributes[14] + ", Black Can Capture: " + attributes[15]);
		return s;
	}
	public int evaluate (Board position) {
		int sum = 0;
		if (max == 1){
			sum = sum + (position.redPieces() * attributes[0]);
			sum = sum + (position.blackPieces() * attributes[1]);
			sum = sum + (position.redKings() * attributes[2]);
			sum = sum + (position.blackKings() * attributes[3]);
			sum = sum + (position.redHome() * attributes[4]);
			sum = sum + (position.blackHome() * attributes[5]);
			sum = sum + (position.redInCorner() * attributes[6]);
			sum = sum + (position.blackInCorner() * attributes[7]);
			sum = sum + (position.rKingInCenter() * attributes[8]);
			sum = sum + (position.bKingInCenter() * attributes[9]);
			sum = sum + (position.onlyRed() * attributes [10]);
			sum = sum + (position.onlyBlack() * attributes[11]);
			sum = sum + (position.potentialRKing() * attributes[12]);
			sum = sum + (position.potentialBKing() * attributes[13]);
			sum = sum + (position.captureByRed() * attributes[14]);
			sum = sum + (position.captureByBlack() * attributes[15]);
			return sum;
		}
		sum = sum + (position.redPieces() * attributes[1]);
		sum = sum + (position.blackPieces() * attributes[0]);
		sum = sum + (position.redKings() * attributes[3]);
		sum = sum + (position.blackKings() * attributes[2]);
		sum = sum + (position.redHome() * attributes[5]);
		sum = sum + (position.blackHome() * attributes[4]);	
		sum = sum + (position.redInCorner() * attributes[7]);
		sum = sum + (position.blackInCorner() * attributes[6]);
		sum = sum + (position.rKingInCenter() * attributes[9]);
		sum = sum + (position.bKingInCenter() * attributes[8]);
		sum = sum + (position.onlyRed() * attributes [11]);
		sum = sum + (position.onlyBlack() * attributes[10]);
		sum = sum + (position.potentialRKing() * attributes[13]);
		sum = sum + (position.potentialBKing() * attributes[12]);
		sum = sum + (position.captureByRed() * attributes[15]);
		sum = sum + (position.captureByBlack() * attributes[14]);

		return sum;
	}
	public void setColor (int color) {
		max = color;
	}
	private int [] body() {return attributes;}

	public int[] getAttributes() {return attributes;}

}
