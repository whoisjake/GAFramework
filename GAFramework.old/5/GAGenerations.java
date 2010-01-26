import java.io.*;
public class GAGenerations
{
	int numGenerations;

	public GAGenerations()
	{
		numGenerations = 0;
	}

	public void savePopulation(GAPopulation pop) throws IOException
	{
		FileOutputStream fileStream = new FileOutputStream("generation"+numGenerations+".tmp"); 
		ObjectOutputStream objectStream = new ObjectOutputStream(fileStream); 
		objectStream.writeObject(pop);
		objectStream.close();
		fileStream.close();
		numGenerations++;
	}

	public GAPopulation getPopulation(int num) throws IOException, ClassNotFoundException
	{
		FileInputStream fileStream = new FileInputStream("generation"+num+".tmp"); 
		ObjectInputStream objectStream = new ObjectInputStream(fileStream); 
		GAPopulation pop = (GAPopulation) objectStream.readObject();
		objectStream.close();
		fileStream.close();
		return pop;
	}

	public int numGenerations()
	{
		return numGenerations;
	}
}