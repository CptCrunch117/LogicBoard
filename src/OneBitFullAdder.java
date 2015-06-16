/**
 * Created by CptAmerica on 6/14/15.
 */
public class OneBitFullAdder {

    public static void main(String[] args){

        //Create Exclusive or Logic Block
       /* String[] xorIN = {"A","B"};
        LogicBoard xOR = new LogicBoard(xorIN,"XOR");
        xOR.addGate(LogicBoard.NOT,"B'","B",null, 0, 0);
        xOR.addGate(LogicBoard.AND,"AB'","A","B'",0, 0);

        xOR.printGates();

        xOR.addGate(LogicBoard.NOT,"A'","A",null, 0, 0);
        xOR.addGate(LogicBoard.AND,"A'B","A'","B", 0, 0);

        xOR.addGate(LogicBoard.OR,"AB'+A'B","A'B","AB'", 0, 0);

        System.out.println(xOR.generateTruthTable());
        */

        String[] inputs = {"A","B","Cin"};
        LogicBoard fullAdder = new LogicBoard(inputs, "OneBitAdder");

        //XOR1
        fullAdder.addGate(LogicBoard.NOT,"B'","B",null, 0, 0);
        fullAdder.addGate(LogicBoard.AND, "AB'", "A", "B'", 0, 0);


        fullAdder.addGate(LogicBoard.NOT,"A'","A",null, 0, 0);
        fullAdder.addGate(LogicBoard.AND,"A'B","A'","B", 0, 0);

        fullAdder.addGate(LogicBoard.OR,"AB'+A'B","A'B","AB'", 0, 0);
        //-----------------------------------------------------------

        //AND1
        fullAdder.addGate(LogicBoard.AND,"AB","A","B",0, 0);
        //AND2
        fullAdder.addGate(LogicBoard.AND,"(AB'+A'B)C","AB'+A'B","Cin",0, 0);


        //XOR2
        fullAdder.addGate(LogicBoard.NOT,"C'","Cin",null, 0, 0);
        fullAdder.addGate(LogicBoard.AND, "(AB'+A'B)C'", "AB'+A'B", "C'", 0, 0);


        fullAdder.addGate(LogicBoard.NOT,"(AB'+A'B)'","AB'+A'B",null, 0, 0);
        fullAdder.addGate(LogicBoard.AND,"((AB'+A'B)')C","(AB'+A'B)'","Cin", 0, 0);

        //(((AB'+A'B)')C)+((AB'+A'B)C)
        fullAdder.addGate(LogicBoard.OR,"Sum","((AB'+A'B)')C","(AB'+A'B)C'", 0, 0);
        //-----------------------------------------------------------
        //OR1
        fullAdder.addGate(LogicBoard.OR, "Cout", "AB", "(AB'+A'B)C", 0, 0);

        System.out.println("-----One bit Full adder-----");
        System.out.println(fullAdder.generateTruthTable());

    }

}
