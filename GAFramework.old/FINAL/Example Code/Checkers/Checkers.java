//***********************************************
// file:   Checkers.java
// author: Jake Good
// date:   March 30, 2002
// notes:  create object with one or two players
//         default player will be 
//***********************************************


   public class Checkers
   {
   
      CheckersPlayer red;		// red player
      CheckersPlayer black;	// black player
      Board checkersBoard;		// checkers board
      int gameTime;
      int turn;
   
      public Checkers(CheckersPlayer one)
      {
         red = one;
         black = new CheckersPlayer(new DefaultEvaluator());
         checkersBoard = new Board();
      }
   
      public Checkers(CheckersPlayer one, CheckersPlayer two)
      {
         red = one;
         black = two;
         checkersBoard = new Board();
      }
   	public Board getBoard() { return checkersBoard;}
   
      public int playGame()
      {
         int winner = Board.EMPTY;
      
         red.setColor(Board.RED);
         black.setColor(Board.BLACK);
      
         turn = Board.BLACK;
      
         checkersBoard.reset();
         checkersBoard.print();
         gameTime = 0;
         int numPieces = 24;
         while( winner == Board.EMPTY )
         {
            int currentPieces = checkersBoard.blackPieces()+checkersBoard.redPieces();
            int[]move;
            int legal;
         
         	// tests time for draw
            if (currentPieces == numPieces) {
               gameTime++;
               if (gameTime > 20)
                  return Board.EMPTY;
            }
            else {
               numPieces = currentPieces;
            }
         
         
            if( (checkersBoard.blackPieces() > 0) && (checkersBoard.redPieces() == 0))
            {
               winner = Board.BLACK;
               System.out.println("GAME STOPPED NO PIECES");
               return winner;
            }
            move = black.nextMove(checkersBoard);
            //System.out.println(move[0]+" "+move[1]+" "+move[2]+" "+move[3]);
            if((move[0]==0) && (move[1]==0) || (move[2] ==0) && (move[3]==0))
            {
               System.out.println("GAME STOPPED NULL MOVE");
               return Board.RED;
            }
         /*legal = checkersBoard.ApplyMove(move[0],move[1],move[2],move[3]);
         if(legal == Board.ILLEGALMOVE)
         {
         	System.out.println("GAME STOPPED ILLEGAL MOVE");
         	return Board.RED;
         }
         */
            checkersBoard.move_board(move);
            checkersBoard.print();
            if( (checkersBoard.redPieces() > 0) && (checkersBoard.blackPieces() == 0))
            {
               System.out.println("GAME STOPPED NO PIECES");
               winner = Board.RED;
               return winner;
            }
            move = red.nextMove(checkersBoard);
            //System.out.println(move[0]+" "+move[1]+" "+move[2]+" "+move[3]);
            if((move[0]==0) && (move[1]==0) || (move[2]==0) && (move[3]==0))
            {
               System.out.println("GAME STOPPED NULL MOVE");
               return Board.BLACK;
            }
         /*
         legal = checkersBoard.ApplyMove(move[0],move[1],move[2],move[3]);
         if(legal == Board.ILLEGALMOVE)
         {
         	System.out.println("GAME STOPPED ILLEGAL MOVE");
         	return Board.BLACK;
         }
         */
            checkersBoard.move_board(move);
            checkersBoard.print();
         }
      
         return winner;			
      }
   
   }
