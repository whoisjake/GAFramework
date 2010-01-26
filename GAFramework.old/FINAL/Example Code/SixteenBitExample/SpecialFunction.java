//***********************************************
// file:   SpecialFunction.java
// author: Patrick Burke
// date:   March 09, 2002
// notes:  
//***********************************************
// updated: PJ
// date:    March 11, 2002
// notes:   Updated to work "Product Example"
//     !no longer relfected in this code
//***********************************************
// updated: Ryan Dixon
// date:    April 06, 2002
// notes:   Added Description comments.  Used in
//          conjunction with GAApplication.
//**********************************************
// updated: Ryan Dixon
// date:    April 20, 2002
// notes:   Commented import for java 1.4 compatibility
//**********************************************

// Commented imports for java 1.4 compatibility
//   import SpecialIndividual;
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
