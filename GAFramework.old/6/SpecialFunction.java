
   import java.util.Vector;
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
