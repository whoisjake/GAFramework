// Created By: Patrick Burke and Jake Good
// Modified:   Ryan Dixon
//
// Comments:  This program has been altered to implement GAIndividual,
//	      the new requirements for the interface have been added.

public class SpecialIndividual implements GAIndividual
{
    private int fitness;
    private int [] bitString;
    private int rouletteStart;
    private int rouletteFinish;

    public SpecialIndividual () {
        fitness = 0;
        rouletteStart = 0;
        rouletteFinish = 0;
        bitString = new int [16];
        for (int i = 0; i< bitString.length; i++) {
                bitString [i] = Math.round ((float)Math.random());
        }
    }

    public SpecialIndividual (int [] initial) {
        fitness = 0;
        System.arraycopy(initial,0,bitString,0,initial.length);
        //for (int i = 0; i< initial.length; i++) {
        //    bitString [i] = initial [i] / 1;
        //}
    }
    public SpecialIndividual (int [] mother, int [] father) {
        fitness = 0;
        bitString = new int [mother.length];
        System.arraycopy(mother,0,bitString,0,mother.length/2);
        System.arraycopy(father,mother.length/2,bitString,mother.length/2,bitString.length-mother.length/2);
        
        //bitString = new int [mother.length];
        //for (int i = 0; i < (mother.length /2); i++)
        //    bitString [i] = mother [i] / 1;
        //for (int i = (mother.length /2); i < father.length; i ++)
        //    bitString [i] = father [i] / 1;
    }

    // mutate() addeded to comply with new interface
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

    public int getFitness () {
        return fitness;
    }

    public void setFitness (int fit) {
        fitness = fit;
    }

    public int [] body () {
        return bitString;
    }

    public String toString () {
        String s = "";
        for (int i = 0;i<bitString.length;i++)
            s = (s + (bitString[i]));
        s = (s + " " + fitness);
        return s;
    }
    public void setRoulette (int start, int finish) {
        rouletteStart = start;
        rouletteFinish = finish;
    }
    public boolean hasRouletteNumber (int winner){
        return ((winner >= rouletteStart) && (winner <= rouletteFinish));
    }
}