/**
 * Created by CptAmerica on 6/14/15.
 */
public class OneBitFullAdder {

    public static void main(String[] args){

        //Create Exclusive or Logic Block
        String[] xorIN = {"A","B"};
        LogicBoard xOR = new LogicBoard(xorIN,"XOR");
        xOR.addGate(LogicBoard.NOT,"B'","B",null, 0, 0);
        xOR.addGate(LogicBoard.AND,"AB'","A","B'",0, 0);

        xOR.printGates();

        xOR.addGate(LogicBoard.NOT,"A'","A",null, 0, 0);
        xOR.addGate(LogicBoard.AND,"A'B","A'","B", 0, 0);

        xOR.addGate(LogicBoard.OR,"AB'+A'B","A'B","AB'", 0, 0);

        System.out.println(xOR.generateTruthTable());



    }

}
