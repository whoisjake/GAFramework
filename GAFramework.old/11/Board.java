//***********************************************
// file:   Board.java
// author: Jake Good
// date:   March 30, 2002
// notes:  
//***********************************************
   import java.util.Vector;
   public class Board
   {
      private int[][] position;
   // internal representation of checkers pieces
      static final int RED = 1;
      static final int BLACK = 2;
      static final int RKING = 3;
      static final int BKING = 4;
      static final int EMPTY = 0;
      static final int LEGALMOVE = 1;
      static final int ILLEGALMOVE = 2;
      static final int INCOMPLETEMOVE = 3;
      static final int INFINITY = Integer.MAX_VALUE;
   
      public Board()
      {
         reset();
      }
   
      public Board(Board current)
      {
         position = current.getBoard();
      }
   
      public Board(int[][] current)
      {
         position = current;
      }
   
      public void reset()
      {
         position = new int[8][8];
         for (int i=0; i<8; i++)
         {
            for (int j=0; j<8; j++)
               position[i][j] = EMPTY;
            for (int j=0; j<3; j++)
               if ((i+j)%2 == 1)
                  position[i][j] = RED;
            for (int j=5; j<8; j++)
               if ((i+j)%2 == 1)
                  position[i][j] = BLACK;
         }
      }
   
      public void applyMove(int[][] move)
      {
         for (int i=0; i<8; i++)
            System.arraycopy(move[i],0,position[i],0,8);
      }
   
   
      public int getPiece(int row, int column)
      {
         return position[row][column];
      }
   
      public void print()
      {
         String piece;
         for (int i=0; i<8; i++)
         {
            for (int j=0; j<8; j++)
            {
               if(position[i][j]==BLACK)
                  piece = "B ";
               else if (position[i][j]==BKING)
                  piece = "BK";
               else if (position[i][j]==RKING)
                  piece = "RK";
               else if (position[i][j]==RED)
                  piece = "R ";
               else 
                  piece = "  ";
               System.out.print("["+piece+"]");
            }
            System.out.println();
         }
         System.out.println();
      }
   
      public int[][] getBoard()
      {
         int[][] copy = new int[8][8];
         for (int i=0; i<8; i++)
            System.arraycopy(position[i],0,copy[i],0,8);
         return copy;
      }
   
      public boolean noMovesLeft(int toMove)
      {
         for (int i=0; i<8; i++)
         {
            for (int j=0; j<8; j++)
               if ( (float)(i+j)/2 != (i+j)/2 )
               {
                  if (toMove == RED &&
                        (position[i][j] == RED ||
                        position[i][j] == RKING))
                  {
                     if (canWalk(i,j)) 
                        return false;
                     else if (canCapture(i,j)) 
                        return false;
                  }
                  else if (toMove == BLACK &&
                             (position[i][j] == BLACK ||
                             position[i][j] == BKING))
                  {
                     if (canWalk(i,j)) 
                        return false;
                     else if (canCapture(i,j)) 
                        return false;
                  }
               }
         }
         return true;
      }
   
      public int ApplyMove(int start_i,int start_j,int end_i,int end_j)
      {
         int result = IsMoveLegal(start_i,start_j,end_i,end_j,position[start_i][start_j]);
         if (result != ILLEGALMOVE)
         {
            if ( Math.abs(end_i - start_i) == 1)
            {
               position[end_i][end_j] = position[start_i][start_j];
               position[start_i][start_j] = EMPTY;
            }
            else // capture
            {
               position[(start_i + end_i)/2][(start_j + end_j)/2] = EMPTY;
               position[end_i][end_j] = position[start_i][start_j];
               position[start_i][start_j] = EMPTY;
            }
         
            if (result == INCOMPLETEMOVE)
            {
                                // if there are no further captures
               if (!(canCapture(end_i,end_j)))
                  result = LEGALMOVE;
            }
         
                        // check for new king
            if ( position[end_i][end_j] == RED && end_j == 7)
               position[end_i][end_j] = RKING;
            else if ( position[end_i][end_j] == BLACK && end_j == 0)
               position[end_i][end_j] = BKING;
         
         }
      
         return result;
      }
   
      public int IsMoveLegal(int start_i,int start_j,int end_i,int end_j,int turn)
      {
         if (! (inRange(start_i,start_j) && inRange(end_i,end_j) ) )
            return ILLEGALMOVE;
         if (position[end_i][end_j] != EMPTY)
            return ILLEGALMOVE;
      
         int piece = position[start_i][start_j];
         if ( Math.abs(start_i - end_i) == 1 )
         {
                // first see if any captures are possible
            switch (piece)
            {
               case RED:
               case RKING:
                  for (int i=0;i<8;i++)
                     for (int j=0;j<8;j++)
                     {
                        if ((position[i][j] == RED ||
                              position[i][j] == RKING)
                           && canCapture(i,j))
                           return ILLEGALMOVE;
                     }
                  break;
               case BLACK:
               case BKING:
                  for (int i=0;i<8;i++)
                     for (int j=0;j<8;j++)
                     {
                        if ((position[i][j] == BLACK ||
                              position[i][j] == BKING)
                           && canCapture(i,j))
                           return ILLEGALMOVE;
                     }
                  break;
            } // switch
         
            switch (piece)
            {
               case RED:
                  if (end_j - start_j == 1) 
                     return LEGALMOVE;
                  break;
               case BLACK:
                  if (end_j - start_j == -1) 
                     return LEGALMOVE;
                  break;
               case RKING:
               case BKING:
                  if ( Math.abs(end_j - start_j) == 1 ) 
                     return LEGALMOVE;
                  break;
            } // switch (piece)
         
            return ILLEGALMOVE;
         
         } // if ( (Math.abs(start_i - end_i) == 1 )
         
         else if ( Math.abs(start_i - end_i) == 2 )
         {
            int cap_i = (start_i + end_i) / 2;
            int cap_j = (start_j + end_j) / 2;
            int cap_piece = position[cap_i][cap_j];
         
            if (turn == RED || turn == RKING)
            {
               if (!(cap_piece == BLACK ||
                     cap_piece == BKING))
                  return ILLEGALMOVE;
            }
            else if (!(cap_piece == RED ||
                       cap_piece == RKING))
               return ILLEGALMOVE;
         
            switch (piece)
            {
               case RED:
                  if (end_j - start_j != 2)
                     return ILLEGALMOVE;
                  break;
               case BLACK:
                  if (end_j - start_j != -2)
                     return ILLEGALMOVE;
                  break;
               case RKING:
               case BKING:
                  if (Math.abs(end_j - start_j) != 2)
                     return ILLEGALMOVE;
            }
         
            return INCOMPLETEMOVE;
         }
         return ILLEGALMOVE;
      }
   
   
      private boolean canWalk(int i, int j)
      {
         switch ( position[i][j] )
         {
            case RED:
               if ( isEmpty(i+1,j+1) || isEmpty(i-1,j+1) )
                  return true;
               break;
            case BLACK:
               if ( isEmpty(i+1,j-1) || isEmpty(i-1,j-1) )
                  return true;
               break;
            case RKING:
            case BKING:
               if ( isEmpty(i+1,j+1) || isEmpty(i+1,j-1) 
                  || isEmpty(i-1,j+1) || isEmpty(i-1,j-1) )
                  return true;
         } // switch
         return false;
      }
   
   // checkers that i and j are between 0 and 7 inclusive
      private boolean inRange(int i, int j)
      {
         return (i>-1 && i<8 && j>-1 && j<8);
      }
   
      private int IsWalkLegal(int start_i,int start_j,int end_i,int end_j,int turn)
      {
         if (! (inRange(start_i,start_j) && inRange(end_i,end_j) ) )
            return ILLEGALMOVE;
         if (position[end_i][end_j] != EMPTY)
            return ILLEGALMOVE;
      
         int piece = position[start_i][start_j];
         if ( Math.abs(start_i - end_i) == 1 )
         {
            switch (piece)
            {
               case RED:
                  if (end_j - start_j == 1) 
                     return LEGALMOVE;
                  break;
               case BLACK:
                  if (end_j - start_j == -1) 
                     return LEGALMOVE;
                  break;
               case RKING:
               case BKING:
                  if ( Math.abs(end_j - start_j) == 1 ) 
                     return LEGALMOVE;
                  break;
            } // switch (piece)
         
            return ILLEGALMOVE;
         
         } // if ( (Math.abs(start_i - end_i) == 1 )
      
         return ILLEGALMOVE;
      }
   
      public void move_board(int[] move)
      {
         int startx = move[0];
         int starty = move[1];
         int endx = move[2];
         int endy = move[3];
         while (endx>0 || endy>0)
         {
            ApplyMove(startx,starty,endx%10,endy%10);
            startx = endx%10;
            starty = endy%10;
            endx /= 10;
            endy /= 10;
         } 
      
      }
   
      public boolean canCapture(int toMove)
      {
         for (int i=0; i<8; i++)
            for (int j=0; j<8; j++)
            {
               if ((position[i][j] == toMove && canCapture(i,j)) ||
                     (position[i][j] == toMove+2 && canCapture(i,j)))
                  return true;
            }
         return false;
      }
   
      private boolean canCapture(int i, int j)
      {
         int x,y;
         boolean result = false;
      
         switch (position[i][j])
         {
            case RED:
               if (i+2<8 && j+2<8)
                  if ( (position[i+1][j+1] == BLACK ||
                        position[i+1][j+1] == BKING)
                     &&
                        (position[i+2][j+2] == EMPTY))
                     return true;
               if (i-2>-1 && j+2<8)
                  if ( (position[i-1][j+1] == BLACK ||
                        position[i-1][j+1] == BKING)
                     &&
                     position[i-2][j+2] == EMPTY)
                     return true;
               break;
            case BLACK:
               if (i+2<8 && j-2>-1)
                  if ( (position[i+1][j-1] == RED ||
                        position[i+1][j-1] == RKING)
                     &&
                     position[i+2][j-2] == EMPTY)
                     return true;
               if (i-2>-1 && j-2>-1)
                  if ( (position[i-1][j-1] == RED ||
                        position[i-1][j-1] == RKING)
                     &&
                     position[i-2][j-2] == EMPTY)
                     return true;
               break;
            case RKING:
               if (i+2<8) // can jump down
               {
                  if (j+2<8) // down right
                     if ( (position[i+1][j+1] == BLACK ||
                           position[i+1][j+1] == BKING )
                        &&
                        position[i+2][j+2] == EMPTY)
                        return true;
                  if (j-2>-1) // down left
                     if ( (position[i+1][j-1] == BLACK ||
                           position[i+1][j-1] == BKING )
                        &&
                        position[i+2][j-2] == EMPTY)
                        return true;
               }
               if (i-2>-1) // can jump up
               {
                  if (j+2<8) // up right
                     if ( (position[i-1][j+1] == BLACK ||
                           position[i-1][j+1] == BKING )
                        &&
                        position[i-2][j+2] == EMPTY)
                        return true;
                  if (j-2>-1) //up left
                     if ( (position[i-1][j-1] == BLACK ||
                           position[i-1][j-1] == BKING )
                        &&
                        position[i-2][j-2] == EMPTY)
                        return true;
               }
            
            
               break;
            case BKING:
               if (i+2<8)
               {
                  if (j+2<8)
                     if ( (position[i+1][j+1] == RED ||
                           position[i+1][j+1] == RKING )
                        &&
                        position[i+2][j+2] == EMPTY)
                        return true;
                  if (j-2>-1)
                     if ( (position[i+1][j-1] == RED ||
                           position[i+1][j-1] == RKING )
                        &&
                        position[i+2][j-2] == EMPTY)
                        return true;
               }
               if (i-2>-1)
               {
                  if (j+2<8)
                     if ( (position[i-1][j+1] == RED ||
                           position[i-1][j+1] == RKING )
                        &&
                        position[i-2][j+2] == EMPTY)
                        return true;
                  if (j-2>-1)
                     if ( (position[i-1][j-1] == RED  ||
                           position[i-1][j-1] == RKING )
                        &&
                        position[i-2][j-2] == EMPTY)
                        return true;
               }
            
            
               break;
         } // switch
         return false;
      }
   
      public Vector generate_moves(int turn) {
         Vector moves_list = new Vector();
         int move;
         int king = turn + 2;
      
         for (int i=7; i>=0; i--)
            for (int j=0; j<8; j++)
               if (king==position[i][j] || turn==position [i][j]){ //ONLY CHECKS IF PIECE IS REGULAR PIECE
                  if (canCapture(turn)) {
                     for (int k=-2; k<=2; k+=4)
                        for (int l=-2; l<=2; l+=4)
                        {
                           move=IsMoveLegal(i,j,i+k,j+l,turn);
                           if (move == INCOMPLETEMOVE)
                           {
                              int[] int_array = new int[4];
                              int_array[0]=i; int_array[1]=j;
                              int_array[2]=i+k; int_array[3]=j+l;
                              Board temp_board = new Board(getBoard());
                              move = temp_board.ApplyMove(i,j,i+k,j+l);
                              if (move == INCOMPLETEMOVE)/*(canCapture(temp_board,i+k,j+l))*/
                              {
                                 temp_board.forceCaptures(turn, int_array,moves_list,10);
                              }
                              else
                              {
                                 moves_list.addElement(int_array);
                              }
                           
                           } // if (move)
                        } // if (inRange)
                  }
                  else {
                     for (int k=-1; k<=2; k+=2)
                        for (int l=-1; l<=2; l+=2){
                           if (inRange(i+k,j+l))
                           {
                              move=IsWalkLegal(i,j,i+k,j+l,turn);
                              if (move == LEGALMOVE)
                              {
                                 int[] int_array = new int[4];
                                 int_array[0]=i; int_array[1]=j;
                                 int_array[2]=i+k; int_array[3]=j+l;
                              //a walk has taken place
                              //add the new array to the Vector moves_list
                                 moves_list.addElement(int_array);
                              } // if (move)
                           } // if (inRange)
                        } //end for k,l
                  }
               }//end if
         return moves_list;
      
      }
   
      private void forceCaptures(int turn, int[] move, Vector moves_list,int inc){
         int newx = move[2], newy = move[3], opponent;
      
         while (newx>7 || newy>7){
            newx/=10;
            newy/=10;
         }//end while 
         for (int i=-2; i<=2; i+=4)
            for (int j=-2; j<=2; j+=4)
               if (inRange(newx+i,newy+j)) {
                  Board temp_board = new Board(getBoard());
                  int moveResult = temp_board.ApplyMove(newx,newy,newx+i,newy+j);
                  if (moveResult == LEGALMOVE) {
                     int[] new_move = new int[4];
                     new_move[0] = move[0];
                     new_move[1] = move[1];
                     new_move[2] = move[2]+(newx+i)*inc;
                     new_move[3] = move[3]+(newy+j)*inc;
                     moves_list.addElement(new_move);
                  } // if (moveResult == LEGALMOVE)
                  else if (moveResult == INCOMPLETEMOVE)
                  {
                     int[] new_move = new int[4];
                     new_move[0] = move[0];
                     new_move[1] = move[1];
                     new_move[2] = move[2]+(newx+i)*inc;
                     new_move[3] = move[3]+(newy+j)*inc;
                  
                     temp_board.forceCaptures(turn, new_move, moves_list, inc*10);
                  }
               } //end if for
      
      }
   
      private boolean isEmpty(int i, int j)
      {
         if (i>-1 && i<8 && j>-1 && j<8)
            if (position[i][j] == EMPTY)
               return true;
         return false;
      }
   
      public int redPieces()
      {
         int count = 0;
         for (int i=0; i<8; i++)
         {
            for (int j=0; j<8; j++)
            {
               if ((position[i][j] == RED) || (position[i][j] == RKING))
                  count++;
            }
         }
         return count;
      }
   
      public int blackPieces()
      {
         int count = 0;
         for (int i=0; i<8; i++)
         {
            for (int j=0; j<8; j++)
            {
               if ((position[i][j] == BLACK) || (position[i][j] == BKING))
                  count++;
            }
         }
         return count;
      }
   
      public int redKings()
      {
         int count=0;
         for (int i=0;i<8; i++)
         {
            for (int j=0; j<8; j++)
            {
               if (position[i][j] == RKING)
                  count++;
            }
         }
         return count;
      }
   
      public int blackKings()
      {
         int count=0;
         for (int i=0; i<8; i++)
         {
            for (int j=0; j<8; j++)
            {
               if (position[i][j] == BKING)
                  count++;
            }
         }
         return count;
      }
   
      public int redHome()
      {
         int count=0;
         for (int i=0; i<8; i++)
         {
            if((position[i][0] == RED) || (position[i][0] == RKING))
               count++;
         }
         return count;
      }
   
      public int blackHome()
      {
         int count=0;
         for (int i=0; i<8; i++)
         {
            if((position[7][i] == BLACK) || (position[7][i] == BKING))
               count++;
         }
         return count;
      }
   }
