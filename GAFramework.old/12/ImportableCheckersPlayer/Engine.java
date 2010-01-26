// Updated By: Ryan Dixon 12 April 2002
// -> Included user interface for importing different
//    checker player personalities
// -> ***Very un-java-like due to 'static' declarations
//    in previous code

import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.JTree.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

class Engine 
{

final static int INFINITY = Integer.MAX_VALUE;
final static int CHECKER = 100; //one checker worth 100  
final static int POS=1;  //one position along the -j worth 1
final static int KING=200; //a king's worth
final static int EDGE=10; // effect of king being on the edge
final static int RANDOM_WEIGHT=10; // weight factor
static int[] values = {10,10,10,-10,-10,-10};
static CheckersEvaluator brain;
static String fileName;
static boolean fileSelectionMade   = false;
static JLabel fitnessLabel 	   = new JLabel("< no selection >");
static JTree  playerSelectionTree;


static void initializeCheckersEvaluator()
{
    // Allow User To Select a checkers player to play against
    // Use a simple JTree interface to obtain selection
        
    brain = getUserSelectedPlayer();

    if ( brain == null )
        System.exit(0);
        
    System.out.println("Evaluator changed to: " + brain);
}
  
    static CheckersEvaluator getUserSelectedPlayer()
    {
        File f = new File(".");
        String loadDirectory = f.getAbsolutePath();
        
        JFileChooser chooser = new JFileChooser(loadDirectory);
        chooser.setDialogTitle("Select A Checkers Player File");
        chooser.setMultiSelectionEnabled(false);
        
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
        chooser.addChoosableFileFilter(new ExtensionFileFilter(".checkers"));
        
        
        int result = chooser.showOpenDialog(null);
        File selectedFile = chooser.getSelectedFile();
        
        if (selectedFile != null)
        {
            return displayPlayerData(selectedFile.getPath());
        }
        else
        {
            System.out.println("No Player File Selected!  Execution halted!");
            System.exit(0);
        }
        
        // Should never be reached!  Added for compatibility
        return null;
    }
    
    static private CheckersEvaluator displayPlayerData(String file)
    {
        Vector checkerIndividuals = new Vector();
        
        try{
            
            FileInputStream fileStream = new FileInputStream(file); 
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);

            while( fileStream.available() > 1 )
            {
                checkerIndividuals.addElement( objectStream.readObject() );
            }
            
            sort(checkerIndividuals);
            
            objectStream.close();
            fileStream.close();
            
        } catch(IOException e2) {System.err.println(e2);
                                System.exit(0);}
          catch(Exception evt) {System.err.println(evt);
                                System.exit(0);}
        
