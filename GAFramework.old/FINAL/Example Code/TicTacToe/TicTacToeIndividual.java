//***********************************************
// file:   TicTacToeIndividual.java
// author: Ryan Dixon
// date:   May 02, 2002
// notes:  Used as GAIndividual for evolving
//         better tic-tac-toe players.
//         Currently evaluates 4 attributes (linear combinations).
//***********************************************

//#TicTacToeIndividual:#//
//#Analyzes board positions based on four aspects.#//
//#Capable of rapidly converging to semi-optimal answers.#//

import java.io.*;

public class TicTacToeIndividual implements GAIndividual, Serializable
{
    private int [] attributes;
    private int player;
    
    public TicTacToeIndividual()
    {
        attributes = new int [4];
        for (int i = 0; i < attributes.length; i++)
        {
            attributes [i] = (int) (Math.random()*11);
        }
    }
    
    public TicTacToeIndividual(int player)
    {
        this.player = player;
        
        attributes = new int [4];
        for (int i = 0; i < attributes.length; i++)
        {
            attributes [i] = (int) (Math.random()*11);
        }
    }
    
    public TicTacToeIndividual(int[] values)
    {
        attributes = values;
    }
    
    public TicTacToeIndividual(int[] values, int player)
    {
        attributes = values;
        this.player = player;
    }

    public TicTacToeIndividual (int [] mother, int [] father, int player)
    {
        int crossoverPoint = (int)(Math.random()*4);
        attributes = new int [mother.length];
        System.arraycopy(mother,0,attributes,0,crossoverPoint);
        System.arraycopy(father,crossoverPoint,attributes,crossoverPoint,
                            attributes.length-crossoverPoint);
        
        this.player = player;
    }
    
    public GAIndividual directReproduce(){return this;}
    
    public GAIndividual reproduce( GAIndividual i )
    {
        return new TicTacToeIndividual(body(),((TicTacToeIndividual)i).body(), player);
    }
    
    public void mutate(double rate)
    {
        if (Math.random() <= rate)
        {
            int child [] = attributes;
            int bitToChange = (int)(Math.random() * child.length);
            attributes [bitToChange] = (int)(Math.random()*4 + 1);
        }
    }
    
    public GAIndividual randomInstance()
    {           
        return new TicTacToeIndividual();
    }
    
    public String toString()
    {
        String s = new String("");
        s = (s + "A0: " + attributes[0] + ", A1: " + attributes[1]);
        s = (s +  ", A2: " + attributes[2] + ", A3: " + attributes[3]);
        //s = (s +  ", A4: " + attributes[4] + ", A5: " + attributes[5]);
        
        return s;
    }
    
    public int evaluate (BoardC board)
    {
        int sum = 0;

       	sum = sum + (board.centerPiece(player) * attributes[0]);
        sum = sum + (board.cornerPieces(player) * attributes[1]);
        sum = sum + (board.nonCornerCenterPieces(player) * attributes[2]);
        sum = sum + (board.twoInCorner(player) * attributes[3]);
        //sum = sum + (board.redHome() * attributes[4]);
        //sum = sum + (board.blackHome() * attributes[5]);
        
        //System.out.println("sum: " + sum);
        return sum;
    }
    
    public void setPlayer (int player)
    {
        this.player = player;
    }
    
    private int [] body() { return attributes; }
    public int[] getAttributes() { return attributes; }

}
