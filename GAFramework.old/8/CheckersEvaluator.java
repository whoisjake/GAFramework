
public class CheckersEvaluator implements GAIndividual, Evaluator {
	private int [] attributes;
	private int max;

	public CheckersEvaluator () {
		attributes = new int [6];
		for (int i = 0; i < attributes.length; i++) {
			
			attributes [i] = (int)((Math.random()*20 +1)-10);
		}
	}
	public CheckersEvaluator (int [] mother, int [] father) {
		int crossoverPoint = (int)(Math.random()*6);
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
			attributes [bitToChange] = (int)(Math.random()*6 + 1);
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
			return sum;
		}
		sum = sum + (position.redPieces() * attributes[1]);
		sum = sum + (position.blackPieces() * attributes[0]);
		sum = sum + (position.redKings() * attributes[3]);
		sum = sum + (position.blackKings() * attributes[2]);
		sum = sum + (position.redHome() * attributes[5]);
		sum = sum + (position.blackHome() * attributes[4]);	
		return sum;
	}
	public void setColor (int color) {
		max = color;
	}

	private int [] body() {return attributes;}

}
