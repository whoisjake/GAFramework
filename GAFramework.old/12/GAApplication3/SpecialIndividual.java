// Created By: Patrick Burke and Jake Good
// Modified Last:   Patrick Burke on Sat Mar 09 2002
//
// Comments:  This program has been altered to implement GAIndividual,
//	      the new requirements for the interface have been added.

//#SpecialIndividual:#//
//#A 16-bit implementation that attempts to evolve to all ones.#//
//#All information for this class is encoded as a bit string.#//

import java.io.*;

   public class SpecialIndividual implements GAIndividual, Serializable
   {
      private int [] bitString;
   
      public SpecialIndividual () {
         bitString = new int [16];
         for (int i = 0; i< bitString.length; i++) {
            bitString [i] = Math.round ((float)Math.random());
         }
      }
   
      public SpecialIndividual (int [] initial) {
         System.arraycopy(initial,0,bitString,0,initial.length);
      }
   
      public SpecialIndividual (int [] mother, int [] father) {
         bitString = new int [mother.length];
         System.arraycopy(mother,0,bitString,0,mother.length/2);
         System.arraycopy(father,mother.length/2,bitString,mother.length/2,bitString.length-mother.length/2);
      }
      public GAIndividual randomInstance () {
         return new SpecialIndividual();
      }
      public GAIndividual directReproduce()
      {
         return this;
      }
   
      public void mutate (double rate) {
         if (Math.random() <= rate) {
            int child [] = bitString;
            int bitToChange = (int)(Math.random() * child.length);
            if (child [bitToChange] == 1){
               child [bitToChange] = 0; }
            else { child [bitToChange] = 1;}
         }
      }
   
      public GAIndividual reproduce (GAIndividual i) {
         return new SpecialIndividual (bitString, ((SpecialIndividual)i).body());
      }
   
      public int [] body () {
         return bitString;
      }
   
      public String toString () {
         String s = "";
         for (int i = 0;i<bitString.length;i++)
            s = (s + (bitString[i]));
         return s;
      }
   }