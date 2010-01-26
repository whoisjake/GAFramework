------------ Genetic Algorithm Framework ------------
--------------------GAApplication--------------------

Authors: (in alphabetical order)
Pat Burke
Ryan Dixon
Jake Good
Jayapal Prabakaran

Date: May 06, 2002
----------------------------------------------------

Structural Format:
(1) File Structure
(2) Installation Procedures
(3) A First Test / General Startup procedures


----------------------------------------------------
1.  FILE STRUCTURE:
----------------------------------------------------

   This CD contains all of the software tools necessary to start creating and evolving objects via a genetic algorithm.  The full source code accompanied by a .jar file contaiing the GAFramework code has been included.  The following files and directories will appear in the root folder.

----------------------------------------------------

index.html:
   A link to javadoc documentation contained on this CD.  This link gives programmers the abiliity to easily view all class contructors and methods required to interact with the supplied framework.


reference (directory):
  A directory containing all of the javadoc resources and .html files.

----------------------------------------------------

GAFramework.jar:
   A jar file containing all of the compiled classes pertaining to GAApplication and the associated GAFramework.


edu (directory):
   The expanded GAFramework.jar file.


GAFramework Source (director):
   The full, uncompiled and documented source code for the GAFramework package.

----------------------------------------------------

Example Code:
   Uncompiled source code containing examples implementations.
   The following directories/examples are included.  Each directory corresponds to one example.

   SixteenBitExample (directory):
      An simple example that attempts to converge on a series of all 16 bits becoming 1.

   TicTacToe (directory):
      A text-based implementation of tic-tac-toe accompanied by a fitness and individual class capable of learning (at least) semi-optimal solutions to the classic game.

   Checkers (directory):
      ((C)opyright 1997 Victor Huang and Sung Ha Huh. All rights reserved.) 
      A fully graphical checkers implementation with a CheckersEvaluator (fitness) class capable of learning new CheckersPlayer objects.  This example uses 16 board attributes in it's analysis of static board positions.

----------------------------------------------------

Documentation:
   An electronic copy of all of the supplied system documentation in pdf format.



----------------------------------------------------
2.  Installation Procedures
----------------------------------------------------

   All that is required for installation is simply to copy the GAFramework.jar file and edu directory to the location in which new source code with be importing the GAFramework package.  Alternatively, the GAFramework.jar file and edu directory can be placed in one desiarble location and the system's PATH variable can be set accordingly.  To ensure proper installation, compile one of the provided examples and test GAApplication with the newly created classes.  (See Section 3)



----------------------------------------------------
3.  A First Test / General Startup procedures
----------------------------------------------------

   Provided the GAFramework package has been installed properly, a test can be performed by copying any one of the example directory files contents to the the same location as the GAFramework.jar file and edu directory.  Compile the example files using the command

   javac *.java

After compilation is complete, start GAApplication by using the command
   java edu.uni.GAFramework.GAApplication  

If the installation was successful, you should be prompted to select the appopriate fitness and individual classes.  (Refer to documentation for step by step application-specific features).


In the case of failure, repeat step 2.  If unsuccessful after the second attempt, consult the supplied documentation for a listing of potential error messages.



----------------------------------------------------
Enjoy!
----------------------------------------------------

Any questions or concerns may be directed to:

Ryan Dixon   ryan@uni.edu
----------------------------------------------------