   
   import java.util.Vector;
   
//#SpecialFunction:#//
//#A 16-bit implementation that attempts to evolve to all ones.#//
//#Fitness is calculated by adding the ones within the 16-bit sequence.#//

   public class SpecialFunction implements FitnessFunction
   {
      public double evaluate(GAIndividual individual, Vector pop)
      {
      
         int sum=0;
         int [] bitString = ((SpecialIndividual)individual).body();
      
         for(int bit=0; bit < 16; bit++)
         {
            sum+=bitString[bit];
         }
      
         return sum;
      }
   }
