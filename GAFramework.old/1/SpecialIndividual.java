public class SpecialIndividual implements Individual
{
	private int fitness;
	private int [] bitString;

	public SpecialIndividual () {
		fitness = 0;
		bitString = new int [16];
		for (int i = 0; i< bitString.length; i++) {
			bitString [i] = Math.round ((float)Math.random());
		}
	}

	public SpecialIndividual (int [] initial) {
		fitness = 0;
		bitString = new int [initial.length];
		for (int i = 0; i< initial.length; i++) {
			bitString [i] = initial [i];
		}
	}

	public Individual reproduce () {
		int [] child = bitString;
		int bitToChange = (int)(Math.random() * child.length);
		if (child [bitToChange] == 1){
			child [bitToChange] = 0; }
		else { child [bitToChange] = 1;}
		
		return new SpecialIndividual (child);
		
	}

	public Individual reproduce (Individual i) {
		return new SpecialIndividual (bitString);
	}

	public int fitness () {
		return fitness;
	}

	public void setFitness (int fit) {
		fitness = fit;
	}

	public int [] body () {
		return bitString;
	}

	public void print () {
		for (int i = 0;i<bitString.length;i++)
			System.out.print (bitString[i]);
		System.out.println (" " + fitness);
	}
}
