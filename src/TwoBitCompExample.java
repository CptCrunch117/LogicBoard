import logicboard.LogicBoard;
import logicboard.boardbuilder.BoardTreeBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by CptAmerica on 7/6/15.
 */
public class TwoBitCompExample {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BoardTreeBuilder builder = new BoardTreeBuilder();
        //Build main logic
        String[] inp = {"A", "B", "C", "D"};
        BoardTreeBuilder build = new BoardTreeBuilder();
        LogicBoard jonnyHWK = build.buildTree("( ( A * ( C ' ) ) + ( ( B * ( C ' ) ) * ( D ' ) ) ) + ( ( A * B ) * ( D ' ) )", inp, "NAND");
        jonnyHWK = builder.buildTree("( ( ( A ' ) * C ) + ( ( ( A ' ) * ( B ' ) ) * D ) ) + ( ( ( B ' ) * C ) * D )", jonnyHWK);
        //-----------------------------------------------------------

        //Grab an XOR gate
        FileInputStream fis = new FileInputStream("XOR.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        LogicBoard xorGate = (LogicBoard) ois.readObject();
        //------------------------------------------------------

        //Add XOR gate
        ArrayList<String> xorInputs = new ArrayList<String>();
        xorInputs.add(jonnyHWK.getOpenOutputs().get(0).getGateID());
        xorInputs.add(jonnyHWK.getOpenOutputs().get(1).getGateID());
        jonnyHWK.addGate(xorGate, "XOR1", xorInputs);
        //-----------------------------------------------------------
        //Negate XOR logic
        jonnyHWK.addGate(LogicBoard.NOT, "XNOR", "XOR_AB'+A'B", null);
        //-----------------------------------------------------------


        //Print out stuffs
        System.out.println(jonnyHWK.generateTruthTable());
        jonnyHWK.printGates();
    }
}
