//***********************************************
// file:   CheckersPlayer.java
// author: Jake Good
// date:   March 30, 2002
// notes:  
//***********************************************
   import java.util.Vector;
   public class CheckersPlayer
   {
      Evaluator function;
      int color;
      public CheckersPlayer(Evaluator fnx)
      {
         function = fnx;
      }
      public void setColor(int color)
      {
         this.color = color;
         function.setColor(color);
      }
   
      public int[] nextMove(Board currentBoard)
      {
         int score;
         int[] result = new int[4];
         int counter = 0;
         score = MiniMax(currentBoard,0,3,result,opponent(color),counter, Board.INFINITY, -Board.INFINITY);
         return result;
      }
   
      private int evaluateMove(Board position)
      {
         return function.evaluate(position);
      }
      private int opponent(int turn)
      {
         return turn==Board.BLACK ? Board.RED : Board.BLACK;
      }
      public int MiniMax(Board board, int depth, int max_depth, int[] the_move, int turn, int counter, int red_best, int black_best)
      {
         int the_score=0;
         Board new_board = new Board();
         int best_score, chosen_score;
         int[] best_move=new int[4];
      
         Vector moves_list = new Vector();  //vector of 4x1 arrays
         Thread.yield();
      
      //assumes that depth is never equal to max_depth to begin with since
      //chosen_move is not set here
         if (depth==max_depth) 
         {
            best_score = evaluateMove(board);
            counter++;
         }
         else
         {
            moves_list = board.generate_moves(turn);
            best_score = (turn == Board.BLACK ? -Board.INFINITY : Board.INFINITY);
            switch (moves_list.size())
            {
               case 0:
                  counter++;
                  return best_score;
               case 1:
                  if (depth == 0)  
                  {
                  // forced move: immediately return control
                     best_move = (int[])moves_list.elementAt(0);
                     for (int k=0; k<4; k++)
                        the_move[k]=best_move[k];
                     return 0;
                  }
                  else
                  {
                  // extend search since there is a forcing move
                     max_depth += 1;
                  }
            }    
            for (int i=0;i<moves_list.size();i++){
               new_board = new Board(board);  //board need not be touched
               new_board.move_board((int[])moves_list.elementAt(i)); //returns new_board
               int temp[] = new int[4];
               the_score= MiniMax(new_board, depth+1, max_depth, temp, opponent(turn), counter, red_best, black_best);
            
               if (turn==Board.BLACK && the_score > best_score) {
                  best_move = (int[])moves_list.elementAt(i);
                  best_score = the_score;
                  if (best_score > black_best) 
                  {
                     if (best_score >= red_best) 
                        break;  /*  alpha_beta cutoff  */
                     else
                        black_best = best_score;  //the_score
                  }
               }
               
               else if (turn==Board.RED && the_score < best_score) {
                  best_move = (int[])moves_list.elementAt(i);
                  best_score = the_score;
                  if (best_score < red_best) 
                  {
                     if (best_score <= black_best) 
                        break;  /*  alpha_beta cutoff  */
                     else
                        red_best = best_score;  //the_score
                  }
               }
            
            } //end for
         }//end else
         for (int k=0; k<4; k++)
            the_move[k]=best_move[k];
         return best_score;
      }
   }
