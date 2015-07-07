import logicboard.LogicBoard;

import java.util.ArrayList;

/**
 * Created by CptAmerica on 6/14/15.
 */
public class OneBitFullAdder {

    public static void main(String[] args){

        //Create Exclusive or Logic Block
        String[] xorIN = {"A","B"};
        LogicBoard xOR = new LogicBoard(xorIN,"XOR");
        xOR.addGate(LogicBoard.NOT,"B'","B",null);
        xOR.addGate(LogicBoard.AND,"AB'","A","B'");

        xOR.printGates();

        xOR.addGate(LogicBoard.NOT,"A'","A",null);
        xOR.addGate(LogicBoard.AND,"A'B","A'","B");

        xOR.addGate(LogicBoard.OR,"AB'+A'B","A'B","AB'");


        //System.out.println(xOR.generateTruthTable());


        String[] inputs = {"A","B","Cin"};
        LogicBoard fullAdder = new LogicBoard(inputs, "OneBitAdder");

        //XOR1
        fullAdder.addGate(LogicBoard.NOT,"B'","B",null);
        fullAdder.addGate(LogicBoard.AND, "AB'", "A", "B'");


        fullAdder.addGate(LogicBoard.NOT,"A'","A",null);
        fullAdder.addGate(LogicBoard.AND,"A'B","A'","B");

        fullAdder.addGate(LogicBoard.OR,"AB'+A'B","A'B","AB'");
        //-----------------------------------------------------------

        //AND1
        fullAdder.addGate(LogicBoard.AND,"AB","A","B");
        //AND2
        fullAdder.addGate(LogicBoard.AND,"(AB'+A'B)C","AB'+A'B","Cin");


        //XOR2
        fullAdder.addGate(LogicBoard.NOT,"C'","Cin",null);
        fullAdder.addGate(LogicBoard.AND, "(AB'+A'B)C'", "AB'+A'B", "C'");


        fullAdder.addGate(LogicBoard.NOT,"(AB'+A'B)'","AB'+A'B",null);
        fullAdder.addGate(LogicBoard.AND,"((AB'+A'B)')C","(AB'+A'B)'","Cin");

        //(((AB'+A'B)')C)+((AB'+A'B)C)
        fullAdder.addGate(LogicBoard.OR,"Sum","((AB'+A'B)')C","(AB'+A'B)C'");
        //-----------------------------------------------------------
        //OR1
        fullAdder.addGate(LogicBoard.OR, "Cout", "AB", "(AB'+A'B)C");

        System.out.println("-----One bit Full adder-----");
        System.out.println(fullAdder.generateTruthTable());


       ArrayList<String> out = new ArrayList<String>();
       out.add("Sum");
       out.add("Cout");
       fullAdder.setSystemOutputs(out);
       //Gate fullbitAdd = fullAdder.convertBoard();

      String[] binSwitch = {"A","B","C", "D"};
      LogicBoard board = new LogicBoard(binSwitch, "tester");

        String[] in = {"A","B","C"};
        board.addGate(fullAdder, "Adder",in);
        board.updateLogicBoard();
        System.out.println(board.generateTruthTable());


    }

}
