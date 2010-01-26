public interface Individual
{	
	public Individual reproduce ();
	
	public Individual reproduce (Individual i);

	public int fitness ();

	public void setFitness (int fit);

	public void print ();
}
