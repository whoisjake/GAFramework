// Created By: Patrick Burke and Jake Good
// Modified:   Ryan Dixon
//					Jake Good on Sat Feb 16 2002
//
// Comments:  Planetary System will hold one formula that will try to fit the
//            sample table given.

    public class PlanetarySystem implements GAIndividual
   {
      private double [][] valueTable;		// holds values for its own table for simple checking
      private MathEvaluator formula;		// holds the single formula for its own self
  		private String [] operators;    
		private double fitness;					// fitness
		private double previousFitness;     //
      private int rouletteStart;			// roulette wheel
      private int rouletteFinish;
   
      public PlanetarySystem () {
         fitness = 0;
         previousFitness = 0;
         rouletteStart = 0;
         rouletteFinish = 0;
			formula = new MathEvaluator("A + A");
			operators = formula.operators();
         valueTable = new double [6][2];
         initTable();
      }
   
       public PlanetarySystem (String exp)
		 {
         fitness = 0;
			previousFitness = 0;
         rouletteStart = 0;
         rouletteFinish = 0;
			formula = new MathEvaluator(exp);
			operators = formula.operators();
         valueTable = new double [6][2];
         initTable();      
		}
   
    // mutate() addeded to comply with new interface
       public GAIndividual directReproduce()
      {
         return new PlanetarySystem (newExp());
      }

/////////////////////////////////////////////////////////////////////////////// 
      public void mutate (double rate) {
			if (Math.random() < rate)
			// {formula = new MathEvaluator("sqrt{("+formula.getExpression()+"))");}
			 {formula = new MathEvaluator("sqrt A"); fitness = 0.0;}

      }
   
      public GAIndividual reproduce (GAIndividual i) {
		String newOperator = operators[(int) (Math.random()*6)];
		PlanetarySystem result;
		if (Math.random() > .05)
		{	result = new PlanetarySystem(formula.getExpression() + newOperator + "("+newExp()+")" + newOperator + i.toString()); }
		else
		{ 	result = new PlanetarySystem(newExp() + newOperator + i.toString()); }
		return result;      
		}

		public String newExp()
		{
			String newOperator = operators[(int) (Math.random()*6)];
			return "(" + formula.getExpression() + ")" + newOperator + "A";
		} 
/////////////////////////////////////////////////////////////////////////////// 
       public double getFitness () {
         return fitness;
      }
   
       public void setFitness (double fit) {
         fitness = fit;
      }
      
       public String toString () {
         return formula.getExpression();
      }
   
       public void setRoulette (int start, int finish) {
         rouletteStart = start;
         rouletteFinish = finish;
      }
       public boolean hasRouletteNumber (int winner){
         return ((winner >= rouletteStart) && (winner <= rouletteFinish));
      }
   
       private void initTable()
      {
         for(int a=0; a<6; a++)
         {
            for(int p=0; p<2; p++)
            {
               valueTable[a][p]=0.0;
            }
         }
      }
   
   }