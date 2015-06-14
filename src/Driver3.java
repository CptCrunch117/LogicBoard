
import java.util.Scanner;

/**
 * Created by Kyle Ferguson on 6/3/15.
 */
public class Driver3 {


    public static void main(String[] args){

        //Mathematically:
        //LogicBoard is a set of gate elements.
        //A logicBoard set can also contain other LogicBoard sets.
        //This allows for logic blocking, where a LogicBoard subset
        //takes care of only a portion of the logic of the superset.


        //A logicBoard System is a system of logic gates that all share a commonality
        //that commonality is a System's inputs. These inputs are the center of a system.
        //It tells the program how many possible inputs can be made, they also determine the system's output as well as
        //their values are whats changed by the generationTable or user.


        //Next Addon function (aka not yet implemented):
        //Once an output of a gate in the system is defined as the System's output, you can plug that into another system.
        //Note: Binary switch will be made but its output will be based on the output of the system's output that's connected
        //to it.


        //Initializing the board:
        //Set number of inputs on logic gate
        //To create a logic "System" create a list the Systems input's names
        //names for that "System". Then pass in the list!
        String[] names = {"A", "B","C", "D", "E", "F"};
        LogicBoard board = new LogicBoard(names);
        //------------------------------------------------------------------

        //Adding gates to the board:
        board.addGate(LogicBoard.NOT, "A'", "A", null);
        board.addGate(LogicBoard.OR, "A'+B", "A'","B" );
        board.addGate(LogicBoard.AND, "CD","C", "D");
        board.addGate(LogicBoard.OR, "E+F", "E","F");
        board.addGate(LogicBoard.OR,"(A'+B)+CD","A'+B","CD");
        board.addGate(LogicBoard.OR,"((A'+B)+CD)+E+F","(A'+B)+CD","E+F");
        board.addGate(LogicBoard.NOT,"(((A'+B)+CD)+E+F)'","((A'+B)+CD)+E+F",null);
        //------------------------------------------------------------------------

        //Generating the truth table of a LogicBoard System:
        System.out.println(board.generateTruthTable());
        //---------------------------------------------

        //Printing Gate relations in a LogicBoard System (a basic version for console aka not fancy):
        board.printGates();
        //-----------------



        //Removing gates
        //--------------


        //Remove and Replace
        //------------------


        //Hot-Swap (swap and gate with or gate, or vice versa)
        //--------


    }
}