        new CheckersPlayerSelector(checkerIndividuals);
        return CheckersPlayerSelector.getValue();
    }
    
    static private class CheckersPlayerSelector
    {
        // Creates a JTree interface for selecting one player to compete against!
        JDialog 			playerSelectionDialog;
        DefaultMutableTreeNode  	node;
        JLabel				fitnessTitleLabel;
        JButton				playButton;
        TreeSelectionModel      	singleSelectionModel;
        static CheckersEvaluator	evaluator;
        
        public CheckersPlayerSelector(Vector players)
        {
            playerSelectionDialog = new JDialog(new Frame(), "Select the Desired Checkers Player", true);
            node 		  = new DynamicUtilTreeNode( "Checkers Players", players );
            fitnessTitleLabel     = new JLabel("Player Rating: ");
            playButton	     	  = new JButton("Play");
            singleSelectionModel  = new DefaultTreeSelectionModel();
            singleSelectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            
            playButton.setEnabled(true);
            playButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e)
                {
                    DefaultMutableTreeNode selection = 
                        (DefaultMutableTreeNode) playerSelectionTree.getLastSelectedPathComponent();
                    evaluator =
                        (CheckersEvaluator) ((IndividualHolder)selection.getUserObject()).individual();
                    
                    playerSelectionDialog.dispose();
                }
            });
                
            playerSelectionTree = new JTree(node);
            playerSelectionTree.setSelectionModel(singleSelectionModel);
            playerSelectionTree.setShowsRootHandles(true);
            playerSelectionTree.setRootVisible(false);
            playerSelectionTree.putClientProperty("JTree.lineStyle", "Angled");
            playerSelectionTree.addTreeSelectionListener( new TreeSelectionListener(){
                public void valueChanged (TreeSelectionEvent event)
                {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
                                event.getNewLeadSelectionPath().getLastPathComponent();
                                
                    IndividualHolder holder = (IndividualHolder) selectedNode.getUserObject();

                    fitnessLabel.setText( Double.toString(holder.getFitness()) );
                }
            });
    
            JScrollPane srcollPane = new JScrollPane(playerSelectionTree);
            
            // Configure/Display Dialog layout
            playerSelectionDialog.setSize(600,400);
            //playerSelectionDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            
            playerSelectionDialog.getContentPane().setLayout( new GridBagLayout() );
            GridBagConstraints c = new GridBagConstraints();
            c.gridx 	= 0;
            c.gridy 	= GridBagConstraints.RELATIVE;
            c.gridwidth	= GridBagConstraints.REMAINDER;
            c.gridheight	= 1;
            c.weightx	= 1.0;
            c.weighty	= 1.0;
            c.fill		= GridBagConstraints.BOTH;
            c.anchor	= GridBagConstraints.NORTHWEST;
            c.insets	= new Insets(0,1,0,1);
            playerSelectionDialog.getContentPane().add(srcollPane, c);
            
            c.gridwidth = 1;
            c.weighty = 0.0;
            c.weightx = 0.0;
            c.fill    = GridBagConstraints.HORIZONTAL;
            playerSelectionDialog.getContentPane().add(fitnessTitleLabel, c);
            
            c.gridx   = 1;
            c.gridy   = 1;
            c.weightx = 1.0;
            c.fill    = GridBagConstraints.HORIZONTAL;
            playerSelectionDialog.getContentPane().add(fitnessLabel, c);
            
            c.gridwidth	= GridBagConstraints.REMAINDER;
            c.gridx   = 0;
            c.gridy   = 2;
            c.fill    = GridBagConstraints.HORIZONTAL;
            playerSelectionDialog.getContentPane().add(playButton, c);
            
            playerSelectionDialog.setVisible(true);
        }
        
        static public CheckersEvaluator getValue()
        {
            if ( evaluator != null )
                return evaluator;
            
            // Evaluator has not been initialized!
            return null;
        }
    }
    
    // JTree Action Listener
    static private class SingleTreeSelectionListener implements TreeSelectionListener
    {   
        public SingleTreeSelectionListener()
        {
            super();
        }
        
        public void valueChanged (TreeSelectionEvent event)
        {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
                        event.getNewLeadSelectionPath().getLastPathComponent();
                        
            IndividualHolder holder = (IndividualHolder) selectedNode.getUserObject();
            
            fitnessLabel.setText( Double.toString(holder.getFitness()) );
        }
    }
        
        
    // Destructive sort that rearranges the Vector that is passed in
    static private void sort(Vector population)
    {
        IndividualHolder best;
        IndividualHolder temp;
        int spot=0;
        
        for(int out=0; out<population.size(); out++)
        {
            best = (IndividualHolder) population.elementAt(out);
            spot = out;
            for(int in=out; in<population.size(); in++)
            {
                if(((IndividualHolder) population.elementAt(in)).getFitness() > best.getFitness())
                {
                    best=(IndividualHolder) population.elementAt(in);
                    spot=in;
                }
            }
            temp= (IndividualHolder) population.elementAt(out);
            population.removeElement (best);
            population.insertElementAt(best, out); 
        }
        
    }
    
  static int opponent(int turn){
	return turn==Checkers.BLACK ? Checkers.WHITE : Checkers.BLACK;
  }

  static int which_turn(int turn) {
    return Move.color(turn)==Checkers.BLACK ? -INFINITY : INFINITY;
  }
  
  public static int MiniMax(int[][] board, int depth, int max_depth, int[] the_move, int toMove, int[] counter)
  {
	  return MiniMax(board,depth,max_depth,the_move,toMove,counter,INFINITY,-INFINITY);
  }
  
  static int MiniMax(int[][] board, int depth, int max_depth,int[] the_move, int turn, int[] counter, int white_best, int black_best)
  {
	int the_score=0;
    int[][] new_board=new int[8][8];
    int best_score, chosen_score;
    int[] best_move=new int[4];
	
	Vector moves_list = new Vector();  //vector of 4x1 arrays
    
	Thread.yield();

	//assumes that depth is never equal to max_depth to begin with since
    //chosen_move is not set here
    if (depth==max_depth) 
	{
    	best_score = brain.evaluate(new Board(board));
		counter[0]++;
	}
    else {
		moves_list = Move.generate_moves(board,turn);
		best_score=which_turn(turn);
		switch (moves_list.size())
		{
		case 0:
			counter[0]++;
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
		new_board = copy_board(board);  //board need not be touched
        Move.move_board(new_board, (int[])moves_list.elementAt(i)); //returns new_board
		int temp[] = new int[4];
		the_score= MiniMax(new_board, depth+1, max_depth, temp, opponent(turn), counter, white_best, black_best);
	
		if (turn==Checkers.BLACK && the_score > best_score) {
			best_move = (int[])moves_list.elementAt(i);
			best_score = the_score;
			if (best_score > black_best) 
			{
				if (best_score >= white_best) 
					break;  /*  alpha_beta cutoff  */
				else
					black_best = best_score;  //the_score
			}
		}
	
		else if (turn==Checkers.WHITE && the_score < best_score) {
			best_move = (int[])moves_list.elementAt(i);
			best_score = the_score;
			if (best_score < white_best) 
			{
				if (best_score <= black_best) 
					break;  /*  alpha_beta cutoff  */
				else
				 	white_best = best_score;  //the_score
			}
		}
	
    } //end for

	}//end else
    for (int k=0; k<4; k++)
    	the_move[k]=best_move[k];
    return best_score;
  } //end minimax
  
  static int[][] copy_board(int[][] board){
    int[][] copy = new int[8][8];

    for (int i=0; i<8; i++)
      System.arraycopy(board[i],0,copy[i],0,8);
    return copy;
  }//end copy_board
  
  static boolean better(int the_score, int best, int turn){
    if (turn==Checkers.BLACK )
		return the_score>best;
	return the_score<best;
  }//end better           
}//end class engine

