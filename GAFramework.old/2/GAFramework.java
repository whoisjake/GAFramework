//
// GAFramework.java
//
// Author: Ryan Dixon
//
// Comments:
//

import java.awt.*;
//import java.awt.event.*;
import GAEvolver;
import FormulaLearner;

public class GAFramework
{
	private GAEvolver  evolve;
	private GAStrategy strategy;

	// Public Constructors
	public GAFramework( GAEvolver evolve )
	{
		super();
		
		this.evolve = evolve;

		if ( evolve.hasStrategy() )
			strategy = evolve.getPreferredStrategy();
// // // 		evolve.generatePopulation( 500 );
	}

// // // 	public GAFramework( GAEvolver evolve, int initPop, boolean mutation,
// // // 						boolean direct, boolean reproduction )
// // // 	{
// // // 		super();
// // // 
// // // 		initialPopulation   = initPop;
// // // 		prefersMutation     = mutation;
// // // 		prefersDirect       = direct;
// // //       prefersReproduction = reproduction;
// // // 
// // // 		this.evolve = evolve;
// // // 		evolve.generatePopulation( initialPopulation );
// // // 	}	

	public static void main( String args[] )
	{
		// This could potentially allow for command line control over
		// this GA Framework's reproduction tendencies
		// *** NOT YET IMPLEMENTED
		GAFramework myGAFramework;
		
		myGAFramework = new GAFramework( new FormulaLearner( 20 ) ); // Statically typed for now

		myGAFramework.run();
	}

	public void run()
	{
		// Start test series here.
		// If a Strategy has not been supplied, the default (inner-class)
		// strategy will be used for this process.

		// Fitness eval, etc, should be considered within this process.
		// This feature can be used for multi-threading.

		System.out.println( "run() envoked" );

		if ( evolve == null )
			System.exit(0);

		evolve.run();
	}
}

